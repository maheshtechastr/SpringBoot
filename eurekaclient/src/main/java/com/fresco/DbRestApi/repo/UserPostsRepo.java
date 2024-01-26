package com.fresco.DbRestApi.repo;

import com.fresco.DbRestApi.model.UserPosts;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPostsRepo extends MongoRepository<UserPosts, String> {
    UserPosts findPostByUserId(String userId);
}



