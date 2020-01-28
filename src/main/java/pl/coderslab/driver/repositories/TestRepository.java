package pl.coderslab.driver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.driver.entities.Test;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {

    Test findTestById (Long testId);

    @Query("SELECT t FROM Test t JOIN FETCH t.advice a WHERE a.id = ?1")
    List<Test> findTestWithAnswersByAdviceId(Long adviceId);

}
