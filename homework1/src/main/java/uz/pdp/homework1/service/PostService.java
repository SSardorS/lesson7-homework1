package uz.pdp.homework1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.homework1.entity.Post;
import uz.pdp.homework1.entity.User;
import uz.pdp.homework1.exeptions.ForbiddenException;
import uz.pdp.homework1.payload.ApiResponse;
import uz.pdp.homework1.payload.PostDto;
import uz.pdp.homework1.repository.PostRepository;

import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public ApiResponse addPost(PostDto postDto) {

        boolean existsByTitle = postRepository.existsByTitle(postDto.getTitle());
        if (existsByTitle)
            return new ApiResponse("Bunday post allaqachon qoyilgan", false);

        Post post = new Post(postDto.getTitle(), postDto.getText(), postDto.getUrl().toLowerCase());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setCreatedBy(user);
        postRepository.save(post);
        return new ApiResponse("succesfull Added", true);
    }



    public ApiResponse deletePost(Long id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Optional<Post> byTitle = postRepository.findByUrl(title.toLowerCase());
        Optional<Post> byId = postRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("bunday post qoshilmagan", false);

        Post post = byId.get();
        if (!post.getCreatedBy().getId().equals(user.getId()))
            throw  new ForbiddenException(user.getUsername(),"bu postni siz qoshmagansiz");

        postRepository.deleteById(post.getId());
        return new ApiResponse("SuccesFull Deleted" ,true);
    }


}
