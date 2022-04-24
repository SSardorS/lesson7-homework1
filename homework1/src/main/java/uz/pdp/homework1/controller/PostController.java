package uz.pdp.homework1.controller;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.homework1.aop.CheckPermissionForPostAndCommet;
import uz.pdp.homework1.payload.ApiResponse;
import uz.pdp.homework1.payload.PostDto;
import uz.pdp.homework1.service.PostService;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    PostService postService;

    @CheckPermissionForPostAndCommet(permission = "ADD_POST")
    @PostMapping
    public HttpEntity<?> addPost(@RequestBody PostDto postDto) {
        ApiResponse apiResponse = postService.addPost(postDto);
        return ResponseEntity.status(apiResponse.isSucces()?200:405).body(apiResponse);
    }


    @CheckPermissionForPostAndCommet(permission = "DELETED_POST")
    @DeleteMapping("/{title}")
    public HttpEntity<?> deletePost(@PathVariable String title) {
        ApiResponse apiResponse = postService.deletePost(title);
        return ResponseEntity.status(apiResponse.isSucces()?200:405).body(apiResponse);
    }
}
