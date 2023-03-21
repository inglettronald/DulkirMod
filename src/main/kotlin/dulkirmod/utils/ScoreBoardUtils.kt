package dulkirmod.utils

import dulkirmod.DulkirMod
import net.minecraft.scoreboard.Score
import net.minecraft.scoreboard.ScorePlayerTeam

object ScoreBoardUtils {
    var isInM7: Boolean = false;
    fun getLines(): MutableList<String> {
        val scoreboard = DulkirMod.mc.thePlayer.worldScoreboard
        val sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1)
        val scores: List<Score> = ArrayList(scoreboard.getSortedScores(sidebarObjective))
        val lines: MutableList<String> = ArrayList()
        for (i in scores.indices.reversed()) {
            val score = scores[i]
            val scoreplayerteam1 = scoreboard.getPlayersTeam(score.playerName)
            val line = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score.playerName)
            lines.add(line)
        }
        return lines
    }

    fun inM7(): Boolean {
        if (!Utils.isInSkyblock()) {
            isInM7 = false
            return false
        }
        val lines = getLines()
        if (lines.size < 4) {
            isInM7 = false
            return false
        }
        val line = lines.getOrNull(3)
        var unformattedText = line?.replace("\\p{So}|\\p{Sk}".toRegex(), "")
        if (unformattedText != null) {
            unformattedText = Utils.stripColorCodes(unformattedText)
        }
        if (unformattedText == "  The Catacombs (M7)") {
            isInM7 = true
            return true
        }
        isInM7 = false
        return false
    }
}