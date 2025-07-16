package com.dev.kmmrealm.presentation.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.dev.kmmrealm.data.model.Employee
import com.dev.kmmrealm.presentation.viewmodel.EmployeeViewModel

enum class Screen {
    EmployeeList,
    AddEmployee,
    EditEmployee
}

@Composable
fun EmployeeApp(viewModel: EmployeeViewModel) {
    var currentScreen by remember { mutableStateOf(Screen.EmployeeList) }
    var selectedEmployee by remember { mutableStateOf<Employee?>(null) }
    
    MaterialTheme {
        when (currentScreen) {
            Screen.EmployeeList -> {
                EmployeeListScreen(
                    viewModel = viewModel,
                    onAddEmployeeClick = {
                        currentScreen = Screen.AddEmployee
                        selectedEmployee = null
                    },
                    onEditEmployeeClick = { employee ->
                        selectedEmployee = employee
                        currentScreen = Screen.EditEmployee
                    }
                )
            }
            
            Screen.AddEmployee -> {
                AddEditEmployeeScreen(
                    viewModel = viewModel,
                    employee = null,
                    onBackClick = {
                        currentScreen = Screen.EmployeeList
                    },
                    onSaveSuccess = {
                        currentScreen = Screen.EmployeeList
                    }
                )
            }
            
            Screen.EditEmployee -> {
                AddEditEmployeeScreen(
                    viewModel = viewModel,
                    employee = selectedEmployee,
                    onBackClick = {
                        currentScreen = Screen.EmployeeList
                        selectedEmployee = null
                    },
                    onSaveSuccess = {
                        currentScreen = Screen.EmployeeList
                        selectedEmployee = null
                    }
                )
            }
        }
    }
}
