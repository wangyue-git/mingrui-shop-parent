package com.ali.shop.service.impl;

import com.ali.shop.base.BaseApiService;
import com.ali.shop.base.Result;
import com.ali.shop.dto.SpuDTO;
import com.ali.shop.feign.GoodsFeign;
import com.ali.shop.service.ShopElasticsearchService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @ClassName ShopElasticsearchServiceImpl
 * @Description: TODO
 * @Author wangyue
 * @Date 2021/3/4
 * @Version V1.0
 **/
@RestController
public class ShopElasticsearchServiceImpl extends BaseApiService implements ShopElasticsearchService {

    @Autowired
    private GoodsFeign goodsFeign;

    @Override
    public Result<JSONObject> esGoodsInfo() {
        SpuDTO spuDTO = new SpuDTO();
        spuDTO.setPage(1);
        spuDTO.setRows(5);
        Result<List<SpuDTO>> spuInfo = goodsFeign.getSpuInfo(spuDTO);
        System.out.println(spuInfo);
        return null;
    }
}
