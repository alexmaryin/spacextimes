// Generated by Dagger (https://dagger.dev).
package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel;

import dagger.internal.Factory;
import dagger.internal.Preconditions;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class SpaceXViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<String> {
  @Override
  public String get() {
    return provide();
  }

  public static SpaceXViewModel_HiltModules_KeyModule_ProvideFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static String provide() {
    return Preconditions.checkNotNullFromProvides(SpaceXViewModel_HiltModules.KeyModule.provide());
  }

  private static final class InstanceHolder {
    private static final SpaceXViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new SpaceXViewModel_HiltModules_KeyModule_ProvideFactory();
  }
}