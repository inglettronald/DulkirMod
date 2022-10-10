package dulkirmod.features

import dulkirmod.DulkirMod.Companion.config
import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.utils.Utils.stripColorCodes
import net.minecraft.entity.item.EntityArmorStand
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

/**
 * Code inspired heavily by Skytils.
 */
object NametagCleaner {
    @SubscribeEvent
    fun onRenderLivingPre(event: RenderLivingEvent.Pre<*>) {
        if (event.entity is EntityArmorStand && event.entity.hasCustomName()) {
           if (config.hideTags) {
                val name = stripColorCodes(event.entity.customNameTag)
                if (name.contains("ABILITY DAMAGE") || name.contains("DEFENSE") || name.contains("DAMAGE")
                    || name.contains("Superboom TNT") || name.contains ("Blessing")) {
                   mc.theWorld.removeEntity(event.entity)
                }
           }

            if (config.hideArachneTags) {
                val name = stripColorCodes(event.entity.customNameTag)
                if (name.contains("Luxurious Spool") || name.contains("String") || name.contains("Arachne Fragment")){
                    mc.theWorld.removeEntity(event.entity)
                }
            }
        }
    }
}
