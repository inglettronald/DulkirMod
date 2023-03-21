package dulkirmod.features

import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.Config
import dulkirmod.utils.ScoreBoardUtils
import dulkirmod.utils.WorldRenderUtils
import net.minecraft.util.BlockPos
import net.minecraft.util.Vec3
import net.minecraft.world.World
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Color
import kotlin.math.max

class DragonTimer {
    data class Dragon (val color: String, val pos: Vec3, var spawnTime: Long)

    companion object {
        var dragons = arrayOf(
            Dragon("orange", Vec3(84.0, 18.0, 56.0), 0),
            Dragon("red", Vec3(27.0, 18.0, 56.0), 0),
            Dragon("green", Vec3(26.0, 18.0, 95.0), 0),
            Dragon("purple", Vec3(57.0, 18.0, 125.0), 0),
            Dragon("blue", Vec3(84.0, 18.0, 95.0), 0)
        )
    }

    /**
     * Called from within the MixinWorld Class
     */
    fun handleNewParticle(particleID: Int, xCoord: Double, yCoord: Double, zCoord: Double) {
        if (particleID != 26) return
        // if (!TabListUtils.isInDungeons) return
        if (!Config.dragonTimer) return
        //TextUtils.info("§6particle id ${particleID} 175 = $p_175720_2_")

        val particleVec = Vec3(xCoord, yCoord, zCoord)
        for (d in dragons) {
            if (inRangeOf(d.color, particleVec)) {
                if (System.currentTimeMillis() - d.spawnTime > 10000) {
                    d.spawnTime = System.currentTimeMillis()
                }
            }
        }
    }

    @SubscribeEvent
    fun onRenderWorld(event: RenderWorldLastEvent) {
        val curTime: Long = System.currentTimeMillis()
        // for some reason this really doesn't like the syntax for (d in dragons)
        for (i in 0..4) {
            // d.spawnTime + 5000 is when dragon actually spawn
            val d = dragons[i]
            if (d.spawnTime + 5000 > curTime) {
                if (isDead(d.color))
                    return
                val timeUntilSpawn: Float = ((d.spawnTime + 5000 - curTime).toFloat() / 1000f)
                val color = when {
                    timeUntilSpawn <= 1 -> "§c"
                    timeUntilSpawn <= 3 -> "§e"
                    else -> "§a"
                }
                val playerVec = mc.thePlayer.positionVector
                val scale = max(1f, playerVec.distanceTo(d.pos).toFloat()/5f)
                WorldRenderUtils.renderString(d.pos, "${color}${String.format("%.2f", timeUntilSpawn)}",
                    false, scale, true)
            }
        }
    }

    /**
     * true = dead
     */
    private fun isDead(color:String): Boolean {
        val world: World = mc.theWorld
        val pos = when (color) {
            "orange" -> BlockPos(90, 21, 56)
            "red" -> BlockPos(20,22,59)
            "green" -> BlockPos(22,21,94)
            "purple" -> BlockPos(56,20,130)
            "blue" -> BlockPos(89,21,94)
            else -> BlockPos(0,0,0)
        }
        return world.isAirBlock(pos)
    }
    private fun inRangeOf(color: String, pos: Vec3): Boolean {
        when (color) {
            "orange" -> {
                return (pos.xCoord.toInt() in 82..88
                        && pos.yCoord.toInt() in 15..22
                        && pos.zCoord.toInt() in 53..59)
            }
            "red" -> {
                return (pos.xCoord.toInt() in 24..30
                        && pos.yCoord.toInt() in 15..22
                        && pos.zCoord.toInt() in 56..62)
            }
            "green" -> {
                return (pos.xCoord.toInt() in 23..29
                        && pos.yCoord.toInt() in 15..22
                        && pos.zCoord.toInt() in 91..97)
            }
            "purple" -> {
                return (pos.xCoord.toInt() in 53..59
                        && pos.yCoord.toInt() in 15..22
                        && pos.zCoord.toInt() in 122..128)
            }
            "blue" -> {
                return (pos.xCoord.toInt() in 82..88
                        && pos.yCoord.toInt() in 15..22
                        && pos.zCoord.toInt() in 91..97)
            }
            else -> {
                return false
            }
        }
    }

    @SubscribeEvent
    fun dragonBoxes(event: RenderWorldLastEvent) {
        if (!Config.dragonKillBox) return
        if (!ScoreBoardUtils.isInM7) return
        if (mc.thePlayer.positionVector.yCoord > 45) return
        // Blue
        WorldRenderUtils.drawCustomBox(71.5, 25.0, 16.0, 10.0, 82.5, 25.0, Color(0, 170, 170, 255), 3f, phase = false)
        // Purple
        WorldRenderUtils.drawCustomBox(45.5, 23.0, 13.0, 10.0, 113.5, 23.0, Color(170, 0, 170, 255), 3f, phase = false)
        // Green
        WorldRenderUtils.drawCustomBox(7.0, 30.0, 8.0, 20.0, 80.0, 30.0, Color(85, 255, 85, 255), 3f, phase = false)
        // Red
        WorldRenderUtils.drawCustomBox(14.5, 25.0, 13.0, 15.0, 45.5, 25.0, Color(255, 85, 85, 255), 3f, phase = false)
        // Orange
        WorldRenderUtils.drawCustomBox(72.0, 30.0, 8.0, 20.0, 47.0, 30.0, Color(255, 170, 0, 255), 3f, phase = false)
    }
}

