package com.ali.shop.feign;

import com.ali.shop.service.GoodsService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "xxx-server")
public interface GoodsFeign extends GoodsService {
}
