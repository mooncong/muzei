/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.apps.muzei.wallpaper

import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import net.nurik.roman.muzei.BuildConfig

object WallpaperActiveState : MutableLiveData<Boolean>()

/**
 * LifecycleObserver responsible for sending analytics callbacks based on the state of the wallpaper
 */
class WallpaperAnalytics(private val context: Context) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        FirebaseAnalytics.getInstance(context).setUserProperty("device_type", BuildConfig.DEVICE_TYPE)
    }

    override fun onResume(owner: LifecycleOwner) {
        FirebaseAnalytics.getInstance(context).logEvent("wallpaper_created", null)
        WallpaperActiveState.value = true
    }

    override fun onPause(owner: LifecycleOwner) {
        FirebaseAnalytics.getInstance(context).logEvent("wallpaper_destroyed", null)
        WallpaperActiveState.value = false
    }
}
