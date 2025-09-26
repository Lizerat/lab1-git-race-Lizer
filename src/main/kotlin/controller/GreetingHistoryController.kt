package es.unizar.webeng.hello.controller

import es.unizar.webeng.hello.GreetingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/history")
class GreetingHistoryController(
    private val history: GreetingService
) {
    @GetMapping
    fun listGreetings() = history.findGreetings()
}