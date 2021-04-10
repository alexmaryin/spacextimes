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

  private data class ActionShowCoreDetails(
    public val coreId: String
  ) : NavDirections {
    public override fun getActionId(): Int = R.id.action_showCoreDetails

    public override fun getArguments(): Bundle {
      val result = Bundle()
      result.putString("coreId", this.coreId)
      return result
    }
  }

  private data class ActionShowRocketDetails(
    public val rocketId: String
  ) : NavDirections {
    public override fun getActionId(): Int = R.id.action_showRocketDetails

    public override fun getArguments(): Bundle {
      val result = Bundle()
      result.putString("rocketId", this.rocketId)
      return result
    }
  }

  private data class ActionShowLaunchPadDetails(
    public val launchPadId: String
  ) : NavDirections {
    public override fun getActionId(): Int = R.id.action_showLaunchPadDetails

    public override fun getArguments(): Bundle {
      val result = Bundle()
      result.putString("launchPadId", this.launchPadId)
      return result
    }
  }

  private data class ActionShowLandingPadDetails(
    public val landingPadId: String
  ) : NavDirections {
    public override fun getActionId(): Int = R.id.action_showLandingPadDetails

    public override fun getArguments(): Bundle {
      val result = Bundle()
      result.putString("landingPadId", this.landingPadId)
      return result
    }
  }

  private data class ActionShowCapsuleDetails(
    public val capsuleId: String
  ) : NavDirections {
    public override fun getActionId(): Int = R.id.action_showCapsuleDetails

    public override fun getArguments(): Bundle {
      val result = Bundle()
      result.putString("capsuleId", this.capsuleId)
      return result
    }
  }

  private data class ActionShowLaunchDetails(
    public val launchId: String
  ) : NavDirections {
    public override fun getActionId(): Int = R.id.action_showLaunchDetails

    public override fun getArguments(): Bundle {
      val result = Bundle()
      result.putString("launchId", this.launchId)
      return result
    }
  }

  public companion object {
    public fun actionShowCrewMember(crewId: String): NavDirections = ActionShowCrewMember(crewId)

    public fun actionShowDragonDetails(dragonId: String): NavDirections =
        ActionShowDragonDetails(dragonId)

    public fun actionShowCoreDetails(coreId: String): NavDirections = ActionShowCoreDetails(coreId)

    public fun actionShowRocketDetails(rocketId: String): NavDirections =
        ActionShowRocketDetails(rocketId)

    public fun actionShowLaunchPadDetails(launchPadId: String): NavDirections =
        ActionShowLaunchPadDetails(launchPadId)

    public fun actionShowLandingPadDetails(landingPadId: String): NavDirections =
        ActionShowLandingPadDetails(landingPadId)

    public fun actionShowCapsuleDetails(capsuleId: String): NavDirections =
        ActionShowCapsuleDetails(capsuleId)

    public fun actionShowLaunchDetails(launchId: String): NavDirections =
        ActionShowLaunchDetails(launchId)
  }
}
