package io.molkars.vesper.util;

import org.jetbrains.annotations.Nullable;

public final class Parser {
  public static @Nullable Integer tryParse(String input) {
    try {
      return Integer.parseInt(input);
    } catch (NumberFormatException e) {
      return null;
    }
  }
}
