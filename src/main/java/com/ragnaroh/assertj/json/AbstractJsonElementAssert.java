package com.ragnaroh.assertj.json;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;

import org.assertj.core.api.BigDecimalAssert;
import org.assertj.core.api.BooleanAssert;
import org.assertj.core.api.DoubleAssert;
import org.assertj.core.api.IntegerAssert;
import org.assertj.core.api.StringAssert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class AbstractJsonElementAssert<SELF extends AbstractJsonElementAssert<SELF>>
      extends AbstractJsonAssert<SELF, JsonNode> {

   protected AbstractJsonElementAssert(String actual, Class<SELF> selfType, ObjectMapper mapper)
         throws JsonProcessingException {
      super(toJsonElement(actual, mapper), selfType);
   }

   protected AbstractJsonElementAssert(JsonNode actual, Class<SELF> selfType) {
      super(actual, selfType);
   }

   private static JsonNode toJsonElement(String json, ObjectMapper mapper) throws JsonProcessingException {
      return mapper.readValue(json, JsonNode.class);
   }

   public SELF isAString() {
      isNotNull();
      String value = toString(actual);
      if (value == null) {
         failWithMessage("Expected JSON element to be a string, was <%s>", actual);
      }
      return myself;
   }

   public SELF isABigDecimal() {
      isNotNull();
      BigDecimal value = toBigDecimal(actual);
      if (value == null) {
         failWithMessage("Expected JSON element to be a number, was <%s>", actual);
      }
      return myself;
   }

   public SELF isADouble() {
      isNotNull();
      Double value = toDouble(actual);
      if (value == null) {
         failWithMessage("Expected JSON element to be a number, was <%s>", actual);
      }
      return myself;
   }

   public SELF isAnInteger() {
      isNotNull();
      Integer value = toInteger(actual);
      if (value == null) {
         failWithMessage("Expected JSON element to be an integer, was <%s>", actual);
      }
      return myself;
   }

   public SELF isABoolean() {
      isNotNull();
      Boolean value = toBoolean(actual);
      if (value == null) {
         failWithMessage("Expected JSON element to be a boolean, was <%s>", actual);
      }
      return myself;
   }

   public SELF isAJsonObject() {
      isNotNull();
      ObjectNode jsonObject = toJsonObject(actual);
      if (jsonObject == null) {
         failWithMessage("Expected JSON element to be an object, was <%s>", actual);
      }
      return myself;
   }

   public SELF isAJsonArray() {
      isNotNull();
      ArrayNode jsonArray = toJsonArray(actual);
      if (jsonArray == null) {
         failWithMessage("Expected JSON element to be an array, was <%s>", actual);
      }
      return myself;
   }

   public SELF isAStringEqualTo(String expected) {
      requireNonNull(expected);
      isNotNull();
      String value = toString(actual);
      if (value == null || !value.equals(expected)) {
         failWithMessage("Expected JSON element to be a string equal to <%s>, was <%s>", expected, actual);
      }
      return myself;
   }

   public SELF isANumberEqualTo(Number expected) {
      requireNonNull(expected);
      isNotNull();
      Number value = toNumber(actual);
      if (value == null || !value.equals(expected)) {
         failWithMessage("Expected JSON element to be a number equal to <%s>, was <%s>", expected, actual);
      }
      return myself;
   }

   public SELF isANumberEqualTo(BigDecimal expected) {
      requireNonNull(expected);
      isNotNull();
      BigDecimal value = toBigDecimal(actual);
      if (value == null || !value.equals(expected)) {
         failWithMessage("Expected JSON element to be a number equal to <%s>, was <%s>", expected, actual);
      }
      return myself;
   }

   public SELF isANumberEqualByComparingTo(BigDecimal expected) {
      requireNonNull(expected);
      isNotNull();
      BigDecimal value = toBigDecimal(actual);
      if (value == null || value.compareTo(expected) != 0) {
         failWithMessage("Expected JSON element to be a number equal to <%s>, was <%s>", expected, actual);
      }
      return myself;
   }

   public SELF isANumberEqualTo(double expected) {
      requireNonNull(expected);
      isNotNull();
      Double value = toDouble(actual);
      if (value == null || value.doubleValue() != expected) {
         failWithMessage("Expected JSON element to be a number equal to <%s>, was <%s>", actual);
      }
      return myself;
   }

   public SELF isAnIntegerEqualTo(int expected) {
      isNotNull();
      Integer value = toInteger(actual);
      if (value == null || value.intValue() != expected) {
         failWithMessage("Expected JSON element to be an integer equal to <%d>, was <%s>", expected, actual);
      }
      return myself;
   }

   public SELF isABooleanEqualTo(boolean expected) {
      isNotNull();
      Boolean value = toBoolean(actual);
      if (value == null) {
         failWithMessage("Expected JSON element to be a boolean equal to <%s>, was <%s>", expected, actual);
      }
      return myself;
   }

   public StringAssert asString() {
      isNotNull();
      String value = toString(actual);
      if (value == null) {
         failWithMessage("Expected JSON element to be a string, was <%s>", actual);
      }
      return new StringAssert(value);
   }

   public BigDecimalAssert asBigDecimal() {
      isNotNull();
      BigDecimal value = toBigDecimal(actual);
      if (value == null) {
         failWithMessage("Expected JSON element to be a number, was <%s>", actual);
      }
      return new BigDecimalAssert(value);
   }

   public DoubleAssert asDouble() {
      isNotNull();
      Double value = toDouble(actual);
      if (value == null) {
         failWithMessage("Expected JSON element to be a number, was <%s>", actual);
      }
      return new DoubleAssert(value);
   }

   public IntegerAssert asInteger() {
      isNotNull();
      Integer value = toInteger(actual);
      if (value == null) {
         failWithMessage("Expected JSON element to be an integer, was <%s>", actual);
      }
      return new IntegerAssert(value);
   }

   public BooleanAssert asBoolean() {
      isNotNull();
      Boolean value = toBoolean(actual);
      if (value == null) {
         failWithMessage("Expected JSON element to be a boolean, was <%s>", actual);
      }
      return new BooleanAssert(value);
   }

   public JsonObjectAssert asJsonObject() {
      isNotNull();
      ObjectNode jsonObject = toJsonObject(actual);
      if (jsonObject == null) {
         failWithMessage("Expected JSON element to be an object, was <%s>", actual);
      }
      return new JsonObjectAssert(jsonObject);
   }

   public JsonArrayAssert asJsonArray() {
      isNotNull();
      ArrayNode jsonArray = toJsonArray(actual);
      if (jsonArray == null) {
         failWithMessage("Expected JSON element to be an array, was <%s>", actual);
      }
      return new JsonArrayAssert(jsonArray);
   }

}
