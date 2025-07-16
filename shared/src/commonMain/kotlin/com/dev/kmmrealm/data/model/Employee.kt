package com.dev.kmmrealm.data.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Employee : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var firstName: String = ""
    var lastName: String = ""
    var email: String = ""
    var phoneNumber: String = ""
    var department: String = ""
    var position: String = ""
    var salary: Double = 0.0
    var hireDate: String = ""
    var isActive: Boolean = true
    
    // Helper properties
    val fullName: String get() = "$firstName $lastName"
}
