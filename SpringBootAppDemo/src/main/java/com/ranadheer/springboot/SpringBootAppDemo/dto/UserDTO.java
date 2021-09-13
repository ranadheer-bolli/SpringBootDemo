package com.ranadheer.springboot.SpringBootAppDemo.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ranadheer.springboot.SpringBootAppDemo.entity.Article;
import com.ranadheer.springboot.SpringBootAppDemo.entity.Role;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
