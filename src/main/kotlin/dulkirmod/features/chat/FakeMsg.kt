package dulkirmod.features.chat

import dulkirmod.utils.TextUtils
import net.minecraftforge.client.event.ClientChatReceivedEvent

object FakeMsg {
    private val dulkirRegex = "From \\[MVP(\\+|\\+\\+)] Dulkir: c:".toRegex()
    fun handle(event: ClientChatReceivedEvent, unformatted: String) {
        if (dulkirRegex.matches(unformatted)) {
            event.isCanceled = true
            val message = unformatted.replace(dulkirRegex, "").replace("&", "§")
            TextUtils.info(message, false)
        }
    }
}