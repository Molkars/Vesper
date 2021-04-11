package io.molkars.vesper.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ListUtil {
  public static <T> List<T> filled(int length, T value) {
    return Stream
        .generate(() -> value)
        .limit(length)
        .collect(Collectors.toList());
  }
}
