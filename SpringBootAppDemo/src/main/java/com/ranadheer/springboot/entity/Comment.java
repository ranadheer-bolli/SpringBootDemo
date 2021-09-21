package com.ranadheer.springboot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "comments")
public class Comment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false,name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH,
            CascadeType.MERGE,
            CascadeType.DETACH})
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH,
    CascadeType.DETACH,
    CascadeType.MERGE,
    })
    @JoinColumn(name="article_id")
    @JsonBackReference
    private Article articleId;

    public Comment() {
    }

    public Comment(String comment) {
        this.comment = comment;
    }


}
