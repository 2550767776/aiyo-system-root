package com.aiyo.vo;

import com.aiyo.entity.SysUser;
import lombok.Data;

import java.util.List;

/**
 * 用户实体 vo
 *
 * @author boylin
 * @create 2019-03-26 23:44
 **/
@Data
public class UserVo extends SysUser {

    private Long loginId;

    private String tokenSalt;

    private List<String> permCodes;
}
