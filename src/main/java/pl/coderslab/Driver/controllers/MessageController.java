package pl.coderslab.Driver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Driver.converters.*;
import pl.coderslab.Driver.dto.MessageDto;
import pl.coderslab.Driver.entities.Message;
import pl.coderslab.Driver.entities.User;
import pl.coderslab.Driver.repositories.MessageRepository;


@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    UserConverter userConverter;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MessageConverter messageConverter;

    @PostMapping("/postNewMessage")
    public MessageDto postNewMessage(@RequestBody MessageDto messageDto,
                               @RequestHeader(name="Authorization") String token) {
        User user = userConverter.convertTokenToUser(token);
        Message message = messageConverter.convertMessageDtoToMessage(messageDto, user);
        message =  messageRepository.save(message);
        return messageConverter.convertMessageToMessageDto(message);
    }

}
