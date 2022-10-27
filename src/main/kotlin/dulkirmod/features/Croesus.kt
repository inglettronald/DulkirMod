package dulkirmod.features

import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.Config
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraft.inventory.ContainerChest
import net.minecraft.inventory.Slot

class Croesus {
    companion object {
        var currentlyOpenChestName = ""

        fun inCroesus(): Boolean {
            if (mc.currentScreen == null || !(mc.currentScreen is GuiChest)) return false
            val chest = mc.currentScreen as GuiChest
            val container = chest.inventorySlots as ContainerChest
            currentlyOpenChestName = container.lowerChestInventory.displayName.unformattedText
            if (currentlyOpenChestName == "Croesus") return true
            return false
        }

        fun isChestOpened(slotIn: Slot): Boolean {
            if (!Config.hideOpenedChests) return false
            if (slotIn.stack?.getTooltip(mc.thePlayer, false) == null) return false

            var tooltip = slotIn.stack.getTooltip(mc.thePlayer, false)
            if (tooltip.contains("§5§o§aChests have been opened!")) return true

            return false
        }
    }
}