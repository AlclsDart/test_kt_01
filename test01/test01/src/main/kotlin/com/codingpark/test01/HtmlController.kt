package com.codingpark.test01

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception
import java.security.MessageDigest
import javax.servlet.http.HttpSession
import org.springframework.web.bind.annotation.RequestMapping
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.databind.JsonNode

@Controller
class HtmlController {

    @Autowired
    lateinit var repository: UserListRepository
    lateinit var repositoryList : MutableList<UserListRepository>

    @GetMapping("/index")
    fun index(model: Model) : String{

        println("start Index Method")
        model.addAttribute("title", "Home")
        return "index"
    }
    fun crypto(ss: String):String{
        val sha=MessageDigest.getInstance("SHA-256")
        val hexa=sha.digest(ss.toByteArray())
        val crypto_str=hexa.fold("", {str, it -> str + "%02x".format(it)})
        return crypto_str
    }
    @GetMapping("/post/{num}")
    fun post(model:Model, @PathVariable num : Int){
        println("num :\t${num}")
    }

    @GetMapping("/{formType}")
    fun htmlForm(model:Model, @PathVariable formType: String) : String{
        println("formType : \t${formType}")
        var response:String = "unknown"

        if (formType.equals("sign") || formType.equals("login") || formType.equals("get_userlist")) {
            response = formType
        }
        else{
            response = "unknown"
        }

        model.addAttribute("title", response)

        return response
    }

    @PostMapping("/sign")
    fun postSign(model: Model,
                 @RequestParam(value = "id") userId:String,
                 @RequestParam(value = "password") password:String):String{
        try {
            println("Start Post Sign")
            val cryptoPass=crypto(password)
            val user = repository.save(UserList(userId, cryptoPass))
            println(user.toString())
        } catch (e:Exception){
            e.printStackTrace()
        }
        model.addAttribute("title", "index")
        return "index"
    }

    @PostMapping("/login")
    fun postLogin(model: Model,
                  session: HttpSession,
                  @RequestParam(value = "id") userId: String,
                  @RequestParam(value = "password") password: String):String{
        var pageName = ""
        try{
            val cryptoPass=crypto(password)
            val db_user = repository.findByUserId(userId)

            println("db_user : [${db_user}]")
            if (db_user != null){
                val db_pass = db_user.password
                println("db_pass : [${db_pass}]")
                if (cryptoPass.equals(db_pass)){
                    session.setAttribute("userId", db_user.userId)
                    model.addAttribute("title", "welcome")
                    model.addAttribute("userId", userId)
                    pageName = "welcome"
                }
                else{
                    model.addAttribute("title", "login")
                    pageName = "login"
                }
            }
        }
        catch (e: Exception){
            e.printStackTrace()
        }


        return pageName
    }

//    @ResponseBody
//    @RequestMapping("get_userlist")
//    fun postUser(model: Model, memberRepository: UserListRepository):String{
//        val mapper = jacksonObjectMapper()
//        val users = memberRepository.findAll()
//        println("Debug 001 Users:  ${users}")
////        var parsedMap: List<UserList> =  jacksonObjectMapper().readValue(users.toString().)
//        var html_data = ""
////        println("Debug 003 Users:  ${parsedMap}")
//        if( users == null){
//            html_data = "<h1>No Data.</h1>"
//        }
//        else{
//            html_data = ResponseEntity.ok(users).toString()
//        }
//        model.addAttribute("html_data", html_data)
//        println("users :  ${users}")
//        return "get_userlist"
//
//    }
}