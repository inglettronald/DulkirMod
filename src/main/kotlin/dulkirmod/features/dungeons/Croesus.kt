package dulkirmod.features.dungeons

import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.DulkirConfig
import dulkirmod.utils.ContainerNameUtil
import net.minecraft.client.gui.inventory.GuiChest
import net.minecraft.inventory.Slot
import net.minecraft.nbt.NBTTagList
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

object Croesus {

	var lastGuiOpenEvent: Long = 0
	var lastPageNumber = 1

	@SubscribeEvent
	fun onTick(event: TickEvent.ClientTickEvent) {
		val lastInCroesus = inCroesusBool
		var pageNumber = 1

		if (!DulkirConfig.hideOpenedChests) return
		if (mc.currentScreen == null || !(mc.currentScreen is GuiChest)) {
			inCroesusBool = false
			return
		}
		inCroesusBool = (ContainerNameUtil.currentGuiChestName == "Croesus")

		if (inCroesusBool) {
			pageNumber = findPageNumber()
		}

		// weird way of detecting page turn
		if (lastPageNumber != pageNumber)
			lastGuiOpenEvent = System.currentTimeMillis()

		if (inCroesusBool && !lastInCroesus) {
			lastGuiOpenEvent = System.currentTimeMillis()
		}

		if (inCroesusBool && System.currentTimeMillis() - lastGuiOpenEvent < 300) {
			for (i in 9..44) {
				boolArray[i - 9] = false
				val slotIn = mc.thePlayer.openContainer.getSlot(i)

				if (slotIn.stack == null) continue
				val stack = slotIn.stack

				val tagList: NBTTagList = stack.getSubCompound("display", false)?.getTagList("Lore", 8) ?: continue
				for (j in 0 until tagList.tagCount()) {
					if (tagList.getStringTagAt(j) == "§aNo more Chests to open!") boolArray[i - 9] = true
				}
			}
		}
	}

	private fun findPageNumber(): Int {
		val stackPrev = mc.thePlayer.openContainer.getSlot(45).stack ?: return lastPageNumber

		val stackPrevLore = stackPrev.getSubCompound("display", false)?.getTagList("Lore", 8) ?: return 1

		if (stackPrevLore.getStringTagAt(0).contains("1")) return 2

		return 3
	}

	var inCroesusBool: Boolean = false

	var boolArray = BooleanArray(36) { false }

	@JvmStatic
	fun inCroesus(): Boolean {
		return inCroesusBool
	}

	@JvmStatic
	fun isChestOpened(slotIn: Slot): Boolean {
		if (!inCroesusBool) return false
		if (slotIn.inventory == mc.thePlayer.inventory) return false
		val slotindex = slotIn.slotIndex
		if (slotindex !in 9..44) return false
		return boolArray[slotindex - 9]
	}
}