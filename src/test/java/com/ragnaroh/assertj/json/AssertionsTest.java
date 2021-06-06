package com.ragnaroh.assertj.json;

import static com.ragnaroh.assertj.json.Assertions.assertThat;
import static com.ragnaroh.assertj.json.Assertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

class AssertionsTest {

   private final String json = """
         {
            "string": "string",
            "intNumber": 1,
            "decimalNumber": 0.1,
            "scientificNumber": 1E5,
            "boolean": true,
            "dateTime": "2021-06-06T10:11:12",
            "zonedDateTime": "2021-06-06T10:11:12+02:00",
            "timestamp": 1622990127961,
            "emptyObject": {},
            "emptyArray": [],
            "null": null,
            "object": {
               "string": "string",
               "intNumber": 1,
               "decimalNumber": 0.1,
               "scientificNumber": 0.1E6,
               "boolean": true,
               "emptyObject": {},
               "emptyArray": [],
               "null": null
            },
            "stringArray": ["A", "B", "C"],
            "intNumberArray": [1, 2, 3],
            "decimalNumberArray": [0.5, 1, 1.5],
            "scientificNumberArray": [0.5E2, 1E3, 1.5E-4],
            "booleanArray": [true, false, false],
            "emptyObjectArray": [{}, {}, {}],
            "emptyArrayArray": [[], [], []]
         }
         """;

   @Test
   void stringField() {
      assertThatJson(json)
            .asObjectNode()
            .contains("string")
            .contains("string", "string")
            .containsString("string")
            .containsNodeSatisfying("string", node -> {
               assertThat(node).isString().isStringEqualTo("string");
               assertThat(node).asString().isEqualTo("string");
            });
   }

   @Test
   void intNumberField() {
      assertThatJson(json)
            .asObjectNode()
            .contains("intNumber")
            .contains("intNumber", 1)
            .contains("intNumber", 1.0)
            .containsNumber("intNumber", "1")
            .containsIntSatisfying("intNumber", value -> {
               assertThat(value).isOne();
            })
            .containsBigDecimalSatisfying("intNumber", value -> {
               assertThat(value).isOne();
            })
            .containsNodeSatisfying("intNumber", node -> {
               assertThat(node)
                     .isNumber()
                     .isNumberEqualTo(1)
                     .isNumberEqualTo((Number) 1)
                     .isNumberEqualTo(1.0)
                     .isNumberEqualTo("1")
                     .isNumberEqualTo(new BigDecimal("1"));
               assertThat(node).asInteger().isEqualTo(1);
               assertThat(node).asDouble().isEqualTo(1);
               assertThat(node).asBigDecimal().isEqualTo("1");
            });
   }

   @Test
   void decimalNumberField() {
      assertThatJson(json)
            .asObjectNode()
            .contains("decimalNumber")
            .contains("decimalNumber", 0.1)
            .containsNumber("decimalNumber", "0.1")
            .containsDoubleSatisfying("decimalNumber", value -> {
               assertThat(value).isEqualTo(0.1);
            })
            .containsBigDecimalSatisfying("decimalNumber", value -> {
               assertThat(value).isEqualByComparingTo(new BigDecimal("0.1"));
            })
            .containsNodeSatisfying("decimalNumber", node -> {
               assertThat(node)
                     .isNumber()
                     .isNumberEqualTo(0.1)
                     .isNumberEqualTo((Number) 0.1)
                     .isNumberEqualTo("0.1")
                     .isNumberEqualTo(new BigDecimal("0.1"));
               assertThat(node).asDouble().isEqualTo(0.1);
               assertThat(node).asBigDecimal().isEqualTo("0.1");
            });
   }

   @Test
   void scientificNumberField() {
      assertThatJson(json)
            .asObjectNode()
            .contains("scientificNumber")
            .contains("scientificNumber", 0.1E6)
            .contains("scientificNumber", 100000.0)
            .containsNumber("scientificNumber", "0.1E6")
            .containsDoubleSatisfying("scientificNumber", value -> {
               assertThat(value).isEqualTo(0.1E6);
            })
            .containsBigDecimalSatisfying("scientificNumber", value -> {
               assertThat(value).isEqualByComparingTo(new BigDecimal("0.1e6"));
            })
            .containsNodeSatisfying("scientificNumber", value -> {
               assertThat(value)
                     .isNumber()
                     .isNumberEqualTo(0.1E6)
                     .isNumberEqualTo(100000)
                     .isNumberEqualTo((Number) 0.1E6)
                     .isNumberEqualTo(new BigDecimal("0.1E6"))
                     .isNumberEqualTo(new BigDecimal("100000"));
            });
   }

   @Test
   void booleanField() {
      assertThatJson(json).asObjectNode().contains("boolean").contains("boolean", true).containsBoolean("boolean");
   }

   @Test
   void dateTimeField() {
      assertThatJson(json)
            .asObjectNode()
            .contains("dateTime")
            .contains("dateTime", LocalDateTime.of(2021, 6, 6, 10, 11, 12))
            .containsLocalDateTime("dateTime", "2021-06-06T10:11:12");
   }

   @Test
   void zonedDateTimeField() {
      assertThatJson(json)
            .asObjectNode()
            .contains("zonedDateTime")
            .contains("zonedDateTime", ZonedDateTime.of(2021, 6, 6, 10, 11, 12, 0, ZoneOffset.ofHours(2)))
            .containsInstant("zonedDateTime", "2021-06-06T08:11:12Z")
            .containsInstantSatisfying("zonedDateTime", value -> {
               assertThat(value).isEqualTo(ZonedDateTime.of(2021, 6, 6, 10, 11, 12, 0, ZoneOffset.ofHours(2)));
            });
   }

   @Test
   void timestampField() {
      assertThatJson(json)
            .withDeserializationFeature(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
            .asObjectNode()
            .contains("timestamp")
            .contains("timestamp", Instant.ofEpochMilli(1622990127961L))
            .containsInstant("timestamp", "2021-06-06T14:35:27.961Z");
   }

   @Test
   void emptyObjectField() {
      assertThatJson(json)
            .asObjectNode()
            .contains("emptyObject")
            .containsObject("emptyObject")
            .containsEmptyObject("emptyObject")
            .containsObjectSatisfying("emptyObject", obj -> {
               assertThat(obj).isEmpty();
            });
   }

   @Test
   void emptyArrayField() {
      assertThatJson(json)
            .asObjectNode()
            .contains("emptyArray")
            .containsArray("emptyArray")
            .containsEmptyArray("emptyArray")
            .containsArraySatisfying("emptyArray", array -> {
               assertThat(array).isEmpty();
            });
   }

   @Test
   void nullField() {
      assertThatJson(json).asObjectNode().contains("null").containsNull("null");
   }

   @Test
   void objectField() {
      assertThatJson(json)
            .asObjectNode()
            .contains("object")
            .containsObject("object")
            .containsObjectSatisfying("object", object -> {
               assertThat(object)
                     .containsString("string")
                     .contains("intNumber", 1)
                     .contains("decimalNumber", 0.1)
                     .contains("scientificNumber", 0.1E6)
                     .contains("boolean", true)
                     .containsEmptyObject("emptyObject")
                     .containsEmptyArray("emptyArray")
                     .containsNull("null")
                     .containsNoUnassertedFields();
            });
   }

   @Test
   void stringArrayField() {
      assertThatJson(json)
            .asObjectNode()
            .contains("stringArray")
            .containsArray("stringArray")
            .containsStringArrayOfSize("stringArray", 3)
            .containsStringArraySatisfying("stringArray", array -> {
               assertThat(array).containsExactly("A", "B", "C");
            })
            .containsArraySatisfying("stringArray", array -> {
               assertThat(array).containsExactly("A", "B", "C");
               assertThat(array).containsStringsSatisfying(value -> {
                  assertThat(value).isGreaterThanOrEqualTo("A").isLessThanOrEqualTo("C");
               });
               assertThat(array).asStringArray().containsExactly("A", "B", "C");
            });
   }

   @Test
   void intArrayField() {
      assertThatJson(json)
            .asObjectNode()
            .contains("intNumberArray")
            .containsArray("intNumberArray")
            .containsIntegerArrayOfSize("intNumberArray", 3)
            .containsIntegerArraySatisfying("intNumberArray", array -> {
               assertThat(array).containsExactly(1, 2, 3);
            })
            .containsArraySatisfying("intNumberArray", array -> {
               assertThat(array).containsExactly(1, 2, 3);
               assertThat(array).asIntArray().containsExactly(1, 2, 3);
            });
   }

   @Test
   void decimalNumberArrayField() {
      assertThatJson(json)
            .asObjectNode()
            .contains("decimalNumberArray")
            .containsArray("decimalNumberArray")
            .containsNumberArrayOfSize("decimalNumberArray", 3);
   }

   @Test
   void scientificNumberArrayField() {
      assertThatJson(json)
            .asObjectNode()
            .contains("scientificNumberArray")
            .containsArray("scientificNumberArray")
            .containsNumberArrayOfSize("scientificNumberArray", 3)
            .containsNumberArraySatisfying("scientificNumberArray", array -> {
               assertThat(array).containsExactly(0.5E2, 1E3, 1.5E-4);
            });
   }

   @Test
   void booleanArrayField() {
      assertThatJson(json)
            .asObjectNode()
            .contains("booleanArray")
            .containsArray("booleanArray")
            .containsBooleanArrayOfSize("booleanArray", 3)
            .containsBooleanArraySatisfying("booleanArray", array -> {
               assertThat(array).containsExactly(true, false, false);
            });
   }

   @Test
   void emptyObjectArrayField() {
      assertThatJson(json)
            .asObjectNode()
            .contains("emptyObjectArray")
            .containsArray("emptyObjectArray")
            .containsObjectArrayOfSize("emptyObjectArray", 3)
            .containsObjectArraySatisfying("emptyObjectArray", array -> {
               assertThat(array).allMatch(ObjectNode::isEmpty);
            });
   }

   @Test
   void emptyArrayArrayField() {
      assertThatJson(json)
            .asObjectNode()
            .contains("emptyArrayArray")
            .containsArray("emptyArrayArray")
            .containsArrayArrayOfSize("emptyArrayArray", 3)
            .containsArrayArraySatisfying("emptyArrayArray", array -> {
               assertThat(array).allMatch(ArrayNode::isEmpty);
            });
   }

   @Test
   void unassertedFields() {
      var objectAssert = assertThatJson(json).asObjectNode();
      assertThatExceptionOfType(AssertionError.class)
            .isThrownBy(() -> objectAssert.containsNoUnassertedFields())
            .withMessage("Found additional fields: <[string, intNumber, decimalNumber, scientificNumber, boolean, dateTime, zonedDateTime, timestamp, emptyObject, emptyArray, null, object, stringArray, intNumberArray, decimalNumberArray, scientificNumberArray, booleanArray, emptyObjectArray, emptyArrayArray]>");
      objectAssert
            .contains("string")
            .contains("intNumber")
            .contains("decimalNumber")
            .contains("scientificNumber")
            .contains("boolean")
            .contains("dateTime")
            .contains("zonedDateTime")
            .contains("timestamp")
            .contains("emptyObject")
            .contains("emptyArray")
            .contains("null")
            .contains("object")
            .contains("stringArray")
            .contains("intNumberArray")
            .contains("decimalNumberArray")
            .contains("scientificNumberArray")
            .contains("booleanArray")
            .contains("emptyObjectArray")
            .contains("emptyArrayArray")
            .containsNoUnassertedFields();
   }

}
