package com.dev.kmmrealm.UI

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitViewController
import androidx.compose.ui.unit.dp
import com.dev.kmmrealm.LocalNAtiveViewFactory
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun NativeButton(onClick: () -> Unit, modifier: Modifier) {
    val factory = LocalNAtiveViewFactory.current
    UIKitViewController(modifier = modifier.width(100.dp)
        .height(40.dp),
        factory = {
            factory.createButtonView("iOS button", onClick)
        })
}