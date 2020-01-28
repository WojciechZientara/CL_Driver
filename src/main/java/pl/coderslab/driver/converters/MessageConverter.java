package pl.coderslab.driver.converters;

import org.springframework.stereotype.Component;
import pl.coderslab.driver.dto.MessageDto;
import pl.coderslab.driver.entities.Conversation;
import pl.coderslab.driver.entities.Message;
import pl.coderslab.driver.entities.User;

@Component
public class MessageConverter {

    public Message convertMessageDtoToMessage(MessageDto messageDto, User user, Conversation conversation) {
        Message message = new Message();
        message.setContent(messageDto.getContent());
        message.setUser(user);
        message.setConversation(conversation);
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

}
