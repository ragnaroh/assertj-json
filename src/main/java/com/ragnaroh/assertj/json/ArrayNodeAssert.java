package com.ragnaroh.assertj.json;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class ArrayNodeAssert extends AbstractArrayNodeAssert<ArrayNodeAssert> {

   public ArrayNodeAssert(String actual) {
      super(actual, ArrayNodeAssert.class);
   }

   public ArrayNodeAssert(ArrayNode actual) {
      super(actual, ArrayNodeAssert.class);
   }

}
