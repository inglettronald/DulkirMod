package dulkirmod.features

import dulkirmod.DulkirMod
import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.Config
import dulkirmod.utils.ScoreBoardUtils
import dulkirmod.utils.Utils

var lastUpdate : Long = 0

fun alarmClock() {
    // CHECK IF IN SKYBLOCK
    if (!Utils.isInSkyblock()) return
    // CHECK TIME
    val currTime : Long = System.currentTimeMillis()
    val lines = ScoreBoardUtils.getLines()
    for (l in lines) {
        // ZOMBIE VILLAGER
        if (Config.notifyZombieVillager && l.contains("8:00pm") && (currTime - lastUpdate) > 15000) {
            lastUpdate = currTime
            val color = Utils.getColorString(Config.bestiaryNotifColor)
            DulkirMod.titleUtils.drawStringForTime("${color}Zombie Villager", 5000)
            if (Config.bestiaryAlertSounds)
                mc.thePlayer.playSound("mob.villager.yes", 1f * Config.bestiaryNotifVol, 0f)
        }
        // GHASTS
        else if (Config.notifyGhast && l.contains("9:00pm") && (currTime - lastUpdate) > 15000) {
            lastUpdate = currTime
            val color = Utils.getColorString(Config.bestiaryNotifColor)
            DulkirMod.titleUtils.drawStringForTime("${color}Ghast", 5000)
            if (Config.bestiaryAlertSounds)
                mc.thePlayer.playSound("mob.ghast.scream", 1f * Config.bestiaryNotifVol, 1f)
        }

    }

}