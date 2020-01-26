package com.ragnaroh.assertj.json;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

public abstract class AbstractJsonObjectAssert<SELF extends AbstractJsonObjectAssert<SELF>>
      extends AbstractJsonAssert<SELF, JsonObject> {

   private final Set<String> assertedFields = new HashSet<>();

   protected AbstractJsonObjectAssert(String actual, Class<SELF> selfType) {
      this(toJsonObject(actual), selfType);
   }

   protected AbstractJsonObjectAssert(JsonObject actual, Class<SELF> selfType) {
      super(actual, selfType);
   }

   private static JsonObject toJsonObject(String json) {
      return new Gson().fromJson(json, JsonObject.class);
   }

   public SELF isEmpty() {
      isNotNull();
      Set<String> fieldNames = actual.keySet();
      if (!fieldNames.isEmpty()) {
         failWithMessage("Expected JSON object to be empty, contained fields: %s", fieldNames);
      }
      return myself;
   }

   public SELF isEqualTo(String expected) {
      requireNonNull(expected);
      JsonObject expectedJson = new Gson().fromJson(expected, JsonObject.class);
      if (!expectedJson.equals(actual)) {
         failWithMessage("Expected <%s> to be equal to <%s>", actual, expected);
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
      getJsonObject(fieldName);
      return myself;
   }

   public SELF containsArray(String fieldName) {
      requireNonNull(fieldName);
      getJsonArray(fieldName);
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

   private void failValueCompare(String fieldName, Object expectedValue, Object actualValue) {
      failWithMessage("Expected field \"%s\" to have the value <%s>, was <%s>", fieldName, expectedValue, actualValue);
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

   public SELF containsObjectSatisfying(String fieldName, Consumer<JsonObject> valueRequirements) {
      requireNonNull(fieldName);
      requireNonNull(valueRequirements);
      valueRequirements.accept(getJsonObject(fieldName));
      return myself;
   }

   public SELF containsArraySatisfying(String fieldName, Consumer<JsonArray> valueRequirements) {
      requireNonNull(fieldName);
      requireNonNull(valueRequirements);
      valueRequirements.accept(getJsonArray(fieldName));
      return myself;
   }

   public SELF containsStringMatching(String fieldName, String valueRegex) {
      requireNonNull(fieldName);
      requireNonNull(valueRegex);
      String actualValue = getString(fieldName);
      if (!actualValue.matches(valueRegex)) {
         failWithMessage("Expected \"%s\" field to match regex <%s>, was <%s>", fieldName, valueRegex, actualValue);
      }
      return myself;
   }

   public SELF containsNumberMatching(String fieldName, String valueRegex) {
      requireNonNull(fieldName);
      requireNonNull(valueRegex);
      Number actualValue = getNumber(fieldName);
      if (!actualValue.toString().matches(valueRegex)) {
         failWithMessage("Expected \"%s\" field to match regex <%s>, was <%s>", fieldName, valueRegex, actualValue);
      }
      return myself;
   }

   public SELF containsNullValue(String fieldName) {
      requireNonNull(fieldName);
      JsonElement actualValue = getJsonElement(fieldName);
      if (!actualValue.isJsonNull()) {
         failWithMessage("Expected \"%s\" field to be <null>, was <%s>", fieldName, actualValue);
      }
      return myself;
   }

   public SELF containsEmptyObject(String fieldName) {
      requireNonNull(fieldName);
      JsonObject actualValue = getJsonObject(fieldName);
      if (actualValue.size() != 0) {
         failWithMessage("Expected \"%s\" field to be an empty object, was: %s", fieldName, actualValue);
      }
      return myself;
   }

   public SELF containsEmptyArray(String fieldName) {
      requireNonNull(fieldName);
      JsonArray actualValue = getJsonArray(fieldName);
      if (actualValue.size() != 0) {
         failWithMessage("Expected \"%s\" field to be an empty array, was: %s", fieldName, actualValue);
      }
      return myself;
   }

   public SELF containsLocalDateTimeArray(String fieldName, LocalDateTime expectedValue) {
      requireNonNull(fieldName);
      requireNonNull(expectedValue);
      LocalDateTime actualValue = getLocalDateTime(fieldName);
      if (!actualValue.equals(expectedValue)) {
         failWithMessage("Expected \"%s\" field to represent LocalDateTime <%s>, was <%s>",
                         fieldName,
                         expectedValue,
                         actualValue);
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
      JsonArray jsonArray = getJsonArray(fieldName);
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
         failWithMessage("Field \"%s\" did not represent a valid datetime: %s", fieldName, jsonArray);
      }
      failWithMessage("Expected \"%s\" field size of LocalDateTime array to be 5, 6 or 7, was <%d>", array.length);
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
      return containsArrayOfSize(fieldName, JsonObject.class, AbstractJsonAssert::toJsonObject, expectedSize);
   }

   public SELF containsArrayArrayOfSize(String fieldName, int expectedSize) {
      requireNonNull(fieldName);
      return containsArrayOfSize(fieldName, JsonArray.class, AbstractJsonAssert::toJsonArray, expectedSize);
   }

   private <T> SELF containsArrayOfSize(String fieldName,
                                        Class<T> elementType,
                                        Function<JsonElement, T> valueGetter,
                                        int expectedSize) {
      T[] actualValue = getArray(fieldName, elementType, valueGetter);
      if (actualValue.length != expectedSize) {
         failWithMessage("Expected \"%s\" field to be an array of size <%d>, was of size <%d>",
                         fieldName,
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

   public SELF containsObjectArraySatisfying(String fieldName, Consumer<JsonObject[]> requirements) {
      requireNonNull(fieldName);
      JsonObject[] actualValue = getArray(fieldName, JsonObject.class, AbstractJsonAssert::toJsonObject);
      requirements.accept(actualValue);
      return myself;
   }

   public SELF containsArrayArraySatisfying(String fieldName, Consumer<JsonArray[]> requirements) {
      requireNonNull(fieldName);
      JsonArray[] actualValue = getArray(fieldName, JsonArray.class, AbstractJsonAssert::toJsonArray);
      requirements.accept(actualValue);
      return myself;
   }

   private <T> T[] getArray(String fieldName, Class<T> elementType, Function<JsonElement, T> valueGetter) {
      JsonArray jsonArray = getJsonArray(fieldName);
      T[] array = convertArray(jsonArray, elementType, valueGetter);
      if (array == null) {
         failWithMessage("Unexpected type in array field \"%s\": %s", fieldName, jsonArray);
      }
      return array;
   }

   /**
    * Verifies that all fields have been asserted (by any of the "contains*" methods).
    */
   public void containsNoUnassertedFields() {
      Set<String> actualFields = new TreeSet<>(actual.keySet());
      actualFields.removeAll(assertedFields);
      if (!actualFields.isEmpty()) {
         failWithMessage("Found additional fields: <%s>", actualFields);
      }
   }

   private String getString(String fieldName) {
      isNotNull();
      JsonElement jsonElement = getJsonElement(fieldName);
      String string = toString(jsonElement);
      if (string == null) {
         failWithMessage("Expected field \"%s\" to be a string, was: %s", fieldName, jsonElement);
      }
      return string;
   }

   private Number getNumber(String fieldName) {
      isNotNull();
      JsonElement jsonElement = getJsonElement(fieldName);
      Number value = toNumber(jsonElement);
      if (value == null) {
         failWithMessage("Expected field \"%s\" to be a number, was: %s", fieldName, jsonElement);
      }
      return value;
   }

   private Number getIntegralNumber(String fieldName) {
      isNotNull();
      JsonElement jsonElement = getJsonElement(fieldName);
      Number value = toIntegralNumber(jsonElement);
      if (value == null) {
         failWithMessage("Expected field \"%s\" to be an integral number, was: %s", fieldName, jsonElement);
      }
      return value;
   }

   private boolean getBoolean(String fieldName) {
      isNotNull();
      JsonElement jsonElement = getJsonElement(fieldName);
      Boolean value = toBoolean(jsonElement);
      if (value == null) {
         failWithMessage("Expected field \"%s\" to be a boolean, was: %s", fieldName, jsonElement);
      }
      return value;
   }

   private JsonNull getJsonNull(String fieldName) {
      isNotNull();
      JsonElement jsonElement = getJsonElement(fieldName);
      JsonNull value = toJsonNull(jsonElement);
      if (value == null) {
         failWithMessage("Expected field \"%s\" to be a boolean, was: %s", fieldName, jsonElement);
      }
      return value;
   }

   private JsonObject getJsonObject(String fieldName) {
      isNotNull();
      JsonElement jsonElement = getJsonElement(fieldName);
      JsonObject value = toJsonObject(jsonElement);
      if (value == null) {
         failWithMessage("Expected field \"%s\" to be a JSON object, was: %s", fieldName, jsonElement);
      }
      return value;
   }

   private JsonArray getJsonArray(String fieldName) {
      isNotNull();
      JsonElement jsonElement = getJsonElement(fieldName);
      JsonArray value = toJsonArray(jsonElement);
      if (value == null) {
         failWithMessage("Expected field \"%s\" to be a JSON array, was: %s", fieldName, jsonElement);
      }
      return value;
   }

   private JsonElement getJsonElement(String fieldName) {
      isNotNull();
      JsonElement value = actual.get(fieldName);
      if (value == null) {
         failWithMessage("Expected field named \"%s\"", fieldName);
      }
      markAsAsserted(fieldName);
      return value;
   }

   protected final void markAsAsserted(String fieldName) {
      assertedFields.add(fieldName);
   }

}
