package dulkirmod.features.rift

import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.DulkirConfig
import dulkirmod.utils.*
import net.minecraft.util.Vec3
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import kotlin.math.max

object EffigyWaypoint {
    var effigyWaypoints = arrayOf(
        Effigy(Vec3(150.5, 76.0, 95.5)),
        Effigy(Vec3(193.5, 90.0, 119.5)),
        Effigy(Vec3(235.5, 107.0, 147.5)),
        Effigy(Vec3(294.5, 93.0, 134.5)),
        Effigy(Vec3(240.5, 126.0, 118.5)),
        Effigy(Vec3(262.5, 96.0, 94.5))
    )

    private val c7OnlyRegex = Regex("[^c7]")

    @SubscribeEvent
    fun onRender(event: RenderWorldLastEvent) {
        // if we have any waypoints that need rendering, Do so.
        val playerVec = mc.thePlayer.positionVector
        for (effigy in effigyWaypoints) {
            if (effigy.render) {
                WorldRenderUtils.renderString(effigy.coords, "§6Inactive", false,
                    max(1f, playerVec.distanceTo(effigy.coords).toFloat()/10f), true
                )
            }
        }
    }

    /**
     * Run once per second to check scoreboard data and update our data struct
     */
    fun checkEffigies() {
        if (!DulkirConfig.effigyWaypoint) return
        if (!Utils.isInSkyblock()) return
        if (TabListUtils.area != "The Rift") return
        val lines = ScoreBoardUtils.getLines()
        if (lines.size <= 7) return
        if (lines[3] != " §5ф §cStillgore\uD83D\uDC0D§c Château") return
        val effigyStatusLine = lines[6].replace(c7OnlyRegex, "")
        if (effigyStatusLine.length != 6) {
            TextUtils.info("§6Something went wrong with Scoreboard parsing in Effigy Feature.")
            TextUtils.info("   §6Turning off feature, please report to Dulkir.", prefix = false)
            DulkirConfig.effigyWaypoint = false
            return
        }
        for (i in 0..5) {
            effigyWaypoints[i].render = (effigyStatusLine[i] == '7')
        }
    }

    /**
     * data class for storing the effigy coordinates and whether they need to be rendered
     */
    data class Effigy(val coords: Vec3, var render: Boolean = false)
}