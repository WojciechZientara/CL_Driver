package pl.coderslab.driver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.driver.converters.*;
import pl.coderslab.driver.dto.ConversationDto;
import pl.coderslab.driver.entities.Advice;
import pl.coderslab.driver.entities.Conversation;
import pl.coderslab.driver.entities.Message;
import pl.coderslab.driver.entities.User;
import pl.coderslab.driver.jwt.JwtUserDetailsService;
import pl.coderslab.driver.repositories.AdviceRepository;
import pl.coderslab.driver.repositories.ConversationRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/conversation")
public class ConversationController {

    @Autowired
    JwtUserDetailsService userDetailsService;

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    ConversationConverter conversationConverter;

    @Autowired
    AdviceRepository adviceRepository;

    @Autowired
    MessageConverter messageConverter;

    @GetMapping("/{conversationId}")
    public ResponseEntity<?> getConversation(@PathVariable Long conversationId) {
        Conversation conversation = conversationRepository.findConversationById(conversationId);
        Set messages = new HashSet<>();
        for (Message message : conversation.getMessages()) {
            messages.add(messageConverter.convertMessageToMessageDto(message));
        }
        ConversationDto conversationDto = conversationConverter.
                convertConversationToConversationDtoWithMessages(conversation, messages);
        return ResponseEntity.ok(conversationDto);
    }

    @PostMapping("/")
    public ResponseEntity<?> addNewConversation(@RequestBody ConversationDto conversationDto,
                                                 @RequestHeader(name="Authorization") String token) {
        User user = userDetailsService.convertTokenToUser(token);
        Advice advice = adviceRepository.findAdviceById(conversationDto.getAdviceId());
        Conversation conversation = conversationConverter.
                convertConversationDtoToConversation(conversationDto, advice);
        conversation = conversationRepository.save(conversation);
        return ResponseEntity.ok(conversationConverter.convertConversationToConversationDtoWithoutMessages(conversation));
    }

    @PutMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> editConversation(@RequestBody ConversationDto conversationDto) {
        Conversation conversation = conversationRepository.findConversationById(conversationDto.getId());
        Advice advice = adviceRepository.findAdviceById(conversationDto.getAdviceId());
        Conversation update = conversationConverter.
                convertConversationDtoToConversation(conversationDto, advice);
        conversation = conversationConverter.applyChanges(conversation, update);
        conversationRepository.save(conversation);
        conversationDto = conversationConverter.convertConversationToConversationDtoWithoutMessages(conversation);
        return ResponseEntity.ok(conversationDto);
    }

    @DeleteMapping("/{conversationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteConversation(@PathVariable Long conversationId) {
        conversationRepository.deleteById(conversationId);
        return ResponseEntity.ok().body("Success");
    }

    @GetMapping("/getlist/{adviceId}")
    public ResponseEntity<?> getConversationsByAdviceId(@PathVariable Long adviceId) {
        List<Conversation> conversations = conversationRepository.findConversationsByAdvice_Id(adviceId);
        List<ConversationDto> conversationDtos = new ArrayList<>();
        for (Conversation conversation : conversations) {
            conversationDtos.add(conversationConverter.convertConversationToConversationDtoWithoutMessages(conversation));
        }
        return ResponseEntity.ok(conversationDtos);
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
