package dulkirmod.features

import dulkirmod.config.DulkirConfig
import dulkirmod.utils.TextUtils
import dulkirmod.utils.Utils
import dulkirmod.utils.WorldRenderUtils
import net.minecraft.util.Vec3
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object ArachneTimer {
    private var startmillis: Long = -1
    private var endmillis: Long = -1
    private var spawnmillis: Long = -1
    private var bigboy: Boolean = false

    @SubscribeEvent(receiveCanceled = true, priority = EventPriority.LOW)
    fun onChat(event: ClientChatReceivedEvent) {
        if (!DulkirConfig.arachneKillTimer) return

        var killtime: Float = -1f

        if (event.type == 2.toByte()) {
            return
        }

        val unformatted = Utils.stripColorCodes(event.message.unformattedText)
        if (unformatted == "[BOSS] Arachne: With your sacrifice." || unformatted == "[BOSS] Arachne: A befitting welcome!") {
            bigboy = false
            startmillis = System.currentTimeMillis()
        } else if (unformatted.startsWith('☄') && unformatted.contains("Something is awakening!")) {
            if (unformatted.contains("Arachne Crystal!")) bigboy = true
            spawnmillis = System.currentTimeMillis()
        }

        if (unformatted.startsWith("                           Runecrafting:")) {
            endmillis = System.currentTimeMillis()
            if (startmillis > -1) {
                killtime = (endmillis - startmillis).toFloat() / 1000
                TextUtils.info("§6Arachne took §7$killtime §6seconds to kill.")
            }
        }
    }

    @SubscribeEvent
    fun onWorldRenderLast(event: RenderWorldLastEvent) {
        if (!DulkirConfig.arachneSpawnTimer) return

        if (spawnmillis > startmillis) {
            val color = Utils.getColorString(DulkirConfig.bestiaryNotifColor)
            var time: Int
            time = if (bigboy) {
                (40 - (System.currentTimeMillis() - spawnmillis) / 1000).toInt()
            } else {
                (18 - (System.currentTimeMillis() - spawnmillis) / 1000).toInt()
            }
            if (time < 0) time = 0
            WorldRenderUtils.renderString(Vec3(-282.5, 50.8, -178.5), "${color}${time}", false)
        }
    }
}