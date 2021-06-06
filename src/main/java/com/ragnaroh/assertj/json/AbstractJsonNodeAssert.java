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

public abstract class AbstractJsonNodeAssert<SELF extends AbstractJsonNodeAssert<SELF>>
      extends AbstractJsonAssert<SELF, JsonNode> {

   protected AbstractJsonNodeAssert(String actual, Class<SELF> selfType, ObjectMapper mapper)
         throws JsonProcessingException {
      super(toJsonElement(actual, mapper), selfType);
   }

   protected AbstractJsonNodeAssert(JsonNode actual, Class<SELF> selfType) {
      super(actual, selfType);
   }

   private static JsonNode toJsonElement(String json, ObjectMapper mapper) throws JsonProcessingException {
      return mapper.readValue(json, JsonNode.class);
   }

   public SELF isString() {
      isNotNull();
      String value = toString(actual);
      if (value == null) {
         failWithMessage("Expected JSON node to be a string, was <%s>", actual);
      }
      return myself;
   }

   public SELF isBigDecimal() {
      isNotNull();
      BigDecimal value = toBigDecimal(actual);
      if (value == null) {
         failWithMessage("Expected JSON node to be a number, was <%s>", actual);
      }
      return myself;
   }

   public SELF isDouble() {
      isNotNull();
      Double value = toDouble(actual);
      if (value == null) {
         failWithMessage("Expected JSON node to be a number, was <%s>", actual);
      }
      return myself;
   }

   public SELF isInteger() {
      isNotNull();
      Integer value = toInteger(actual);
      if (value == null) {
         failWithMessage("Expected JSON node to be an integer, was <%s>", actual);
      }
      return myself;
   }

   public SELF isBoolean() {
      isNotNull();
      Boolean value = toBoolean(actual);
      if (value == null) {
         failWithMessage("Expected JSON node to be a boolean, was <%s>", actual);
      }
      return myself;
   }

   public SELF isObjectNode() {
      isNotNull();
      ObjectNode objectNode = toObjectNode(actual);
      if (objectNode == null) {
         failWithMessage("Expected JSON node to be an object, was <%s>", actual);
      }
      return myself;
   }

   public SELF isArrayNode() {
      isNotNull();
      ArrayNode arrayNode = toArrayNode(actual);
      if (arrayNode == null) {
         failWithMessage("Expected JSON node to be an array, was <%s>", actual);
      }
      return myself;
   }

   public SELF isStringEqualTo(String expected) {
      requireNonNull(expected);
      isNotNull();
      String value = toString(actual);
      if (value == null || !value.equals(expected)) {
         failWithMessage("Expected JSON node to be a string equal to <%s>, was <%s>", expected, actual);
      }
      return myself;
   }

   public SELF isANumberEqualTo(Number expected) {
      requireNonNull(expected);
      isNotNull();
      Number value = toNumber(actual);
      if (value == null || !value.equals(expected)) {
         failWithMessage("Expected JSON node to be a number equal to <%s>, was <%s>", expected, actual);
      }
      return myself;
   }

   public SELF isNumberEqualTo(BigDecimal expected) {
      requireNonNull(expected);
      isNotNull();
      BigDecimal value = toBigDecimal(actual);
      if (value == null || !value.equals(expected)) {
         failWithMessage("Expected JSON node to be a number equal to <%s>, was <%s>", expected, actual);
      }
      return myself;
   }

   public SELF isNumberEqualByComparingTo(BigDecimal expected) {
      requireNonNull(expected);
      isNotNull();
      BigDecimal value = toBigDecimal(actual);
      if (value == null || value.compareTo(expected) != 0) {
         failWithMessage("Expected JSON node to be a number equal to <%s>, was <%s>", expected, actual);
      }
      return myself;
   }

   public SELF isNumberEqualTo(double expected) {
      requireNonNull(expected);
      isNotNull();
      Double value = toDouble(actual);
      if (value == null || value.doubleValue() != expected) {
         failWithMessage("Expected JSON node to be a number equal to <%s>, was <%s>", actual);
      }
      return myself;
   }

   public SELF isIntegerEqualTo(int expected) {
      isNotNull();
      Integer value = toInteger(actual);
      if (value == null || value.intValue() != expected) {
         failWithMessage("Expected JSON node to be an integer equal to <%d>, was <%s>", expected, actual);
      }
      return myself;
   }

   public SELF isBooleanEqualTo(boolean expected) {
      isNotNull();
      Boolean value = toBoolean(actual);
      if (value == null) {
         failWithMessage("Expected JSON node to be a boolean equal to <%s>, was <%s>", expected, actual);
      }
      return myself;
   }

   public StringAssert asString() {
      isNotNull();
      String value = toString(actual);
      if (value == null) {
         failWithMessage("Expected JSON node to be a string, was <%s>", actual);
      }
      return new StringAssert(value);
   }

   public BigDecimalAssert asBigDecimal() {
      isNotNull();
      BigDecimal value = toBigDecimal(actual);
      if (value == null) {
         failWithMessage("Expected JSON node to be a number, was <%s>", actual);
      }
      return new BigDecimalAssert(value);
   }

   public DoubleAssert asDouble() {
      isNotNull();
      Double value = toDouble(actual);
      if (value == null) {
         failWithMessage("Expected JSON node to be a number, was <%s>", actual);
      }
      return new DoubleAssert(value);
   }

   public IntegerAssert asInteger() {
      isNotNull();
      Integer value = toInteger(actual);
      if (value == null) {
         failWithMessage("Expected JSON node to be an integer, was <%s>", actual);
      }
      return new IntegerAssert(value);
   }

   public BooleanAssert asBoolean() {
      isNotNull();
      Boolean value = toBoolean(actual);
      if (value == null) {
         failWithMessage("Expected JSON node to be a boolean, was <%s>", actual);
      }
      return new BooleanAssert(value);
   }

   public ObjectNodeAssert asObjectNode() {
      isNotNull();
      ObjectNode objectNode = toObjectNode(actual);
      if (objectNode == null) {
         failWithMessage("Expected JSON node to be an object, was <%s>", actual);
      }
      return new ObjectNodeAssert(objectNode);
   }

   public JsonArrayAssert asArrayNode() {
      isNotNull();
      ArrayNode arrayNode = toArrayNode(actual);
      if (arrayNode == null) {
         failWithMessage("Expected JSON node to be an array, was <%s>", actual);
      }
      return new JsonArrayAssert(arrayNode);
   }

}
