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
    public ConversationDto postNewConversation(@RequestBody ConversationDto conversationDto,
                                                 @RequestHeader(name="Authorization") String token) {
        User user = userConverter.convertTokenToUser(token);
        Conversation conversation = conversationConverter.convertConversationDtoToConversation(conversationDto);
        conversation = conversationRepository.save(conversation);
        return conversationConverter.convertConversationToConversationDtoWithoutMessages(conversation);
    }

    @GetMapping("/getConversationsList/{adviceId}")
    List<ConversationDto> getConversations(@PathVariable Long adviceId) {
        List<Conversation> conversations = conversationRepository.findConversationsByAdvice_Id(adviceId);
        List<ConversationDto> conversationDtos = new ArrayList<>();
        for (Conversation conversation : conversations) {
            conversationDtos.add(conversationConverter.convertConversationToConversationDtoWithoutMessages(conversation));
        }
        return conversationDtos;
    }

    @GetMapping("/getConversation/{conversationId}")
    ConversationDto getConversation(@PathVariable Long conversationId) {
        Conversation conversation = conversationRepository.findConversationById(conversationId);
        ConversationDto conversationDto = conversationConverter.convertConversationToConversationDtoWithMessages(conversation);
        return conversationDto;
    }

}
