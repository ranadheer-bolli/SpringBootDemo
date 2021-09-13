package com.ranadheer.springboot.SpringBootAppDemo.controllers;

import com.ranadheer.springboot.SpringBootAppDemo.converter.ArticleConverter;
import com.ranadheer.springboot.SpringBootAppDemo.converter.CommentConverter;
import com.ranadheer.springboot.SpringBootAppDemo.dto.CommentDTO;
import com.ranadheer.springboot.SpringBootAppDemo.entity.Article;
import com.ranadheer.springboot.SpringBootAppDemo.entity.Comment;
import com.ranadheer.springboot.SpringBootAppDemo.entity.User;
import com.ranadheer.springboot.SpringBootAppDemo.services.ArticlesService;
import com.ranadheer.springboot.SpringBootAppDemo.services.CommentService;
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

    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.DELETE,RequestMethod.GET})
    public String delete(@PathVariable int id) throws Exception{
        Comment comment = commentService.getComment(id);
        String title = comment.getArticleId().getTitle();
        commentService.delete(id);
        return "redirect:/articles/post/"+title;
    }

    @RequestMapping(value = "/add/{title}", method = RequestMethod.POST)
    public String addComment(@ModelAttribute("Comment") CommentDTO commentDTO,@PathVariable String title) throws Exception{
        Comment comment = commentConverter.dtoToEntity(commentDTO);
        try {
          Article article = commentService.findArticle(title);
            // to add  comment to article
            article.addComment(comment);
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<User> user = articlesService.findUser(name);
            user.ifPresent(user1 -> {
                // add comment to user
                comment.setUserId(user1);
            });
          // to save into database
          commentService.addComment(comment);
            return "redirect:/articles/post/"+title;
        }
        catch (Exception e){
            System.out.println(comment+"-------------------"+e);
        }
        return "redirect:/articles/";
    }
}
