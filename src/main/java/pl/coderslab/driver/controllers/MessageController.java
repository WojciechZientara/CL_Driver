package pl.coderslab.driver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.driver.converters.*;
import pl.coderslab.driver.dto.MessageDto;
import pl.coderslab.driver.entities.Conversation;
import pl.coderslab.driver.entities.Message;
import pl.coderslab.driver.entities.User;
import pl.coderslab.driver.jwt.JwtUserDetailsService;
import pl.coderslab.driver.repositories.ConversationRepository;
import pl.coderslab.driver.repositories.MessageRepository;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MessageConverter messageConverter;

    @Autowired
    ConversationRepository conversationRepository;

    @GetMapping("/{messageId}")
    public ResponseEntity<?> getMessage(@PathVariable Long messageId){

        Message message = messageRepository.findMessageById(messageId);
        if (message == null) {
            MessageDto empty = new MessageDto();
            empty.setContent("No message with such id");
            return ResponseEntity.ok(empty);
        } else {
            return ResponseEntity.ok(messageConverter.convertMessageToMessageDto(message));
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> addMessage(@RequestBody MessageDto messageDto,
                                            @RequestHeader(name="Authorization") String token) {
        User user = jwtUserDetailsService.convertTokenToUser(token);
        Conversation conversation = conversationRepository.findConversationById(messageDto.getConversationId());
        Message message = messageConverter.convertMessageDtoToMessage(messageDto, user, conversation);
        message =  messageRepository.save(message);
        return ResponseEntity.ok(messageConverter.convertMessageToMessageDto(message));
    }

    @PutMapping("/")
    public ResponseEntity<?> editMessage(HttpServletRequest request, @RequestBody MessageDto messageDto,
                                     @RequestHeader(name="Authorization") String token) {
        User user = jwtUserDetailsService.convertTokenToUser(token);
        Message message = messageRepository.findMessageById(messageDto.getId());
        if (request.isUserInRole("ROLE_ADMIN") || message.getUser().getId() == user.getId()) {
            message.setContent(messageDto.getContent());
            messageRepository.save(message);
            return ResponseEntity.ok(messageConverter.convertMessageToMessageDto(message));
        } else {
            return ResponseEntity.badRequest().body("Not allowed to modify this message.");
        }
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<?> deleteMessage(HttpServletRequest request, @PathVariable Long messageId,
                                        @RequestHeader(name="Authorization") String token) {
        User user = jwtUserDetailsService.convertTokenToUser(token);
        Message message = messageRepository.findMessageById(messageId);
        if (request.isUserInRole("ROLE_ADMIN") || message.getUser().getId() == user.getId()) {
            messageRepository.deleteById(messageId);
            return ResponseEntity.ok().body("Success");
        } else {
            return ResponseEntity.badRequest().body("Not allowed to delete this message.");
        }
    }

}
