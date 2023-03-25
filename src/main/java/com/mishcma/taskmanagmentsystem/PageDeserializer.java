package com.mishcma.taskmanagmentsystem;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mishcma.taskmanagmentsystem.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PageDeserializer<T> extends StdDeserializer<Page<T>> {

    public PageDeserializer() {
        super(Page.class);
    }

    @Override
    public Page<T> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode rootNode = mapper.readTree(jsonParser);

        int pageNumber = rootNode.get("number").asInt();
        int pageSize = rootNode.get("size").asInt();
        int totalElements = rootNode.get("totalElements").asInt();

        List<T> content = new ArrayList<>();
        JsonNode contentNode = rootNode.get("content");
        if (contentNode.isArray()) {
            for (JsonNode node : contentNode) {
                content.add((T) mapper.treeToValue(node, Task.class));
            }
        }

        return new PageImpl<>(content, PageRequest.of(pageNumber, pageSize), totalElements);
    }
}