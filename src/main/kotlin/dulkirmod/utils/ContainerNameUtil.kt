package dulkirmod.utils

import dulkirmod.DulkirMod.Companion.mc
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraft.inventory.ContainerChest
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

class ContainerNameUtil {
    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        if (mc.currentScreen !is GuiChest) return
        val chest = mc.currentScreen as GuiChest
        val container = chest.inventorySlots as ContainerChest
        currentGuiChestName = container.lowerChestInventory.displayName.unformattedText
    }

    companion object {
        var currentGuiChestName = ""
    }
}