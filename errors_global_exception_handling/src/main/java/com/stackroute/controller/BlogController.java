package com.stackroute.controller;

import com.stackroute.domain.Blog;
import com.stackroute.exception.BlogAlreadyExistsException;
import com.stackroute.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* Throw suitable exceptions for respective methods. For e.g. while saving a blog which is already saved you should throw BlogAlreadyExistsException and handle it.
* Similarly for all other methods throw suitable exceptions*/
/**
 * RestController annotation is used to create Restful web services using Spring MVC
 */
@RestController
/**
 * RequestMapping annotation maps HTTP requests to handler methods
 */
@RequestMapping(value = "/api/v1/")
public class BlogController {

    private BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    /* This method saves a blog and returns the blog object */
    @PostMapping("/blog")
    public ResponseEntity<Blog> saveBlog(@RequestBody Blog blog){
        try {
        	
        	Blog savedBlog = blogService.saveBlog(blog);
            return new ResponseEntity<Blog>(savedBlog, HttpStatus.CREATED);
			
		} catch (BlogAlreadyExistsException e) {
			  return new ResponseEntity<Blog>(HttpStatus.CONFLICT);
		}
    }

    /* This method fetches all blogs and returns the list of all blogs */
    @GetMapping("/blogs")
    public ResponseEntity<List<Blog>> getAllBlogs() {
        return new ResponseEntity<List<Blog>>((List<Blog>) blogService.getAllBlogs(), HttpStatus.OK);

    }

    /* This method fetches a blog by its id and returns the respective blog */
    @GetMapping("blog/{blogId}")
    public ResponseEntity<Blog> getBlogById(@PathVariable("blogId") int blogId) {
        return new ResponseEntity<Blog>(blogService.getBlogById(blogId), HttpStatus.OK);
    }


    /* This method deletes a blog by its id and returns the deleted blog object */
    @DeleteMapping("blog/{blogId}")
    public ResponseEntity<Blog> getBlogAfterDeleting(@PathVariable("blogId") int blogId) {
        try {
        	return new ResponseEntity<Blog>(blogService.deleteBlog(blogId), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

    /* This method updates the blog content and returns the updated blog object */
    @PutMapping("blog")
    public ResponseEntity<?> updateBlog(@RequestBody Blog blog)  {
        try {
        	Blog updatedBlog = blogService.updateBlog(blog);
            return new ResponseEntity<Blog>(updatedBlog,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }
}