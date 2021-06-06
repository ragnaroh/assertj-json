package com.ragnaroh.assertj.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class JsonArrayAssert extends AbstractArrayNodeAssert<JsonArrayAssert> {

   public JsonArrayAssert(String actual) throws JsonProcessingException {
      this(actual, new ObjectMapper());
   }

   public JsonArrayAssert(String actual, ObjectMapper mapper) throws JsonProcessingException {
      super(actual, JsonArrayAssert.class, mapper);
   }

   public JsonArrayAssert(ArrayNode actual) {
      super(actual, JsonArrayAssert.class);
   }

}
