package pl.coderslab.Driver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.Driver.entities.Advice;

import java.util.List;

public interface AdviceRepository extends JpaRepository<Advice, Long> {

    @Query(value = "SELECT * FROM advices WHERE id NOT IN " +
            "(SELECT DISTINCT advices.id FROM advices LEFT JOIN displays ON advices.id = displays.advice_id " +
            "WHERE displays.user_id = ?1)",
            nativeQuery = true)
    List<Advice> findNewAdvicesByUserId(Long userId);

    @Query("SELECT a FROM Advice a WHERE a.id = ?1")
    Advice findAdviceById(Long adviceId);

}
