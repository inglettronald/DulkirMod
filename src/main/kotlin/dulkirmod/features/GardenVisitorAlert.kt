package dulkirmod.features

import dulkirmod.DulkirMod
import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.DulkirConfig
import dulkirmod.utils.TabListUtils
import dulkirmod.utils.TitleUtils
import dulkirmod.utils.Utils
import net.minecraft.client.audio.SoundCategory

object GardenVisitorAlert {
    private var hasSentAlert = false
    private var lastAlert = 0

    fun alert() {
        if (!DulkirConfig.notifyMaxVisitors) return
        if (!Utils.isInSkyblock()) return

        if (TabListUtils.area != "Garden") {
            hasSentAlert = false
        }

        if (TabListUtils.maxVisitors && !hasSentAlert) {
            val color = Utils.getColorString(DulkirConfig.bestiaryNotifColor)
            TitleUtils.drawStringForTime("${color}Max Visitors", 5000)


            val prevNote = mc.gameSettings.getSoundLevel(SoundCategory.RECORDS)
            mc.gameSettings.setSoundLevel(SoundCategory.RECORDS, 1f)
            mc.thePlayer.playSound("note.pling", 1f * DulkirConfig.bestiaryNotifVol, .3f)
            mc.thePlayer.playSound("note.pling", 1f * DulkirConfig.bestiaryNotifVol, .6f)
            mc.thePlayer.playSound("note.pling", 1f * DulkirConfig.bestiaryNotifVol, .9f)
            mc.gameSettings.setSoundLevel(SoundCategory.RECORDS, prevNote)
            hasSentAlert = true
            lastAlert = System.currentTimeMillis().toInt()
        } else if (!TabListUtils.maxVisitors) hasSentAlert = false

        val timeSinceLastAlert = System.currentTimeMillis().toInt() - lastAlert

        if (TabListUtils.maxVisitors && hasSentAlert && timeSinceLastAlert > 5000 && DulkirConfig.persistentAlert) {
            lastAlert = System.currentTimeMillis().toInt()
            val color = Utils.getColorString(DulkirConfig.bestiaryNotifColor)
            TitleUtils.drawStringForTime("${color}Max Visitors", 5000)
            mc.thePlayer.playSound("note.pling", 1f * DulkirConfig.bestiaryNotifVol, .3f)
            mc.thePlayer.playSound("note.pling", 1f * DulkirConfig.bestiaryNotifVol, .6f)
            mc.thePlayer.playSound("note.pling", 1f * DulkirConfig.bestiaryNotifVol, .9f)
        }
    }

}