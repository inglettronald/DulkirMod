package dulkirmod.features

import dulkirmod.DulkirMod
import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.Config
import dulkirmod.utils.Utils
import net.minecraft.util.ChatComponentText
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class ArachneTimer {
    private var startmillis : Long = -1;
    private var endmillis : Long = -1;

    @SubscribeEvent(receiveCanceled = true, priority = EventPriority.LOW)
    fun onChat(event: ClientChatReceivedEvent) {
        if (!Config.arachneKillTimer) return

        var killtime : Float = -1f;

        if (event.type == 2.toByte()) {
            return
        }

        val unformatted = Utils.stripColorCodes(event.message.unformattedText)
        if (unformatted == "[BOSS] Arachne: You dare to call me, the queen of the dark, to you. I'll accept no excuses, you shall die!") {
            startmillis = System.currentTimeMillis()
        }

        if (unformatted == "[BOSS] Arachne: You are lucky this time that you only called out a portion of my power. If you dared to face me at my peak, you would not survive!") {
            endmillis = System.currentTimeMillis()
            if (startmillis > -1) {
                killtime = (endmillis - startmillis).toFloat() / 1000

                mc.thePlayer.addChatMessage(
                    ChatComponentText("${DulkirMod.CHAT_PREFIX} §6Arachne took §7$killtime §6seconds to kill.")
                )
            }
        }
    }
}