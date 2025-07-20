package com.dev.kmmrealm

import androidx.compose.runtime.Composable
import com.dev.kmmrealm.di.DatabaseModule
import com.dev.kmmrealm.presentation.ui.EmployeeApp

/**
 * Application factory that provides the main entry point for the Employee Management app.
 * Handles initialization and provides the root Compose UI.
 */
class EmployeeAppFactory {
    private val platform: Platform = getPlatform()

    /**
     * Provides a platform-specific greeting message.
     */
    fun getPlatformGreeting(): String {
        return "Hello, ${platform.name}! Employee Management System is ready."
    }
    
    /**
     * Initializes the database and required dependencies.
     * Should be called before using any app features.
     */
    fun initialize() {
        DatabaseModule.initialize()
    }
    
    /**
     * Provides the configured EmployeeViewModel instance.
     */
    fun getEmployeeViewModel() = DatabaseModule.getEmployeeViewModel()
    
    /**
     * Main Compose UI entry point for the Employee Management application.
     */
    @Composable
    fun CreateApp() {
        EmployeeApp(viewModel = getEmployeeViewModel())
    }
}