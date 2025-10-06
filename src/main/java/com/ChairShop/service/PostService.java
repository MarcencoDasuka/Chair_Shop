package com.ChairShop.service;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final List<String> posts = new ArrayList<>();


    public void CreatePost(String postContent){
        posts.add(postContent);
    }

}




