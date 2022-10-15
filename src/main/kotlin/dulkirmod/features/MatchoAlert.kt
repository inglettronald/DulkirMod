package dulkirmod.features

import dulkirmod.DulkirMod
import dulkirmod.config.Config
import dulkirmod.utils.TabListUtils
import dulkirmod.utils.Utils

class MatchoAlert() {

    var hasSentAlert = false

    fun alert() {
        if (!Config.notifyMatcho) return
        if (!Utils.isInSkyblock()) return

        val scoreboardList: List<String?> = TabListUtils.fetchTabEntires().map {
            it.displayName?.unformattedText
        }

        var explo = false
        for (s in scoreboardList) {
            if (explo) {
                // This line is status of Volcano
                if (s != " INACTIVE" && !hasSentAlert) {
                    val color = Utils.getColorString(Config.bestiaryNotifColor)
                    DulkirMod.titleUtils.drawStringForTime("${color}Matcho", 5000)
                    if (Config.bestiaryAlertSounds)
                        DulkirMod.mc.thePlayer.playSound("mob.villager.yes", 1f * Config.bestiaryNotifVol, 0f)
                    hasSentAlert = true;
                } else if (s == " INACTIVE") hasSentAlert = false
                break;
            }
            if (s == "Volcano Explosivity:")
                explo = true
            if (s != null) {
                if (s.contains("Area:") && !s.contains("Crimson Isle")) {
                    hasSentAlert = false
                    break;
                }
            }
        }
    }
}