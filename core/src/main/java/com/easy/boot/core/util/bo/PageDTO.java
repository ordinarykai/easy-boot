package com.easy.boot.core.util.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author kai
 * @date 2022/3/12 14:12
 */
@Data
public class PageDTO {

    @ApiModelProperty(value = "每页条数 默认10", example = "10")
    private Long limit;

    @ApiModelProperty(value = "当前页 默认1", example = "1")
    private Long page;

}
