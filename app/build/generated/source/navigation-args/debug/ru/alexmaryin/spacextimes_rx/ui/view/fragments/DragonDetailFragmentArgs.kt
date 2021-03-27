package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.os.Bundle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class DragonDetailFragmentArgs(
  public val dragonId: String
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("dragonId", this.dragonId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): DragonDetailFragmentArgs {
      bundle.setClassLoader(DragonDetailFragmentArgs::class.java.classLoader)
      val __dragonId : String?
      if (bundle.containsKey("dragonId")) {
        __dragonId = bundle.getString("dragonId")
        if (__dragonId == null) {
          throw IllegalArgumentException("Argument \"dragonId\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"dragonId\" is missing and does not have an android:defaultValue")
      }
      return DragonDetailFragmentArgs(__dragonId)
    }
  }
}
