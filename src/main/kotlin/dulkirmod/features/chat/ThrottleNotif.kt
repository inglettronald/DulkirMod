package dulkirmod.features.chat

import dulkirmod.DulkirMod
import dulkirmod.config.Config
import dulkirmod.utils.Utils
import net.minecraftforge.client.event.ClientChatReceivedEvent

object ThrottleNotif {
    private var lastThrottle: Long = 0
    fun handle(event: ClientChatReceivedEvent, unformatted: String) {
        if (unformatted == "This menu has been throttled! Please slow down..." && DulkirMod.config.throttleNotifier
            && Utils.isInDungeons()
        ) {
            event.isCanceled = true;
            if (!Config.throttleNotifierSpam && System.currentTimeMillis() - lastThrottle > 8000) {
                DulkirMod.mc.thePlayer.sendChatMessage("/pc " + DulkirMod.config.customMessage)
            } else {
                DulkirMod.mc.thePlayer.sendChatMessage("/pc " + DulkirMod.config.customMessage)
            }
            lastThrottle = System.currentTimeMillis()
        }
    }
}