package com.ranadheer.springboot.SpringBootAppDemo.services;

import com.ranadheer.springboot.SpringBootAppDemo.entity.Article;
import com.ranadheer.springboot.SpringBootAppDemo.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    public List<Comment> getComments();
    public Comment getComment(int id);
    public void delete(int id);
    void update(Comment comment);
    Comment addComment(Comment comment);

    Article findArticle(String name);
}
