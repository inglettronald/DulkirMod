package dulkirmod.features.chat

import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import net.minecraftforge.client.event.ClientChatReceivedEvent

object FakeMsg {
    fun handle(event: ClientChatReceivedEvent, unformatted: String) {
        if (unformatted.startsWith("From [MVP++] Dulkir: c:")) {
            event.isCanceled = true
            val newst = unformatted.substring("From [MVP++] Dulkir: c:".length)
            Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText(newst.replace("&", "§")))
        }
        if (unformatted.startsWith("From [MVP+] Dulkir: c:")) {
            event.isCanceled = true
            val newst = unformatted.substring("From [MVP+] Dulkir: c:".length)
            Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText(newst.replace("&", "§")))
        }
    }
}