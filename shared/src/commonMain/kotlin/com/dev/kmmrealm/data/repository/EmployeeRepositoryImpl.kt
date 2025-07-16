package com.dev.kmmrealm.data.repository

import com.dev.kmmrealm.data.model.Employee
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId

class EmployeeRepositoryImpl(private val realm: Realm) : EmployeeRepository {
    
    override fun getAllEmployees(): Flow<List<Employee>> {
        return realm.query<Employee>()
            .find()
            .asFlow()
            .map { results -> results.list.toList() }
    }
    
    override fun getEmployeeById(id: ObjectId): Flow<Employee?> {
        return realm.query<Employee>("_id == $0", id)
            .first()
            .asFlow()
            .map { it.obj }
    }
    
    override suspend fun addEmployee(employee: Employee): Result<Unit> {
        return try {
            realm.write {
                copyToRealm(employee)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun updateEmployee(employee: Employee): Result<Unit> {
        return try {
            realm.write {
                val existingEmployee = query<Employee>("_id == $0", employee._id).first().find()
                existingEmployee?.let {
                    it.firstName = employee.firstName
                    it.lastName = employee.lastName
                    it.email = employee.email
                    it.phoneNumber = employee.phoneNumber
                    it.department = employee.department
                    it.position = employee.position
                    it.salary = employee.salary
                    it.hireDate = employee.hireDate
                    it.isActive = employee.isActive
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun deleteEmployee(id: ObjectId): Result<Unit> {
        return try {
            realm.write {
                val employee = query<Employee>("_id == $0", id).first().find()
                employee?.let { delete(it) }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override fun searchEmployees(query: String): Flow<List<Employee>> {
        return realm.query<Employee>(
            "firstName CONTAINS[c] $0 OR lastName CONTAINS[c] $0 OR email CONTAINS[c] $0 OR department CONTAINS[c] $0",
            query
        )
            .find()
            .asFlow()
            .map { results -> results.list.toList() }
    }
    
    override fun getEmployeesByDepartment(department: String): Flow<List<Employee>> {
        return realm.query<Employee>("department == $0", department)
            .find()
            .asFlow()
            .map { results -> results.list.toList() }
    }
}
