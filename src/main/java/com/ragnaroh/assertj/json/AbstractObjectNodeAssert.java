package com.ragnaroh.assertj.json;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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

@SuppressWarnings({ "java:S2160", "java:S119" })
public abstract class AbstractObjectNodeAssert<SELF extends AbstractObjectNodeAssert<SELF>>
      extends AbstractJsonAssert<SELF, ObjectNode> {

   private final Set<String> assertedFields = new HashSet<>();

   protected AbstractObjectNodeAssert(String actual, Class<SELF> selfType) {
      this(toObjectNode(actual), selfType);
   }

   protected AbstractObjectNodeAssert(ObjectNode actual, Class<SELF> selfType) {
      super(actual, selfType);
   }

   private static ObjectNode toObjectNode(String json) {
      try {
         return new ObjectMapper().readValue(json, ObjectNode.class);
      } catch (JsonProcessingException e) {
         throw new IllegalArgumentException("Could not parse actual value as JSON object node: " + e.getMessage());
      }
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
      getInteger(fieldName);
      return myself;
   }

   public SELF containsBoolean(String fieldName) {
      requireNonNull(fieldName);
      getBoolean(fieldName);
      return myself;
   }

   public SELF containsNull(String fieldName) {
      requireNonNull(fieldName);
      getNullNode(fieldName);
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
         throw fieldValueFailure(fieldName, expectedValue, actualValue);
      }
      return myself;
   }

   public SELF contains(String fieldName, int expectedValue) {
      requireNonNull(fieldName);
      Integer actualValue = getInteger(fieldName);
      if (actualValue.intValue() != expectedValue) {
         throw fieldValueFailure(fieldName, expectedValue, actualValue);
      }
      return myself;
   }

   public SELF contains(String fieldName, double expectedValue) {
      requireNonNull(fieldName);
      Number actualValue = getNumber(fieldName);
      if (actualValue.doubleValue() != expectedValue) {
         throw fieldValueFailure(fieldName, expectedValue, actualValue);
      }
      return myself;
   }

   public SELF contains(String fieldName, BigDecimal expectedValue) {
      requireNonNull(fieldName);
      requireNonNull(expectedValue);
      Number actualValue = getNumber(fieldName);
      if (new BigDecimal(actualValue.toString()).compareTo(expectedValue) != 0) {
         throw fieldValueFailure(fieldName, expectedValue, actualValue);
      }
      return myself;
   }

   public SELF containsNumber(String fieldName, String expectedValueAsString) {
      requireNonNull(fieldName);
      requireNonNull(expectedValueAsString);
      Number actualValue = getNumber(fieldName);
      if (new BigDecimal(actualValue.toString()).compareTo(new BigDecimal(expectedValueAsString)) != 0) {
         throw fieldValueFailure(fieldName, expectedValueAsString, actualValue);
      }
      return myself;
   }

   public SELF contains(String fieldName, boolean expectedValue) {
      requireNonNull(fieldName);
      boolean actualValue = getBoolean(fieldName);
      if (actualValue != expectedValue) {
         throw fieldValueFailure(fieldName, expectedValue, actualValue);
      }
      return myself;
   }

   public SELF contains(String fieldName, LocalDateTime expectedValue) {
      requireNonNull(fieldName);
      requireNonNull(expectedValue);
      LocalDateTime actualValue = getLocalDateTime(fieldName);
      if (!actualValue.equals(expectedValue)) {
         throw fieldValueFailure(fieldName, expectedValue, actualValue);
      }
      return myself;
   }

   public SELF contains(String fieldName, ZonedDateTime expectedValue) {
      requireNonNull(fieldName);
      requireNonNull(expectedValue);
      ZonedDateTime actualValue = getZonedDateTime(fieldName);
      if (!actualValue.isEqual(expectedValue)) {
         throw fieldValueFailure(fieldName, expectedValue, actualValue);
      }
      return myself;
   }

   public SELF contains(String fieldName, Instant expectedValue) {
      requireNonNull(fieldName);
      requireNonNull(expectedValue);
      Instant actualValue = getInstant(fieldName);
      if (!actualValue.equals(expectedValue)) {
         throw fieldValueFailure(fieldName, expectedValue, actualValue);
      }
      return myself;
   }

   public SELF containsLocalDateTime(String fieldName, String expectedLocalDateTimeAsString) {
      requireNonNull(fieldName);
      requireNonNull(expectedLocalDateTimeAsString);
      LocalDateTime actualValue = getLocalDateTime(fieldName);
      if (!actualValue.equals(LocalDateTime.parse(expectedLocalDateTimeAsString))) {
         throw fieldValueFailure(fieldName, expectedLocalDateTimeAsString, actualValue);
      }
      return myself;
   }

   public SELF containsInstant(String fieldName, String expectedInstantAsString) {
      requireNonNull(fieldName);
      requireNonNull(expectedInstantAsString);
      Instant actualValue = getInstant(fieldName);
      if (!actualValue.equals(Instant.parse(expectedInstantAsString))) {
         throw fieldValueFailure(fieldName, expectedInstantAsString, actualValue);
      }
      return myself;
   }

   private AssertionError fieldValueFailure(String fieldName, Object expectedValue, Object actualValue) {
      return fieldFailure(fieldName, "Expected value <%s>, was: <%s>", expectedValue, actualValue);
   }

   public SELF containsNodeSatisfying(String fieldName, Consumer<JsonNode> valueRequirements) {
      requireNonNull(fieldName);
      requireNonNull(valueRequirements);
      valueRequirements.accept(getJsonNode(fieldName));
      return myself;
   }

   public <T> SELF containsNodeSatisfying(String fieldName, Class<T> nodeType, Consumer<T> valueRequirements) {
      requireNonNull(fieldName);
      requireNonNull(valueRequirements);
      valueRequirements.accept(getNode(fieldName, nodeType));
      return myself;
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
      valueRequirements.accept(getInteger(fieldName));
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
         throw fieldFailure(fieldName, "Expected value matching regex <%s>, was: <%s>", valueRegex, actualValue);
      }
      return myself;
   }

   public SELF containsNumberMatching(String fieldName, String valueRegex) {
      requireNonNull(fieldName);
      requireNonNull(valueRegex);
      Number actualValue = getNumber(fieldName);
      if (!actualValue.toString().matches(valueRegex)) {
         throw fieldFailure(fieldName, "Expected value matching regex <%s>, was: <%s>", valueRegex, actualValue);
      }
      return myself;
   }

   public SELF containsEmptyObject(String fieldName) {
      requireNonNull(fieldName);
      ObjectNode actualValue = getObjectNode(fieldName);
      if (actualValue.size() != 0) {
         throw fieldFailure(fieldName, "Expected empty object, was: <%s>", actualValue);
      }
      return myself;
   }

   public SELF containsEmptyArray(String fieldName) {
      requireNonNull(fieldName);
      ArrayNode actualValue = getArrayNode(fieldName);
      if (actualValue.size() != 0) {
         throw fieldFailure(fieldName, "Expected empty array, was: <%s>", actualValue);
      }
      return myself;
   }

   public SELF containsLocalDateTimeSatisfying(String fieldName, Consumer<LocalDateTime> valueRequirements) {
      requireNonNull(fieldName);
      valueRequirements.accept(getLocalDateTime(fieldName));
      return myself;
   }

   public SELF containsInstantSatisfying(String fieldName, Consumer<ZonedDateTime> valueRequirements) {
      requireNonNull(fieldName);
      valueRequirements.accept(getZonedDateTime(fieldName));
      return myself;
   }

   public SELF containsLocalDateTime(String fieldName) {
      requireNonNull(fieldName);
      getLocalDateTime(fieldName);
      return myself;
   }

   private LocalDateTime getLocalDateTime(String fieldName) {
      JsonNode node = getJsonNode(fieldName);
      try {
         return mapper.treeToValue(node, LocalDateTime.class);
      } catch (JsonProcessingException e) {
         throw fieldFailure(fieldName, "Expected field to be parsable as LocalDateTime, was <%s>", node);
      }
   }

   private ZonedDateTime getZonedDateTime(String fieldName) {
      JsonNode node = getJsonNode(fieldName);
      try {
         return mapper.treeToValue(node, ZonedDateTime.class);
      } catch (JsonProcessingException e) {
         throw fieldFailure(fieldName, "Expected field to be parsable as ZonedDateTime, was <%s>", node);
      }
   }

   private Instant getInstant(String fieldName) {
      JsonNode node = getJsonNode(fieldName);
      try {
         return mapper.treeToValue(node, Instant.class);
      } catch (JsonProcessingException e) {
         throw fieldFailure(fieldName, "Expected field to be parsable as Instant, was <%s>", node);
      }
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
      return containsArrayOfSize(fieldName, Number.class, AbstractJsonAssert::toInteger, expectedSize);
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
         throw fieldFailure(fieldName, "Expected array of size <%d>, size was <%d>", expectedSize, actualValue.length);
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

   public SELF containsIntegerArraySatisfying(String fieldName, Consumer<Integer[]> requirements) {
      requireNonNull(fieldName);
      Integer[] actualValue = getArray(fieldName, Integer.class, AbstractJsonAssert::toInteger);
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
         throw fieldFailure(fieldName, "Unexpected type in array, was: <%s>", arrayNode);
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
      JsonNode jsonNode = getJsonNode(fieldName);
      String string = toString(jsonNode);
      if (string == null) {
         throw fieldFailure(fieldName, "Expected string, was: <%s>", jsonNode);
      }
      return string;
   }

   private Number getNumber(String fieldName) {
      isNotNull();
      JsonNode jsonNode = getJsonNode(fieldName);
      Number value = toNumber(jsonNode);
      if (value == null) {
         throw fieldFailure(fieldName, "Expected number, was: <%s>", jsonNode);
      }
      return value;
   }

   private Integer getInteger(String fieldName) {
      isNotNull();
      JsonNode jsonNode = getJsonNode(fieldName);
      Integer value = toInteger(jsonNode);
      if (value == null) {
         throw fieldFailure(fieldName, "Expected integral number, was: <%s>", jsonNode);
      }
      return value;
   }

   private boolean getBoolean(String fieldName) {
      isNotNull();
      JsonNode jsonNode = getJsonNode(fieldName);
      Boolean value = toBoolean(jsonNode);
      if (value == null) {
         throw fieldFailure(fieldName, "Expected boolean, was: <%s>", jsonNode);
      }
      return value;
   }

   private NullNode getNullNode(String fieldName) {
      isNotNull();
      JsonNode jsonNode = getJsonNode(fieldName);
      NullNode value = toJsonNull(jsonNode);
      if (value == null) {
         throw fieldFailure(fieldName, "Expected <null>, was: <%s>", jsonNode);
      }
      return value;
   }

   private ObjectNode getObjectNode(String fieldName) {
      isNotNull();
      JsonNode jsonNode = getJsonNode(fieldName);
      ObjectNode value = toObjectNode(jsonNode);
      if (value == null) {
         throw fieldFailure(fieldName, "Expected JSON object, was: <%s>", jsonNode);
      }
      return value;
   }

   private ArrayNode getArrayNode(String fieldName) {
      isNotNull();
      JsonNode jsonNode = getJsonNode(fieldName);
      ArrayNode value = toArrayNode(jsonNode);
      if (value == null) {
         throw fieldFailure(fieldName, "Expected JSON array, was: <%s>", jsonNode);
      }
      return value;
   }

   private JsonNode getJsonNode(String fieldName) {
      isNotNull();
      JsonNode value = actual.get(fieldName);
      if (value == null) {
         failWithMessage("Expected field named \"%s\"", fieldName);
      }
      markAsAsserted(fieldName);
      return value;
   }

   private <T> T getNode(String fieldName, Class<T> nodeType) {
      isNotNull();
      JsonNode node = actual.get(fieldName);
      try {
         T value = mapper.treeToValue(node, nodeType);
         if (value == null) {
            failWithMessage("Expected field named \"%s\"", fieldName);
         }
         markAsAsserted(fieldName);
         return value;
      } catch (JsonProcessingException e) {
         throw fieldFailure(fieldName, "Could not convert value <%s> to <%s>", node, nodeType);
      }
   }

   protected final void markAsAsserted(String fieldName) {
      assertedFields.add(fieldName);
   }

   private AssertionError fieldFailure(String fieldName, String message, Object... arguments) {
      return failure("Field \"" + fieldName + "\": " + message, arguments);
   }

}
