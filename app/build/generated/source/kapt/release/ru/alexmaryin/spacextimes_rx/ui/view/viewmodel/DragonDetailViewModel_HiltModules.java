package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.internal.lifecycle.HiltViewModelMap;
import dagger.hilt.codegen.OriginatingElement;
import dagger.multibindings.IntoMap;
import dagger.multibindings.IntoSet;
import dagger.multibindings.StringKey;
import java.lang.String;

@OriginatingElement(
    topLevelClass = DragonDetailViewModel.class
)
public final class DragonDetailViewModel_HiltModules {
  private DragonDetailViewModel_HiltModules() {
  }

  @Module
  @InstallIn(ViewModelComponent.class)
  public abstract static class BindsModule {
    @Binds
    @IntoMap
    @StringKey("ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.DragonDetailViewModel")
    @HiltViewModelMap
    public abstract ViewModel binds(DragonDetailViewModel vm);
  }

  @Module
  @InstallIn(ActivityRetainedComponent.class)
  public static final class KeyModule {
    private KeyModule() {
    }

    @Provides
    @IntoSet
    @HiltViewModelMap.KeySet
    public static String provide() {
      return "ru.alexmaryin.spacextimes_rx.ui.view.viewmodel.DragonDetailViewModel";
    }
  }
}
