package com.ragnaroh.assertj.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonNodeAssert extends AbstractJsonNodeAssert<JsonNodeAssert> {

   public JsonNodeAssert(String actual) throws JsonProcessingException {
      this(actual, new ObjectMapper());
   }

   public JsonNodeAssert(String actual, ObjectMapper mapper) throws JsonProcessingException {
      super(actual, JsonNodeAssert.class, mapper);
   }

   public JsonNodeAssert(JsonNode actual) {
      super(actual, JsonNodeAssert.class);
   }

}
