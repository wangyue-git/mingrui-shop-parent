package com.ali.shop.service.impl;

import com.ali.shop.base.BaseApiService;
import com.ali.shop.base.Result;
import com.ali.shop.entity.CategoryBrandEntity;
import com.ali.shop.entity.CategoryEntity;
import com.ali.shop.mapper.CategoryBrandMapper;
import com.ali.shop.mapper.CategoryMapper;
import com.ali.shop.service.CategoryService;
import com.ali.shop.utils.ObjectUtil;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.beans.Transient;
import java.util.List;

/**
 * @ClassName CategoryServiceImpl
 * @Description: TODO
 * @Author wangyue
 * @Date 2020/12/23
 * @Version V1.0
 **/
@RestController
public class CategoryServiceImpl extends BaseApiService implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private CategoryBrandMapper categoryBrandMapper;

    @Transactional
    @Override
    public Result<JsonObject> addCategoryById(CategoryEntity categoryEntity) {
        CategoryEntity parentCategoryEntity = new CategoryEntity();
        parentCategoryEntity.setId(categoryEntity.getParentId());
        parentCategoryEntity.setIsParent(1);
        categoryMapper.updateByPrimaryKeySelective(parentCategoryEntity);

        categoryMapper.insertSelective(categoryEntity);

        return this.setResultSuccess();
    }

    @Transactional
    @Override
    public Result<JsonObject> editCategoryById(CategoryEntity categoryEntity) {
        categoryMapper.updateByPrimaryKeySelective(categoryEntity);
        return this.setResultSuccess();
    }

    @Override
    public Result<List<CategoryEntity>> getCategoryByPid(Integer pid) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setParentId(pid);
        List<CategoryEntity> list = categoryMapper.select(categoryEntity);
        return this.setResultSuccess(list);
    }

    @Override
    public Result<List<CategoryEntity>> getCategoryByBrandId(Integer brandId) {
        List<CategoryEntity>list=categoryMapper.getCategoryByBrandId(brandId);
        return this.setResultSuccess(list);
    }


    @Transactional
    @Override
    public Result<JsonObject> deleteCategoryById(Integer id) {
        //校验id是否合法
        if (ObjectUtil.isNull(id) || id <= 0) return this.setResultError("id不合法");

        CategoryEntity categoryEntity = categoryMapper.selectByPrimaryKey(id);

        //判断id是否存在
        if(ObjectUtil.isNull(categoryEntity))return this.setResultError("数据不存在");

        //判断当前节点是否为父节点
        if(categoryEntity.getIsParent()==1)return this.setResultError("当前节点为父节点");

        Example example1 = new Example(CategoryBrandEntity.class);
        example1.createCriteria().andEqualTo("categoryId",id);
        List<CategoryBrandEntity> categoryBrandEntities = categoryBrandMapper.selectByExample(example1);
        if(categoryBrandEntities.size() != 0){
            return this.setResultError("该属性已被品牌绑定不能删除");
        }



        //通过当前节点的父节点id查询当前节点(将要被删除的节点)的父节点下是否还有其它子节点
        Example example = new Example(CategoryEntity.class);
        example.createCriteria().andEqualTo("parentId",categoryEntity.getParentId());
        List<CategoryEntity>categoryList=categoryMapper.selectByExample(example);

        //如果size<=1  -->如果当前节点被删除的话,当前节点的父节点下没有节点了-->将当前节点的父节点状态改为叶子节点
        if(categoryList.size()<=1){

            CategoryEntity updateCategoryEntity = new CategoryEntity();
            updateCategoryEntity.setIsParent(0);
            updateCategoryEntity.setId(categoryEntity.getParentId());

            categoryMapper.updateByPrimaryKeySelective(updateCategoryEntity);

        }
        //通过id删除节点
        categoryMapper.deleteByPrimaryKey(id);

        return this.setResultSuccess();

    }

}
