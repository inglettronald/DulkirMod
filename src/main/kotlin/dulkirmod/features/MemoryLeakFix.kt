package dulkirmod.features

import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.DulkirConfig
import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityArmorStand
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

object MemoryLeakFix {
	private var lastClear = System.currentTimeMillis()

	@SubscribeEvent
	fun onTick(event: TickEvent.ClientTickEvent) {
		if (!DulkirConfig.crimsonIslesMemoryLeakPatch) return

		if (System.currentTimeMillis() - lastClear >= 30000L) {
			val world = mc.theWorld ?: return
			val currentEnts = world.playerEntities.toMutableList()
			currentEnts.forEach {
				if (it.isDead) {
					world.playerEntities.remove(it)
				}
				if (isNullVec(it)) {
					world.removeEntityFromWorld(it.entityId)
				}
			}
			lastClear = System.currentTimeMillis()
		}
	}

	private fun isNullVec(entity: Entity): Boolean {
		return entity.posX == 0.0 && entity.posY == 0.0 && entity.posZ == 0.0
	}

	fun clearBlankStands() {
		if (!DulkirConfig.blankStandRemoval) return
		val world = mc.theWorld ?: return
		val currentEnts = world.loadedEntityList
		currentEnts.forEach {
			if (it !is EntityArmorStand) return@forEach
			if (it.hasCustomName()) return@forEach
			if (it.inventory.any{slot -> slot != null}) return@forEach
			if (it.ticksExisted < 1200) return@forEach
			world.removeEntityFromWorld(it.entityId)
		}
	}
}