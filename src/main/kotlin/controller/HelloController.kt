package es.unizar.webeng.hello.controller

import es.unizar.webeng.hello.Greeting
import es.unizar.webeng.hello.GreetingService
import es.unizar.webeng.hello.controller.HelloController.Companion.greeting
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
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

/**
 * REST controller that handles API requests for greeting messages.
 *
 * This controller provides an endpoint to generate and save a greeting message,
 * and returns the greeting along with a timestamp in JSON format.
 *
 * @property greetingService Service responsible for managing greeting records.
 */
@RestController
class HelloApiController (
    private val greetingService: GreetingService
) {

    /**
     * Handles GET requests to `/api/hello` and returns a greeting message.
     *
     * Generates a greeting based on the current time of day, saves it to the database,
     * and returns a JSON object containing the greeting message and the current timestamp.
     *
     * @param name The name to include in the greeting. Defaults to `"World"` if not provided.
     * @return A map containing:
     *   - `"message"`: the generated greeting message including the provided name.
     *   - `"timestamp"`: the current timestamp.
     */
    @GetMapping("/api/hello", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun helloApi(@RequestParam(defaultValue = "World") name: String): Map<String, String> {
        val timestamp = java.time.Instant.now().toString()
        val hourGreeting = greeting()
        val greeting = Greeting(
            id = null,
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
