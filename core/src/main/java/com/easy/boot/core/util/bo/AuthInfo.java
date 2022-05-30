package com.easy.boot.core.util.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户登录缓存信息
 *
 * @author kai
 * @date 2022/3/12 13:47
 */
@Data
public class AuthInfo {

    @ApiModelProperty(value = "当前登录者ID")
    private Integer id;

    @ApiModelProperty(value = "当前登录者账号")
    private String account;

    @ApiModelProperty(value = "当前登录者角色ID集合")
    private List<Integer> roleIds;

    @ApiModelProperty(value = "当前登录者权限ID集合")
    private List<Integer> permissionIds;

}
