package com.ranadheer.springboot.controllers;

import com.ranadheer.springboot.converter.ArticleConverter;
import com.ranadheer.springboot.converter.CommentConverter;
import com.ranadheer.springboot.dto.CommentDTO;
import com.ranadheer.springboot.entity.Article;
import com.ranadheer.springboot.entity.Comment;
import com.ranadheer.springboot.entity.User;
import com.ranadheer.springboot.services.ArticlesService;
import com.ranadheer.springboot.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticlesService articlesService;

    @Autowired
    private ArticleConverter articleConverter;

    @Autowired
    private CommentConverter commentConverter;

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id){
        Comment comment = commentService.getComment(id);
        String title = comment.getArticleId().getTitle();
        commentService.delete(id);
        return "redirect:/articles/post/"+title;
    }

    @PostMapping("/add/{title}")
    public String addComment(@ModelAttribute("Comment") CommentDTO commentDTO, @PathVariable String title){
        Comment comment = commentConverter.dtoToEntity(commentDTO);
          Article article = commentService.findArticle(title);
            // to add  comment to article
            article.addComment(comment);
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<User> user = articlesService.findUser(name);
            user.ifPresent(user1 -> {
                // add comment to user
                comment.setUserId(user1);
                // to save into database
                commentService.addComment(comment);
            });
            return "redirect:/articles/post/"+title;
    }
}
