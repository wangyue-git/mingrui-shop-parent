package com.ali.shop.service;

import com.ali.shop.base.Result;
import com.ali.shop.dto.SpuDTO;
import com.ali.shop.entity.SpuEntity;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

@Api(tags = "商品接口")
public interface GoodsService {
    @ApiOperation(value = "查询spu信息")
    @GetMapping(value = "/goods/getSpuInfo")
    Result<PageInfo<SpuEntity>> getSpuInfo(SpuDTO spuDTO);
}
