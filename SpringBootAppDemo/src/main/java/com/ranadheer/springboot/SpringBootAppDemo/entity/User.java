package com.ranadheer.springboot.SpringBootAppDemo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "password")
    private String password;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "userId",
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Article> articleList;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public User(){}
    public User(String userName,String email,String phoneNo,String password){
        this.userName=userName;
        this.password=password;
        this.phoneNo=phoneNo;
        this.email=email;
    }

    public String toString(){
        return "Id: "+id+" Username: "+userName+" email "+email+" phone no: "+phoneNo+" password"+password;
    }

    public void add(Article article){
           if(articleList ==null){
               articleList =new ArrayList<>();
           }
           articleList.add(article);
           article.setUserId(this);
    }

    public void addRole(Role role){
        if(roles == null){
            roles = new ArrayList<>();
        }
        roles.add(role);
    }
}
