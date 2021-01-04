package com.ali.shop.service;

import com.ali.shop.base.Result;
import com.ali.shop.entity.CategoryEntity;
import com.ali.shop.validata.group.MingruiOperation;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(tags="商品分类接口")
public interface CategoryService {

    @ApiOperation(value="新增")
    @PostMapping(value="/category/add")
    Result<JsonObject>addCategoryById(@Validated({MingruiOperation.Add.class})@RequestBody CategoryEntity categoryEntity);

    @ApiOperation(value="更新")
    @PutMapping(value = "/category/edit")
    Result<JsonObject> editCategoryById(@Validated({MingruiOperation.Update.class})@RequestBody CategoryEntity categoryEntity);

    @ApiOperation(value="通过查询商品分类")
    @GetMapping(value="category/list")
    Result<List<CategoryEntity>> getCategoryByPid(Integer pid);

    @ApiOperation(value="通过品牌id查询分类信息")
    @GetMapping(value="category/brand")
    Result<List<CategoryEntity>> getCategoryByBrandId(Integer brandId);

    @ApiOperation(value="通过id删除分类")
    @DeleteMapping(value = "/category/delete")
    public Result<JsonObject> deleteCategoryById(Integer id);
}
