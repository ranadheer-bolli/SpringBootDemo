package com.ranadheer.springboot;

import com.ranadheer.springboot.entity.Article;
import com.ranadheer.springboot.entity.Comment;
import com.ranadheer.springboot.repository.ArticleRepository;
import com.ranadheer.springboot.repository.CommentRepository;
import com.ranadheer.springboot.services.ArticlesService;
import com.ranadheer.springboot.services.ArticlesServiceImpl;
import com.ranadheer.springboot.services.CommentService;
import com.ranadheer.springboot.services.CommentServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest
class SpringBootAppDemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private ArticlesService articlesService;

	@Autowired
	private CommentService commentService;

	@Mock
	private ArticleRepository mockArticleRepo;

	@Mock
	private CommentRepository mockCommentRepo;

	@BeforeEach
	void initUseCase() {
		articlesService = new ArticlesServiceImpl(mockArticleRepo);
		commentService = new CommentServiceImpl(mockCommentRepo);
	}

	@Test
	 void findById() {
		Article article = new Article("sample article","DP");
		Mockito.when(mockArticleRepo.getById(1)).thenReturn(article);
		Assert.assertEquals(article,articlesService.get(1));
		Mockito.verify(mockArticleRepo).getById(1);
	}

	@Test
	 void saveArticle_WhenInvoked_Success(){
		Article article = new Article("sample article","DP");
		Mockito.when(mockArticleRepo.save(article)).thenReturn(article);
		Article article1 =articlesService.addArticle(article);
		assertThat(article1.getTitle()).isNotNull();
	}

	@Test
	 void findByCommentId() {
		Comment comment = new Comment("excellent article");
		Mockito.when(mockCommentRepo.getById(1)).thenReturn(comment);
		Assert.assertEquals(comment,commentService.getComment(1));
		Mockito.verify(mockCommentRepo).getById(1);
	}

	@Test
    void saveComment_WhenInvoked_Success(){
		Comment comment = new Comment("excellent article");
		Mockito.when(mockCommentRepo.save(comment)).thenReturn(comment);
		Comment comment1 = commentService.addComment(comment);
		assertThat(comment1.getComment()).isNotNull();
	}


}