package com.ragnaroh.assertj.json;

import org.assertj.core.api.AbstractAssert;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class JsonAssert extends AbstractAssert<JsonAssert, String> {

   public JsonAssert(String actual) {
      super(actual, JsonAssert.class);
   }

   public JsonObjectAssert asObject() {
      isNotNull();
      Gson gson = new Gson();
      try {
         return new JsonObjectAssert(gson.fromJson(actual, JsonObject.class));
      } catch (JsonSyntaxException ex) {
         failWithMessage("Not a valid JSON object: %s", actual);
         return null;
      }
   }

   public JsonArrayAssert asArray() {
      isNotNull();
      Gson gson = new Gson();
      try {
         return new JsonArrayAssert(gson.fromJson(actual, JsonArray.class));
      } catch (JsonSyntaxException ex) {
         failWithMessage("Not a valid JSON array: %s", actual);
         return null;
      }
   }

}
