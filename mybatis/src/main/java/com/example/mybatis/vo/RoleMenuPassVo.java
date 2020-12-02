package com.example.mybatis.vo;

import com.example.mybatis.entity.Menu;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RoleMenuPassVo {
    private String managersId;

    private String parentId;

    private String realNmae;

    private String userName;

    private String loginMoblie;

    private Integer statue;

    private LocalDateTime createDate;

    private List<Menu> list;
}
