package dulkirmod.features

import dulkirmod.DulkirMod
import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.Config
import dulkirmod.utils.Utils
import net.minecraft.scoreboard.Score
import net.minecraft.scoreboard.ScorePlayerTeam
import net.minecraft.util.EnumChatFormatting

var lastUpdate : Long = 0

fun alarmClock() {
    // CHECK IF IN SKYBLOCK
    if (!Utils.isInSkyblock()) return
    // CHECK TIME
    val currTime : Long = System.currentTimeMillis()
    val scoreboard = mc.thePlayer.worldScoreboard
    val sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1)
    val scores: List<Score> = ArrayList(scoreboard.getSortedScores(sidebarObjective))
    val lines: MutableList<String> = ArrayList()
    for (i in scores.indices.reversed()) {
        val score = scores[i]
        val scoreplayerteam1 = scoreboard.getPlayersTeam(score.playerName)
        val line = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score.playerName)
        lines.add(line)
    }
    for (l in lines) {
        // ZOMBIE VILLAGER
        if (Config.notifyZombieVillager && l.contains("8:00pm") && (currTime - lastUpdate) > 15000) {
            lastUpdate = currTime
            val color = if (Config.bestiaryNotifColor == 16) "§z" else EnumChatFormatting.values()[Config.bestiaryNotifColor]
            DulkirMod.titleUtils.drawStringForTime("${color}Zombie Villager", 5000)
            if (Config.bestiaryAlertSounds)
                mc.thePlayer.playSound("mob.villager.yes", 1f * Config.bestiaryNotifVol, 0f)
        }
        // GHASTS
        else if (Config.notifyGhast && l.contains("9:00pm") && (currTime - lastUpdate) > 15000) {
            lastUpdate = currTime
            val color = if (Config.bestiaryNotifColor == 16) "§z" else EnumChatFormatting.values()[Config.bestiaryNotifColor]
            DulkirMod.titleUtils.drawStringForTime("${color}Ghast", 5000)
            if (Config.bestiaryAlertSounds)
                mc.thePlayer.playSound("mob.ghast.scream", 1f * Config.bestiaryNotifVol, 1f)
        }

    }

}