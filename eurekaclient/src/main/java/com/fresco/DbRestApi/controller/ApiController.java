package com.fresco.DbRestApi.controller;

import com.fresco.DbRestApi.dto.DTO;
import com.fresco.DbRestApi.dto.PostDTO;
import com.fresco.DbRestApi.model.Post;
import com.fresco.DbRestApi.model.UserPosts;
import com.fresco.DbRestApi.repo.UserPostsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.http.HttpStatus;

@RestController
public class ApiController {

    /*
     * you only need to code on the following files
     * 1.ApiController --> controller file
     * 2.UserPostsRepo --> Repository file
     * 3.DbRestApiApplication --> main class file
     * 4.application.properties --> config file
     * 5.POM.xml --> dependency { add necessary eureka client dependency }
     * */

    /*
       This controller is responsible for the REST operations
       Make sure to add required annotation and complete the functions
    */

    private static int id = 1;
    private int generateId(){
        return id++;
    }
    @Autowired
    private UserPostsRepo userPostsRepo;

    @RequestMapping(value = "/addpost", method = RequestMethod.POST)
    public ResponseEntity<Object> addPost(@RequestBody PostDTO postDTO) {
        try {
            UserPosts userposts = new UserPosts();
            userposts.setUserId(postDTO.getUserId());

            ArrayList<Post> posts = new ArrayList<>();
            Post post = new Post();
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern("dd-MM-yyyy");
            post.setPostDate(sdf.format(new Date()));
            post.setPostBody(postDTO.getPostBody());
            post.setPostId(generateId());
            posts.add(post);
            userposts.setPosts(posts);
            userposts.setSubscribed(new ArrayList<>());
            userPostsRepo.save(userposts);
            return new ResponseEntity(userposts, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(postDTO, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/getpost/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getPost(@PathVariable String userId) {
        Optional<UserPosts> userposts = userPostsRepo.findById(userId);
        List<Post> obj = new ArrayList<>();
        try {
            if(!userposts.isPresent())
                return new ResponseEntity(userId, HttpStatus.NOT_FOUND);
            // obj = new Object[userposts.get().getPosts().size()];
            // for (int i = 0; i < userposts.get().getPosts().size(); i++) {
            //     obj[i] = userposts.get().getPosts().get(i);
            // }
            obj = userposts.get().getPosts();
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity(userId, HttpStatus.NOT_FOUND);
        }
        // System.out.println(userPostsRepo.findByUserId(userId));
        return new ResponseEntity(obj, HttpStatus.OK);
    }

    @RequestMapping(value = "/delpost/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delPost(@PathVariable String userId, @RequestBody DTO dTO) {
        Optional<UserPosts> userposts = userPostsRepo.findById(userId);
        try {
            if(!userposts.isPresent())
                return new ResponseEntity(userId, HttpStatus.NOT_FOUND);
            ArrayList<Post> pst = new ArrayList<Post>();
            for (int i = 0; i < userposts.get().getPosts().size(); i++) {
                Post post = new Post();
                if (userposts.get().getPosts().get(i).getPostId() != dTO.getPostId()) {
                    post.setPostBody(userposts.get().getPosts().get(i).getPostBody());
                    post.setPostId(userposts.get().getPosts().get(i).getPostId());
                    post.setPostDate(userposts.get().getPosts().get(i).getPostDate());
                    pst.add(post);
                }
            }

            userposts.get().setPosts(pst);
            userPostsRepo.save(userposts.get());
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity(dTO.getPostId(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(userId, HttpStatus.OK);
    }

    @RequestMapping(value = "/searchpost/{userId}", method = RequestMethod.POST)
    public ResponseEntity<Object> searchPost(@PathVariable String userId, @RequestBody DTO dTO) {
        HashMap<String, Boolean> hashmap = new HashMap<String, Boolean>();
        Optional<UserPosts> userposts = userPostsRepo.findById(userId);
        List<Post> posts = new ArrayList<>();
        try {
            if(!userposts.isPresent())
                return new ResponseEntity("UserPost not found", HttpStatus.NOT_FOUND);
            for (int i = 0; i < userposts.get().getSubscribed().size(); i++) {
                if (userposts.get().getSubscribed().get(i).contains(dTO.getSearchText())) {
                    hashmap.put(userposts.get().getSubscribed().get(i), true);
                    posts.add(userposts.get().getPosts().get(i));
                } else {
                    hashmap.put(userposts.get().getSubscribed().get(i), false);
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity(hashmap, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(posts, HttpStatus.OK);
    }

    @RequestMapping(value = "/subscribe/{userId}", method = RequestMethod.POST)
    public ResponseEntity<Object> subscribe(@PathVariable String userId, @RequestBody DTO dTO) {
        Optional<UserPosts> userposts = userPostsRepo.findById(userId);
        // UserPosts userposts = userPostsRepo.findPostByUserId(userId);
        try {
            if(!userposts.isPresent())
                return new ResponseEntity(userId, HttpStatus.NOT_FOUND);
            int flag = -1;
            for (int i = 0; i < userposts.get().getSubscribed().size(); i++) {
                if (userposts.get().getSubscribed().get(i).equals(dTO.getSubscriber())) {
                    flag = i;
                }
            }

            if (flag > -1) {
                userposts.get().getSubscribed().remove(flag);
            } else {
                userposts.get().getSubscribed().add(dTO.getSubscriber());
            }
            userPostsRepo.save(userposts.get());

        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity(userposts, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(userposts, HttpStatus.OK);
    }

}
