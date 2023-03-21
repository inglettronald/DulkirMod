package dulkirmod.utils

import dulkirmod.DulkirMod.Companion.mc
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraft.inventory.ContainerChest
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

object ContainerNameUtil {
    var currentGuiChestName = ""

    @SubscribeEvent
	fun onTick(event: TickEvent.ClientTickEvent) {
        val chest = mc.currentScreen
		if (chest !is GuiChest) return
		val container = chest.inventorySlots as ContainerChest
		currentGuiChestName = container.lowerChestInventory.displayName.unformattedText
	}
}