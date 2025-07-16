package com.dev.kmmrealm

import androidx.compose.runtime.Composable
import com.dev.kmmrealm.di.DatabaseModule
import com.dev.kmmrealm.presentation.ui.EmployeeApp

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}! Employee Management System is ready."
    }
    
    fun initializeDatabase() {
        DatabaseModule.initialize()
    }
    
    fun getEmployeeViewModel() = DatabaseModule.getEmployeeViewModel()
    
    @Composable
    fun EmployeeManagementApp() {
        EmployeeApp(viewModel = getEmployeeViewModel())
    }
}