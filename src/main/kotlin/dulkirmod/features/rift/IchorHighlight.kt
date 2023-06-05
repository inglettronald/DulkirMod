package dulkirmod.features.rift

import dulkirmod.config.DulkirConfig
import dulkirmod.utils.TabListUtils
import dulkirmod.utils.WorldRenderUtils
import net.minecraft.entity.item.EntityArmorStand
import net.minecraft.init.Items
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Color

object IchorHighlight {
    private const val ichorTexture =
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzAzNDA5MjNhNmRlNDgyNWExNzY4MTNkMTMzNTAzZWZmMTg2ZGIwODk2ZTMyYjY3MDQ5MjhjMmEyYmY2ODQyMiJ9fX0="

    @SubscribeEvent
    fun onRenderLiving(event: RenderLivingEvent.Post<*>) {
        if (!DulkirConfig.ichorHighlight) return
        if (TabListUtils.area != "The Rift") return
        val entity = event.entity

        val (x, y, z) = WorldRenderUtils.fixRenderPos(event.x, event.y, event.z)

        if (entity is EntityArmorStand) {
            if (entity.getEquipmentInSlot(4) != null && entity.getEquipmentInSlot(4).item === Items.skull) {
                val stack = entity.getEquipmentInSlot(4)
                if (stack.hasTagCompound() && stack.tagCompound.hasKey("SkullOwner")) {
                    val skullOwner = stack.tagCompound.getCompoundTag("SkullOwner")
                    if (skullOwner.hasKey("Properties")) {
                        val properties = skullOwner.getCompoundTag("Properties")
                        if (properties.hasKey("textures")) {
                            if (ichorTexture == properties.getTagList("textures", 10).getCompoundTagAt(0)
                                    .getString("Value")
                            ) {
                                WorldRenderUtils.drawCustomBox(
                                    x - .5,
                                    1.0,
                                    y + 1,
                                    1.0,
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
            }
        }
    }
}