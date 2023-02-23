package dulkirmod.features

import dulkirmod.DulkirMod
import dulkirmod.config.Config
import dulkirmod.utils.TabListUtils
import dulkirmod.utils.Utils

class GardenVisitorAlert {
    var hasSentAlert = false

    fun alert() {
        if (!Config.notifyMaxVisitors) return
        if (!Utils.isInSkyblock()) return

        if (TabListUtils.area != "Garden") {
            hasSentAlert = false
        }

        if (TabListUtils.maxVisitors && !hasSentAlert) {
            val color = Utils.getColorString(Config.bestiaryNotifColor)
            DulkirMod.titleUtils.drawStringForTime("${color}Max Visitors", 5000)
            DulkirMod.mc.thePlayer.playSound("mob.cat.meow", 1f * Config.bestiaryNotifVol, 1f)
            hasSentAlert = true
        } else if (!TabListUtils.maxVisitors) hasSentAlert = false

    }

}