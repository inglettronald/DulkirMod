package dulkirmod.features.chat

import dulkirmod.DulkirMod
import dulkirmod.config.Config

object VanquisherTrigger {
    fun handle(message: String) {
        if (!Config.vanqBroadcast) return
        if (message == "A Vanquisher is spawning nearby!") {
            DulkirMod.mc.thePlayer.sendChatMessage("/patcher sendcoords")
        }
    }
}