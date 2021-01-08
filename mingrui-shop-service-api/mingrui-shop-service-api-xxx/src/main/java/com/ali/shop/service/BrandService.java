package com.ali.shop.service;

import com.ali.shop.base.Result;
import com.ali.shop.dto.BrandDTO;
import com.ali.shop.entity.BrandEntity;
import com.ali.shop.validata.group.MingruiOperation;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.scripts.JS;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "品牌接口")
public interface BrandService {
    @GetMapping(value = "brand/getBrandInfo")
    @ApiOperation(value="查询品牌列表")
    Result<PageInfo<BrandEntity>>getBrandInfo(BrandDTO brandDTO);

    @PostMapping(value="brand/save")
    @ApiOperation(value="新增品牌")
    Result<JSONObject>saveBrandInfo(@Validated({MingruiOperation.Add.class})@RequestBody BrandDTO brandDTO);

    @PutMapping(value = "brand/save")
    @ApiOperation(value = "修改品牌")
    Result<JSONObject> editBrandInfo(@Validated({MingruiOperation.Update.class})@RequestBody BrandDTO brandDTO);

    @DeleteMapping(value = "brand/delete")
    @ApiOperation(value = "删除品牌")
    Result<JSONObject> deleteBrandInfo(Integer id);

    @GetMapping(value = "brand/getBrandInfoByCategoryId")
    @ApiOperation(value = "通过分类id查询品牌")
    Result<List<BrandEntity>> getBrandInfoByCategoryId(Integer cid);
}
