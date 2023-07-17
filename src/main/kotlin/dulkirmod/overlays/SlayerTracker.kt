package dulkirmod.overlays

import cc.polyfrost.oneconfig.hud.TextHud
import dulkirmod.utils.SlayerTrackerUtil.averageBossesPerHour
import dulkirmod.utils.SlayerTrackerUtil.averageSpawnKillTime
import dulkirmod.utils.SlayerTrackerUtil.averageXPPerHour
import dulkirmod.utils.SlayerTrackerUtil.currentSlayerType
import dulkirmod.utils.SlayerTrackerUtil.sessionTime
import dulkirmod.utils.SlayerTrackerUtil.sessionXP
import dulkirmod.utils.Utils
import java.text.NumberFormat

class SlayerTracker : TextHud(false) {
    override fun getLines(lines: MutableList<String>?, example: Boolean) {
        if (currentSlayerType != "" && !example) {
            val trimmedSlayer = if (currentSlayerType == "Endermen") {
                "Enderman"
            } else {
                currentSlayerType.trimEnd('s')
            }
            lines?.add("Slayer: $trimmedSlayer")
            lines?.add("Session XP: ${NumberFormat.getInstance().format(sessionXP)}")
            lines?.add("Average Spawn + Kill Time: ${"%.2f".format(averageSpawnKillTime)}s")
            lines?.add("Average Bosses Per Hour: ${"%.1f".format(averageBossesPerHour)}")
            lines?.add("Average XP Per Hour: ${NumberFormat.getInstance().format(averageXPPerHour)}")
            lines?.add("Session Timer: ${Utils.formatTime(sessionTime)}")
        }
    }
}