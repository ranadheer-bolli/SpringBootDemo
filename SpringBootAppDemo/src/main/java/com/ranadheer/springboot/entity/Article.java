package com.ranadheer.springboot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@JsonIgnoreProperties
@Entity
@Table(name="articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "article")
    private String article;

    @Column(name = "title")
    private String title;


    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH,
            CascadeType.MERGE,
            CascadeType.DETACH})
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User userId;

    @OneToMany(mappedBy = "articleId",cascade = {CascadeType.ALL})
    @Column(name = "article")
    @JsonManagedReference
    private List<Comment> commentList;



    public Article(){}
    public Article(String article,String title) {
        this.article = article;
        this.title=title;
    }


    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", article='" + article + '\'' +
                ", title='" + title + '\'' +
                ", userId=" + userId +
                '}';
    }


    public void addComment(Comment comment){
        if(commentList==null){
            commentList= new ArrayList<>();
        }
        commentList.add(comment);
        System.out.println(this);
        comment.setArticleId(this);
    }
}
