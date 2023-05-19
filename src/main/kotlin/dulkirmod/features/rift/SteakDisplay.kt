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
            val x =
                event.entity.lastTickPosX + (event.entity.posX - event.entity.lastTickPosX) * WorldRenderUtils.partialTicks
            val y =
                event.entity.lastTickPosY + (event.entity.posY - event.entity.lastTickPosY) * WorldRenderUtils.partialTicks
            val z =
                event.entity.lastTickPosZ + (event.entity.posZ - event.entity.lastTickPosZ) * WorldRenderUtils.partialTicks
            if (name.contains(char)) {
                WorldRenderUtils.drawCustomBox(
                    x - .5,
                    1.0,
                    y - 2,
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