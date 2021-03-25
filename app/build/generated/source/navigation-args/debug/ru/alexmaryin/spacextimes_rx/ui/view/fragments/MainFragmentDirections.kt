package ru.alexmaryin.spacextimes_rx.ui.view.fragments

import android.os.Bundle
import androidx.navigation.NavDirections
import kotlin.Int
import kotlin.String
import ru.alexmaryin.spacextimes_rx.R

public class MainFragmentDirections private constructor() {
  private data class ActionShowCrewMember(
    public val crewId: String
  ) : NavDirections {
    public override fun getActionId(): Int = R.id.action_showCrewMember

    public override fun getArguments(): Bundle {
      val result = Bundle()
      result.putString("crewId", this.crewId)
      return result
    }
  }

  private data class ActionShowDragonDetails(
    public val dragonId: String
  ) : NavDirections {
    public override fun getActionId(): Int = R.id.action_showDragonDetails

    public override fun getArguments(): Bundle {
      val result = Bundle()
      result.putString("dragonId", this.dragonId)
      return result
    }
  }

  public companion object {
    public fun actionShowCrewMember(crewId: String): NavDirections = ActionShowCrewMember(crewId)

    public fun actionShowDragonDetails(dragonId: String): NavDirections =
        ActionShowDragonDetails(dragonId)
  }
}
