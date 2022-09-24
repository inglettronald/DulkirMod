package dulkirmod.events

import dulkirmod.DulkirMod
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class ChatEvent {
    @SubscribeEvent(receiveCanceled = true, priority = EventPriority.LOW)
    fun onChat(event: ClientChatReceivedEvent) {
        if (event.type == 2.toByte()) {
            return
        }
        val unformatted = stripColorCodes(event.message.unformattedText)
        if (unformatted == "Warping you to your SkyBlock island..." && DulkirMod.config.throttleNotifier) {
            event.isCanceled = true;
            DulkirMod.mc.thePlayer.sendChatMessage("/pc " + DulkirMod.config.customMessage)
        }
    }
    private fun stripColorCodes(string: String): String {
        return string.replace("§.".toRegex(), "")
    }
}