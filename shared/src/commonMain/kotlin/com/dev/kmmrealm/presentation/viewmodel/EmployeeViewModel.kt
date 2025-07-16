package com.dev.kmmrealm.presentation.viewmodel

import com.dev.kmmrealm.data.model.Employee
import com.dev.kmmrealm.data.repository.EmployeeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class EmployeeViewModel(
    private val repository: EmployeeRepository,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
    
    private val _uiState = MutableStateFlow(EmployeeUiState())
    val uiState: StateFlow<EmployeeUiState> = _uiState.asStateFlow()
    
    private val _employees = MutableStateFlow<List<Employee>>(emptyList())
    val employees: StateFlow<List<Employee>> = _employees.asStateFlow()
    
    private val _selectedEmployee = MutableStateFlow<Employee?>(null)
    val selectedEmployee: StateFlow<Employee?> = _selectedEmployee.asStateFlow()
    
    init {
        loadEmployees()
    }
    
    private fun loadEmployees() {
        coroutineScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                repository.getAllEmployees().collectLatest { employeeList ->
                    _employees.value = employeeList
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load employees: ${e.message}"
                )
            }
        }
    }
    
    fun addEmployee(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        department: String,
        position: String,
        salary: Double,
        hireDate: String
    ) {
        coroutineScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                
                val employee = Employee().apply {
                    this.firstName = firstName
                    this.lastName = lastName
                    this.email = email
                    this.phoneNumber = phoneNumber
                    this.department = department
                    this.position = position
                    this.salary = salary
                    this.hireDate = hireDate
                    this.isActive = true
                }
                
                val result = repository.addEmployee(employee)
                if (result.isSuccess) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        successMessage = "Employee added successfully"
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Failed to add employee: ${result.exceptionOrNull()?.message}"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to add employee: ${e.message}"
                )
            }
        }
    }
    
    fun updateEmployee(employee: Employee) {
        coroutineScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                
                val result = repository.updateEmployee(employee)
                if (result.isSuccess) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        successMessage = "Employee updated successfully"
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Failed to update employee: ${result.exceptionOrNull()?.message}"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to update employee: ${e.message}"
                )
            }
        }
    }
    
    fun deleteEmployee(id: ObjectId) {
        coroutineScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                
                val result = repository.deleteEmployee(id)
                if (result.isSuccess) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        successMessage = "Employee deleted successfully"
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Failed to delete employee: ${result.exceptionOrNull()?.message}"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to delete employee: ${e.message}"
                )
            }
        }
    }
    
    fun searchEmployees(query: String) {
        if (query.isBlank()) {
            loadEmployees()
            return
        }
        
        coroutineScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                repository.searchEmployees(query).collectLatest { searchResults ->
                    _employees.value = searchResults
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Search failed: ${e.message}"
                )
            }
        }
    }
    
    fun getEmployeesByDepartment(department: String) {
        coroutineScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                repository.getEmployeesByDepartment(department).collectLatest { departmentEmployees ->
                    _employees.value = departmentEmployees
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load department employees: ${e.message}"
                )
            }
        }
    }
    
    fun selectEmployee(employee: Employee) {
        _selectedEmployee.value = employee
    }
    
    fun clearSelectedEmployee() {
        _selectedEmployee.value = null
    }
    
    fun clearMessages() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null,
            successMessage = null
        )
    }
}

data class EmployeeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)
