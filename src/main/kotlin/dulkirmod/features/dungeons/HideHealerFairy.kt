package dulkirmod.features.dungeons


import dulkirmod.DulkirMod.Companion.config
import dulkirmod.utils.TabListUtils
import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityArmorStand
import net.minecraft.init.Items
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

object HideHealerFairy {
    fun handle(entity: Entity, cir: CallbackInfoReturnable<Boolean>) {
        if (!config.hideHealerFairy) return
        if (TabListUtils.area != "Dungeon") return
        if (entity is EntityArmorStand) {
            if (entity.heldItem != null && entity.heldItem.item === Items.skull) {
                val stack = entity.heldItem
                if (stack.hasTagCompound() && stack.tagCompound.hasKey("SkullOwner")) {
                    val skullOwner = stack.tagCompound.getCompoundTag("SkullOwner")
                    if (skullOwner.hasKey("Properties")) {
                        val properties = skullOwner.getCompoundTag("Properties")
                        if (properties.hasKey("textures")) {
                            val healerFairyTexture =
                                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTZjM2UzMWNmYzY2NzMzMjc1YzQyZmNmYjVkOWE0NDM0MmQ2NDNiNTVjZDE0YzljNzdkMjczYTIzNTIifX19"
                            if (healerFairyTexture == properties.getTagList("textures", 10).getCompoundTagAt(0)
                                    .getString("Value")
                            ) {
                                cir.cancel()
                            }
                        }
                    }
                }
            }
        }
    }
}