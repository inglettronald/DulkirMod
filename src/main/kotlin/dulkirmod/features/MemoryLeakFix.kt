package dulkirmod.features

import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.DulkirConfig
import dulkirmod.utils.WorldRenderUtils
import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityArmorStand
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import java.awt.Color

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

	@SubscribeEvent
	fun displayBlankStands(event: RenderLivingEvent.Post<*>) {
		if (!DulkirConfig.debugStandRemoval) return
		if (event.entity !is EntityArmorStand) return
		if (event.entity.hasCustomName()) return
		if (event.entity.inventory.any{slot -> slot != null}) return
		val pos = WorldRenderUtils.fixRenderPos(event.x, event.y, event.z)
		val x = pos[0]
		val y = pos[1]
		val z = pos[2]

		WorldRenderUtils.drawCustomBox(
			x - .5,
			1.0,
			y - .5,
			1.0,
			z - .5,
			1.0,
			Color(15, 247, 236, 255),
			3f,
			phase = true
		)
	}
}