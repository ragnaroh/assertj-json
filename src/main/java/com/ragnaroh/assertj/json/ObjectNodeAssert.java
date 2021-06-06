package com.ragnaroh.assertj.json;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class ObjectNodeAssert extends AbstractObjectNodeAssert<ObjectNodeAssert> {

   public ObjectNodeAssert(String actual) {
      super(actual, ObjectNodeAssert.class);
   }

   public ObjectNodeAssert(ObjectNode actual) {
      super(actual, ObjectNodeAssert.class);
   }

}
