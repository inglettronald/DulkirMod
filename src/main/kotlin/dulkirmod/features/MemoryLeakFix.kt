package dulkirmod.features

import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.DulkirConfig
import net.minecraft.entity.Entity
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
}