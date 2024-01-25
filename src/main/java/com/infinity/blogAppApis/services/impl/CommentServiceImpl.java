package com.infinity.blogAppApis.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infinity.blogAppApis.entites.Comment;
import com.infinity.blogAppApis.entites.Post;
import com.infinity.blogAppApis.exceptions.ResourceNotFoundException;
import com.infinity.blogAppApis.payloads.CommentDto;
import com.infinity.blogAppApis.repo.CommentRepo;
import com.infinity.blogAppApis.repo.PostRepo;
import com.infinity.blogAppApis.repo.PostRepo;
import com.infinity.blogAppApis.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelmapper;

	@Override
	public CommentDto createComment(CommentDto dto, Long postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));


		Comment comment = this.modelmapper.map(dto, Comment.class);

		comment.setPost(post);
		comment.setUser(null);
		Comment savedComment = this.commentRepo.save(comment);

		return this.modelmapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Long commentId) {

		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));

		this.commentRepo.delete(comment);
	}

	@Override
	public List<CommentDto> getComments(Long postId) {

		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","PostID",postId));
		
		List<Comment> comments = this.commentRepo.findByPost(post);
		
		List<CommentDto> commentDtos = comments.stream().map(comment->modelmapper.map(comment, CommentDto.class)).collect(Collectors.toList());
		return commentDtos;
	}

}
