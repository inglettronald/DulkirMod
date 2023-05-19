package dulkirmod.overlays

import cc.polyfrost.oneconfig.hud.TextHud
import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.DulkirConfig
import dulkirmod.utils.TabListUtils
import dulkirmod.utils.Utils

class YawDisplayHud : TextHud(false) {
    override fun getLines(lines: MutableList<String>?, example: Boolean) {
        if (!Utils.isInSkyblock()) return
        if (!DulkirConfig.showYawEverywhere && TabListUtils.area != "Garden") return

        val pitch = mc.thePlayer.rotationPitch
        var yaw = mc.thePlayer.rotationYaw % 360f

        if (yaw < -180.0f) {
            yaw += 360.0f;
        } else if (yaw > 180.0f) {
            yaw -= 360.0f;
        }
        if (DulkirConfig.yaw3Decimals) {
            lines?.add(0, String.format("Yaw: %.3f", yaw))
        } else {
            lines?.add(0, String.format("Yaw: %.2f", yaw))
        }
        if (DulkirConfig.showPitch)
            lines?.add(1, String.format("Pitch: %.2f", pitch))
    }
}