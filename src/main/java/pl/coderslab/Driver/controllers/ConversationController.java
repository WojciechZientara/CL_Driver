package pl.coderslab.Driver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Driver.converters.*;
import pl.coderslab.Driver.dto.AdviceDto;
import pl.coderslab.Driver.dto.ConversationDto;
import pl.coderslab.Driver.dto.MessageDto;
import pl.coderslab.Driver.entities.Advice;
import pl.coderslab.Driver.entities.Conversation;
import pl.coderslab.Driver.entities.Message;
import pl.coderslab.Driver.entities.User;
import pl.coderslab.Driver.repositories.AdviceRepository;
import pl.coderslab.Driver.repositories.ConversationRepository;
import pl.coderslab.Driver.repositories.MessageRepository;
import pl.coderslab.Driver.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/conversation")
public class ConversationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserConverter userConverter;

    @Autowired
    AdviceRepository adviceRepository;

    @Autowired
    DisplayConverter displayConverter;

    @Autowired
    AdviceConverter adviceConverter;

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    ConversationConverter conversationConverter;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MessageConverter messageConverter;


    @PostMapping("/postNewConversation")
    public void postNewConversation(@RequestBody ConversationDto conversationDto,
                                    @RequestHeader(name="Authorization") String token) {
        User user = userConverter.convertTokenToUser(token);
        Conversation conversation = conversationConverter.convertConversationDtoToConversation(conversationDto);
        conversationRepository.save(conversation);
        conversationRepository.flush();
        for (MessageDto messageDto : conversationDto.getMessageDtos()) {
            Message message = messageConverter.convertMessageDtoToMessage(messageDto, user, conversation);
            messageRepository.save(message);
        }
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

    @PostMapping("/postNewMessage")
    public void postNewMessage(@RequestBody MessageDto messageDto,
                               @RequestHeader(name="Authorization") String token) {
        User user = userConverter.convertTokenToUser(token);
        Message message = messageConverter.convertMessageDtoToMessage(messageDto, user);
        messageRepository.save(message);
    }

}
