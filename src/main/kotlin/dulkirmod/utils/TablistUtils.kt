package dulkirmod.utils

import com.google.common.collect.ComparisonChain
import com.google.common.collect.Ordering
import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.DulkirConfig
import net.minecraft.client.network.NetworkPlayerInfo
import net.minecraft.world.WorldSettings

val NetworkPlayerInfo.text: String
    get() = mc.ingameGUI.tabList.getPlayerName(this)

// STOLEN FROM SKYTILS mmm yes
object TabListUtils {
    var area: String = ""
    var explosivity: Boolean = false
    var maxVisitors: Boolean = false
    var emptyComposter: Boolean = false
    var gardenMilestone: String = ""
    var timeTillNextVisitor: String = ""
    var numVisitors: Int = 0
    var archerName: String = ""

    private val playerInfoOrdering = object : Ordering<NetworkPlayerInfo>() {
        override fun compare(p_compare_1_: NetworkPlayerInfo?, p_compare_2_: NetworkPlayerInfo?): Int {
            val scorePlayerTeam = p_compare_1_?.playerTeam
            val scorePlayerTeam1 = p_compare_2_?.playerTeam
            if (p_compare_1_ != null) {
                if (p_compare_2_ != null) {
                    return ComparisonChain.start().compareTrueFirst(
                        p_compare_1_.gameType != WorldSettings.GameType.SPECTATOR,
                        p_compare_2_.gameType != WorldSettings.GameType.SPECTATOR
                    ).compare(
                        if (scorePlayerTeam != null) scorePlayerTeam.registeredName else "",
                        if (scorePlayerTeam1 != null) scorePlayerTeam1.registeredName else ""
                    ).compare(p_compare_1_.gameProfile.name, p_compare_2_.gameProfile.name).result()
                }
                return 0
            }
            return -1
        }
    }
    var tabEntries: List<Pair<NetworkPlayerInfo, String>> = emptyList()
    fun fetchTabEntries(): List<NetworkPlayerInfo> =
        if (mc.thePlayer == null) emptyList() else playerInfoOrdering.sortedCopy(
            mc.thePlayer.sendQueue.playerInfoMap
        )

    /**
     * Sets a bunch of useful values based on the state of the scoreboard. Functionality is collected all into
     * this one method in order to avoid more transversal of the list than is necessary, as these checks need
     * to happen somewhat frequently.
     */
    fun parseTabEntries() {
        // exploFlag is just telling the loop that the next line is the relevant tab entry
        var exploFlag = false
        var numVisitorsFlag = false
        // dungeonFlag keeps track of whether we've found the in-dungeons state.
        val scoreboardList: List<String> = fetchTabEntries().mapNotNull {
            it.displayName?.unformattedText
        }


        for (line in scoreboardList) {
            when {
                line.startsWith("Area: ") -> area = line.substring(6)
                line == "Volcano Explosivity:" -> exploFlag = true
                exploFlag -> {
                    exploFlag = false
                    if (line != " INACTIVE") {
                        explosivity = true
                    }
                }
                line == "       Dungeon Stats" -> {
                    area = "Dungeon"
                }
                line.startsWith(" Time Left:") -> {
                    emptyComposter = (line.substring(12) == "INACTIVE")
                }
                line.startsWith(" Milestone") -> gardenMilestone = line.substring(1)
                line.startsWith(" Next Visitor:") -> {
                    timeTillNextVisitor = line.substring(15)
                    maxVisitors = (timeTillNextVisitor == "Queue Full!")
                }
                line.startsWith("Visitors:") -> {
                    numVisitors = line.substring(11, 12).toInt() // TODO: FIX WHEN THEY ADD THE TENTH VISITOR
                    numVisitorsFlag = true
                }
                line.contains("(Archer") -> {
                    val strArr = line.split(" ")
                    archerName = strArr[1]
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
}