package com.ragnaroh.assertj.json;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class JsonArrayAssert extends AbstractArrayNodeAssert<JsonArrayAssert> {

   public JsonArrayAssert(String actual) {
      super(actual, JsonArrayAssert.class);
   }

   public JsonArrayAssert(ArrayNode actual) {
      super(actual, JsonArrayAssert.class);
   }

}
