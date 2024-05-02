package com.example.RestTemplatedemo.controller;

import com.example.RestTemplatedemo.DTO.Blog;
import com.example.RestTemplatedemo.DTO.User;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Value("${blog.service.baseUrl}")
    public String blogServiceURL;

    @Value("${admin.service.baseURL}")
    public String adminServiceURL;

    @Autowired
    public RestTemplate restTemplate;

    @GetMapping("/blogs")
    public ResponseEntity<JsonNode> getBlogs()
    {
        JsonNode jsonNode = restTemplate.getForObject(blogServiceURL, JsonNode.class);
        return ResponseEntity.ok(jsonNode);
    }

    @GetMapping("/blogs/{id}")
    public ResponseEntity<Blog> getBlogsById(@PathVariable Long id)
    {
        Blog jsonNode = restTemplate.getForObject(blogServiceURL+id, Blog.class);
        jsonNode.setDate(new Date());
        return ResponseEntity.ok(jsonNode);
    }

    @PostMapping("/create-user")
    public ResponseEntity<Long> createUser(@RequestBody User userData)
    {
        //Long id = restTemplate.postForEntity(adminServiceURL, userData, Long.class,"").getBody();
        // Pass header to pass credentials
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Authorization", "Basic bmFuZGFuOmhhY2s=");
        HttpEntity<User> httpEntity = new HttpEntity(userData, headers);
        Long id = restTemplate.exchange(adminServiceURL, HttpMethod.POST, httpEntity,Long.class).getBody();
        return ResponseEntity.ok(id);
    }
}
