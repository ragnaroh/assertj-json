package com.ragnaroh.assertj.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Assertions extends org.assertj.core.api.Assertions {

   public static JsonElementAssert assertThatJson(String actual) throws JsonProcessingException {
      return new JsonElementAssert(actual);
   }

   public static JsonElementAssert assertThatJson(String actual, ObjectMapper mapper) throws JsonProcessingException {
      return new JsonElementAssert(actual, mapper);
   }

   public static JsonObjectAssert assertThat(ObjectNode actual) {
      return new JsonObjectAssert(actual);
   }

   public static JsonArrayAssert assertThat(ArrayNode actual) {
      return new JsonArrayAssert(actual);
   }

   public static JsonElementAssert assertThat(JsonNode actual) {
      return new JsonElementAssert(actual);
   }

}
