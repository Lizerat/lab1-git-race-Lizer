package es.unizar.webeng.hello.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Controller
class HelloController(
    @param:Value("\${app.message:Hello World}") 
    private val message: String
) {
    
    @GetMapping("/")
    fun welcome(
        model: Model,
        @RequestParam(defaultValue = "") name: String
    ): String {
        val tiempo = java.time.Instant.now().toString()
        val hora = tiempo.split("T")[1].split(":")[0].toInt()
        val momentoDelDia = when(hora) {
            in 6..14 -> "Good morning"
            in 15..21 -> "Good afternoon"
            in 21..23, in 0..5 -> "Good night"
            else -> "Hello"
        }
        val greeting = if (name.isNotBlank()) "$momentoDelDia, $name!" else message
        model.addAttribute("message", greeting)
        model.addAttribute("name", name)
        return "welcome"
    }
}

@RestController
class HelloApiController {
    val tiempo = java.time.Instant.now().toString()
    val hora = tiempo.split("T")[1].split(":")[0].toInt()
    val momentoDelDia = when(hora) {
        in 6..14 -> "Good morning"
        in 15..21 -> "Good afternoon"
        in 21..23, in 0..5 -> "Good night"
        else -> "Hello"
    }
    @GetMapping("/api/hello", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun helloApi(@RequestParam(defaultValue = "World") name: String): Map<String, String> {
        return mapOf(
            "message" to "$momentoDelDia, $name!",
            "timestamp" to java.time.Instant.now().toString()
        )
    }
}
