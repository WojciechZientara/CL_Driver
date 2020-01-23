package pl.coderslab.Driver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Driver.converters.ConversationConverter;
import pl.coderslab.Driver.dto.ConversationDto;
import pl.coderslab.Driver.entities.Conversation;
import pl.coderslab.Driver.repositories.ConversationRepository;


@RestController
@RequestMapping("/admin/conversation")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminConversationController {

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    ConversationConverter conversationConverter;

    @PutMapping("/editConversation")
    public ResponseEntity<?> editConversation(@RequestBody ConversationDto conversationDto) {
        Conversation conversation = conversationRepository.findConversationById(conversationDto.getId());
        Conversation update = conversationConverter.convertConversationDtoToConversation(conversationDto);
        conversation = conversationConverter.updateConversation(conversation, update);
        conversationRepository.save(conversation);
        conversationDto = conversationConverter.convertConversationToConversationDtoWithoutMessages(conversation);
        return ResponseEntity.ok(conversationDto);
    }

    @DeleteMapping("/deleteConversation/{conversationId}")
    public ResponseEntity<?> deleteConversation(@PathVariable Long conversationId) {
        conversationRepository.deleteById(conversationId);
        return ResponseEntity.ok().body("Success");
    }


}
