package pl.coderslab.Driver.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.coderslab.Driver.dto.MessageDto;
import pl.coderslab.Driver.entities.Conversation;
import pl.coderslab.Driver.entities.Message;
import pl.coderslab.Driver.entities.User;
import pl.coderslab.Driver.repositories.AdviceRepository;
import pl.coderslab.Driver.repositories.ConversationRepository;
import pl.coderslab.Driver.repositories.UserRepository;

import java.util.HashSet;

@Component
public class MessageConverter {

    @Autowired
    AdviceRepository adviceRepository;

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    UserRepository userRepository;
    
    public Message convertMessageDtoToMessage(MessageDto messageDto, User user, Conversation conversation) {
        Message message = setBasicMessageData(messageDto, user);
        message.setConversation(conversation);
        return message;
    }

    public Message convertMessageDtoToMessage(MessageDto messageDto, User user) {
        Message message = setBasicMessageData(messageDto, user);
        message.setConversation(conversationRepository.findConversationById(messageDto.getConversationId()));
        return message;
    }

    public MessageDto convertMessageToMessageDto(Message message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setConversationId(message.getConversation().getId());
        messageDto.setContent(message.getContent());
        messageDto.setUserId(message.getUser().getId());
        messageDto.setCreated(message.getCreated());
        return messageDto;
    }

    public Message setBasicMessageData(MessageDto messageDto, User user) {
        Message message = new Message();
        message.setContent(messageDto.getContent());
        message.setUser(user);
        return message;
    }
}
