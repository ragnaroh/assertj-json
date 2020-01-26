package com.ragnaroh.assertj.json;

import com.google.gson.JsonObject;

public class JsonObjectAssert extends AbstractJsonObjectAssert<JsonObjectAssert> {

   public JsonObjectAssert(String actual) {
      super(actual, JsonObjectAssert.class);
   }

   public JsonObjectAssert(JsonObject actual) {
      super(actual, JsonObjectAssert.class);
   }

}
