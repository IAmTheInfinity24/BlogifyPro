package com.infinity.blogAppApis.services;

import java.util.List;

import com.infinity.blogAppApis.payloads.PostDto;
import com.infinity.blogAppApis.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto,Long userid,Long categoryId);
	
	PostDto updatePost(PostDto postDto, Long postId);
	
	void deletePost(Long postId);
	
	PostDto getPost(Long postId);
	
	PostResponse getPosts(Integer pageNo,Integer pageSize,String sortBy,String sortDir);
	
	PostResponse getPostsByUser(Long userId,Integer pn,Integer ps);
	
	PostResponse getPostsByCategory(Long categoryId,Integer pn,Integer ps);
	
	List<PostDto> searchPost(String keyword);
}
