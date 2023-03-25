package dulkirmod.features

import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.DulkirConfig
import dulkirmod.utils.ScoreBoardUtils
import dulkirmod.utils.TitleUtils
import dulkirmod.utils.Utils

var lastUpdate: Long = 0

fun alarmClock() {
    // CHECK IF IN SKYBLOCK
    if (!Utils.isInSkyblock()) return
    // CHECK TIME
    val currTime: Long = System.currentTimeMillis()
    val lines = ScoreBoardUtils.getLines()
    for (l in lines) {
        // ZOMBIE VILLAGER
        if (DulkirConfig.notifyZombieVillager && l.contains("8:00pm") && (currTime - lastUpdate) > 15000) {
            lastUpdate = currTime
            val color = Utils.getColorString(DulkirConfig.bestiaryNotifColor)
            TitleUtils.drawStringForTime("${color}Zombie Villager", 5000)
            if (DulkirConfig.bestiaryAlertSounds)
                mc.thePlayer.playSound("mob.villager.yes", 1f * DulkirConfig.bestiaryNotifVol, 0f)
        }
        // GHASTS
        else if (DulkirConfig.notifyGhast && l.contains("9:00pm") && (currTime - lastUpdate) > 15000) {
            lastUpdate = currTime
            val color = Utils.getColorString(DulkirConfig.bestiaryNotifColor)
            TitleUtils.drawStringForTime("${color}Ghast", 5000)
            if (DulkirConfig.bestiaryAlertSounds)
                mc.thePlayer.playSound("mob.ghast.scream", 1f * DulkirConfig.bestiaryNotifVol, 1f)
        }

    }

}