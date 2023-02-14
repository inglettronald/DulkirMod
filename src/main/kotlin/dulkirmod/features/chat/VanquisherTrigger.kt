package dulkirmod.features.chat

import dulkirmod.config.Config
import dulkirmod.utils.TextUtils

object VanquisherTrigger {
    fun handle(message: String) {
        if (!Config.vanqBroadcast) return
        if (message == "A Vanquisher is spawning nearby!") {
            TextUtils.sendMessage("/patcher sendcoords")
        }
    }
}