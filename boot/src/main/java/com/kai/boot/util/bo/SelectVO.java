package com.kai.boot.util.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author kai
 * @date 2022/3/12 14:15
 */
@Data
public class SelectVO {

    @ApiModelProperty("下拉框value值")
    private String value;

    @ApiModelProperty("下拉框文本值")
    private String label;

}
