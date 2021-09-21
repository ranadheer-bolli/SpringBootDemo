package com.ranadheer.springboot.dto;

import com.ranadheer.springboot.entity.Article;
import com.ranadheer.springboot.entity.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private int id;
    private String userName;
    private String email;
    private String phoneNo;
    private String password;
    private List<Article> articleList;
    private List<Role> roleList;
}
