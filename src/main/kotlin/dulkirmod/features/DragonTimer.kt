package dulkirmod.features

import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.DulkirConfig
import dulkirmod.utils.ScoreBoardUtils
import dulkirmod.utils.WorldRenderUtils
import net.minecraft.util.BlockPos
import net.minecraft.util.Vec3
import net.minecraft.world.World
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Color
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.math.max

object DragonTimer {
	data class Dragon(val color: String, val pos: Vec3, var spawnTime: Long)

	private val dragons = arrayOf(
		Dragon("orange", Vec3(84.0, 18.0, 56.0), 0),
		Dragon("red", Vec3(27.0, 18.0, 56.0), 0),
		Dragon("green", Vec3(26.0, 18.0, 95.0), 0),
		Dragon("purple", Vec3(57.0, 18.0, 125.0), 0),
		Dragon("blue", Vec3(84.0, 18.0, 95.0), 0)
	)

	/**
	 * Called from within the MixinWorld Class
	 */
	fun handleNewParticle(pID: Int, x: Double, y: Double, z: Double) {
		if (!DulkirConfig.dragonTimer) return
		if (!ScoreBoardUtils.isInM7) return

		if (pID != 26) return

		val particleVec = Vec3(x, y, z)
		dragons.forEach {
			if (System.currentTimeMillis() - it.spawnTime < 10000 || !inRangeOf(it.color, particleVec)) return@forEach
			it.spawnTime = System.currentTimeMillis()
		}
	}

	@SubscribeEvent
	fun onRenderWorld(event: RenderWorldLastEvent) {
		renderDragonBoxes()

		if (!DulkirConfig.dragonTimer) return
		if (!ScoreBoardUtils.isInM7) return

		val curTime = System.currentTimeMillis()
		dragons.forEach {
			if (it.spawnTime + 5000 < curTime || isDead(it.color)) return@forEach
			val timeUntilSpawn = (it.spawnTime + 5000 - curTime) / 1000f
			val color = when {
				timeUntilSpawn <= 1 -> "§c"
				timeUntilSpawn <= 3 -> "§e"
				else -> "§a"
			}

			val scale = max(1.0, mc.thePlayer.positionVector.distanceTo(it.pos) / 5.0).toFloat()

			WorldRenderUtils.renderString(
				it.pos, "${color}${String.format("%.2f", timeUntilSpawn)}", false, scale, true
			)
		}
	}

	/**
	 * true = dead
	 */
	private fun isDead(color: String): Boolean {
		val world: World = mc.theWorld
		val pos = when (color) {
			"orange" -> BlockPos(90, 21, 56)
			"red" -> BlockPos(20, 22, 59)
			"green" -> BlockPos(22, 21, 94)
			"purple" -> BlockPos(56, 20, 130)
			"blue" -> BlockPos(89, 21, 94)
			else -> BlockPos(0, 0, 0)
		}
		return world.isAirBlock(pos)
	}

	private fun inRangeOf(color: String, pos: Vec3): Boolean {
		val x = pos.xCoord.toInt()
		val y = pos.yCoord.toInt()
		val z = pos.zCoord.toInt()

		return when (color) {
			"orange" -> {
				x in 82..88 && y in 15..22 && z in 53..59
			}

			"red" -> {
				x in 24..30 && y in 15..22 && z in 56..62
			}

			"green" -> {
				x in 23..29 && y in 15..22 && z in 91..97
			}

			"purple" -> {
				x in 53..59 && y in 15..22 && z in 122..128
			}

			"blue" -> {
				x in 82..88 && y in 15..22 && z in 91..97
			}

			else -> {
				false
			}
		}
	}

	private fun renderDragonBoxes() {
		if (!DulkirConfig.dragonKillBox) return
		if (!ScoreBoardUtils.isInM7) return
		if (mc.thePlayer.positionVector.yCoord > 45) return
		// Blue
		if (!isDead("blue"))
			WorldRenderUtils.drawCustomBox(71.5, 25.0, 16.0, 10.0, 82.5, 25.0, Color(0, 170, 170, 255), 3f, phase = false)
		// Purple
		if (!isDead("purple"))
			WorldRenderUtils.drawCustomBox(45.5, 23.0, 13.0, 10.0, 113.5, 23.0, Color(170, 0, 170, 255), 3f, phase = false)
		// Green
		if (!isDead("green"))
			WorldRenderUtils.drawCustomBox(7.0, 30.0, 8.0, 20.0, 80.0, 30.0, Color(85, 255, 85, 255), 3f, phase = false)
		// Red
		if (!isDead("red"))
			WorldRenderUtils.drawCustomBox(14.5, 25.0, 13.0, 15.0, 45.5, 25.0, Color(255, 85, 85, 255), 3f, phase = false)
		// Orange
		if (!isDead("orange"))
			WorldRenderUtils.drawCustomBox(72.0, 30.0, 8.0, 20.0, 47.0, 29.0, Color(255, 170, 0, 255), 3f, phase = false)
	}

	@SubscribeEvent
	fun updateM7Check(event: WorldEvent.Load) {
		val executor = Executors.newSingleThreadScheduledExecutor()
		executor.schedule({
			ScoreBoardUtils.inM7()
		}, 10, TimeUnit.SECONDS)
	}
}