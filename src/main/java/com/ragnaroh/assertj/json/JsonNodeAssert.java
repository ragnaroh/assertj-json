package com.ragnaroh.assertj.json;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonNodeAssert extends AbstractJsonNodeAssert<JsonNodeAssert> {

   public JsonNodeAssert(String actual) {
      super(actual, JsonNodeAssert.class);
   }

   public JsonNodeAssert(JsonNode actual) {
      super(actual, JsonNodeAssert.class);
   }

}
