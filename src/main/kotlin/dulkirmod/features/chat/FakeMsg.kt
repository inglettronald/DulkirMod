package dulkirmod.features.chat

import dulkirmod.utils.TextUtils
import net.minecraftforge.client.event.ClientChatReceivedEvent

object FakeMsg {
    private val dulkirRegex = "^From \\[MVP(\\+|\\+\\+)] Dulkir: c:".toRegex()
    fun handle(event: ClientChatReceivedEvent, unformatted: String) {
        if (unformatted.contains(dulkirRegex)) {
            event.isCanceled = true
            val message = unformatted.replace(dulkirRegex, "").replace("&", "§").trim()
            TextUtils.info(message, false)
        }
    }
}