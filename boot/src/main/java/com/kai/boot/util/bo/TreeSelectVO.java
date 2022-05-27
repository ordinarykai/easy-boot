package com.kai.boot.util.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author kai
 */
@Data
public class TreeSelectVO {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("label")
    private String label;

    @ApiModelProperty("children")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelectVO> children;

}
