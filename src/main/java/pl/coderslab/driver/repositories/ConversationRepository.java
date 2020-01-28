package pl.coderslab.driver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.driver.entities.Conversation;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Conversation findConversationById(Long conversationId);
    List<Conversation> findConversationsByAdvice_Id(Long adviceId);
    List<Conversation> findConversationsBySubjectContainingIgnoreCase(String phrase);

}
