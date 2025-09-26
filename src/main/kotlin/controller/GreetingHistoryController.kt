package es.unizar.webeng.hello.controller

import es.unizar.webeng.hello.GreetingService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST controller responsible for providing the greeting history through the API.
 *
 * @property history The [GreetingService] instance used to access greeting data.
 */
@RestController
@RequestMapping("/api/history")
class GreetingHistoryController(
    private val history: GreetingService
) {

    /**
     * Retrieves all stored greetings as a JSON list.
     *
     * @return A list of [Greeting] objects representing the greeting history.
     */
    @GetMapping
    fun listGreetings() = history.findGreetings()
}

/**
 * Controller responsible for serving the greeting history page.
 */
@Controller
@RequestMapping("/history")
class HistoryController {

    /**
     * Returns the name of the view template for the greeting history page.
     *
     * This method maps the GET request for "/history" to the corresponding HTML view.
     *
     * @return The view template name "history".
     */
    @GetMapping()
    fun historyPage(): String {
        return "history"
    }
}
