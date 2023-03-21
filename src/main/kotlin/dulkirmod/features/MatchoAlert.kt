package dulkirmod.features

import dulkirmod.DulkirMod
import dulkirmod.config.Config
import dulkirmod.utils.TabListUtils
import dulkirmod.utils.TitleUtils
import dulkirmod.utils.Utils

object MatchoAlert {

    var hasSentAlert = false

    fun alert() {
        if (!Config.notifyMatcho) return
        if (!Utils.isInSkyblock()) return

        if (TabListUtils.area != "Crimson Isle") {
            hasSentAlert = false
        }

        if (TabListUtils.explosivity && !hasSentAlert) {
            val color = Utils.getColorString(Config.bestiaryNotifColor)
            TitleUtils.drawStringForTime("${color}Matcho", 5000)
            if (Config.bestiaryAlertSounds)
                DulkirMod.mc.thePlayer.playSound("mob.villager.yes", 1f * Config.bestiaryNotifVol, 0f)
            hasSentAlert = true
        } else if (!TabListUtils.explosivity) hasSentAlert = false
    }
}