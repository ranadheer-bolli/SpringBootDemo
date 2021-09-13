package com.ranadheer.springboot.SpringBootAppDemo.repository;

import com.ranadheer.springboot.SpringBootAppDemo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
