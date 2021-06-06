package com.ragnaroh.assertj.json;

import static com.ragnaroh.assertj.json.Assertions.assertThat;
import static com.ragnaroh.assertj.json.Assertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

class AssertionsTest {

   private final String json = """
         {
            "string": "string",
            "intNumber": 1,
            "decimalNumber": 0.1,
            "scientificNumber": 1E5,
            "boolean": true,
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
   void stringField() throws JsonProcessingException {
      assertThatJson(json).asObjectNode().contains("string").contains("string", "string").containsString("string");
   }

   @Test
   void intNumberField() throws JsonProcessingException {
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
            });
   }

   @Test
   void decimalNumberField() throws JsonProcessingException {
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
            });
   }

   @Test
   void scientificNumberField() throws JsonProcessingException {
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
            });
   }

   @Test
   void booleanField() throws JsonProcessingException {
      assertThatJson(json).asObjectNode().contains("boolean").contains("boolean", true).containsBoolean("boolean");
   }

   @Test
   void emptyObjectField() throws JsonProcessingException {
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
   void emptyArrayField() throws JsonProcessingException {
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
   void nullField() throws JsonProcessingException {
      assertThatJson(json).asObjectNode().contains("null").containsNull("null");
   }

   @Test
   void objectField() throws JsonProcessingException {
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
   void stringArrayField() throws JsonProcessingException {
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
   void intArrayField() throws JsonProcessingException {
      assertThatJson(json)
            .asObjectNode()
            .contains("intNumberArray")
            .containsArray("intNumberArray")
            .containsIntegerArrayOfSize("intNumberArray", 3)
            .containsIntArraySatisfying("intNumberArray", array -> {
               assertThat(array).containsExactly(1, 2, 3);
            })
            .containsArraySatisfying("intNumberArray", array -> {
               assertThat(array).containsExactly(1, 2, 3);
               assertThat(array).asIntArray().containsExactly(1, 2, 3);
            });
   }

   @Test
   void decimalNumberArrayField() throws JsonProcessingException {
      assertThatJson(json)
            .asObjectNode()
            .contains("decimalNumberArray")
            .containsArray("decimalNumberArray")
            .containsNumberArrayOfSize("decimalNumberArray", 3);
   }

   @Test
   void scientificNumberArrayField() throws JsonProcessingException {
      assertThatJson(json).asObjectNode().containsNumberArrayOfSize("scientificNumberArray", 3);
   }

   @Test
   void booleanArrayField() throws JsonProcessingException {
      assertThatJson(json).asObjectNode().containsBooleanArrayOfSize("booleanArray", 3);
   }

   @Test
   void emptyObjectArrayField() throws JsonProcessingException {
      assertThatJson(json).asObjectNode().containsObjectArrayOfSize("emptyObjectArray", 3);
   }

   @Test
   void emptyArrayArrayField() throws JsonProcessingException {
      assertThatJson(json).asObjectNode().containsArrayArrayOfSize("emptyArrayArray", 3);
   }

   @Test
   void unassertedFields() throws JsonProcessingException {
      var objectAssert = assertThatJson(json).asObjectNode();
      assertThatExceptionOfType(AssertionError.class)
            .isThrownBy(() -> objectAssert.containsNoUnassertedFields())
            .withMessage("Found additional fields: <[string, intNumber, decimalNumber, scientificNumber, boolean, emptyObject, emptyArray, null, object, stringArray, intNumberArray, decimalNumberArray, scientificNumberArray, booleanArray, emptyObjectArray, emptyArrayArray]>");
      objectAssert
            .contains("string")
            .contains("intNumber")
            .contains("decimalNumber")
            .contains("scientificNumber")
            .contains("boolean")
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
