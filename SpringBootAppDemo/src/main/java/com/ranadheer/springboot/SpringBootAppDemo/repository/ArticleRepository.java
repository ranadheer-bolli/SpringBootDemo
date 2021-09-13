package com.ranadheer.springboot.SpringBootAppDemo.repository;

import com.ranadheer.springboot.SpringBootAppDemo.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Integer> {
    Article findByTitle(String title);
}
