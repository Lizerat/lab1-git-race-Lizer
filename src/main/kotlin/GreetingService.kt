package es.unizar.webeng.hello

import org.springframework.stereotype.Service
import org.springframework.jdbc.core.JdbcTemplate
import java.util.UUID

/**
 * Service class responsible for managing greetings in the database.
 *
 * @property db The JDBC template used to perform database operations.
 */
@Service
class GreetingService(private val db: JdbcTemplate) {

    /**
     * Retrieves all greetings stored in the database.
     *
     * @return A list of [Greeting] objects containing all stored greetings.
     */
    fun findGreetings(): List<Greeting> = db.query("select * from greetings") { response, _ ->
        Greeting(
            response.getString("id"),
            response.getString("greeting"),
            response.getString("name"),
            response.getString("timestamp")
        )
    }

    /**
     * Persists a greeting in the database.
     * If the greeting does not already have an ID, a new UUID will be generated.
     *
     * @param message The [Greeting] object to be stored.
     * @return A copy of the original [Greeting] with the generated or existing ID.
     */
    fun save(message: Greeting): Greeting {
        val id = message.id ?: UUID.randomUUID().toString()
        db.update(
            "insert into greetings values (?, ?, ?, ?)",
            id, message.greeting, message.name, message.timestamp
        )
        return message.copy(id = id)
    }
}