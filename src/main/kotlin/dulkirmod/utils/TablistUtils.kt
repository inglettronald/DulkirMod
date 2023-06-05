package dulkirmod.utils

import com.google.common.collect.ComparisonChain
import com.google.common.collect.Ordering
import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.DulkirConfig
import net.minecraft.client.network.NetworkPlayerInfo
import net.minecraft.world.WorldSettings
import net.minecraft.world.WorldSettings.GameType

// STOLEN FROM SKYTILS mmm yes
object TabListUtils {
    val NetworkPlayerInfo.text: String
        get() = mc.ingameGUI.tabList.getPlayerName(this)

    private val visitorPattern = "Visitors: \\((.+)\\)".toRegex()
    private val nextVisitorPattern = "Next Visitor: (.+)".toRegex()
    private val areaPattern = "Area: (.+)".toRegex()

    var area: String = ""
    var explosivity: Boolean = false
    var maxVisitors: Boolean = false
    var emptyComposter: Boolean = false
    var gardenMilestone: String = ""
    var timeTillNextVisitor: String = ""
    var numVisitors: Int = 0
    var archerName: String = ""

    var tabEntries = emptyList<Pair<NetworkPlayerInfo, String>>()

    private val playerInfoOrdering = object : Ordering<NetworkPlayerInfo>() {
        override fun compare(info1: NetworkPlayerInfo?, info2: NetworkPlayerInfo?): Int {
            if (info1 == null) return -1
            if (info2 == null) return 0
            return ComparisonChain.start()
                .compareTrueFirst(!info1.gameType.isSpectator(), !info2.gameType.isSpectator())
                .compare(info1.playerTeam?.registeredName ?: "", info2.playerTeam?.registeredName ?: "")
                .compare(info1.gameProfile.name, info2.gameProfile.name).result()
        }
    }

    fun fetchTabEntries(): List<NetworkPlayerInfo> {
        return if (mc.thePlayer == null) emptyList()
        else playerInfoOrdering.sortedCopy(mc.thePlayer.sendQueue.playerInfoMap)
    }

    /**
     * Sets a bunch of useful values based on the state of the scoreboard. Functionality is collected all into
     * this one method in order to avoid more transversal of the list than is necessary, as these checks need
     * to happen somewhat frequently.
     */
    fun parseTabEntries() {
        // exploFlag is just telling the loop that the next line is the relevant tab entry
        var exploFlag = false
        var numVisitorsFlag = false

        val scoreboardList = fetchTabEntries().mapNotNull {
            it.displayName?.unformattedText
        }


        for (line in scoreboardList) {
            val trimmed = line.trim()
            when {
                trimmed.contains(areaPattern) -> area = areaPattern.find(trimmed)!!.groupValues[1]
                trimmed == "Volcano Explosivity:" -> exploFlag = true
                exploFlag -> {
                    exploFlag = false
                    explosivity = trimmed != "INACTIVE"
                }

                trimmed == "Dungeon Stats" -> {
                    area = "Dungeon"
                }

                trimmed.startsWith("Time Left: ") -> {
                    emptyComposter = trimmed.split(": ")[1] == "INACTIVE"
                }

                trimmed.startsWith("Milestone") -> gardenMilestone = trimmed
                trimmed.contains(nextVisitorPattern) -> {
                    timeTillNextVisitor = nextVisitorPattern.find(trimmed)!!.groupValues[1]
                    maxVisitors = timeTillNextVisitor == "Queue Full!"
                }

                trimmed.contains(visitorPattern) -> {
                    numVisitors = visitorPattern.find(trimmed)!!.groupValues[1].toInt()
                    numVisitorsFlag = true
                }

                line.contains("(Archer") -> {
                    archerName = line.split(" ")[1]
                }
            }
        }

        if (area != "Dungeon") {
            archerName = ""
        }
        if (area != "Crimson Isle") {
            explosivity = false
        }
        if (area != "Garden") {
            maxVisitors = false
        }
        if (!numVisitorsFlag) {
            numVisitors = 0
        }
    }

    private fun GameType.isSpectator() = this == GameType.SPECTATOR
}