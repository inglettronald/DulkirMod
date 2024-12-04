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
                                "ewogICJ0aW1lc3RhbXAiIDogMTcxOTQ2MzA5MTA0NywKICAicHJvZmlsZUlkIiA6ICIyNjRkYzBlYjVlZGI0ZmI3OTgxNWIyZGY1NGY0OTgyNCIsCiAgInByb2ZpbGVOYW1lIiA6ICJxdWludHVwbGV0IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzJlZWRjZmZjNmExMWEzODM0YTI4ODQ5Y2MzMTZhZjdhMjc1MmEzNzZkNTM2Y2Y4NDAzOWNmNzkxMDhiMTY3YWUiCiAgICB9CiAgfQp9"
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