package com.ragnaroh.assertj.json;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class AbstractObjectNodeAssert<SELF extends AbstractObjectNodeAssert<SELF>>
      extends AbstractJsonAssert<SELF, ObjectNode> {

   private final Set<String> assertedFields = new HashSet<>();

   protected AbstractObjectNodeAssert(String actual, Class<SELF> selfType, ObjectMapper mapper)
         throws JsonProcessingException {
      this(toObjectNode(actual, mapper), selfType);
   }

   protected AbstractObjectNodeAssert(ObjectNode actual, Class<SELF> selfType) {
      super(actual, selfType);
   }

   private static ObjectNode toObjectNode(String json, ObjectMapper mapper) throws JsonProcessingException {
      return mapper.readValue(json, ObjectNode.class);
   }

   public SELF isEmpty() {
      isNotNull();
      Iterable<String> iterable = actual::fieldNames;
      List<String> fieldNames = StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
      if (!fieldNames.isEmpty()) {
         failWithMessage("Expected JSON object to be empty, contained fields: %s", fieldNames);
      }
      return myself;
   }

   public SELF contains(String fieldName) {
      requireNonNull(fieldName);
      isNotNull();
      if (!actual.has(fieldName)) {
         failWithMessage("No field named \"%s\"", fieldName);
      }
      markAsAsserted(fieldName);
      return myself;
   }

   public SELF containsString(String fieldName) {
      requireNonNull(fieldName);
      getString(fieldName);
      return myself;
   }

   public SELF containsNumber(String fieldName) {
      requireNonNull(fieldName);
      getNumber(fieldName);
      return myself;
   }

   public SELF containsInteger(String fieldName) {
      requireNonNull(fieldName);
      getIntegralNumber(fieldName);
      return myself;
   }

   public SELF containsBoolean(String fieldName) {
      requireNonNull(fieldName);
      getBoolean(fieldName);
      return myself;
   }

   public SELF containsNull(String fieldName) {
      requireNonNull(fieldName);
      getJsonNull(fieldName);
      return myself;
   }

   public SELF containsObject(String fieldName) {
      requireNonNull(fieldName);
      getObjectNode(fieldName);
      return myself;
   }

   public SELF containsArray(String fieldName) {
      requireNonNull(fieldName);
      getArrayNode(fieldName);
      return myself;
   }

   public SELF contains(String fieldName, String expectedValue) {
      requireNonNull(fieldName);
      requireNonNull(expectedValue);
      String actualValue = getString(fieldName);
      if (!expectedValue.equals(actualValue)) {
         failValueCompare(fieldName, expectedValue, actualValue);
      }
      return myself;
   }

   public SELF contains(String fieldName, int expectedValue) {
      requireNonNull(fieldName);
      Number actualValue = getIntegralNumber(fieldName);
      if (actualValue.intValue() != expectedValue) {
         failValueCompare(fieldName, expectedValue, actualValue);
      }
      return myself;
   }

   public SELF contains(String fieldName, long expectedValue) {
      requireNonNull(fieldName);
      Number actualValue = getIntegralNumber(fieldName);
      if (actualValue.longValue() != expectedValue) {
         failValueCompare(fieldName, expectedValue, actualValue);
      }
      return myself;
   }

   public SELF contains(String fieldName, double expectedValue) {
      requireNonNull(fieldName);
      Number actualValue = getNumber(fieldName);
      if (Double.doubleToLongBits(actualValue.doubleValue()) != Double.doubleToLongBits(expectedValue)) {
         failValueCompare(fieldName, expectedValue, actualValue);
      }
      return myself;
   }

   public SELF contains(String fieldName, BigDecimal expectedValue) {
      requireNonNull(fieldName);
      requireNonNull(expectedValue);
      Number actualValue = getNumber(fieldName);
      if (new BigDecimal(actualValue.toString()).compareTo(expectedValue) != 0) {
         failValueCompare(fieldName, expectedValue, actualValue);
      }
      return myself;
   }

   public SELF containsNumber(String fieldName, String expectedValueAsString) {
      requireNonNull(fieldName);
      requireNonNull(expectedValueAsString);
      Number actualValue = getNumber(fieldName);
      if (new BigDecimal(actualValue.toString()).compareTo(new BigDecimal(expectedValueAsString)) != 0) {
         failValueCompare(fieldName, expectedValueAsString, actualValue);
      }
      return myself;
   }

   public SELF contains(String fieldName, boolean expectedValue) {
      requireNonNull(fieldName);
      boolean actualValue = getBoolean(fieldName);
      if (actualValue != expectedValue) {
         failValueCompare(fieldName, expectedValue, actualValue);
      }
      return myself;
   }

   public SELF contains(String fieldName, LocalDateTime expectedValue) {
      requireNonNull(fieldName);
      requireNonNull(expectedValue);
      LocalDateTime actualValue = getLocalDateTime(fieldName);
      if (!actualValue.equals(expectedValue)) {
         failValueCompare(fieldName, expectedValue, actualValue);
      }
      return myself;
   }

   public SELF containsLocalDateTime(String fieldName, String expectedValue) {
      requireNonNull(fieldName);
      requireNonNull(expectedValue);
      LocalDateTime actualValue = getLocalDateTime(fieldName);
      if (!actualValue.equals(LocalDateTime.parse(expectedValue))) {
         failValueCompare(fieldName, expectedValue, actualValue);
      }
      return myself;
   }

   private void failValueCompare(String fieldName, Object expectedValue, Object actualValue) {
      failFieldWithMessage(fieldName, "Expected value <%s>, was: <%s>", expectedValue, actualValue);
   }

   public SELF containsStringSatisfying(String fieldName, Consumer<String> valueRequirements) {
      requireNonNull(fieldName);
      requireNonNull(valueRequirements);
      valueRequirements.accept(getString(fieldName));
      return myself;
   }

   public SELF containsIntSatisfying(String fieldName, Consumer<Integer> valueRequirements) {
      requireNonNull(fieldName);
      requireNonNull(valueRequirements);
      valueRequirements.accept(getIntegralNumber(fieldName).intValue());
      return myself;
   }

   public SELF containsLongSatisfying(String fieldName, Consumer<Long> valueRequirements) {
      requireNonNull(fieldName);
      requireNonNull(valueRequirements);
      valueRequirements.accept(getIntegralNumber(fieldName).longValue());
      return myself;
   }

   public SELF containsDoubleSatisfying(String fieldName, Consumer<Double> valueRequirements) {
      requireNonNull(fieldName);
      requireNonNull(valueRequirements);
      valueRequirements.accept(getNumber(fieldName).doubleValue());
      return myself;
   }

   public SELF containsBigDecimalSatisfying(String fieldName, Consumer<BigDecimal> valueRequirements) {
      requireNonNull(fieldName);
      requireNonNull(valueRequirements);
      valueRequirements.accept(new BigDecimal(getNumber(fieldName).toString()));
      return myself;
   }

   public SELF containsObjectSatisfying(String fieldName, Consumer<ObjectNode> valueRequirements) {
      requireNonNull(fieldName);
      requireNonNull(valueRequirements);
      valueRequirements.accept(getObjectNode(fieldName));
      return myself;
   }

   public SELF containsArraySatisfying(String fieldName, Consumer<ArrayNode> valueRequirements) {
      requireNonNull(fieldName);
      requireNonNull(valueRequirements);
      valueRequirements.accept(getArrayNode(fieldName));
      return myself;
   }

   public SELF containsStringMatching(String fieldName, String valueRegex) {
      requireNonNull(fieldName);
      requireNonNull(valueRegex);
      String actualValue = getString(fieldName);
      if (!actualValue.matches(valueRegex)) {
         failFieldWithMessage(fieldName, "Expected value matching regex <%s>, was: <%s>", valueRegex, actualValue);
      }
      return myself;
   }

   public SELF containsNumberMatching(String fieldName, String valueRegex) {
      requireNonNull(fieldName);
      requireNonNull(valueRegex);
      Number actualValue = getNumber(fieldName);
      if (!actualValue.toString().matches(valueRegex)) {
         failFieldWithMessage(fieldName, "Expected value matching regex <%s>, was: <%s>", valueRegex, actualValue);
      }
      return myself;
   }

   public SELF containsEmptyObject(String fieldName) {
      requireNonNull(fieldName);
      ObjectNode actualValue = getObjectNode(fieldName);
      if (actualValue.size() != 0) {
         failFieldWithMessage(fieldName, "Expected empty object, was: <%s>", actualValue);
      }
      return myself;
   }

   public SELF containsEmptyArray(String fieldName) {
      requireNonNull(fieldName);
      ArrayNode actualValue = getArrayNode(fieldName);
      if (actualValue.size() != 0) {
         failFieldWithMessage(fieldName, "Expected empty array, was: <%s>", actualValue);
      }
      return myself;
   }

   public SELF containsLocalDateTimeArray(String fieldName, LocalDateTime expectedValue) {
      requireNonNull(fieldName);
      requireNonNull(expectedValue);
      LocalDateTime actualValue = getLocalDateTime(fieldName);
      if (!actualValue.equals(expectedValue)) {
         failFieldWithMessage(fieldName, "Expected datetime %s, was: <%s>", expectedValue, actualValue);
      }
      return myself;
   }

   public SELF containsLocalDateTimeSatisfying(String fieldName, Consumer<LocalDateTime> valueRequirements) {
      requireNonNull(fieldName);
      valueRequirements.accept(getLocalDateTime(fieldName));
      return myself;
   }

   public SELF containsAnyLocalDateTime(String fieldName) {
      requireNonNull(fieldName);
      getLocalDateTime(fieldName);
      return myself;
   }

   private LocalDateTime getLocalDateTime(String fieldName) {
      ArrayNode arrayNode = getArrayNode(fieldName);
      Integer[] array = getArray(fieldName, Integer.class, AbstractJsonAssert::toInteger);
      try {
         if (array.length == 5) {
            return LocalDateTime.of(array[0], array[1], array[2], array[3], array[4]);
         }
         if (array.length == 6) {
            return LocalDateTime.of(array[0], array[1], array[2], array[3], array[4], array[5]);
         }
         if (array.length == 7) {
            return LocalDateTime.of(array[0], array[1], array[2], array[3], array[4], array[5], array[6]);
         }
      } catch (DateTimeException ex) {
         failFieldWithMessage(fieldName, "Invalid datetime array: <%s>", arrayNode);
      }
      failFieldWithMessage(fieldName, "Expected integer array of size 5, 6 or 7, size was: <%d>", array.length);
      return null;
   }

   public SELF containsStringArrayOfSize(String fieldName, int expectedSize) {
      requireNonNull(fieldName);
      return containsArrayOfSize(fieldName, String.class, AbstractJsonAssert::toString, expectedSize);
   }

   public SELF containsNumberArrayOfSize(String fieldName, int expectedSize) {
      requireNonNull(fieldName);
      return containsArrayOfSize(fieldName, Number.class, AbstractJsonAssert::toNumber, expectedSize);
   }

   public SELF containsIntegerArrayOfSize(String fieldName, int expectedSize) {
      requireNonNull(fieldName);
      return containsArrayOfSize(fieldName, Number.class, AbstractJsonAssert::toIntegralNumber, expectedSize);
   }

   public SELF containsBooleanArrayOfSize(String fieldName, int expectedSize) {
      requireNonNull(fieldName);
      return containsArrayOfSize(fieldName, Boolean.class, AbstractJsonAssert::toBoolean, expectedSize);
   }

   public SELF containsObjectArrayOfSize(String fieldName, int expectedSize) {
      requireNonNull(fieldName);
      return containsArrayOfSize(fieldName, ObjectNode.class, AbstractJsonAssert::toObjectNode, expectedSize);
   }

   public SELF containsArrayArrayOfSize(String fieldName, int expectedSize) {
      requireNonNull(fieldName);
      return containsArrayOfSize(fieldName, ArrayNode.class, AbstractJsonAssert::toArrayNode, expectedSize);
   }

   private <T> SELF containsArrayOfSize(String fieldName,
                                        Class<T> elementType,
                                        Function<JsonNode, T> valueGetter,
                                        int expectedSize) {
      T[] actualValue = getArray(fieldName, elementType, valueGetter);
      if (actualValue.length != expectedSize) {
         failFieldWithMessage(fieldName,
                              "Expected array of size <%d>, size was <%d>",
                              expectedSize,
                              actualValue.length);
      }
      return myself;
   }

   public SELF containsStringArraySatisfying(String fieldName, Consumer<String[]> requirements) {
      requireNonNull(fieldName);
      String[] actualValue = getArray(fieldName, String.class, AbstractJsonAssert::toString);
      requirements.accept(actualValue);
      return myself;
   }

   public SELF containsNumberArraySatisfying(String fieldName, Consumer<Number[]> requirements) {
      requireNonNull(fieldName);
      Number[] actualValue = getArray(fieldName, Number.class, AbstractJsonAssert::toNumber);
      requirements.accept(actualValue);
      return myself;
   }

   public SELF containsIntArraySatisfying(String fieldName, Consumer<Integer[]> requirements) {
      requireNonNull(fieldName);
      Integer[] actualValue = getArray(fieldName, Integer.class, AbstractJsonAssert::toInteger);
      requirements.accept(actualValue);
      return myself;
   }

   public SELF containsDoubleArraySatisfying(String fieldName, Consumer<Double[]> requirements) {
      requireNonNull(fieldName);
      Double[] actualValue = getArray(fieldName, Double.class, AbstractJsonAssert::toDouble);
      requirements.accept(actualValue);
      return myself;
   }

   public SELF containsBigDecimalArraySatisfying(String fieldName, Consumer<BigDecimal[]> requirements) {
      requireNonNull(fieldName);
      BigDecimal[] actualValue = getArray(fieldName, BigDecimal.class, AbstractJsonAssert::toBigDecimal);
      requirements.accept(actualValue);
      return myself;
   }

   public SELF containsBooleanArraySatisfying(String fieldName, Consumer<Boolean[]> requirements) {
      requireNonNull(fieldName);
      Boolean[] actualValue = getArray(fieldName, Boolean.class, AbstractJsonAssert::toBoolean);
      requirements.accept(actualValue);
      return myself;
   }

   public SELF containsObjectArraySatisfying(String fieldName, Consumer<ObjectNode[]> requirements) {
      requireNonNull(fieldName);
      ObjectNode[] actualValue = getArray(fieldName, ObjectNode.class, AbstractJsonAssert::toObjectNode);
      requirements.accept(actualValue);
      return myself;
   }

   public SELF containsArrayArraySatisfying(String fieldName, Consumer<ArrayNode[]> requirements) {
      requireNonNull(fieldName);
      ArrayNode[] actualValue = getArray(fieldName, ArrayNode.class, AbstractJsonAssert::toArrayNode);
      requirements.accept(actualValue);
      return myself;
   }

   private <T> T[] getArray(String fieldName, Class<T> elementType, Function<JsonNode, T> valueGetter) {
      ArrayNode arrayNode = getArrayNode(fieldName);
      T[] array = convertArray(arrayNode, elementType, valueGetter);
      if (array == null) {
         failFieldWithMessage(fieldName, "Unexpected type in array, was: <%s>", arrayNode);
      }
      return array;
   }

   /**
    * Verifies that all fields have been asserted (by any of the "contains*" methods).
    */
   public void containsNoUnassertedFields() {
      Iterator<String> actualFields = actual.fieldNames();
      List<String> additionalFields = new ArrayList<>();
      while (actualFields.hasNext()) {
         String field = actualFields.next();
         if (!assertedFields.contains(field)) {
            additionalFields.add(field);
         }
      }
      if (!additionalFields.isEmpty()) {
         failWithMessage("Found additional fields: <%s>", additionalFields);
      }
   }

   private String getString(String fieldName) {
      isNotNull();
      JsonNode jsonNode = getJsonElement(fieldName);
      String string = toString(jsonNode);
      if (string == null) {
         failFieldWithMessage(fieldName, "Expected string, was: <%s>", jsonNode);
      }
      return string;
   }

   private Number getNumber(String fieldName) {
      isNotNull();
      JsonNode jsonNode = getJsonElement(fieldName);
      Number value = toNumber(jsonNode);
      if (value == null) {
         failFieldWithMessage(fieldName, "Expected number, was: <%s>", jsonNode);
      }
      return value;
   }

   private Number getIntegralNumber(String fieldName) {
      isNotNull();
      JsonNode jsonNode = getJsonElement(fieldName);
      Number value = toIntegralNumber(jsonNode);
      if (value == null) {
         failFieldWithMessage(fieldName, "Expected integral number, was: <%s>", jsonNode);
      }
      return value;
   }

   private boolean getBoolean(String fieldName) {
      isNotNull();
      JsonNode jsonNode = getJsonElement(fieldName);
      Boolean value = toBoolean(jsonNode);
      if (value == null) {
         failFieldWithMessage(fieldName, "Expected boolean, was: <%s>", jsonNode);
      }
      return value;
   }

   private NullNode getJsonNull(String fieldName) {
      isNotNull();
      JsonNode jsonNode = getJsonElement(fieldName);
      NullNode value = toJsonNull(jsonNode);
      if (value == null) {
         failFieldWithMessage(fieldName, "Expected <null>, was: <%s>", jsonNode);
      }
      return value;
   }

   private ObjectNode getObjectNode(String fieldName) {
      isNotNull();
      JsonNode jsonNode = getJsonElement(fieldName);
      ObjectNode value = toObjectNode(jsonNode);
      if (value == null) {
         failFieldWithMessage(fieldName, "Expected JSON object, was: <%s>", jsonNode);
      }
      return value;
   }

   private ArrayNode getArrayNode(String fieldName) {
      isNotNull();
      JsonNode jsonNode = getJsonElement(fieldName);
      ArrayNode value = toArrayNode(jsonNode);
      if (value == null) {
         failFieldWithMessage(fieldName, "Expected JSON array, was: <%s>", jsonNode);
      }
      return value;
   }

   private JsonNode getJsonElement(String fieldName) {
      isNotNull();
      JsonNode value = actual.get(fieldName);
      if (value == null) {
         failWithMessage("Expected field named \"%s\"", fieldName);
      }
      markAsAsserted(fieldName);
      return value;
   }

   protected final void markAsAsserted(String fieldName) {
      assertedFields.add(fieldName);
   }

   private void failFieldWithMessage(String fieldName, String message, Object... arguments) {
      failWithMessage("Field \"" + fieldName + "\": " + message, arguments);
   }

}
