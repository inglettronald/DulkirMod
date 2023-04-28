package dulkirmod.features

import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.DulkirConfig
import dulkirmod.utils.TabListUtils
import dulkirmod.utils.TitleUtils
import dulkirmod.utils.Utils
import net.minecraft.client.audio.SoundCategory

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
            if (DulkirConfig.bestiaryAlertSounds) {
                val prevVol = mc.gameSettings.getSoundLevel(SoundCategory.MOBS)
                mc.gameSettings.setSoundLevel(SoundCategory.MOBS, 1f)
                mc.thePlayer.playSound("mob.villager.yes", 1f * DulkirConfig.bestiaryNotifVol, 0f)
                mc.gameSettings.setSoundLevel(SoundCategory.MOBS, prevVol)
            }
            hasSentAlert = true
        } else if (!TabListUtils.explosivity) hasSentAlert = false
    }
}