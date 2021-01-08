package com.ali.shop.dto;

import com.ali.shop.base.BaseDTO;
import com.ali.shop.validata.group.MingruiOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @ClassName BrandDTO
 * @Description: TODO
 * @Author wangyue
 * @Date 2020/12/25
 * @Version V1.0
 **/
@Data
@ApiModel(value = "品牌DTO")
public class BrandDTO extends BaseDTO {

    @ApiModelProperty(value = "品牌主键",example = "1")
    @NotNull(message = "主键不能为空",groups={MingruiOperation.Update.class})
    private Integer id;

    @ApiModelProperty(value = "品牌名称")
    @NotNull(message = "名牌名称不能为空",groups={MingruiOperation.Add.class,MingruiOperation.Update.class})
    private String name;

    @ApiModelProperty(value = "品牌图片")
    private String image;

    @ApiModelProperty(value = "品牌首字母")
    private Character letter;

    @ApiModelProperty(value="分类集合")
    @NotEmpty(message = "分类id不能为空",groups = {MingruiOperation.Update.class,MingruiOperation.Add.class})
    private String categories;
}
