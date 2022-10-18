package com.codingpark.test01
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import java.net.URLEncoder

@Controller
@RequestMapping("/get_userlist")
class MemberController(val memberRepository: UserListRepository) {


    @GetMapping()
    fun getUser(model: Model):String{
        val mapper = jacksonObjectMapper()
        val users = memberRepository.findAll()
        var html_data:String = ""
        var tmpId = ""
        var tmpUser = ""
        var tmpPassword = ""

        println("Debug 001 Users:  ${users}")
        if (users == null){
            html_data = "<h1>No Data.</h1>"
        }
        else{
            //html_data = "===========================<br/>"
            val iterData = users.iterator()
            while (iterData.hasNext()) {
                val iterResult = iterData.next()
                tmpId = iterResult.id.toString()
                tmpUser = iterResult.userId.toString()
                tmpPassword = iterResult.password.toString()
                html_data = html_data + "ID : [$tmpId]" + " USER : [$tmpUser]" + " PASSWORD : [$tmpPassword]<br>"
                println("(ing)html_data :  ${html_data}")
            }
        }

//        println("Debug 002 Users:  ${users.iterator().next().userId.toString()}")

//        var parsedMap: List<UserList> =  jacksonObjectMapper().readValue(users.toString().)

//        println("Debug 003 Users:  ${parsedMap}")
        model.addAttribute("title", "UserList")
        model.addAttribute("html_data", html_data)
        println("users :  ${users}")
        println("(bef)html_data :  ${html_data}")
        //html_data = URLEncoder.encode(html_data, "utf-8")
        println("(aft)html_data :  ${html_data}")
        return "get_userlist"

    }
}