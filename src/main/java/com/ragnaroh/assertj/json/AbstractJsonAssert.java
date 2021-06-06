package com.ragnaroh.assertj.json;

import static java.util.Objects.requireNonNull;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.function.Function;

import org.assertj.core.api.AbstractAssert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class AbstractJsonAssert<SELF extends AbstractJsonAssert<SELF, ACTUAL>, ACTUAL extends JsonNode>
      extends AbstractAssert<SELF, ACTUAL> {

   protected AbstractJsonAssert(ACTUAL actual, Class<SELF> selfType) {
      super(actual, selfType);
   }

   public SELF isEqualTo(String expected) throws JsonProcessingException {
      requireNonNull(expected);
      JsonNode expectedJson = new ObjectMapper().readValue(expected, JsonNode.class);
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

   <T> T[] convertArray(ArrayNode jsonArray, Class<T> elementType, Function<JsonNode, T> valueMapper) {
      T[] array = (T[]) Array.newInstance(elementType, jsonArray.size());
      for (int i = 0; i < jsonArray.size(); i++) {
         JsonNode jsonElement = jsonArray.get(i);
         T value = valueMapper.apply(jsonElement);
         if (value == null) {
            return null;
         }
         array[i] = value;
      }
      return array;
   }

   static String toString(JsonNode jsonElement) {
      if (jsonElement.isTextual()) {
         return jsonElement.textValue();
      }
      return null;
   }

   static Number toNumber(JsonNode jsonElement) {
      if (jsonElement.isNumber()) {
         return jsonElement.numberValue();
      }
      return null;
   }

   static Number toIntegralNumber(JsonNode jsonElement) {
      return toInteger(jsonElement);
   }

   static BigDecimal toBigDecimal(JsonNode jsonElement) {
      if (jsonElement.isBigDecimal()) {
         return jsonElement.decimalValue();
      }
      return null;
   }

   static Double toDouble(JsonNode jsonElement) {
      Number number = toNumber(jsonElement);
      if (number != null) {
         return number.doubleValue();
      }
      return null;
   }

   static Integer toInteger(JsonNode jsonElement) {
      if (jsonElement.isIntegralNumber()) {
         return jsonElement.intValue();
      }
      return null;
   }

   static Boolean toBoolean(JsonNode jsonElement) {
      if (jsonElement.isBoolean()) {
         return jsonElement.booleanValue();
      }
      return null;
   }

   static NullNode toJsonNull(JsonNode jsonElement) {
      if (jsonElement instanceof NullNode) {
         return (NullNode) jsonElement;
      }
      return null;
   }

   static ObjectNode toJsonObject(JsonNode jsonElement) {
      if (jsonElement instanceof ObjectNode) {
         return (ObjectNode) jsonElement;
      }
      return null;
   }

   static ArrayNode toJsonArray(JsonNode jsonElement) {
      if (jsonElement instanceof ArrayNode) {
         return (ArrayNode) jsonElement;
      }
      return null;
   }

}
