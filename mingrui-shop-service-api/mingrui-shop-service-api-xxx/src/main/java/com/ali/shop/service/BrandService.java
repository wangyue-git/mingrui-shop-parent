package com.ali.shop.service;

import com.ali.shop.base.Result;
import com.ali.shop.dto.BrandDTO;
import com.ali.shop.entity.BrandEntity;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.scripts.JS;
import org.springframework.web.bind.annotation.*;

@Api(tags = "品牌接口")
public interface BrandService {
    @GetMapping(value = "brand/getBrandInfo")
    @ApiOperation(value="查询品牌列表")
    Result<PageInfo<BrandEntity>>getBrandInfo(BrandDTO brandDTO);

    @PostMapping(value="brand/save")
    @ApiOperation(value="新增品牌")
    Result<JSONObject>saveBrandInfo(@RequestBody BrandDTO brandDTO);

    @PutMapping(value = "brand/save")
    @ApiOperation(value = "修改品牌")
    Result<JSONObject> editBrandInfo(@RequestBody BrandDTO brandDTO);

    @DeleteMapping(value = "brand/delete")
    @ApiOperation(value = "查询品牌列表")
    Result<JSONObject> deleteBrandInfo(Integer id);
}
