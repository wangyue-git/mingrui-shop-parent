package com.ali.shop.service;

import com.ali.shop.base.Result;
import com.ali.shop.dto.SkuDTO;
import com.ali.shop.dto.SpuDTO;
import com.ali.shop.entity.SpuDetailEntity;
import com.ali.shop.entity.SpuEntity;
import com.ali.shop.validata.group.MingruiOperation;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Api(tags = "商品接口")
public interface GoodsService {
    @ApiOperation(value = "查询spu信息")
    @GetMapping(value = "/goods/getSpuInfo")
    Result<List<SpuDTO>> getSpuInfo(@SpringQueryMap SpuDTO spuDTO);

    @ApiOperation(value = "新增商品")
    @PostMapping(value = "/goods/save")
    Result<JSONObject> saveGoods(@Validated({MingruiOperation.Add.class})@RequestBody SpuDTO spuDTO);

    @ApiOperation(value = "通过spuId查询spudetail信息")
    @GetMapping(value = "/goods/getSpuDetailBySpuId")
    Result<SpuDetailEntity> getSpuDetailBySpuId(@NotNull Integer spuId);

    @ApiOperation(value = "通过spuId查询sku信息")
    @GetMapping(value = "/goods/getSkusBySpuId")
    Result<List<SkuDTO>> getSkusBySpuId(@NotNull Integer spuId);

    @ApiOperation(value = "商品修改")
    @PutMapping(value = "/goods/save")
    Result<JSONObject> editGoods(@Validated({MingruiOperation.Update.class}) @RequestBody SpuDTO spuDTO);

    @ApiOperation(value = "商品删除")
    @DeleteMapping(value = "/goods/delete")
    Result<JSONObject> deleteGoods(@NotNull Integer spuId);

    @ApiOperation(value = "商品状态")
    @PutMapping(value = "/goods/updateStauts")
    Result<JSONObject> updateStauts(@RequestBody SpuDTO spuDTO);
}
