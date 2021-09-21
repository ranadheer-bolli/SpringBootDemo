package com.ranadheer.springboot.repository;

import com.ranadheer.springboot.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Integer> {
    Article findByTitle(String title);
}
