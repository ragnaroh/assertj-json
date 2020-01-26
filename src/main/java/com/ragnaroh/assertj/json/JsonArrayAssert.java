package com.ragnaroh.assertj.json;

import com.google.gson.JsonArray;

public class JsonArrayAssert extends AbstractJsonArrayAssert<JsonArrayAssert> {

   public JsonArrayAssert(String actual) {
      super(actual, JsonArrayAssert.class);
   }

   public JsonArrayAssert(JsonArray actual) {
      super(actual, JsonArrayAssert.class);
   }

}
