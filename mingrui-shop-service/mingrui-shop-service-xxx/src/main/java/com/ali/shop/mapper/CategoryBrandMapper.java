package com.ali.shop.mapper;

import com.ali.shop.entity.CategoryBrandEntity;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface CategoryBrandMapper extends InsertListMapper<CategoryBrandEntity>, Mapper<CategoryBrandEntity> {
}
