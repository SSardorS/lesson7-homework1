package uz.pdp.homework1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.homework1.entity.Commet;
import uz.pdp.homework1.entity.Post;
import uz.pdp.homework1.entity.User;
import uz.pdp.homework1.payload.ApiResponse;
import uz.pdp.homework1.payload.CommetDto;
import uz.pdp.homework1.repository.CommetRepository;
import uz.pdp.homework1.repository.PostRepository;

import java.util.Optional;

@Service
public class CommetService {

    @Autowired
    CommetRepository commetRepository;

    @Autowired
    PostRepository postRepository;

    public ApiResponse addCommet(CommetDto commetDto) {
        Optional<Post> byIdPost = postRepository.findById(commetDto.getPostId());
        if (!byIdPost.isPresent()) {
            return new ApiResponse("Postni tanlang", false);
        }

        Commet commet = new Commet(commetDto.getText(), byIdPost.get());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        commet.setCreatedBy(user);
        commetRepository.save(commet);
        return new ApiResponse("Added", true);
    }

    public ApiResponse deleteCommet(Long id) {
        Optional<Commet> byId = commetRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("Bunday comentariya topilmadi", false);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.getId().equals(byId.get().getId()))
            return new ApiResponse("Bu text sizniki emas",false);

        commetRepository.deleteById(id);
        return new ApiResponse("Deleted", true);
    }
}
