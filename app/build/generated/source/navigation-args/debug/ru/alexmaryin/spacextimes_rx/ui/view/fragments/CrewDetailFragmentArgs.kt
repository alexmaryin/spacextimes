package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.os.Bundle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class CrewDetailFragmentArgs(
  public val crewId: String
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("crewId", this.crewId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): CrewDetailFragmentArgs {
      bundle.setClassLoader(CrewDetailFragmentArgs::class.java.classLoader)
      val __crewId : String?
      if (bundle.containsKey("crewId")) {
        __crewId = bundle.getString("crewId")
        if (__crewId == null) {
          throw IllegalArgumentException("Argument \"crewId\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"crewId\" is missing and does not have an android:defaultValue")
      }
      return CrewDetailFragmentArgs(__crewId)
    }
  }
}
