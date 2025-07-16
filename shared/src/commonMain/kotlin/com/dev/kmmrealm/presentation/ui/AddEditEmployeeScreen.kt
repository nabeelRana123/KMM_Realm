package com.dev.kmmrealm.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dev.kmmrealm.data.model.Employee
import com.dev.kmmrealm.presentation.viewmodel.EmployeeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditEmployeeScreen(
    viewModel: EmployeeViewModel,
    employee: Employee? = null,
    onBackClick: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    
    var firstName by remember { mutableStateOf(employee?.firstName ?: "") }
    var lastName by remember { mutableStateOf(employee?.lastName ?: "") }
    var email by remember { mutableStateOf(employee?.email ?: "") }
    var phoneNumber by remember { mutableStateOf(employee?.phoneNumber ?: "") }
    var department by remember { mutableStateOf(employee?.department ?: "") }
    var position by remember { mutableStateOf(employee?.position ?: "") }
    var salary by remember { mutableStateOf(employee?.salary?.toString() ?: "") }
    var hireDate by remember { mutableStateOf(employee?.hireDate ?: "") }
    
    val isEditing = employee != null
    
    // Handle success message
    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage != null) {
            kotlinx.coroutines.delay(1000)
            onSaveSuccess()
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Header with back button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = if (isEditing) "Edit Employee" else "Add New Employee",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        
        // Form Fields
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name *") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )
        
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name *") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )
        
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email *") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )
        
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true
        )
        
        OutlinedTextField(
            value = department,
            onValueChange = { department = it },
            label = { Text("Department *") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )
        
        OutlinedTextField(
            value = position,
            onValueChange = { position = it },
            label = { Text("Position *") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )
        
        OutlinedTextField(
            value = salary,
            onValueChange = { salary = it },
            label = { Text("Salary") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true
        )
        
        OutlinedTextField(
            value = hireDate,
            onValueChange = { hireDate = it },
            label = { Text("Hire Date (YYYY-MM-DD)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            singleLine = true,
            placeholder = { Text("2024-01-15") }
        )
        
        // Error Message
        uiState.errorMessage?.let { error ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Text(
                    text = error,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
        
        // Save Button
        Button(
            onClick = {
                val salaryValue = salary.toDoubleOrNull() ?: 0.0
                
                if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || 
                    department.isBlank() || position.isBlank()) {
                    // You might want to show validation error here
                    return@Button
                }
                
                if (isEditing && employee != null) {
                    // Update existing employee
                    val updatedEmployee = Employee().apply {
                        _id = employee._id
                        this.firstName = firstName
                        this.lastName = lastName
                        this.email = email
                        this.phoneNumber = phoneNumber
                        this.department = department
                        this.position = position
                        this.salary = salaryValue
                        this.hireDate = hireDate
                        this.isActive = employee.isActive
                    }
                    viewModel.updateEmployee(updatedEmployee)
                } else {
                    // Add new employee
                    viewModel.addEmployee(
                        firstName = firstName,
                        lastName = lastName,
                        email = email,
                        phoneNumber = phoneNumber,
                        department = department,
                        position = position,
                        salary = salaryValue,
                        hireDate = hireDate
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = !uiState.isLoading && firstName.isNotBlank() && lastName.isNotBlank() && 
                     email.isNotBlank() && department.isNotBlank() && position.isNotBlank()
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(if (isEditing) "Update Employee" else "Add Employee")
            }
        }
        
        // Cancel Button
        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Cancel")
        }
    }
}
