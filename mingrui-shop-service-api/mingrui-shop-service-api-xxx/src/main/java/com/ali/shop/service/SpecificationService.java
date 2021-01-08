package com.ali.shop.service;

import com.ali.shop.base.Result;
import com.ali.shop.dto.SpecGroupDTO;
import com.ali.shop.dto.SpecParamDTO;
import com.ali.shop.entity.SpecGroupEntity;
import com.ali.shop.entity.SpecParamEntity;
import com.ali.shop.validata.group.MingruiOperation;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Api(tags = "规格接口")
public interface SpecificationService {

    @ApiOperation(value = "通过条件查询规格组")
    @GetMapping(value = "specgroup/getSpecGroupInfo")
    Result<List<SpecGroupEntity>> getSepcGroupInfo(SpecGroupDTO specGroupDTO);

    @ApiOperation(value = "新增规格组")
    @PostMapping(value = "specgroup/save")
    Result<JSONObject> saveSpecGroup(@Validated({MingruiOperation.Add.class}) @RequestBody SpecGroupDTO specGroupDTO);

    @ApiOperation(value = "修改规格组")
    @PutMapping(value = "specgroup/save")
    Result<JSONObject> editSpecGroup(@Validated({MingruiOperation.Update.class}) @RequestBody SpecGroupDTO specGroupDTO);

    @ApiOperation(value = "删除规格组")
    @DeleteMapping(value = "specgroup/delete/{id}")
    Result<JSONObject> deleteSpecGroupById(@NotNull @PathVariable Integer id);

    @ApiOperation(value = "通过条件查询规格参数")
    @GetMapping(value = "specparam/getSpecParamInfo")
    Result<List<SpecParamEntity>> getSpecParamInfo(SpecParamDTO specParamDTO);

    @ApiOperation(value = "新增规格参数")
    @PostMapping(value = "specparam/save")
    Result<JSONObject> saveSpecParam(@Validated({MingruiOperation.Add.class}) @RequestBody SpecParamDTO specParamDTO);

    @ApiOperation(value = "修改规格参数")
    @PutMapping(value = "specparam/save")
    Result<JSONObject> editSpecParam(@Validated({MingruiOperation.Update.class}) @RequestBody SpecParamDTO specParamDTO);


    @ApiOperation(value = "删除规格参数")
    @DeleteMapping(value = "specparam/delete")
    Result<JSONObject> deleteSpecParam(@NotNull Integer id);

}
