package com.infinity.blogAppApis.services;

import java.util.List;

import com.infinity.blogAppApis.payloads.CommentDto;

public interface CommentService {

	
	CommentDto createComment(CommentDto dto,Long postId);
	
	void deleteComment(Long commentId);
	
	List<CommentDto> getComments(Long postid );

}