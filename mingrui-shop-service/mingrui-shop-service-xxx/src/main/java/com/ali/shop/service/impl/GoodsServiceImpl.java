package com.ali.shop.service.impl;

import com.ali.shop.base.BaseApiService;
import com.ali.shop.base.Result;
import com.ali.shop.dto.SkuDTO;
import com.ali.shop.dto.SpuDTO;
import com.ali.shop.dto.SpuDetailDTO;
import com.ali.shop.entity.*;
import com.ali.shop.mapper.*;
import com.ali.shop.service.GoodsService;
import com.ali.shop.status.HTTPStatus;
import com.ali.shop.utils.ALiBeanUtil;
import com.ali.shop.utils.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName GoodsServiceImpl
 * @Description: TODO
 * @Author wangyue
 * @Date 2021/1/5
 * @Version V1.0
 **/
@RestController
public class GoodsServiceImpl extends BaseApiService implements GoodsService {

    @Resource
    private SpuMapper spuMapper;

    @Resource
    private BrandMapper brandMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SpuDetailMapper spuDetailMapper;

    @Resource
    private SkuMapper skuMapper;

    @Resource
    private StockMapper stockMapper;

    //查询
    @Override
    public Result<List<SpuDTO>> getSpuInfo(SpuDTO spuDTO) {
        //分页
        if(ObjectUtil.isNotNull(spuDTO.getPage()) && ObjectUtil.isNotNull(spuDTO.getRows()))
            PageHelper.startPage(spuDTO.getPage(),spuDTO.getRows());
        //排序
        if(!StringUtils.isEmpty(spuDTO.getOrder()) && !StringUtils.isEmpty(spuDTO.getSort()))
            PageHelper.orderBy(spuDTO.getOrderBy());

        Example example = new Example(SpuEntity.class);
        Example.Criteria criteria = example.createCriteria();

        //判断是否上架
        if(ObjectUtil.isNotNull(spuDTO.getSaleable()) && spuDTO.getSaleable() < 2)
            criteria.andEqualTo("saleable",spuDTO.getSaleable());
        //查询搜索
        if(!StringUtils.isEmpty(spuDTO.getTitle()))
            criteria.andLike("title","%" + spuDTO.getTitle() + "%");
        //查询sql
        List<SpuEntity> spuEntities = spuMapper.selectByExample(example);

        //java1.8新特性
        List<SpuDTO> spuDTOList = spuEntities.stream().map(spuEntity -> {
            SpuDTO spuDTO1 = ALiBeanUtil.copyProperties(spuEntity, SpuDTO.class);
            //通过分类id集合查询数据
            List<CategoryEntity>categoryEntities = categoryMapper.selectByIdList(Arrays.asList(spuEntity.getCid1(),spuEntity.getCid2(),spuEntity.getCid3()));

            String categoryName = categoryEntities.stream().map(categoryEntity -> categoryEntity.getName()).collect(Collectors.joining("/"));
            spuDTO1.setCategoryName(categoryName);

            BrandEntity brandEntity = brandMapper.selectByPrimaryKey(spuEntity.getBrandId());
            spuDTO1.setBrandName(brandEntity.getName());
            return spuDTO1;

        }).collect(Collectors.toList());



        PageInfo<SpuEntity> spuEntityPageInfo = new PageInfo<>(spuEntities);

        return this.setResult(HTTPStatus.OK,spuEntityPageInfo.getTotal()+"",spuDTOList);
    }

    //新增
    @Override
    @Transactional
    public Result<JSONObject> saveGoods(SpuDTO spuDTO) {
        final Date date = new Date();
        //新增spu,新增返回主键,给必要字段赋默认值
        SpuEntity spuEntity = ALiBeanUtil.copyProperties(spuDTO, SpuEntity.class);
        spuEntity.setSaleable(1);
        spuEntity.setValid(1);
        spuEntity.setCreateTime(date);
        spuEntity.setLastUpdateTime(date);
        spuMapper.insertSelective(spuEntity);

        //新增spuDetail
        SpuDetailDTO spuDetail = spuDTO.getSpuDetail();
        SpuDetailEntity spuDetailEntity = ALiBeanUtil.copyProperties(spuDetail, SpuDetailEntity.class);
        spuDetailEntity.setSpuId(spuEntity.getId());
        spuDetailMapper.insertSelective(spuDetailEntity);

        //调用封装,新增sku list插入顺序有序 b,a set a,b treeSet b,a
        this.saveSkusAndStockInfo(spuDTO,spuEntity.getId(),date);
        return this.setResultSuccess();
    }

    @Override
    public Result<SpuDetailEntity> getSpuDetailBySpuId(Integer spuId) {
        SpuDetailEntity spuDetailEntity = spuDetailMapper.selectByPrimaryKey(spuId);
        return this.setResultSuccess(spuDetailEntity);
    }

    @Override
    public Result<List<SkuDTO>> getSkusBySpuId(Integer spuId) {
        List<SkuDTO> list = skuMapper.getSkusAndStockBySpuId(spuId);
        return this.setResultSuccess(list);
    }



    //修改
    @Override
    @Transactional
    public Result<JSONObject> editGoods(SpuDTO spuDTO) {
        final Date date = new Date();
        //修改spu
        SpuEntity spuEntity = ALiBeanUtil.copyProperties(spuDTO,SpuEntity.class);
        spuEntity.setLastUpdateTime(date);
        spuMapper.updateByPrimaryKeySelective(spuEntity);

        //修改spuDetail
        spuDetailMapper.updateByPrimaryKeySelective(ALiBeanUtil.copyProperties(spuDTO.getSpuDetail(),SpuDetailEntity.class));

        //通过spuId查询sku信息,先删除后新增
        this.deleteSkusAndStock(spuEntity.getId());

        this.saveSkusAndStockInfo(spuDTO,spuEntity.getId(),date);

        return this.setResultSuccess();
    }

    //删除
    @Override
    @Transactional
    public Result<JSONObject> deleteGoods(Integer spuId) {

        //删除spu
        spuMapper.deleteByPrimaryKey(spuId);
        //删除spuDetail
        spuDetailMapper.deleteByPrimaryKey(spuId);
        //调用封装删除  删除sku和stock
        this.deleteSkusAndStock(spuId);

        return this.setResultSuccess();
    }

    @Override
    @Transactional
    public Result<JSONObject> updateStauts(SpuDTO spuDTO) {
        SpuEntity spuEntity = ALiBeanUtil.copyProperties(spuDTO,SpuEntity.class);
        if(ObjectUtil.isNotNull(spuEntity.getSaleable())&&spuEntity.getSaleable()<2){
            if(spuEntity.getSaleable()==1){
                spuEntity.setSaleable(0);
                spuMapper.updateByPrimaryKeySelective(spuEntity);
                return this.setResultSuccess("已下架");
            }
            if(spuEntity.getSaleable()==0){
                spuEntity.setSaleable(1);
                spuMapper.updateByPrimaryKeySelective(spuEntity);
                return this.setResultSuccess("已上架");
            }
        }
        return this.setResultError("下架失败");
    }


    //封装删除
    private void deleteSkusAndStock(Integer spuId){
        Example example = new Example(SkuEntity.class);
        example.createCriteria().andEqualTo("spuId",spuId);
        List<SkuEntity> skuEntities =skuMapper.selectByExample(example);
        //遍历得到sku集合
        List<Long> skuIdList =skuEntities.stream().map(skuEntity -> skuEntity.getId()).collect(Collectors.toList());
        //通过skuId集合删除信息
        skuMapper.deleteByIdList(skuIdList);
        //通过skuId集合删除stock信息
        stockMapper.deleteByIdList(skuIdList);

    }

    //封装新增
    private void saveSkusAndStockInfo(SpuDTO spuDTO,Integer spuId,Date date){
        List<SkuDTO> skus = spuDTO.getSkus();
        skus.stream().forEach(skuDTO -> {
            SkuEntity skuEntity = ALiBeanUtil.copyProperties(skuDTO, SkuEntity.class);
            skuEntity.setSpuId(spuId);
            skuEntity.setCreateTime(date);
            skuEntity.setLastUpdateTime(date);
            skuMapper.insertSelective(skuEntity);

            //新增stock
            StockEntity stockEntity = new StockEntity();
            stockEntity.setSkuId(skuEntity.getId());
            stockEntity.setStock(skuDTO.getStock());
            stockMapper.insertSelective(stockEntity);
        });
    }

}
