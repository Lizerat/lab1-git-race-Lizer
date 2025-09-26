package es.unizar.webeng.hello.controller

import es.unizar.webeng.hello.Greeting
import es.unizar.webeng.hello.GreetingService
import es.unizar.webeng.hello.controller.HelloController.Companion.greeting
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.ZoneId

@Controller
class HelloController(
    @param:Value("\${app.message:Hello World}") 
    private val message: String
) {
    /**
     * This method changes the greeting based on the local hour.
     */
    companion object {
        fun greeting(): String {
            val hora = java.time.LocalTime.now(ZoneId.systemDefault()).hour
            return when(hora) {
                in 6..14 -> "Good morning"
                in 15..20 -> "Good afternoon"
                in 21..23, in 0..5 -> "Good night"
                else -> "Hello"
            }
        }
    }

    @GetMapping("/")
    fun welcome(
        model: Model,
        @RequestParam(defaultValue = "") name: String
    ): String {
        val hourGreeting = greeting()
        val greeting = if (name.isNotBlank()) "$hourGreeting, $name!" else message
        model.addAttribute("message", greeting)
        model.addAttribute("name", name)
        return "welcome"
    }
}

@RestController
class HelloApiController (
    private val greetingService: GreetingService
) {

    @GetMapping("/api/hello", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun helloApi(@RequestParam(defaultValue = "World") name: String): Map<String, String> {
        val timestamp = java.time.Instant.now().toString()
        val hourGreeting = greeting()
        val greeting = Greeting(
            id = null, // se generará UUID en el servicio
            greeting = hourGreeting,
            name = name,
            timestamp = timestamp
        )
        greetingService.save(greeting)
        return mapOf(
            "message" to "$hourGreeting, $name!",
            "timestamp" to timestamp
        )
    }
}
