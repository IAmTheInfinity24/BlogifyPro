package com.infinity.blogAppApis.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infinity.blogAppApis.payloads.ApiResponse;
import com.infinity.blogAppApis.payloads.CommentDto;
import com.infinity.blogAppApis.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment, @PathVariable Long postId) {
        CommentDto commentDto = this.commentService.createComment(comment, postId);
        return new ResponseEntity<CommentDto>(commentDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentId) {

        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Sucessfully!..", true, new Date()),
                HttpStatus.OK);

    }

    @GetMapping("/posts/{postId}/comments")
    ResponseEntity<List<CommentDto>> getCommentByPost(@PathVariable Long postId) {

        List<CommentDto> comments = this.commentService.getComments(postId);
        return new ResponseEntity<List<CommentDto>>(comments, HttpStatus.OK);

    }

}
