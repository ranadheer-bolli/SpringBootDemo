package com.ranadheer.springboot.services;

import com.ranadheer.springboot.entity.Article;
import com.ranadheer.springboot.entity.User;
import com.ranadheer.springboot.repository.UserRepository;
import com.ranadheer.springboot.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ArticlesServiceImpl implements ArticlesService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    public ArticlesServiceImpl(ArticleRepository mockRepo) {
        this.articleRepository=mockRepo;
    }

    @Override
    public void delete(int id) {
        Article article = articleRepository.getById(id);
        articleRepository.delete(article);
    }

    @Override
    public Article get(int id){
       return articleRepository.getById(id);
    }

    @Override
    public List<Article> get() {
        return articleRepository.findAll();
    }

    @Override
    public void update(Article article) {
            articleRepository.save(article);
    }

    @Override
    @Transactional
    public Article addArticle(Article article) {
           return articleRepository.save(article);
    }

    @Override
    public Optional<User> findUser(String s) {
        return userRepository.findByUserName(s);
    }

    @Override
    public Page<Article> findPaginated(int pageNo, int pageSIze) {
        Pageable pageable = PageRequest.of(pageNo-1,pageSIze);
        return this.articleRepository.findAll(pageable);
    }


}