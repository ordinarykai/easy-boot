package com.easy.boot.core.util.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author kai
 * @date 2022/3/12 14:41
 */
@Data
public class FileVO {

    @ApiModelProperty("文件名称")
    private String name;

    @ApiModelProperty("文件uri")
    private String uri;

}
