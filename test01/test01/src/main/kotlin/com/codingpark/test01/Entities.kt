package com.codingpark.test01

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class UserList(
    var userId:String,
    var password:String,
    @Id @GeneratedValue var id:Long?=null
)