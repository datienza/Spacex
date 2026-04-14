package com.datienza.spacex.feature.rocketinfo.view

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class RocketInfoFragmentArgs(
  public val rocketId: String,
  public val rocketDescription: String,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("rocketId", this.rocketId)
    result.putString("rocketDescription", this.rocketDescription)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("rocketId", this.rocketId)
    result.set("rocketDescription", this.rocketDescription)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): RocketInfoFragmentArgs {
      bundle.setClassLoader(RocketInfoFragmentArgs::class.java.classLoader)
      val __rocketId : String?
      if (bundle.containsKey("rocketId")) {
        __rocketId = bundle.getString("rocketId")
        if (__rocketId == null) {
          throw IllegalArgumentException("Argument \"rocketId\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"rocketId\" is missing and does not have an android:defaultValue")
      }
      val __rocketDescription : String?
      if (bundle.containsKey("rocketDescription")) {
        __rocketDescription = bundle.getString("rocketDescription")
        if (__rocketDescription == null) {
          throw IllegalArgumentException("Argument \"rocketDescription\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"rocketDescription\" is missing and does not have an android:defaultValue")
      }
      return RocketInfoFragmentArgs(__rocketId, __rocketDescription)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): RocketInfoFragmentArgs {
      val __rocketId : String?
      if (savedStateHandle.contains("rocketId")) {
        __rocketId = savedStateHandle["rocketId"]
        if (__rocketId == null) {
          throw IllegalArgumentException("Argument \"rocketId\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"rocketId\" is missing and does not have an android:defaultValue")
      }
      val __rocketDescription : String?
      if (savedStateHandle.contains("rocketDescription")) {
        __rocketDescription = savedStateHandle["rocketDescription"]
        if (__rocketDescription == null) {
          throw IllegalArgumentException("Argument \"rocketDescription\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"rocketDescription\" is missing and does not have an android:defaultValue")
      }
      return RocketInfoFragmentArgs(__rocketId, __rocketDescription)
    }
  }
}
