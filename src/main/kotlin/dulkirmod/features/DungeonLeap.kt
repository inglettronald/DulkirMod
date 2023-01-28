package dulkirmod.features

import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.Config
import dulkirmod.utils.ContainerNameUtil
import dulkirmod.utils.Utils
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraft.inventory.Slot
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

class DungeonLeap {


    private var lastGuiOpenEvent: Long = 0

    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        val lastInLeap = inLeapMenuBool

        if (!Config.highlightLeap) return
        if (mc.currentScreen == null || mc.currentScreen !is GuiChest) {
            inLeapMenuBool = false
            return
        }
        inLeapMenuBool = (ContainerNameUtil.currentGuiChestName == "Spirit Leap")

        if (inLeapMenuBool && !lastInLeap) {
            lastGuiOpenEvent = System.currentTimeMillis()
        }

        if (inLeapMenuBool && System.currentTimeMillis() - lastGuiOpenEvent < 300) {
            for (i in 11..15) {
                boolArray[i - 11] = false
                val slotIn = mc.thePlayer.openContainer.getSlot(i)

                if (slotIn.stack == null) continue
                val stack = slotIn.stack
                if (Utils.stripColorCodes(stack.displayName).lowercase() == Config.highlightLeapName.lowercase()) boolArray[i - 11] = true
            }
        }
    }

    companion object {
        var inLeapMenuBool: Boolean = false
        var boolArray = BooleanArray(5) { false }

        fun inLeapMenu(): Boolean {
            return inLeapMenuBool
        }

        fun isHighlightedLeapPlayer(slotIn: Slot): Boolean {
            if (!inLeapMenuBool) return false
            if (slotIn.inventory == mc.thePlayer.inventory) return false
            val slotIndex = slotIn.slotIndex
            if (slotIndex !in 11..15) return false
            return boolArray[slotIndex - 11]
        }
    }
}