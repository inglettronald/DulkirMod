package dulkirmod.utils

import com.google.gson.Gson
import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.DulkirConfig
import net.minecraft.util.EnumChatFormatting
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection
import java.util.*

object Utils {
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
		val clipboard = Toolkit.getDefaultToolkit().systemClipboard
		val base64 = clipboard.getData(DataFlavor.stringFlavor) as String
		try {
			val jsonString = String(Base64.getDecoder().decode(base64))
			val import = gson.fromJson(jsonString, ConfigData::class.java)
			DulkirConfig.customSize = import.size
			DulkirConfig.customSpeed = import.speed
			DulkirConfig.doesScaleSwing = import.scaleSwing
			DulkirConfig.customX = import.x
			DulkirConfig.customY = import.y
			DulkirConfig.customZ = import.z
			DulkirConfig.customYaw = import.yaw
			DulkirConfig.customPitch = import.pitch
			DulkirConfig.customRoll = import.roll
			DulkirConfig.drinkingSelector = import.drinkingFix
			DulkirConfig.ignoreHaste = import.ignoreHaste
		} catch (e: Exception) {
			TextUtils.info("§6§lCurrent clipboard is not a recognizable Custom Animation Preset.")
		}
		mc.displayGuiScreen(null)
	}

	fun isInSkyblock(): Boolean {
		if (mc.theWorld == null || mc.thePlayer == null) return false
        if (mc.isSingleplayer) return false
		if (mc.thePlayer.clientBrand?.contains("hypixel", true) == false) return false
        val objective = mc.thePlayer.worldScoreboard.getObjectiveInDisplaySlot(1) ?: return false
		return stripColorCodes(objective.displayName).contains("skyblock", true)
	}

	fun getColorString(int: Int): String {
		return if (int == 16) "§z" else EnumChatFormatting.values()[int].toString()
	}

	/**
	 * method that takes an amount of time described by a millisecond amount and translates it to a readable string.
	 */
	fun formatTime(millisAmount: Long, includeSubSeconds: Boolean = false): String {
		if (millisAmount < 0)
			return ""
		val sb: StringBuilder = StringBuilder()
		var millis = millisAmount

		if (millis / (3600 * 1000) >= 1) {
			val hours: Long = millisAmount / (3600 * 1000)
			sb.append("$hours:")
			millis -= (hours * 3600 * 1000)
		}
		if (millis / (60 * 1000) >= 1) {
			val minutes = millis / (60 * 1000)
			if (sb.isNotEmpty() && minutes < 10) {
				sb.append(0)
			}
			sb.append("$minutes:")
			millis -= (minutes * 60 * 1000)
		} else if (sb.isNotEmpty()) {
			sb.append("00:")
		}

		if (millis / 1000 >= 1) {
			val seconds = millis / 1000
			if (sb.isNotEmpty() && seconds < 10) {
				sb.append(0)
			}
			sb.append(seconds)
			millis -= (seconds * 1000)
		} else if (sb.isNotEmpty()) {
			sb.append("00")
		}

		if (includeSubSeconds) {
			sb.append('.')
			if (millis < 100) {
				sb.append(0)
				if (millis < 10) {
					sb.append(0)
				}
			}
			sb.append("$millis")
		}
		return sb.toString()
	}
}