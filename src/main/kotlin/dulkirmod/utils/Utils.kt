package dulkirmod.utils

object Utils {
    fun stripColorCodes(string: String): String {
        return string.replace("§.".toRegex(), "")
    }
}