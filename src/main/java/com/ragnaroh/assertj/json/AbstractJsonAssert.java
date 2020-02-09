package com.ragnaroh.assertj.json;

import static java.util.Objects.requireNonNull;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.function.Function;

import org.assertj.core.api.AbstractAssert;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public abstract class AbstractJsonAssert<SELF extends AbstractJsonAssert<SELF, ACTUAL>, ACTUAL extends JsonElement>
      extends AbstractAssert<SELF, ACTUAL> {

   protected AbstractJsonAssert(ACTUAL actual, Class<SELF> selfType) {
      super(actual, selfType);
   }

   public SELF isEqualTo(String expected) {
      requireNonNull(expected);
      JsonElement expectedJson = new Gson().fromJson(expected, JsonElement.class);
      if (!expectedJson.equals(actual)) {
         failWithMessage("Expected <%s> to be equal to <%s>", actual, expected);
      }
      return myself;
   }

   static int[] unbox(Integer[] array) {
      int[] unboxed = new int[array.length];
      for (int i = 0; i < array.length; i++) {
         unboxed[i] = array[i].intValue();
      }
      return unboxed;
   }

   static double[] unbox(Double[] array) {
      double[] unboxed = new double[array.length];
      for (int i = 0; i < array.length; i++) {
         unboxed[i] = array[i].doubleValue();
      }
      return unboxed;
   }

   static boolean[] unbox(Boolean[] array) {
      boolean[] unboxed = new boolean[array.length];
      for (int i = 0; i < array.length; i++) {
         unboxed[i] = array[i].booleanValue();
      }
      return unboxed;
   }

   static Integer[] box(int[] array) {
      Integer[] boxed = new Integer[array.length];
      for (int i = 0; i < array.length; i++) {
         boxed[i] = Integer.valueOf(array[i]);
      }
      return boxed;
   }

   static Double[] box(double[] array) {
      Double[] boxed = new Double[array.length];
      for (int i = 0; i < array.length; i++) {
         boxed[i] = Double.valueOf(array[i]);
      }
      return boxed;
   }

   static Boolean[] box(boolean[] array) {
      Boolean[] boxed = new Boolean[array.length];
      for (int i = 0; i < array.length; i++) {
         boxed[i] = Boolean.valueOf(array[i]);
      }
      return boxed;
   }

   <T> T[] convertArray(JsonArray jsonArray, Class<T> elementType, Function<JsonElement, T> valueMapper) {
      T[] array = (T[]) Array.newInstance(elementType, jsonArray.size());
      for (int i = 0; i < jsonArray.size(); i++) {
         JsonElement jsonElement = jsonArray.get(i);
         T value = valueMapper.apply(jsonElement);
         if (value == null) {
            return null;
         }
         array[i] = value;
      }
      return array;
   }

   static String toString(JsonElement jsonElement) {
      if (jsonElement.isJsonPrimitive()) {
         JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
         if (jsonPrimitive.isString()) {
            return jsonPrimitive.getAsString();
         }
      }
      return null;
   }

   static Number toNumber(JsonElement jsonElement) {
      if (jsonElement.isJsonPrimitive()) {
         JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
         if (jsonPrimitive.isNumber()) {
            return jsonPrimitive.getAsNumber();
         }
      }
      return null;
   }

   static Number toIntegralNumber(JsonElement jsonElement) {
      if (jsonElement.isJsonPrimitive()) {
         JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
         if (isIntegralNumber(jsonPrimitive)) {
            return jsonPrimitive.getAsNumber();
         }
      }
      return null;
   }

   static BigDecimal toBigDecimal(JsonElement jsonElement) {
      if (jsonElement.isJsonPrimitive()) {
         JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
         if (jsonPrimitive.isNumber()) {
            return new BigDecimal(jsonPrimitive.getAsNumber().toString());
         }
      }
      return null;
   }

   static Double toDouble(JsonElement jsonElement) {
      Number number = toNumber(jsonElement);
      if (number != null) {
         return number.doubleValue();
      }
      return null;
   }

   static Integer toInteger(JsonElement jsonElement) {
      Number number = toIntegralNumber(jsonElement);
      if (number != null) {
         int value = number.intValue();
         String valueAsString = number.toString();
         if (valueAsString.equals(Integer.toString(value))) {
            return value;
         }
      }
      return null;
   }

   static boolean isIntegralNumber(JsonPrimitive jsonPrimitive) {
      return jsonPrimitive.isNumber() && jsonPrimitive.getAsString().matches("\\d+");
   }

   static Boolean toBoolean(JsonElement jsonElement) {
      if (jsonElement.isJsonPrimitive()) {
         JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
         if (jsonPrimitive.isBoolean()) {
            return jsonPrimitive.getAsBoolean();
         }
      }
      return null;
   }

   static JsonNull toJsonNull(JsonElement jsonElement) {
      if (jsonElement.isJsonNull()) {
         return jsonElement.getAsJsonNull();
      }
      return null;
   }

   static JsonObject toJsonObject(JsonElement jsonElement) {
      if (jsonElement.isJsonObject()) {
         return jsonElement.getAsJsonObject();
      }
      return null;
   }

   static JsonArray toJsonArray(JsonElement jsonElement) {
      if (jsonElement.isJsonArray()) {
         return jsonElement.getAsJsonArray();
      }
      return null;
   }

}
