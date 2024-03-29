package com.ragnaroh.assertj.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SuppressWarnings("java:S2176")
public class Assertions extends org.assertj.core.api.Assertions {

   public static JsonNodeAssert assertThatJson(String actual) {
      return new JsonNodeAssert(actual);
   }

   public static ObjectNodeAssert assertThatJsonObject(String actual) {
      return new ObjectNodeAssert(actual);
   }

   public static ArrayNodeAssert assertThatJsonArray(String actual) {
      return new ArrayNodeAssert(actual);
   }

   public static ObjectNodeAssert assertThat(ObjectNode actual) {
      return new ObjectNodeAssert(actual);
   }

   public static ArrayNodeAssert assertThat(ArrayNode actual) {
      return new ArrayNodeAssert(actual);
   }

   public static JsonNodeAssert assertThat(JsonNode actual) {
      return new JsonNodeAssert(actual);
   }

}
