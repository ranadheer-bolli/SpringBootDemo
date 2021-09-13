package com.ranadheer.springboot.SpringBootAppDemo.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ranadheer.springboot.SpringBootAppDemo.entity.Article;
import com.ranadheer.springboot.SpringBootAppDemo.entity.User;
import lombok.Data;

@Data
public class CommentDTO {

    private int id;
    private String comment;
    private User userId;
    private Article articleId;

}
