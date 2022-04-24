package uz.pdp.homework1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.homework1.aop.CheckPermissionForPostAndCommet;
import uz.pdp.homework1.payload.ApiResponse;
import uz.pdp.homework1.payload.CommetDto;
import uz.pdp.homework1.payload.PostDto;
import uz.pdp.homework1.service.CommetService;

@RestController
@RequestMapping("/api/commet")
public class CommetController {

    @Autowired
    CommetService commetService;

    @CheckPermissionForPostAndCommet(permission = "ADD_COMMET")
    @PostMapping
    public HttpEntity<?> addPost(@RequestBody CommetDto commetDto) {
        ApiResponse apiResponse = commetService.addCommet(commetDto);
        return ResponseEntity.status(apiResponse.isSucces()?200:405).body(apiResponse);
    }


    @PreAuthorize(value = "hasAnyAuthority('DELETE_MY_COMMET','DELETE_COMMET')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deletePost(@PathVariable Long id) {
        ApiResponse apiResponse = commetService.deleteCommet(id);
        return ResponseEntity.status(apiResponse.isSucces()?200:405).body(apiResponse);
    }
}
