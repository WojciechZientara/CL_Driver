package pl.coderslab.Driver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.Driver.entities.Answer;
import pl.coderslab.Driver.entities.Test;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
