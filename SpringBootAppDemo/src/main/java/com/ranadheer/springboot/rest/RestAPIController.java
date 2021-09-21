package com.ranadheer.springboot.Rest;

import com.ranadheer.springboot.dto.ArticleDTO;
import com.ranadheer.springboot.dto.CommentDTO;
import com.ranadheer.springboot.entity.Article;
import com.ranadheer.springboot.entity.Comment;
import com.ranadheer.springboot.entity.User;
import com.ranadheer.springboot.converter.ArticleConverter;
import com.ranadheer.springboot.converter.CommentConverter;
import com.ranadheer.springboot.services.ArticlesService;
import com.ranadheer.springboot.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest")
public class RestAPIController {

    @Autowired
    private ArticlesService articlesService;

    @Autowired
    private ArticleConverter articleConverter;

    @Autowired
    private CommentConverter commentConverter;

    @Autowired
    private CommentService commentService;

    @GetMapping("/articles")
    public List<Article> listArticles() {
        return articlesService.get();
    }
    @GetMapping("/article/{id}")
    public Article getArticle(@PathVariable int id) {
        return articlesService.get(id);
    }
    @DeleteMapping(value = "/article/{id}")
    public String deleteArticle(@PathVariable int id) {
        articlesService.delete(id);
        return ("DELETED THE ARTICLE WITH ID = " + id);
    }
    @PostMapping(value = "/article/add")
    public Article addArticle(@ModelAttribute("Article") ArticleDTO articledto) throws Exception {
        Article article = articleConverter.dtoToEntity(articledto);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = articlesService.findUser(name);
        user.ifPresent(user1 -> {
            user1.add(article);
        });
        articlesService.addArticle(article);
        return article;
    }

    @PutMapping(value = "/article/{id}")
    public Article updateArticle(@PathVariable int id){
        Article article = articlesService.get(id);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = articlesService.findUser(name);
        user.ifPresent(user1 -> {
            user1.add(article);
        });
        articlesService.update(article);
        return article;
    }



    // Comment APIS
    @PostMapping("/comment/add/{title}")
    public Comment addComment(@ModelAttribute("Comment") CommentDTO commentDTO, @PathVariable String title) {
        Comment comment = commentConverter.dtoToEntity(commentDTO);
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
            return comment;
    }
    @DeleteMapping("comment/{id}")
    public String deleteComment(@PathVariable int id) throws Exception{
        Comment comment = commentService.getComment(id);
        String title = comment.getArticleId().getTitle();
        commentService.delete(id);
        return ("DELETED THE COMMENT WITH ID = " + id);
    }

}