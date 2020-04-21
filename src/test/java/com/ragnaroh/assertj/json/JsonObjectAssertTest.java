package com.ragnaroh.assertj.json;

import org.junit.jupiter.api.Test;

class JsonObjectAssertTest {

   @Test
   void test() {
      String json = """
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
      Assertions
            .assertThatJson(json)
            .asJsonObject()
            .contains("string", "string")
            .contains("intNumber", 1)
            .contains("intNumber", 1.0)
            .contains("decimalNumber", 0.1)
            .contains("scientificNumber", 0.1E6)
            .contains("scientificNumber", 100000.0)
            .containsNumber("intNumber", "1")
            .containsNumber("decimalNumber", "0.1")
            .containsNumber("scientificNumber", "0.1E6")
            .contains("boolean", true)
            .containsEmptyObject("emptyObject")
            .containsObjectSatisfying("object", obj -> {})
            .containsEmptyArray("emptyArray")
            .containsNull("null")
            .containsStringArrayOfSize("stringArray", 3)
            .containsIntegerArrayOfSize("intNumberArray", 3)
            .containsNumberArrayOfSize("decimalNumberArray", 3)
            .containsNumberArrayOfSize("scientificNumberArray", 3)
            .containsBooleanArrayOfSize("booleanArray", 3)
            .containsObjectArrayOfSize("emptyObjectArray", 3)
            .containsArrayArrayOfSize("emptyArrayArray", 3)
            .containsNoUnassertedFields();
   }
}
