package dulkirmod.features.rift

import dulkirmod.config.DulkirConfig
import dulkirmod.utils.TabListUtils
import dulkirmod.utils.Utils
import dulkirmod.utils.WorldRenderUtils
import net.minecraft.entity.item.EntityArmorStand
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Color

object SteakDisplay {
    private const val char = "҉"

    @SubscribeEvent
    fun onRenderLiving(event: RenderLivingEvent.Post<*>) {
        if (!DulkirConfig.steakDisplay) return
        if (TabListUtils.area != "The Rift") return

        if (event.entity is EntityArmorStand && event.entity.hasCustomName()) {
            val name = Utils.stripColorCodes(event.entity.customNameTag)
            val (x, y, z) = WorldRenderUtils.fixRenderPos(event.x, event.y, event.z)
            if (name.contains(char) && name.contains("Vampire Boss")) {
                WorldRenderUtils.drawCustomBox(
                    x - .5,
                    1.0,
                    y - 1.5,
                    1.5,
                    z - .5,
                    1.0,
                    Color(15, 247, 236, 255),
                    3f,
                    phase = false
                )
            }
        }
    }
}