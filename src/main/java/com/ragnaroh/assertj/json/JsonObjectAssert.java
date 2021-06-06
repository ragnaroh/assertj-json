package com.ragnaroh.assertj.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonObjectAssert extends AbstractJsonObjectAssert<JsonObjectAssert> {

   public JsonObjectAssert(String actual) throws JsonProcessingException {
      this(actual, new ObjectMapper());
   }

   public JsonObjectAssert(String actual, ObjectMapper mapper) throws JsonProcessingException {
      super(actual, JsonObjectAssert.class, mapper);
   }

   public JsonObjectAssert(ObjectNode actual) {
      super(actual, JsonObjectAssert.class);
   }

}
