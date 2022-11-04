package dulkirmod.features

import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.Config
import dulkirmod.utils.ContainerNameUtil
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraft.inventory.Slot
import net.minecraft.nbt.NBTTagList
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

class Croesus {

    var lastGuiOpenEvent: Long = 0

    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        val lastInCroesus = inCroesusBool

        if (!Config.hideOpenedChests) return
        if (mc.currentScreen == null || !(mc.currentScreen is GuiChest)) {
            inCroesusBool = false
            return
        }
        inCroesusBool = (ContainerNameUtil.currentGuiChestName == "Croesus")

        if (inCroesusBool && !lastInCroesus) {
            lastGuiOpenEvent = System.currentTimeMillis()
        }

        if (inCroesusBool && System.currentTimeMillis() - lastGuiOpenEvent < 300) {
            for (i in 9..44) {
                boolArray[i - 9] = false
                val slotIn = mc.thePlayer.openContainer.getSlot(i)

                if (slotIn.stack == null) continue
                val stack = slotIn.stack
                if (stack.getSubCompound("display", true)?.getTagList("Lore", 8) == null) continue

                val tagList: NBTTagList = stack.getSubCompound("display", true).getTagList("Lore", 8)
                for (j in 0 until tagList.tagCount()) {
                    if (tagList.getStringTagAt(j) == "§aChests have been opened!") boolArray[i - 9] = true
                }
            }
        }
    }

    companion object {
        var inCroesusBool: Boolean = false
        var boolArray = BooleanArray(36) { false }

        fun inCroesus(): Boolean {
            return inCroesusBool
        }

        fun isChestOpened(slotIn: Slot): Boolean {
            if (!inCroesusBool) return false
            if (slotIn.inventory == mc.thePlayer.inventory) return false
            val slotindex = slotIn.slotIndex
            if (slotindex !in 9..44) return false
            return boolArray[slotindex - 9]
        }
    }
}