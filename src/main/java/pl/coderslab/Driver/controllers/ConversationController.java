package pl.coderslab.Driver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Driver.converters.*;
import pl.coderslab.Driver.dto.ConversationDto;
import pl.coderslab.Driver.entities.Conversation;
import pl.coderslab.Driver.entities.User;
import pl.coderslab.Driver.repositories.ConversationRepository;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/conversation")
public class ConversationController {

    @Autowired
    UserConverter userConverter;

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    ConversationConverter conversationConverter;

    @PostMapping("/postNewConversation")
    public ResponseEntity<?> postNewConversation(@RequestBody ConversationDto conversationDto,
                                                 @RequestHeader(name="Authorization") String token) {
        User user = userConverter.convertTokenToUser(token);
        Conversation conversation = conversationConverter.convertConversationDtoToConversation(conversationDto);
        conversation = conversationRepository.save(conversation);
        return ResponseEntity.ok(conversationConverter.convertConversationToConversationDtoWithoutMessages(conversation));
    }

    @GetMapping("/getConversationsList/{adviceId}")
    public ResponseEntity<?> getConversations(@PathVariable Long adviceId) {
        List<Conversation> conversations = conversationRepository.findConversationsByAdvice_Id(adviceId);
        List<ConversationDto> conversationDtos = new ArrayList<>();
        for (Conversation conversation : conversations) {
            conversationDtos.add(conversationConverter.convertConversationToConversationDtoWithoutMessages(conversation));
        }
        return ResponseEntity.ok(conversationDtos);
    }

    @GetMapping("/getConversation/{conversationId}")
    public ResponseEntity<?> getConversation(@PathVariable Long conversationId) {
        Conversation conversation = conversationRepository.findConversationById(conversationId);
        ConversationDto conversationDto = conversationConverter.convertConversationToConversationDtoWithMessages(conversation);
        return ResponseEntity.ok(conversationDto);
    }

    @GetMapping("/search/{phrase}")
    public ResponseEntity<?> getSearchResults(@PathVariable String phrase) {

        List<Conversation> conversations = conversationRepository.
                findConversationsBySubjectContainingIgnoreCase(phrase);
        List<ConversationDto> dtos = new ArrayList<>();
        for (Conversation conversation : conversations) {
            dtos.add(conversationConverter.convertConversationToConversationDtoWithoutMessages(conversation));
        }
        return ResponseEntity.ok(dtos);
    }
}
