package dulkirmod.features.chat

import dulkirmod.config.DulkirConfig
import dulkirmod.utils.Utils
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import net.minecraftforge.client.event.ClientChatReceivedEvent

object Bridge {
    private val guildFormat =
        "^(?:§r|)(§2Guild|§3Officer) > (?:\\S+ )?([\\w§]{3,18})(?: §[a-z0-9]\\[[\\w]+])?§f: ([^»>:]+)( » | > |: ).+".toRegex()

    fun handle(event: ClientChatReceivedEvent) {
        val message = event.message.unformattedText
        if (guildFormat matches message && DulkirConfig.bridgeBot) {
            val matchResult = guildFormat.find(message)
            val (prefix, name, playerName, separator) = matchResult!!.destructured
            if (Utils.stripColorCodes(name.lowercase()) == DulkirConfig.botName.lowercase()) {
                val newPrefix = if (prefix == "§2Guild") "§2Bridge" else "§3Bridge"
                val color = if (DulkirConfig.bridgeColor == 16) "§z" else EnumChatFormatting.values()[DulkirConfig.bridgeColor]
                event.message.siblings[0] = ChatComponentText(
                    "$newPrefix > $color$playerName§f: "
                )
                event.message.siblings[1] = ChatComponentText(
                    event.message.siblings[1].unformattedText.replace("$playerName$separator", "")
                ).setChatStyle(event.message.siblings[1].chatStyle.createShallowCopy())
            }
        }
    }
}
