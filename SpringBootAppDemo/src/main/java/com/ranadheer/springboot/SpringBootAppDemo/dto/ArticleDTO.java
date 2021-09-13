package com.ranadheer.springboot.SpringBootAppDemo.dto;

import com.ranadheer.springboot.SpringBootAppDemo.entity.Comment;
import com.ranadheer.springboot.SpringBootAppDemo.entity.User;
import lombok.Data;

import javax.persistence.Column;
import java.util.List;

@Data
public class ArticleDTO {

    private int id;
    private String article;
    private String title;
    private User userId;
    private List<Comment> commentList;

}
