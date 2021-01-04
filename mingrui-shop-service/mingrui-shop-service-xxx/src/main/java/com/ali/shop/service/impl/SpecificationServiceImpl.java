package com.ali.shop.service.impl;

import com.ali.shop.base.BaseApiService;
import com.ali.shop.base.Result;
import com.ali.shop.dto.SpecGroupDTO;
import com.ali.shop.dto.SpecParamDTO;
import com.ali.shop.entity.SpecGroupEntity;
import com.ali.shop.entity.SpecParamEntity;
import com.ali.shop.mapper.SpecGroupMapper;
import com.ali.shop.mapper.SpecParamMapper;
import com.ali.shop.service.SpecificationService;
import com.ali.shop.utils.ALiBeanUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName SpecificationServiceImpl
 * @Description: TODO
 * @Author wangyue
 * @Date 2021/1/4
 * @Version V1.0
 **/
@RestController
public class SpecificationServiceImpl extends BaseApiService implements SpecificationService {

    @Resource
    private SpecGroupMapper specGroupMapper;

    @Resource
    private SpecParamMapper specParamMapper;

    @Transactional
    @Override
    public Result<List<SpecGroupEntity>> getSepcGroupInfo(SpecGroupDTO specGroupDTO) {
        Example example = new Example(SpecGroupEntity.class);
        example.createCriteria().andEqualTo("cid", ALiBeanUtil.copyProperties(specGroupDTO,SpecGroupEntity.class).getCid());

        List<SpecGroupEntity> specGroupEntities = specGroupMapper.selectByExample(example);
        return this.setResultSuccess(specGroupEntities);
    }

    @Transactional
    @Override
    public Result<JSONObject> saveSpecGroup(SpecGroupDTO specGroupDTO) {
        specGroupMapper.insertSelective(ALiBeanUtil.copyProperties(specGroupDTO,SpecGroupEntity.class));
        return this.setResultSuccess();
    }

    @Transactional
    @Override
    public Result<JSONObject> editSpecGroup(SpecGroupDTO specGroupDTO) {
        specGroupMapper.updateByPrimaryKeySelective(ALiBeanUtil.copyProperties(specGroupDTO,SpecGroupEntity.class));
        return this.setResultSuccess();
    }

    @Transactional
    @Override
    public Result<JSONObject> deleteSpecGroupById(Integer id) {
        //删除规格组之前需要先判断一下当前规格组下是否有规格参数 true不能被删除
        //将groupId传过来
        Example example = new Example(SpecParamEntity.class);
        //根据groupId查询,如果返回集合大于0,就不能删除
        example.createCriteria().andEqualTo("groupId",id);
        List<SpecParamEntity>specParamEntities=specParamMapper.selectByExample(example);
        if (specParamEntities.size()>0){
            return this.setResultError("该规格组有数据无法删除");
        }
        specGroupMapper.deleteByPrimaryKey(id);
        return this.setResultSuccess();
    }

    @Override
    @Transactional
    public Result<List<SpecParamEntity>> getSpecParamInfo(SpecParamDTO specParamDTO) {
        SpecParamEntity specParamEntity = ALiBeanUtil.copyProperties(specParamDTO, SpecParamEntity.class);
        Example example = new Example(SpecParamEntity.class);
        example.createCriteria().andEqualTo("groupId",specParamEntity.getGroupId());

        List<SpecParamEntity> specParamEntities = specParamMapper.selectByExample(example);

        return this.setResultSuccess(specParamEntities);
    }

    @Transactional
    @Override
    public Result<JSONObject> saveSpecParam(SpecParamDTO specParamDTO) {
        specParamMapper.insertSelective(ALiBeanUtil.copyProperties(specParamDTO,SpecParamEntity.class));

        return this.setResultSuccess();
    }

    @Transactional
    @Override
    public Result<JSONObject> editSpecParam(SpecParamDTO specParamDTO) {
        specParamMapper.updateByPrimaryKeySelective(ALiBeanUtil.copyProperties(specParamDTO,SpecParamEntity.class));
        return this.setResultSuccess();
    }

    @Transactional
    @Override
    public Result<JSONObject> deleteSpecParam(Integer id) {
        specParamMapper.deleteByPrimaryKey(id);
        return this.setResultSuccess();
    }
}
