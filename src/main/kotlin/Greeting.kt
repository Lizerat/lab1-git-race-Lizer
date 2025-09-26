package es.unizar.webeng.hello

/**
 * Represents a greeting stored in the system.
 *
 * @property id The unique identifier of the greeting. It can be `null` if the greeting has not yet been persisted in the database.
 * @property greeting The greeting text.
 * @property name The name of the person associated with the greeting.
 * @property timestamp The timestamp of the greeting.
 */
data class Greeting(val id: String?, val greeting: String, val name: String, val timestamp: String)