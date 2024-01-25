package com.infinity.blogAppApis.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.infinity.blogAppApis.entites.Category;
import com.infinity.blogAppApis.entites.Comment;
import com.infinity.blogAppApis.entites.Post;
import com.infinity.blogAppApis.entites.User;
import com.infinity.blogAppApis.exceptions.ResourceNotFoundException;
import com.infinity.blogAppApis.payloads.PostDto;
import com.infinity.blogAppApis.payloads.PostResponse;
import com.infinity.blogAppApis.repo.CategoryRepo;
import com.infinity.blogAppApis.repo.PostRepo;
import com.infinity.blogAppApis.repo.UserRepo;
import com.infinity.blogAppApis.services.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public PostDto createPost(PostDto postDto, Long userId, Long categoryId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "User Id", userId));

        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category", "Category Id", categoryId));

        Post post = this.mapper.map(postDto, Post.class);
        post.setUser(user);
        post.setCategory(category);
        post.setAddedDate(new Date());
        post.setImageName("default.png");

        Post newPost = this.postRepo.save(post);
        return this.mapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long postId) {

        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost = this.postRepo.save(post);
        PostDto dto = this.mapper.map(updatedPost, PostDto.class);

        return dto;
    }

    @Override
    public void deletePost(Long postId) {

        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostDto getPost(Long postId) {

        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));

        /*List<Comment> comments = post.getComments();
        post.setComments(comments);*/

        PostDto dto = this.mapper.map(post, PostDto.class);

        return dto;
    }

    @Override
    public PostResponse getPosts(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> page = this.postRepo.findAll(p);

        List<Post> posts = page.getContent();

        List<PostDto> dtos = posts.stream().map(post -> mapper.map(post, PostDto.class)).collect(Collectors.toList());

        PostResponse pr = new PostResponse();
        pr.setContent(dtos);
        pr.setPageNo(page.getNumber());
        pr.setPageSize(page.getSize());
        pr.setTotalElements(page.getTotalElements());
        pr.setTotalPages(page.getTotalPages());
        pr.setLastPage(page.isLast());

        return pr;
    }

    @Override
    public PostResponse getPostsByUser(Long userId, Integer pn, Integer ps) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));

        Pageable p = PageRequest.of(pn, ps);
        Page<Post> page = this.postRepo.findByUser(user, p);
        List<Post> posts = page.getContent();

        List<PostDto> dtos = posts.stream().map(post -> mapper.map(post, PostDto.class)).collect(Collectors.toList());

        PostResponse pr = new PostResponse();
        pr.setContent(dtos);
        pr.setPageNo(page.getNumber());
        pr.setPageSize(page.getSize());
        pr.setTotalElements(page.getTotalElements());
        pr.setTotalPages(page.getTotalPages());
        pr.setLastPage(page.isLast());

        return pr;
    }

    @Override
    public PostResponse getPostsByCategory(Long categoryId, Integer pn, Integer ps) {

        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        Pageable p = PageRequest.of(pn, ps);
        Page<Post> page = this.postRepo.findByCategory(category, p);
        List<Post> posts = page.getContent();

        List<PostDto> dtos = posts.stream().map(post -> mapper.map(post, PostDto.class)).collect(Collectors.toList());

        PostResponse pr = new PostResponse();
        pr.setContent(dtos);
        pr.setPageNo(page.getNumber());
        pr.setPageSize(page.getSize());
        pr.setTotalElements(page.getTotalElements());
        pr.setTotalPages(page.getTotalPages());
        pr.setLastPage(page.isLast());

        return pr;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {

        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
        return posts.stream().map(post -> mapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

}
