package pl.coderslab.driver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.driver.entities.Answer;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Answer findAnswerById(Long answerId);
    List<Answer> findAnswersByTestId(Long testId);

}
