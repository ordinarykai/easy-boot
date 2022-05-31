package com.easy.boot.core.util.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author kai
 */
@Data
public class TreeVO {

    @ApiModelProperty("值")
    private String value;

    @ApiModelProperty("文本")
    private String label;

    @ApiModelProperty("是否选中 (0.未选中 1.选中)")
    private String checkArr;

    @ApiModelProperty("下级")
    private List<TreeVO> children;

}
