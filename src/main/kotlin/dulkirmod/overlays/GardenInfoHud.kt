package dulkirmod.overlays

import cc.polyfrost.oneconfig.hud.TextHud
import dulkirmod.config.DulkirConfig
import dulkirmod.utils.TabListUtils
import dulkirmod.utils.Utils

class GardenInfoHud : TextHud(false) {
    override fun getLines(lines: MutableList<String>?, example: Boolean) {
        if (!Utils.isInSkyblock()) return
        if (TabListUtils.area != "Garden") return
        var i = 0
        if (DulkirConfig.gardenMilestoneDisplay) {
            lines?.add(i, TabListUtils.gardenMilestone)
            ++i
        }
        if (DulkirConfig.visitorInfo) {
            lines?.add(i, "Visitors: ${TabListUtils.numVisitors} - ${TabListUtils.timeTillNextVisitor}")
            ++i
        }
        if (DulkirConfig.composterAlert) {
            if (TabListUtils.emptyComposter) {
                lines?.add(i, "Empty Composter!")
                ++i
            }
        }
    }
}