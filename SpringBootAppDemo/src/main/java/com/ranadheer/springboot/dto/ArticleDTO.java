package com.ranadheer.springboot.dto;

import com.ranadheer.springboot.entity.User;
import com.ranadheer.springboot.entity.Comment;
import lombok.Data;

import java.util.List;

@Data
public class ArticleDTO {

    private int id;
    private String article;
    private String title;
    private User userId;
    private List<Comment> commentList;

}
