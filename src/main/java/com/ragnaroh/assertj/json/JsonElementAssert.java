package com.ragnaroh.assertj.json;

import com.google.gson.JsonElement;

public class JsonElementAssert extends AbstractJsonElementAssert<JsonElementAssert> {

   public JsonElementAssert(String actual) {
      super(actual, JsonElementAssert.class);
   }

   public JsonElementAssert(JsonElement actual) {
      super(actual, JsonElementAssert.class);
   }

}
