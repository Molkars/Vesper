package io.molkars.vesper;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@SuppressWarnings("unused")
@ExtendWith(MockitoExtension.class)
final class VesperPluginTest {
  private static ServerMock server;
  private static Vesper plugin;

  @BeforeAll
  static void setup() {
    server = MockBukkit.mock();
    plugin = MockBukkit.load(Vesper.class);
  }

  @AfterAll
  static void dispose() {
    MockBukkit.unmock();
  }

  @Test
  @DisplayName("Plugin Loaded")
  public void pluginLoaded() {

  }
}