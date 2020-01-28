package pl.coderslab.driver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.driver.entities.TestResult;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {

}
