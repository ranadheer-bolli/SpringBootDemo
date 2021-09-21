package com.ranadheer.springboot.dto;

import com.ranadheer.springboot.entity.User;
import com.ranadheer.springboot.entity.Article;
import lombok.Data;

@Data
public class CommentDTO {

    private int id;
    private String comment;
    private User userId;
    private Article articleId;

}
