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

    private val minibosses = "(Flare Demon)|(Kindleheart Demon)|(Burningsoul Demon)|(Smoldering Blaze)".toRegex()

    @SubscribeEvent
    fun onRenderLiving(event: RenderLivingEvent.Post<*>) {
        if (!(DulkirConfig.attunementDisplay || DulkirConfig.minibossHitbox)) return
        if (TabListUtils.area != "Crimson Isle") return

        if (DulkirConfig.attunementDisplay) {
            if (event.entity is EntityArmorStand && event.entity.hasCustomName()) {
                val name = Utils.stripColorCodes(event.entity.customNameTag)
                val x = event.entity.lastTickPosX + (event.entity.posX - event.entity.lastTickPosX) * WorldRenderUtils.partialTicks
                val y = event.entity.lastTickPosY + (event.entity.posY - event.entity.lastTickPosY) * WorldRenderUtils.partialTicks
                val z = event.entity.lastTickPosZ + (event.entity.posZ - event.entity.lastTickPosZ) * WorldRenderUtils.partialTicks
                when {
                    name.contains("CRYSTAL ♨") -> {
                        WorldRenderUtils.drawCustomBox(x -.5, 1.0, y -2, 1.5, z -.5, 1.0, Color(15, 247, 236, 255), 3f, phase = false)
                    }
                    name.contains("ASHEN ♨") -> {
                        WorldRenderUtils.drawCustomBox(x -.5, 1.0, y -2, 1.5, z -.5, 1.0, Color(84, 84, 84, 255), 3f, phase = false)
                    }
                    name.contains("AURIC ♨") -> {
                        WorldRenderUtils.drawCustomBox(x -.5, 1.0, y -2, 1.5, z -.5, 1.0, Color(206, 219, 57, 255), 3f, phase = false)
                    }
                    name.contains("SPIRIT ♨") -> {
                        WorldRenderUtils.drawCustomBox(x -.5, 1.0, y -2, 1.5, z -.5, 1.0, Color(255, 255, 255, 255), 3f, phase = false)
                    }
                }
            }
        }

        if (DulkirConfig.minibossHitbox) {
            if (event.entity is EntityArmorStand && event.entity.hasCustomName()) {
                val name = Utils.stripColorCodes(event.entity.customNameTag)
                val x = event.entity.lastTickPosX + (event.entity.posX - event.entity.lastTickPosX) * WorldRenderUtils.partialTicks
                val y = event.entity.lastTickPosY + (event.entity.posY - event.entity.lastTickPosY) * WorldRenderUtils.partialTicks
                val z = event.entity.lastTickPosZ + (event.entity.posZ - event.entity.lastTickPosZ) * WorldRenderUtils.partialTicks
                if (name.contains(minibosses)) {
                    WorldRenderUtils.drawCustomBox(x-.5, 1.0, y - 1.5, 1.5, z-.5, 1.0, Color(7, 227, 21, 255), 3f, phase = false)
                }
            }
        }
    }
}