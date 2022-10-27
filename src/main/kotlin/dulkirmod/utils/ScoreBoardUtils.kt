package dulkirmod.utils

import dulkirmod.DulkirMod
import net.minecraft.scoreboard.Score
import net.minecraft.scoreboard.ScorePlayerTeam

object ScoreBoardUtils {
    fun getLines() : MutableList<String> {
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
}