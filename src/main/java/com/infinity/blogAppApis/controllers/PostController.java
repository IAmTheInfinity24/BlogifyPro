package com.infinity.blogAppApis.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.infinity.blogAppApis.config.AppConstants;
import com.infinity.blogAppApis.payloads.ApiResponse;
import com.infinity.blogAppApis.payloads.PostDto;
import com.infinity.blogAppApis.payloads.PostResponse;
import com.infinity.blogAppApis.services.FileService;
import com.infinity.blogAppApis.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService FileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/users/{userId}/categories/{categoryId}/posts")
    ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Long userId,
                                       @PathVariable Long categoryId) {
        PostDto dto = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(dto, HttpStatus.CREATED);
    }

    @GetMapping("users/{userId}/posts")
    ResponseEntity<PostResponse> getPostsByUser(@PathVariable Long userId,
                                                @RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                @RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize) {
        PostResponse posts = this.postService.getPostsByUser(userId, pageNo, pageSize);
        return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
    }

    @GetMapping("categories/{categoryId}/posts")
    ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Long categoryId,
                                                    @RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize) {
        PostResponse posts = this.postService.getPostsByCategory(categoryId, pageNo, pageSize);
        return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    ResponseEntity<ApiResponse> deletePost(@PathVariable Long postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted", true, new Date()), HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Long postId) {
        PostDto updatePost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

    }

    @GetMapping("/posts/{postId}")
    ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        PostDto post = this.postService.getPost(postId);
        return new ResponseEntity<PostDto>(post, HttpStatus.OK);
    }

    @GetMapping("/posts")
    ResponseEntity<PostResponse> getPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.page_No, required = false) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.page_Size, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.Sort_By, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.sort_Dir, required = false) String sortDir) {
        PostResponse post = this.postService.getPosts(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<PostResponse>(post, HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keyword}")
    ResponseEntity<List<PostDto>> searchPost(@PathVariable("keyword") String keyword) {
        List<PostDto> searchPost = this.postService.searchPost(keyword);
        return new ResponseEntity<List<PostDto>>(searchPost, HttpStatus.OK);
    }

    @PostMapping("/post/image/upload/{postId}")
    ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable Long postId)
            throws IOException {
        PostDto postDto = this.postService.getPost(postId);
        String fileName = this.FileService.uploadImage(path, image);

        postDto.setImageName(fileName);

        PostDto updatePost = postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
    }

    @GetMapping("/post/image/{imageName}")
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse httpServletResponse)
            throws IOException {
        InputStream inputStream = this.FileService.serveImage(path, imageName);
        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream, httpServletResponse.getOutputStream());
    }
}
