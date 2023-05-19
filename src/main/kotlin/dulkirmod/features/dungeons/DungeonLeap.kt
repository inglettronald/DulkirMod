package dulkirmod.features.dungeons

import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.DulkirConfig
import dulkirmod.utils.ContainerNameUtil
import dulkirmod.utils.Utils
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraft.inventory.Slot
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

object DungeonLeap {
    var inLeapMenu = false
    var leapPlayers = BooleanArray(5) { false }

	private var lastGuiOpenEvent = 0L

	@SubscribeEvent
	fun onTick(event: TickEvent.ClientTickEvent) {
		val lastInLeap = inLeapMenu

		if (!DulkirConfig.highlightLeap) return
		if (mc.currentScreen == null || mc.currentScreen !is GuiChest) {
			inLeapMenu = false
			return
		}
		inLeapMenu = (ContainerNameUtil.currentGuiChestName == "Spirit Leap")

		if (inLeapMenu && !lastInLeap) {
			lastGuiOpenEvent = System.currentTimeMillis()
		}

		if (inLeapMenu && System.currentTimeMillis() - lastGuiOpenEvent < 300) {
			for (i in 11..15) {
				leapPlayers[i - 11] = false
				val slotIn = mc.thePlayer.openContainer.getSlot(i)

				if (slotIn.stack == null) continue
				val stack = slotIn.stack
				if (Utils.stripColorCodes(stack.displayName).equals(DulkirConfig.highlightLeapName, true)) {
                    leapPlayers[i - 11] = true
                }
			}
		}
	}

	fun isHighlightedLeapPlayer(slotIn: Slot): Boolean {
		if (!inLeapMenu) return false
		if (slotIn.inventory == mc.thePlayer.inventory) return false
		val slotIndex = slotIn.slotIndex
		if (slotIndex !in 11..15) return false
		return leapPlayers[slotIndex - 11]
	}
}