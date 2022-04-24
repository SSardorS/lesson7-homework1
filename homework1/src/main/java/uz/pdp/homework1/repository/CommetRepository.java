package uz.pdp.homework1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.homework1.entity.Commet;

import java.util.Optional;


public interface CommetRepository extends JpaRepository<Commet, Long> {

}
