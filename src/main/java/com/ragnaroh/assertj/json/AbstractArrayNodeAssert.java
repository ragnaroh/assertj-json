package com.ragnaroh.assertj.json;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.util.function.Consumer;
import java.util.function.Function;

import org.assertj.core.api.BooleanArrayAssert;
import org.assertj.core.api.DoubleArrayAssert;
import org.assertj.core.api.IntArrayAssert;
import org.assertj.core.api.ObjectArrayAssert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class AbstractArrayNodeAssert<SELF extends AbstractArrayNodeAssert<SELF>>
      extends AbstractJsonAssert<SELF, ArrayNode> {

   protected AbstractArrayNodeAssert(String actual, Class<SELF> selfType, ObjectMapper mapper)
         throws JsonProcessingException {
      this(toArrayNode(actual, mapper), selfType);
   }

   protected AbstractArrayNodeAssert(ArrayNode actual, Class<SELF> selfType) {
      super(actual, selfType);
   }

   private static ArrayNode toArrayNode(String json, ObjectMapper mapper) throws JsonProcessingException {
      return mapper.readValue(json, ArrayNode.class);
   }

   public SELF isEmpty() {
      isNotNull();
      if (actual.size() != 0) {
         failWithMessage("Expected empty array, had size <%d>", actual.size());
      }
      return myself;
   }

   public SELF hasSize(int expectedSize) {
      isNotNull();
      if (actual.size() != expectedSize) {
         failWithMessage("Expected array size <%d>, was <%d>", expectedSize, actual.size());
      }
      return myself;
   }

   public SELF containsStringsSatisfying(Consumer<String> requirements) {
      asStringArray().allSatisfy(requirements);
      return myself;
   }

   public SELF containsNumbersSatisfying(Consumer<Number> requirements) {
      asNumberArray().allSatisfy(requirements);
      return myself;
   }

   public SELF containsBigDecimalsSatisfying(Consumer<BigDecimal> requirements) {
      asBigDecimalArray().allSatisfy(requirements);
      return myself;
   }

   public SELF containsObjectNodesSatisfying(Consumer<ObjectNode> requirements) {
      asObjectNodeArray().allSatisfy(requirements);
      return myself;
   }

   public SELF containsArrayNodesSatisfying(Consumer<ArrayNode> requirements) {
      asArrayNodeArray().allSatisfy(requirements);
      return myself;
   }

   public ObjectArrayAssert<String> asStringArray() {
      return asObjectArray(String.class, AbstractJsonAssert::toString);
   }

   public ObjectArrayAssert<Number> asNumberArray() {
      return asObjectArray(Number.class, AbstractJsonAssert::toNumber);
   }

   public IntArrayAssert asIntArray() {
      isNotNull();
      Integer[] array = convertArray(actual, Integer.class, AbstractJsonAssert::toInteger);
      assertArrayNotNull(array);
      return new IntArrayAssert(unbox(array));
   }

   public DoubleArrayAssert asDoubleArray() {
      isNotNull();
      Double[] array = convertArray(actual, Double.class, AbstractJsonAssert::toDouble);
      assertArrayNotNull(array);
      return new DoubleArrayAssert(unbox(array));
   }

   public ObjectArrayAssert<BigDecimal> asBigDecimalArray() {
      isNotNull();
      BigDecimal[] array = convertArray(actual, BigDecimal.class, AbstractJsonAssert::toBigDecimal);
      assertArrayNotNull(array);
      return new ObjectArrayAssert<>(array);
   }

   public BooleanArrayAssert asBooleanArray() {
      isNotNull();
      Boolean[] array = convertArray(actual, Boolean.class, AbstractJsonAssert::toBoolean);
      assertArrayNotNull(array);
      return new BooleanArrayAssert(unbox(array));
   }

   public ObjectArrayAssert<ObjectNode> asObjectNodeArray() {
      return asObjectArray(ObjectNode.class, AbstractJsonAssert::toObjectNode);
   }

   public ObjectArrayAssert<ArrayNode> asArrayNodeArray() {
      return asObjectArray(ArrayNode.class, AbstractJsonAssert::toArrayNode);
   }

   private <T> ObjectArrayAssert<T> asObjectArray(Class<T> elementType, Function<JsonNode, T> valueMapper) {
      isNotNull();
      T[] array = convertArray(actual, elementType, valueMapper);
      assertArrayNotNull(array);
      return new ObjectArrayAssert<>(array);
   }

   private <T> void assertArrayNotNull(T[] array) {
      if (array == null) {
         failWithMessage("Array contained unexpected elements: %s", actual);
      }
   }

   public SELF containsExactly(String... expected) {
      requireNonNull(expected);
      containsExactly(expected, AbstractJsonAssert::toString);
      return myself;
   }

   public SELF containsExactly(int... expected) {
      requireNonNull(expected);
      containsExactly(box(expected), AbstractJsonAssert::toInteger);
      return myself;
   }

   public SELF containsExactly(double... expected) {
      requireNonNull(expected);
      containsExactly(box(expected), AbstractJsonAssert::toDouble);
      return myself;
   }

   public SELF containsExactly(boolean... expected) {
      requireNonNull(expected);
      containsExactly(box(expected), AbstractJsonAssert::toBoolean);
      return myself;
   }

   private <T> void containsExactly(T[] expected, Function<JsonNode, T> valueMapper) {
      isNotNull();
      if (actual.size() != expected.length) {
         failWithMessage("Expected exactly <%d> elements, was <%d>: %s",
                         expected.length,
                         actual.size(),
                         actual.toString());
      }
      for (int i = 0; i < actual.size(); i++) {
         T actualElement = valueMapper.apply(actual.get(i));
         T expectedElement = expected[i];
         if (!expectedElement.equals(actualElement)) {
            failWithMessage("Expected <%s> at array position %d, was <%s>", expectedElement, i, actualElement);
         }
      }
   }

}
