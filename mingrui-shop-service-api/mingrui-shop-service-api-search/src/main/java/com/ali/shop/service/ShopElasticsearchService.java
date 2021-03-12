package com.ali.shop.service;

import com.ali.shop.base.Result;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

@Api(tags = "es接口")
public interface ShopElasticsearchService {

    @ApiOperation(value = "获取商品信息测试")
    @GetMapping(value = "es/goodsInfo")
    Result<JSONObject> esGoodsInfo();



}
