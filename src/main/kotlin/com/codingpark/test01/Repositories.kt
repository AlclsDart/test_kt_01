package com.codingpark.test01

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.Table

@Repository
interface UserListRepository:CrudRepository<UserList, Long> {
    fun findByUserId(userId:String):UserList
    fun findByPassword(password:String):UserList
}
