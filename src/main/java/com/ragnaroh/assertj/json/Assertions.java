package com.ragnaroh.assertj.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Assertions extends org.assertj.core.api.Assertions {

   public static JsonAssert assertThatJson(String actual) {
      return new JsonAssert(actual);
   }

   public static JsonObjectAssert assertThat(JsonObject actual) {
      return new JsonObjectAssert(actual);
   }

   public static JsonArrayAssert assertThat(JsonArray actual) {
      return new JsonArrayAssert(actual);
   }

}
