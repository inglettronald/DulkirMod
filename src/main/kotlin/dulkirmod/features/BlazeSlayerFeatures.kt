package dulkirmod.features

import dulkirmod.config.DulkirConfig
import dulkirmod.utils.TabListUtils
import dulkirmod.utils.Utils
import dulkirmod.utils.WorldRenderUtils
import net.minecraft.entity.item.EntityArmorStand
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Color

object BlazeSlayerFeatures {

    private val minibosses = "(Flare Demon)|(Kindleheart Demon)|(Burningsoul Demon)".toRegex()

    private val phaseColors = listOf(
        "CRYSTAL ♨" to Color(15, 247, 236, 200),
        "ASHEN ♨" to Color(40, 40, 40, 200),
        "AURIC ♨" to Color(206, 219, 57, 200),
        "SPIRIT ♨" to Color(255, 255, 255, 200)
    )

    @SubscribeEvent
    fun onRenderLiving(event: RenderLivingEvent.Post<*>) {
        if (!(DulkirConfig.attunementDisplay || DulkirConfig.minibossHitbox)) return
        if (TabListUtils.area != "Crimson Isle") return

        if (DulkirConfig.attunementDisplay) {
            if (event.entity is EntityArmorStand && event.entity.hasCustomName()) {
                val name = Utils.stripColorCodes(event.entity.customNameTag)
                val (x, y, z) = WorldRenderUtils.fixRenderPos(event.x, event.y, event.z)
                val color = phaseColors.firstOrNull { name.contains(it.first) }?.second ?: return
                WorldRenderUtils.drawCustomBox(
                    x - 0.5,
                    1.0,
                    y - 2,
                    1.5,
                    z - 0.5,
                    1.0,
                    color,
                    3f,
                    phase = false
                )
            }
        }

        if (DulkirConfig.minibossHitbox) {
            if (event.entity is EntityArmorStand && event.entity.hasCustomName()) {
                val name = Utils.stripColorCodes(event.entity.customNameTag)

                val (x, y, z) = WorldRenderUtils.fixRenderPos(event.x, event.y, event.z)

                if (name.contains(minibosses)) {
                    WorldRenderUtils.drawCustomBox(
                        x - 0.5,
                        1.0,
                        y - 1.5,
                        1.5,
                        z - 0.5,
                        1.0,
                        Color(7, 227, 21, 255),
                        3f,
                        phase = false
                    )
                }
            }
        }
    }
}