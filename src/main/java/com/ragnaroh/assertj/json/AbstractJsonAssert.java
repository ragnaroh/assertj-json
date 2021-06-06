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

   <T> T[] convertArray(ArrayNode arrayNode, Class<T> elementType, Function<JsonNode, T> valueMapper) {
      T[] array = (T[]) Array.newInstance(elementType, arrayNode.size());
      for (int i = 0; i < arrayNode.size(); i++) {
         JsonNode jsonNode = arrayNode.get(i);
         T value = valueMapper.apply(jsonNode);
         if (value == null) {
            return null;
         }
         array[i] = value;
      }
      return array;
   }

   static String toString(JsonNode jsonNode) {
      if (jsonNode.isTextual()) {
         return jsonNode.textValue();
      }
      return null;
   }

   static Number toNumber(JsonNode jsonNode) {
      if (jsonNode.isNumber()) {
         return jsonNode.numberValue();
      }
      return null;
   }

   static Number toIntegralNumber(JsonNode jsonNode) {
      return toInteger(jsonNode);
   }

   static BigDecimal toBigDecimal(JsonNode jsonNode) {
      if (jsonNode.isBigDecimal()) {
         return jsonNode.decimalValue();
      }
      return null;
   }

   static Double toDouble(JsonNode jsonNode) {
      Number number = toNumber(jsonNode);
      if (number != null) {
         return number.doubleValue();
      }
      return null;
   }

   static Integer toInteger(JsonNode jsonNode) {
      if (jsonNode.isIntegralNumber()) {
         return jsonNode.intValue();
      }
      return null;
   }

   static Boolean toBoolean(JsonNode jsonNode) {
      if (jsonNode.isBoolean()) {
         return jsonNode.booleanValue();
      }
      return null;
   }

   static NullNode toJsonNull(JsonNode jsonNode) {
      if (jsonNode instanceof NullNode) {
         return (NullNode) jsonNode;
      }
      return null;
   }

   static ObjectNode toObjectNode(JsonNode jsonNode) {
      if (jsonNode instanceof ObjectNode) {
         return (ObjectNode) jsonNode;
      }
      return null;
   }

   static ArrayNode toArrayNode(JsonNode jsonNode) {
      if (jsonNode instanceof ArrayNode) {
         return (ArrayNode) jsonNode;
      }
      return null;
   }

}
