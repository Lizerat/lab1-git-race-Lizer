package es.unizar.webeng.hello

import org.springframework.stereotype.Service
import org.springframework.jdbc.core.JdbcTemplate
import java.util.UUID

@Service
class GreetingService(private val db: JdbcTemplate) {
    fun findGreetings(): List<Greeting> = db.query("select * from greetings") { response, _ ->
        Greeting(
            response.getString("id"),
            response.getString("greeting"),
            response.getString("name"),
            response.getString("timestamp")
        )
    }

    fun save(message: Greeting): Greeting {
        val id = message.id ?: UUID.randomUUID().toString()
        db.update(
            "insert into greetings values (?, ?, ?, ?)",
            id, message.greeting, message.name, message.timestamp
        )
        return message.copy(id = id)
    }
}