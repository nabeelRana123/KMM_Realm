package com.dev.kmmrealm.data.repository

import com.dev.kmmrealm.data.model.Employee
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface EmployeeRepository {
    fun getAllEmployees(): Flow<List<Employee>>
    fun getEmployeeById(id: ObjectId): Flow<Employee?>
    suspend fun addEmployee(employee: Employee): Result<Unit>
    suspend fun updateEmployee(employee: Employee): Result<Unit>
    suspend fun deleteEmployee(id: ObjectId): Result<Unit>
    fun searchEmployees(query: String): Flow<List<Employee>>
    fun getEmployeesByDepartment(department: String): Flow<List<Employee>>
}
