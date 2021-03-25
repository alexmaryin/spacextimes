package ru.alexmaryin.spacextimes_rx;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ru.alexmaryin.spacextimes_rx.databinding.CapsuleItemBindingImpl;
import ru.alexmaryin.spacextimes_rx.databinding.CoreItemBindingImpl;
import ru.alexmaryin.spacextimes_rx.databinding.CrewDetailFragmentBindingImpl;
import ru.alexmaryin.spacextimes_rx.databinding.CrewItemBindingImpl;
import ru.alexmaryin.spacextimes_rx.databinding.DragonDetailFragmentBindingImpl;
import ru.alexmaryin.spacextimes_rx.databinding.DragonItemBindingImpl;
import ru.alexmaryin.spacextimes_rx.databinding.FragmentMainBindingImpl;
import ru.alexmaryin.spacextimes_rx.databinding.LandingPadItemBindingImpl;
import ru.alexmaryin.spacextimes_rx.databinding.LaunchItemBindingImpl;
import ru.alexmaryin.spacextimes_rx.databinding.LaunchPadItemBindingImpl;
import ru.alexmaryin.spacextimes_rx.databinding.ProgressWebviewPartBindingImpl;
import ru.alexmaryin.spacextimes_rx.databinding.RocketItemBindingImpl;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_CAPSULEITEM = 1;

  private static final int LAYOUT_COREITEM = 2;

  private static final int LAYOUT_CREWDETAILFRAGMENT = 3;

  private static final int LAYOUT_CREWITEM = 4;

  private static final int LAYOUT_DRAGONDETAILFRAGMENT = 5;

  private static final int LAYOUT_DRAGONITEM = 6;

  private static final int LAYOUT_FRAGMENTMAIN = 7;

  private static final int LAYOUT_LANDINGPADITEM = 8;

  private static final int LAYOUT_LAUNCHITEM = 9;

  private static final int LAYOUT_LAUNCHPADITEM = 10;

  private static final int LAYOUT_PROGRESSWEBVIEWPART = 11;

  private static final int LAYOUT_ROCKETITEM = 12;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(12);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.alexmaryin.spacextimes_rx.R.layout.capsule_item, LAYOUT_CAPSULEITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.alexmaryin.spacextimes_rx.R.layout.core_item, LAYOUT_COREITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.alexmaryin.spacextimes_rx.R.layout.crew_detail_fragment, LAYOUT_CREWDETAILFRAGMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.alexmaryin.spacextimes_rx.R.layout.crew_item, LAYOUT_CREWITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.alexmaryin.spacextimes_rx.R.layout.dragon_detail_fragment, LAYOUT_DRAGONDETAILFRAGMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.alexmaryin.spacextimes_rx.R.layout.dragon_item, LAYOUT_DRAGONITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.alexmaryin.spacextimes_rx.R.layout.fragment_main, LAYOUT_FRAGMENTMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.alexmaryin.spacextimes_rx.R.layout.landing_pad_item, LAYOUT_LANDINGPADITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.alexmaryin.spacextimes_rx.R.layout.launch_item, LAYOUT_LAUNCHITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.alexmaryin.spacextimes_rx.R.layout.launch_pad_item, LAYOUT_LAUNCHPADITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.alexmaryin.spacextimes_rx.R.layout.progress_webview_part, LAYOUT_PROGRESSWEBVIEWPART);
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.alexmaryin.spacextimes_rx.R.layout.rocket_item, LAYOUT_ROCKETITEM);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_CAPSULEITEM: {
          if ("layout/capsule_item_0".equals(tag)) {
            return new CapsuleItemBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for capsule_item is invalid. Received: " + tag);
        }
        case  LAYOUT_COREITEM: {
          if ("layout/core_item_0".equals(tag)) {
            return new CoreItemBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for core_item is invalid. Received: " + tag);
        }
        case  LAYOUT_CREWDETAILFRAGMENT: {
          if ("layout/crew_detail_fragment_0".equals(tag)) {
            return new CrewDetailFragmentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for crew_detail_fragment is invalid. Received: " + tag);
        }
        case  LAYOUT_CREWITEM: {
          if ("layout/crew_item_0".equals(tag)) {
            return new CrewItemBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for crew_item is invalid. Received: " + tag);
        }
        case  LAYOUT_DRAGONDETAILFRAGMENT: {
          if ("layout/dragon_detail_fragment_0".equals(tag)) {
            return new DragonDetailFragmentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for dragon_detail_fragment is invalid. Received: " + tag);
        }
        case  LAYOUT_DRAGONITEM: {
          if ("layout/dragon_item_0".equals(tag)) {
            return new DragonItemBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for dragon_item is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTMAIN: {
          if ("layout/fragment_main_0".equals(tag)) {
            return new FragmentMainBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_main is invalid. Received: " + tag);
        }
        case  LAYOUT_LANDINGPADITEM: {
          if ("layout/landing_pad_item_0".equals(tag)) {
            return new LandingPadItemBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for landing_pad_item is invalid. Received: " + tag);
        }
        case  LAYOUT_LAUNCHITEM: {
          if ("layout/launch_item_0".equals(tag)) {
            return new LaunchItemBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for launch_item is invalid. Received: " + tag);
        }
        case  LAYOUT_LAUNCHPADITEM: {
          if ("layout/launch_pad_item_0".equals(tag)) {
            return new LaunchPadItemBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for launch_pad_item is invalid. Received: " + tag);
        }
        case  LAYOUT_PROGRESSWEBVIEWPART: {
          if ("layout/progress_webview_part_0".equals(tag)) {
            return new ProgressWebviewPartBindingImpl(component, new View[]{view});
          }
          throw new IllegalArgumentException("The tag for progress_webview_part is invalid. Received: " + tag);
        }
        case  LAYOUT_ROCKETITEM: {
          if ("layout/rocket_item_0".equals(tag)) {
            return new RocketItemBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for rocket_item is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case LAYOUT_PROGRESSWEBVIEWPART: {
          if("layout/progress_webview_part_0".equals(tag)) {
            return new ProgressWebviewPartBindingImpl(component, views);
          }
          throw new IllegalArgumentException("The tag for progress_webview_part is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(13);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "capsule");
      sKeys.put(2, "clickListener");
      sKeys.put(3, "core");
      sKeys.put(4, "crewMember");
      sKeys.put(5, "crewViewModel");
      sKeys.put(6, "dragon");
      sKeys.put(7, "dragonViewModel");
      sKeys.put(8, "landingPad");
      sKeys.put(9, "launch");
      sKeys.put(10, "launchPad");
      sKeys.put(11, "rocket");
      sKeys.put(12, "spaceXViewModel");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(12);

    static {
      sKeys.put("layout/capsule_item_0", ru.alexmaryin.spacextimes_rx.R.layout.capsule_item);
      sKeys.put("layout/core_item_0", ru.alexmaryin.spacextimes_rx.R.layout.core_item);
      sKeys.put("layout/crew_detail_fragment_0", ru.alexmaryin.spacextimes_rx.R.layout.crew_detail_fragment);
      sKeys.put("layout/crew_item_0", ru.alexmaryin.spacextimes_rx.R.layout.crew_item);
      sKeys.put("layout/dragon_detail_fragment_0", ru.alexmaryin.spacextimes_rx.R.layout.dragon_detail_fragment);
      sKeys.put("layout/dragon_item_0", ru.alexmaryin.spacextimes_rx.R.layout.dragon_item);
      sKeys.put("layout/fragment_main_0", ru.alexmaryin.spacextimes_rx.R.layout.fragment_main);
      sKeys.put("layout/landing_pad_item_0", ru.alexmaryin.spacextimes_rx.R.layout.landing_pad_item);
      sKeys.put("layout/launch_item_0", ru.alexmaryin.spacextimes_rx.R.layout.launch_item);
      sKeys.put("layout/launch_pad_item_0", ru.alexmaryin.spacextimes_rx.R.layout.launch_pad_item);
      sKeys.put("layout/progress_webview_part_0", ru.alexmaryin.spacextimes_rx.R.layout.progress_webview_part);
      sKeys.put("layout/rocket_item_0", ru.alexmaryin.spacextimes_rx.R.layout.rocket_item);
    }
  }
}
