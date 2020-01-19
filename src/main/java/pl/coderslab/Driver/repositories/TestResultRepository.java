package pl.coderslab.Driver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.Driver.entities.Test;
import pl.coderslab.Driver.entities.TestResult;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {

}
