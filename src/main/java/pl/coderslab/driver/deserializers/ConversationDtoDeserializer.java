package pl.coderslab.driver.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import pl.coderslab.driver.dto.ConversationDto;
import java.io.IOException;

public class ConversationDtoDeserializer extends JsonDeserializer<ConversationDto> {

    @Override
    public ConversationDto deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        ConversationDto conversationDto = new ConversationDto();

        if (node.get("id") != null) {
            Long conversationId = node.get("id").asLong();
            conversationDto.setId(conversationId);
        }
        conversationDto.setAdviceId(node.get("adviceId").asLong());
        conversationDto.setSubject(node.get("subject").asText());

        return conversationDto;

    }

}
