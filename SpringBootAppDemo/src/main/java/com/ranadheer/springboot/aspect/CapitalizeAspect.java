package com.ranadheer.springboot.aspect;

import com.ranadheer.springboot.entity.Article;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CapitalizeAspect {

        @Before("execution(* com.ranadheer.springboot.SpringBootAppDemo.service.*.addArticle(..)) && args(article)")
        public void delete(JoinPoint joinPoint, Article article){
                System.out.println(joinPoint.getSignature().getName());
                System.out.println("---------------BEFORE ADDING TO ARTICLES LIST -----------------");
                convertUpperCase(article);
        }
        public void convertUpperCase(Article article){
                        String name= article.getTitle().toUpperCase();
                        article.setTitle(name);
        }

}
