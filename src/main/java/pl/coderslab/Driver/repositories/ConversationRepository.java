package pl.coderslab.Driver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.Driver.entities.Conversation;
import pl.coderslab.Driver.entities.TestResult;

import javax.persistence.OrderBy;
import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    Conversation findConversationById(Long conversationId);
    List<Conversation> findConversationsByAdvice_Id(Long adviceId);
    List<Conversation> findConversationsBySubjectContainingIgnoreCase(String phrase);


}
