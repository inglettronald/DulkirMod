package dulkirmod.utils

import com.google.gson.Gson
import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.Config
import net.minecraft.util.EnumChatFormatting
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection
import java.util.*

object Utils {

    var area = ""
    fun stripColorCodes(string: String): String {
        return string.replace("§.".toRegex(), "")
    }

    fun animationConfigToString() {
        var s = ""
        var gson = Gson()
        var jsonString = gson.toJson(ConfigData())
        s = Base64.getEncoder().encodeToString(jsonString.toByteArray())
        // set clipboard
        val selection = StringSelection(s)
        val clipboard: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
        clipboard.setContents(selection, selection)
    }

    fun animationStringtoConfig() {
        val gson = Gson()
        val clipboard: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
        val base64 = clipboard.getData(DataFlavor.stringFlavor) as String
        try {
            val jsonString = String(Base64.getDecoder().decode(base64))
            val import = gson.fromJson(jsonString, ConfigData::class.java)
            Config.customSize = import.size
            Config.customSpeed = import.speed
            Config.doesScaleSwing = import.scaleSwing
            Config.customX = import.x
            Config.customY = import.y
            Config.customZ = import.z
            Config.customYaw = import.yaw
            Config.customPitch = import.pitch
            Config.customRoll = import.roll
            Config.drinkingSelector = import.drinkingFix
            Config.ignoreHaste = import.ignoreHaste
        } catch (e: Exception) {
            TextUtils.info("§6§lCurrent clipboard is not a recognizable Custom Animation Preset.")
        }
        mc.displayGuiScreen(null)
    }

    fun isInSkyblock(): Boolean {
        if ((mc.theWorld != null) && (mc.thePlayer != null)) {
            if (mc.isSingleplayer || mc.thePlayer.clientBrand == null ||
                !mc.thePlayer.clientBrand.lowercase(Locale.getDefault()).contains("hypixel")
            ) {
                return false
            }
            if (mc.thePlayer.worldScoreboard.getObjectiveInDisplaySlot(1) == null)
                return false
            return stripColorCodes(mc.thePlayer.worldScoreboard.getObjectiveInDisplaySlot(1).displayName).contains("SKYBLOCK")
        }
        return false
    }

    fun isInDungeons(): Boolean {
        val scoreboardList: List<String?> = TabListUtils.fetchTabEntires().map {
            it.displayName?.unformattedText
        }
        for (l in scoreboardList) {
            if (l == "       Dungeon Stats") return true
        }
        return false
    }

    fun getColorString(int: Int): String {
        return if (int == 16) "§z" else EnumChatFormatting.values()[int].toString()
    }

    fun getArea() {
        val scoreboardList: List<String?> = TabListUtils.fetchTabEntires().map {
            it.displayName?.unformattedText
        }
        for (l in scoreboardList) {
            if (l != null && l.startsWith("Area: "))
                area = l.substring(6)
        }
    }
}