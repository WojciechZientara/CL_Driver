package pl.coderslab.driver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.driver.entities.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Message findMessageById(Long messageId);

}
