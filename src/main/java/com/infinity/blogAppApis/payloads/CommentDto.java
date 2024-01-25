package com.infinity.blogAppApis.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

	private long id;
	
	private String content;
	
	private UserDto user;
	
	//private PostDto post;
}
