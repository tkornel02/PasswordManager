package hu.bme.aut.android.passwordapp.generator
import kotlin.random.Random

class PasswordGenerator {

    private val specials: String = "!@#$%^&*()_-+=<>?/{}"
    private val uppers: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val lowers: String = "abcdefghijklmnopqrstuvwxyz"
    private val numbers: String = "0123456789"
    fun generatePassword(length : Int, options: BooleanArray): String {
        var characters = ""
        if (options[0]){
            characters += lowers
        }
        if (options[1]){
            characters += uppers
        }
        if (options[2]){
            characters += numbers
        }
        if (options[3]){
            characters += specials
        }
        return (1..length)
            .map { characters[Random.nextInt(0, characters.length)] }
            .joinToString("")
    }
}