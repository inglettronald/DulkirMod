package dulkirmod.features

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.client.event.sound.PlaySoundEvent
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import kotlin.math.min

object ReaperDisplay {

    var lastReaperUsage = 0L

    fun shouldDisplay(stack: ItemStack, cir: CallbackInfoReturnable<Boolean>) {
        if (!isReaper(stack)) return
        cir.returnValue = System.currentTimeMillis() - lastReaperUsage < 25000
    }

    fun calcDurability(stack: ItemStack, cir: CallbackInfoReturnable<Double>) {
        if (!isReaper(stack)) return
        val time = (System.currentTimeMillis() - lastReaperUsage) / 25000.0
        cir.returnValue = min(1.0, 1.0 - time)
    }

    @SubscribeEvent
    fun onSound(event: PlaySoundEvent) {
        if (event.name != "mob.zombie.remedy") return
        if (event.sound.pitch != 1.0f) return
        if (event.sound.volume != .5f) return
        lastReaperUsage = System.currentTimeMillis()
    }

    @SubscribeEvent
    fun onWorldLoad(event: WorldEvent) {
        lastReaperUsage = 0L
    }

    private fun isReaper(stack: ItemStack): Boolean {
        if (stack.hasTagCompound()) {
            val tag: NBTTagCompound = stack.tagCompound
            if (tag.hasKey("ExtraAttributes", 10) && tag.hasKey("display", 10)) {
                val ea: NBTTagCompound = tag.getCompoundTag("ExtraAttributes")
                if (ea.hasKey("id", 8)) {
                    val id = ea.getString("id")
                    return id matches "(REAPER_CHESTPLATE)|(REAPER_LEGGINGS)|(REAPER_BOOTS)".toRegex()
                }
            }
        }
        return false
    }
}