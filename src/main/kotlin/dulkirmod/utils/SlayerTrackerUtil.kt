package dulkirmod.utils

import dulkirmod.config.DulkirConfig
import dulkirmod.events.SlayerTypeChangeEvent
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object SlayerTrackerUtil {
    private var sessionStartTime = 0L
    var sessionXP: Int = 0
    var averageSpawnKillTime: Float = 0f
    var averageBossesPerHour: Float = 0f
    var averageXPPerHour: Int = 0
    var currentSlayerType: String = ""
    private var lastKillTime = 0L
    var sessionTime: Long = 0L
    var failedRecentSlayerFlag = false

    val slayerQuestStartRegex = "» Slay (\\d{1,3}(?:,\\d{3})*) Combat XP worth of (\\w+).".toRegex()
    val slayerQuestFailRegex = "SLAYER QUEST FAILED!".toRegex()
    @SubscribeEvent
    fun onSlayerTypeChange(event: SlayerTypeChangeEvent) {
        // reset relevant tracking features
        sessionXP = 0
        averageSpawnKillTime = 0f
        averageBossesPerHour = 0f
        averageXPPerHour = 0
        sessionStartTime = System.currentTimeMillis()
        lastKillTime = sessionStartTime
        sessionTime = 0L
        currentSlayerType = ""
        failedRecentSlayerFlag = false
    }

    @SubscribeEvent(receiveCanceled = true, priority = EventPriority.LOW)
    fun onChat(event: ClientChatReceivedEvent) {
        if (event.type == 2.toByte()) {
            return
        }
        val unformatted = Utils.stripColorCodes(event.message.unformattedText).trim()
        if (unformatted matches slayerQuestFailRegex) {
            failedRecentSlayerFlag = true
            return
        }

        if (unformatted matches slayerQuestStartRegex) {
            val type = slayerQuestStartRegex.find(unformatted)?.groupValues?.get(2)
            if (type == null) {
                TextUtils.info("Failed to parse slayer type, please report to dulkir.")
                return
            }
            if (type != currentSlayerType) {
                MinecraftForge.EVENT_BUS.post(SlayerTypeChangeEvent())
                currentSlayerType = type
                return
            }

            if (failedRecentSlayerFlag) {
                failedRecentSlayerFlag = false
                return
            }

            sessionXP += DulkirConfig.slayerXP
            val totalBosses = sessionXP/DulkirConfig.slayerXP

            averageSpawnKillTime = ((averageSpawnKillTime * (totalBosses - 1) + (System.currentTimeMillis() - lastKillTime) / 1000)) / totalBosses
            lastKillTime = System.currentTimeMillis()
            averageBossesPerHour = 3600 / averageSpawnKillTime
            averageXPPerHour = (averageBossesPerHour * 500).toInt()
        }
    }

    fun updateSessionTime() {
        if (currentSlayerType != "")
        sessionTime = System.currentTimeMillis() - sessionStartTime
    }
}