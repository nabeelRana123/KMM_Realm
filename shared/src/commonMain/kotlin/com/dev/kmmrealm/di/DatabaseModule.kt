package com.dev.kmmrealm.di

import com.dev.kmmrealm.data.model.Employee
import com.dev.kmmrealm.data.repository.EmployeeRepository
import com.dev.kmmrealm.data.repository.EmployeeRepositoryImpl
import com.dev.kmmrealm.presentation.viewmodel.EmployeeViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object DatabaseModule {
    
    private lateinit var realm: Realm
    private lateinit var employeeRepository: EmployeeRepository
    private lateinit var employeeViewModel: EmployeeViewModel
    
    fun initialize() {
        val config = RealmConfiguration.Builder(
            schema = setOf(Employee::class)
        )
            .name("employee_database.realm")
            .build()
        
        realm = Realm.open(config)
        employeeRepository = EmployeeRepositoryImpl(realm)
        employeeViewModel = EmployeeViewModel(employeeRepository)
    }
    
    fun getRealm(): Realm = realm
    
    fun getEmployeeRepository(): EmployeeRepository = employeeRepository
    
    fun getEmployeeViewModel(): EmployeeViewModel = employeeViewModel
    
    fun close() {
        if (::realm.isInitialized) {
            realm.close()
        }
    }
}
