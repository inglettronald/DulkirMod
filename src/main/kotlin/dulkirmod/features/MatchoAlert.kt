package dulkirmod.features

import dulkirmod.DulkirMod
import dulkirmod.config.DulkirConfig
import dulkirmod.utils.TabListUtils
import dulkirmod.utils.TitleUtils
import dulkirmod.utils.Utils

object MatchoAlert {

    var hasSentAlert = false

    fun alert() {
        if (!DulkirConfig.notifyMatcho) return
        if (!Utils.isInSkyblock()) return

        if (TabListUtils.area != "Crimson Isle") {
            hasSentAlert = false
        }

        if (TabListUtils.explosivity && !hasSentAlert) {
            val color = Utils.getColorString(DulkirConfig.bestiaryNotifColor)
            TitleUtils.drawStringForTime("${color}Matcho", 5000)
            if (DulkirConfig.bestiaryAlertSounds)
                DulkirMod.mc.thePlayer.playSound("mob.villager.yes", 1f * DulkirConfig.bestiaryNotifVol, 0f)
            hasSentAlert = true
        } else if (!TabListUtils.explosivity) hasSentAlert = false
    }
}