package com.aiyo.basic.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "昵称")
    private String nickname;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "盐")
    private String salt;
    @ApiModelProperty(value = "user id 平台用户统一id")
    private Long loginId;
    @ApiModelProperty(value = "商会，企业，个体组织ID")
    private Long companyId;
    @ApiModelProperty(value = "账号类别,admin：平台账号，business：运营商账号")
    private String accountType;
    @ApiModelProperty(value = "token盐，加密过后的密码")
    private String tokenSalt;
    @ApiModelProperty(value = "用户权限编码")
    private List<String> permCodes;
}
