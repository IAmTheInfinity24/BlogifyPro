package com.infinity.blogAppApis.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infinity.blogAppApis.entites.Comment;
import com.infinity.blogAppApis.entites.Post;

public interface CommentRepo extends JpaRepository<Comment, Long> {

	List<Comment> findByPost(Post post);

}
