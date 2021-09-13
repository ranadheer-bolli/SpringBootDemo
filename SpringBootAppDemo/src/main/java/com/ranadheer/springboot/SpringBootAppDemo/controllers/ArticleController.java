package com.ranadheer.springboot.SpringBootAppDemo.controllers;

import com.ranadheer.springboot.SpringBootAppDemo.converter.ArticleConverter;
import com.ranadheer.springboot.SpringBootAppDemo.converter.CommentConverter;
import com.ranadheer.springboot.SpringBootAppDemo.converter.UserConverter;
import com.ranadheer.springboot.SpringBootAppDemo.dto.ArticleDTO;
import com.ranadheer.springboot.SpringBootAppDemo.dto.CommentDTO;
import com.ranadheer.springboot.SpringBootAppDemo.dto.UserDTO;
import com.ranadheer.springboot.SpringBootAppDemo.entity.Article;
import com.ranadheer.springboot.SpringBootAppDemo.entity.Comment;
import com.ranadheer.springboot.SpringBootAppDemo.entity.Role;
import com.ranadheer.springboot.SpringBootAppDemo.entity.User;
import com.ranadheer.springboot.SpringBootAppDemo.services.ArticlesService;
import com.ranadheer.springboot.SpringBootAppDemo.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticlesService articlesService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private ArticleConverter articleConverter;

    @Autowired
    private CommentConverter commentConverter;


    @GetMapping("/")
    public String listArticles(Model model) {
        return findPaginated(1,model);
    }

    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET,RequestMethod.DELETE})
    public String delete(@PathVariable int id){
        articlesService.delete(id);
        return "redirect:/articles/";
    }

    @GetMapping("/page")
    public String open(Model model){
        model.addAttribute("Article",new ArticleDTO());
        return "new-article";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String addArticle(@ModelAttribute("Article") ArticleDTO articledto) throws Exception{
        Article article = articleConverter.dtoToEntity(articledto);
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<User> user = articlesService.findUser(name);
            user.ifPresent(user1 -> {
                user1.add(article);
            });
            articlesService.addArticle(article);
            return "redirect:/articles/userpost/"+article.getTitle();
        }
        catch (Exception e){
            System.out.println(article+"-------------------"+e);
        }
        return "redirect:/articles/";
    }

    @GetMapping("/form/{id}")
    public String update(Model model,@PathVariable int id)  throws Exception{
        try {
            Article article = articlesService.get(id);
            ArticleDTO articleDTO = articleConverter.entityToDto(article);
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<User> user = articlesService.findUser(name);
            if (user.get().getId() != article.getUserId().getId()) {
                return "access-denied";
            }
            model.addAttribute("Article", articleDTO);
            return "update-article";
        }
        catch (Exception e){
            return "page";
        }
    }


    @GetMapping("/post/{title}")
    public String display(@PathVariable String title,Model model) throws Exception{
        try {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = articlesService.findUser(name);
        UserDTO userDTO = userConverter.entityToDto(user);
        List<Role> roleList = userDTO.getRoleList();
        for(Role role:roleList){
            if(role.getName().equals("USER")){
                return displayUserPost(title,model);
            }
        }

        Article article = commentService.findArticle(title);
        ArticleDTO articleDTO = articleConverter.entityToDto(article);
        List<Comment> comments = article.getCommentList();
        List<CommentDTO> commentDTOList = commentConverter.entityToDto(comments);

        model.addAttribute("User",userDTO);
        model.addAttribute("Comment",new CommentDTO());
        model.addAttribute("Article",articleDTO);
        model.addAttribute("comments",commentDTOList);
        return "post";
    }
        catch (Exception e){
            return "page";
        }
    }

    @GetMapping("/userpost/{title}")
    public String displayUserPost(@PathVariable String title,Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = articlesService.findUser(name);
        Article article = commentService.findArticle(title);
        ArticleDTO articleDTO = articleConverter.entityToDto(article);
        List<Comment> comments = article.getCommentList();
        List<CommentDTO> commentDTOList = commentConverter.entityToDto(comments);
        UserDTO userDTO = userConverter.entityToDto(user);
        model.addAttribute("User",userDTO);
        model.addAttribute("Comment",new CommentDTO());
        model.addAttribute("Article",articleDTO);
        model.addAttribute("comments",commentDTOList);
        return "user-post";
    }

    @GetMapping("/user")
    public String getUserArticles(Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = articlesService.findUser(name);
        user.ifPresent(user1 -> {
            List<Article> articleList = user1.getArticleList();
            List<ArticleDTO> articleDTOList = articleConverter.entityToDto(articleList);
            model.addAttribute("articles",articleDTOList);
        });
        return "user-articles";
    }


    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable int pageNo,Model model){
        int pageSize = 5;
        Page<Article> page = articlesService.findPaginated(pageNo,pageSize);
        List<Article> articleList = page.getContent();
        List<ArticleDTO> articleDTOList = articleConverter.entityToDto(articleList);
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("articles",articleDTOList);
        model.addAttribute("totalItems",page.getTotalElements());
        return "articles-list";
    }
}
