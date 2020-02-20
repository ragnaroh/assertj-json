package com.ragnaroh.assertj.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Assertions extends org.assertj.core.api.Assertions {

   public static JsonElementAssert assertThatJson(String actual) {
      return new JsonElementAssert(actual);
   }

   public static JsonObjectAssert assertThat(JsonObject actual) {
      return new JsonObjectAssert(actual);
   }

   public static JsonArrayAssert assertThat(JsonArray actual) {
      return new JsonArrayAssert(actual);
   }

   public static JsonElementAssert assertThat(JsonElement actual) {
      return new JsonElementAssert(actual);
   }

}
