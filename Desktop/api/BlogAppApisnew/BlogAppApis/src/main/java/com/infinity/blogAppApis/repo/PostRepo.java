package com.infinity.blogAppApis.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.infinity.blogAppApis.entites.Category;
import com.infinity.blogAppApis.entites.Comment;
import com.infinity.blogAppApis.entites.Post;
import com.infinity.blogAppApis.entites.User;

public interface PostRepo extends JpaRepository<Post,Long> {
	
	String findByTitle(Long postId);

	Page<Post> findByUser(User user ,Pageable pageable);

	Page<Post> findByCategoryContaining(Category category,Pageable pageable);
	
	List<Post> findByTitleContaining(String keyword);

}
