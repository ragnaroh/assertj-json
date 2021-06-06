package com.ragnaroh.assertj.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonElementAssert extends AbstractJsonElementAssert<JsonElementAssert> {

   public JsonElementAssert(String actual) throws JsonProcessingException {
      this(actual, new ObjectMapper());
   }

   public JsonElementAssert(String actual, ObjectMapper mapper) throws JsonProcessingException {
      super(actual, JsonElementAssert.class, mapper);
   }

   public JsonElementAssert(JsonNode actual) {
      super(actual, JsonElementAssert.class);
   }

}
