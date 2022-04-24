package uz.pdp.homework1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.homework1.entity.Post;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    boolean existsByTitle(String title);

    Optional<Post> findByUrl(String title);
}
