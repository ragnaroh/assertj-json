package com.ragnaroh.assertj.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Assertions extends org.assertj.core.api.Assertions {

   public static JsonNodeAssert assertThatJson(String actual) throws JsonProcessingException {
      return new JsonNodeAssert(actual);
   }

   public static JsonNodeAssert assertThatJson(String actual, ObjectMapper mapper) throws JsonProcessingException {
      return new JsonNodeAssert(actual, mapper);
   }

   public static ObjectNodeAssert assertThat(ObjectNode actual) {
      return new ObjectNodeAssert(actual);
   }

   public static JsonArrayAssert assertThat(ArrayNode actual) {
      return new JsonArrayAssert(actual);
   }

   public static JsonNodeAssert assertThat(JsonNode actual) {
      return new JsonNodeAssert(actual);
   }

}
