package dulkirmod.features

import dulkirmod.DulkirMod
import dulkirmod.config.Config
import dulkirmod.utils.Utils
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraft.inventory.ContainerChest
import net.minecraft.inventory.Slot

class DungeonLeap {
    companion object {
        fun inLeapMenu(): Boolean {
            if (DulkirMod.mc.currentScreen == null || !(DulkirMod.mc.currentScreen is GuiChest)) return false
            val chest = DulkirMod.mc.currentScreen as GuiChest
            val container = chest.inventorySlots as ContainerChest
            Croesus.currentlyOpenChestName = container.lowerChestInventory.displayName.unformattedText
            if (Croesus.currentlyOpenChestName == "Spirit Leap") return true
            return false
        }

        fun isHighlightedLeapPlayer(slotIn: Slot): Boolean {
            if (slotIn.stack?.getTooltip(DulkirMod.mc.thePlayer, false) == null) return false

            val tooltip = slotIn.stack.getTooltip(DulkirMod.mc.thePlayer, false)
            for (s in tooltip) {
                var t = Utils.stripColorCodes(s)
                if (t == Config.highlightLeapName) return true
            }
            return false
        }
    }
}