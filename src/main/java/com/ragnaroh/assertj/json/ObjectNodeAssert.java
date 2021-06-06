package com.ragnaroh.assertj.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ObjectNodeAssert extends AbstractObjectNodeAssert<ObjectNodeAssert> {

   public ObjectNodeAssert(String actual) throws JsonProcessingException {
      this(actual, new ObjectMapper());
   }

   public ObjectNodeAssert(String actual, ObjectMapper mapper) throws JsonProcessingException {
      super(actual, ObjectNodeAssert.class, mapper);
   }

   public ObjectNodeAssert(ObjectNode actual) {
      super(actual, ObjectNodeAssert.class);
   }

}
