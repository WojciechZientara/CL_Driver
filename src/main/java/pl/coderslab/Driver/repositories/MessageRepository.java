package pl.coderslab.Driver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.Driver.entities.Conversation;
import pl.coderslab.Driver.entities.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
