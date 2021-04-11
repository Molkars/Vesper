package io.molkars.vesper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestTest {

  @Test
  @DisplayName("Testing Tests")
  void test() {
    assertEquals(new Foo().bar(), 2);
  }

  public static class Foo {
    public int bar() {
      return 2;
    }
  }
}
