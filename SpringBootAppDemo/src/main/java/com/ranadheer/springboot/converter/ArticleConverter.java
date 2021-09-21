package com.ranadheer.springboot.converter;
import com.ranadheer.springboot.dto.ArticleDTO;
import com.ranadheer.springboot.entity.Article;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArticleConverter {

    public ArticleDTO entityToDto(Article article)
    {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setArticle(article.getArticle());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setCommentList(article.getCommentList());
        articleDTO.setUserId(article.getUserId());
        return articleDTO;
    }

    public Article dtoToEntity(ArticleDTO articleDTO){
        Article article = new Article();
        article.setId(articleDTO.getId());
        article.setArticle(articleDTO.getArticle());
        article.setTitle(articleDTO.getTitle());
        article.setCommentList(articleDTO.getCommentList());
        article.setUserId(articleDTO.getUserId());
        return article;
    }

    public List<ArticleDTO> entityToDto(List<Article> articles)
    {
        return articles.stream().map(x->entityToDto(x)).collect(Collectors.toList());
    }

    public List<Article> dtoToEntity(List<ArticleDTO> articleDTOS )
    {
        return articleDTOS.stream().map(x->dtoToEntity(x)).collect(Collectors.toList());
    }
}
