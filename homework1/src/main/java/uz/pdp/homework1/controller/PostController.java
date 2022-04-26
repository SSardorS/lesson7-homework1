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
@RequestMapping("/api")
public class PostController {

    @Autowired
    PostService postService;

    @CheckPermissionForPostAndCommet(permission = "ADD_POST")
    @PostMapping("/post")
    public HttpEntity<?> addPost(@RequestBody PostDto postDto) {
        ApiResponse apiResponse = postService.addPost(postDto);
        return ResponseEntity.status(apiResponse.isSucces()?200:405).body(apiResponse);
    }


//    @CheckPermissionForPostAndCommet(permission = "DELETED_POST")
    @DeleteMapping("/post/{id}")
    public HttpEntity<?> deletePost(@PathVariable Long id) {
        ApiResponse apiResponse = postService.deletePost(id);
        return ResponseEntity.status(apiResponse.isSucces()?200:405).body(apiResponse);
    }
}
