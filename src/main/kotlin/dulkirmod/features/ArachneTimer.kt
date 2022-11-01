package dulkirmod.features

import dulkirmod.DulkirMod
import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.Config
import dulkirmod.utils.Utils
import dulkirmod.utils.WorldRenderUtils
import net.minecraft.util.ChatComponentText
import net.minecraft.util.Vec3
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class ArachneTimer {
    private var startmillis: Long = -1
    private var endmillis: Long = -1
    private var spawnmillis: Long = -1

    @SubscribeEvent(receiveCanceled = true, priority = EventPriority.LOW)
    fun onChat(event: ClientChatReceivedEvent) {
        if (!Config.arachneKillTimer) return

        var killtime: Float = -1f;

        if (event.type == 2.toByte()) {
            return
        }

        val unformatted = Utils.stripColorCodes(event.message.unformattedText)
        if (unformatted == "[BOSS] Arachne: You dare to call me, the queen of the dark, to you. I'll accept no excuses, you shall die!") {
            startmillis = System.currentTimeMillis()
        } else if (unformatted.startsWith('☄') && unformatted.contains("Something is awakening!")) {
            spawnmillis = System.currentTimeMillis()
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

    @SubscribeEvent
    fun onWorldRenderLast(event: RenderWorldLastEvent) {
        if (!Config.arachneSpawnTimer) return

        if (spawnmillis > startmillis) {
            val color = Utils.getColorString(Config.bestiaryNotifColor)
            var time = 18 - (System.currentTimeMillis() - spawnmillis) / 1000
            if (time < 0) time = 0
            WorldRenderUtils.render(Vec3(-282.5, 50.8, -178.5), "${color}${time}")
        }
    }
}