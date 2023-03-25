package dulkirmod.features.chat

import dulkirmod.config.DulkirConfig
import dulkirmod.utils.TextUtils

object VanquisherTrigger {
    fun handle(message: String) {
        if (!DulkirConfig.vanqBroadcast) return
        if (message == "A Vanquisher is spawning nearby!") {
            TextUtils.sendMessage("/patcher sendcoords")
        }
    }
}