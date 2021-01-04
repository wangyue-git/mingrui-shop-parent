package com.ali.shop.entity;

import com.ali.shop.validata.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @ClassName CategoryEntity
 * @Description: TODO
 * @Author wangyue
 * @Date 2020/12/23
 * @Version V1.0
 **/
@ApiModel(value="分类实体类")
@Data
@Table(name = "tb_category")
public class CategoryEntity {
    @Id//声明主键
    @ApiModelProperty(value = "分类主键",example = "1")
    @NotNull(message = "id不能为空",groups={MingruiOperation.Update.class})
    private Integer id;

    @ApiModelProperty(value = "分类名称")
    @NotNull(message = "分类名称不能为空",groups={MingruiOperation.Add.class,MingruiOperation.Update.class})
    private String name;

    @ApiModelProperty(value = "父级分类",example = "1")
    @NotNull(message = "父级分类不能为null",groups={MingruiOperation.Add.class})
    private Integer parentId;

    @ApiModelProperty(value = "是否是父级节点",example = "1")
    @NotNull(message="是否为父级点不能为null",groups = {MingruiOperation.Add.class})
    private Integer isParent;

    @ApiModelProperty(value = "排序",example = "1")
    @NotNull(message = "排序字段不能为null",groups = {MingruiOperation.Add.class})
    private Integer sort;


}
