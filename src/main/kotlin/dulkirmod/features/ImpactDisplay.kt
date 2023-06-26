package dulkirmod.features

import dulkirmod.events.AlwaysPlaySoundEvent
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import kotlin.math.min

object ImpactDisplay {

    private var lastImpact = 0L
    private val bladeRegex = "(HYPERION|ASTRAEA|SCYLLA|VALKYRIE)".toRegex()

    fun shouldDisplay(stack: ItemStack, cir: CallbackInfoReturnable<Boolean>) {
        if (!isBlade(stack)) return
        cir.returnValue = System.currentTimeMillis() - lastImpact < 5000
    }

    fun calcDurability(stack: ItemStack, cir: CallbackInfoReturnable<Double>) {
        if (!isBlade(stack)) return
        val time = (System.currentTimeMillis() - lastImpact) / 5000.0
        cir.returnValue = min(1.0, 1.0 - time)
    }

    @SubscribeEvent
    fun onSound(event: AlwaysPlaySoundEvent) {
        if (event.name != "mob.zombie.remedy") return
        if (event.sound.pitch != 0.6984127f) return
        if (event.sound.volume != 1.0f) return
        lastImpact = System.currentTimeMillis()
    }

    @SubscribeEvent
    fun onWorldLoad(event: WorldEvent.Load) {
        lastImpact = 0L
    }

    private fun isBlade(stack: ItemStack): Boolean {
        if (stack.hasTagCompound()) {
            val tag: NBTTagCompound = stack.tagCompound
            if (tag.hasKey("ExtraAttributes", 10) && tag.hasKey("display", 10)) {
                val ea: NBTTagCompound = tag.getCompoundTag("ExtraAttributes")
                if (ea.hasKey("id", 8)) {
                    val id = ea.getString("id")
                    return id matches bladeRegex
                }
            }
        }
        return false
    }
}