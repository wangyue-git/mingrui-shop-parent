package com.ali.shop.dto;

import com.ali.shop.base.BaseDTO;
import com.ali.shop.validata.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @ClassName SpaceGroupDTO
 * @Description: TODO
 * @Author wangyue
 * @Date 2021/1/4
 * @Version V1.0
 **/
@ApiModel(value = "规格组数据传输DTO")
@Data
public class SpecGroupDTO extends BaseDTO {
    @ApiModelProperty(value = "主键", example = "1")
    @NotNull(message = "主键不能为空", groups = {MingruiOperation.Update.class})
    private Integer id;

    @ApiModelProperty(value = "类型id", example = "1")
    @NotNull(message = "类型id不能为空", groups = {MingruiOperation.Add.class})
    private Integer cid;

    @ApiModelProperty(value = "规格组名称")
    @NotEmpty(message = "规格组名称不能为空", groups = {MingruiOperation.Add.class})
    private String name;
}
