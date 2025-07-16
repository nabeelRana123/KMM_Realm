package com.dev.kmmrealm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.window.ComposeUIViewController
import com.dev.kmmrealm.UI.NativeViewFactory
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController


val LocalNAtiveViewFactory = staticCompositionLocalOf<NativeViewFactory> {
    error("No View Factory provided")
}

fun getComposeViewController(nativeViewFactory: NativeViewFactory) =
    ComposeUIViewController {
        CompositionLocalProvider(LocalNAtiveViewFactory provides nativeViewFactory) {
            val greeting = Greeting()
            greeting.initializeDatabase()
            // instantiate your shared VM
            greeting.EmployeeManagementApp()
        }
    }


