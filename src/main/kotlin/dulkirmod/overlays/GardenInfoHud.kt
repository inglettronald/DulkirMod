package dulkirmod.overlays

import cc.polyfrost.oneconfig.hud.TextHud
import dulkirmod.config.DulkirConfig
import dulkirmod.utils.TabListUtils
import dulkirmod.utils.Utils

class GardenInfoHud : TextHud(false) {
    override fun getLines(lines: MutableList<String>?, example: Boolean) {
        if (!Utils.isInSkyblock()) return
        if (TabListUtils.area != "Garden") return
        if (DulkirConfig.gardenMilestoneDisplay && TabListUtils.gardenMilestone != "") {
            lines?.add(TabListUtils.gardenMilestone)
        }
        if (DulkirConfig.visitorInfo) {
            if (TabListUtils.numVisitors == -1) {
                lines?.add("No Visitor info")
            } else {
                lines?.add("Visitors: ${TabListUtils.numVisitors} - ${TabListUtils.timeTillNextVisitor}")
            }
        }
        if (DulkirConfig.composterAlert) {
            if (TabListUtils.emptyComposter) {
                lines?.add("Empty Composter!")
            }
        }
    }
}