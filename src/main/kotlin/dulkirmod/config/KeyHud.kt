package dulkirmod.config
import cc.polyfrost.oneconfig.hud.TextHud
import dulkirmod.features.chat.DungeonKeyDisplay


class KeyHud : TextHud(true) {

    override fun getLines(lines: MutableList<String>?, example: Boolean) {
        if (example) {
            lines?.add(0, "Wither Key Display")
            return
        }
        if (DungeonKeyDisplay.hasKey)
            lines?.add(0, "Key Obtained")
    }
}