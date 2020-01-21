package pl.coderslab.Driver.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import pl.coderslab.Driver.dto.ConversationDto;
import pl.coderslab.Driver.dto.MessageDto;
import pl.coderslab.Driver.entities.Conversation;
import pl.coderslab.Driver.repositories.UserRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;

public class ConversationDtoDeserializer extends JsonDeserializer<ConversationDto> {

    @Override
    public ConversationDto deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        ConversationDto conversationDto = new ConversationDto();
        conversationDto.setMessageDtos(new HashSet<>());

        conversationDto.setAdviceId(node.get("adviceId").asLong());
        conversationDto.setSubject(node.get("subject").asText());

        JsonNode messagesDtosNode = node.get("messageDtos");
        for (JsonNode messageDtoNode : messagesDtosNode){
            MessageDto messageDto = new MessageDto();
            messageDto.setContent(messageDtoNode.get("content").asText());
            conversationDto.getMessageDtos().add(messageDto);
        }

        return conversationDto;

    }

}
