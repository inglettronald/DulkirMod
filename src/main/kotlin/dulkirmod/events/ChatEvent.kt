package dulkirmod.events

import dulkirmod.DulkirMod
import dulkirmod.config.Config
import dulkirmod.utils.Utils
import dulkirmod.utils.Utils.stripColorCodes
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class ChatEvent {
    private val guildFormat = "^(§2Guild|§3Officer) > (?:\\S+ )?([\\w§]{3,18})(?: §[a-z0-9]\\[[A-Z]+])?§f: (\\w+) > .+".toRegex()
    private val alternateFormat = "^(§2Guild|§3Officer) > (?:\\S+ )?([\\w§]{3,18})(?: §[a-z0-9]\\[[A-Z]+])?§f: (\\w+): .+".toRegex()
    private var lastThrottle : Long = 0;
    @SubscribeEvent(receiveCanceled = true, priority = EventPriority.LOW)
    fun onChat(event: ClientChatReceivedEvent) {
        if (event.type == 2.toByte()) {
            return
        }

        // THROTTLE NOTIFIER
        val unformatted = stripColorCodes(event.message.unformattedText)
        if (unformatted == "This menu has been throttled! Please slow down..." && DulkirMod.config.throttleNotifier
            && Utils.isInDungeons()) {
            event.isCanceled = true;
            if (!Config.throttleNotifierSpam && System.currentTimeMillis() - lastThrottle > 5000) {
                DulkirMod.mc.thePlayer.sendChatMessage("/pc " + DulkirMod.config.customMessage)
            }
            else {
                DulkirMod.mc.thePlayer.sendChatMessage("/pc " + DulkirMod.config.customMessage)
            }
            lastThrottle = System.currentTimeMillis()
        }

        // BRIDGE BOT STUFF - CLICKABLE LINKS!
        val message = event.message.unformattedText
        if (guildFormat matches message && Config.bridgeBot) {
            val matchResult = guildFormat.find(message)
            val (prefix, name, playerName) = matchResult!!.destructured
            if (stripColorCodes(name.lowercase()) == Config.botName.lowercase()) {
                val newPrefix = if (prefix == "§2Guild") "§2Bridge" else "§3Bridge"
                val color = if (Config.bridgeColor == 16) "§z" else EnumChatFormatting.values()[Config.bridgeColor]
                event.message.siblings[0] = ChatComponentText(
                    "$newPrefix > $color$playerName§f: "
                )
                event.message.siblings[1] = ChatComponentText(
                    event.message.siblings[1].unformattedText.replace("$playerName > ", "")
                ).setChatStyle(event.message.siblings[1].chatStyle.createShallowCopy())
            }
        }

        // OTHER FORMAT
        else if (alternateFormat matches message && Config.bridgeBot) {
            val matchResult = alternateFormat.find(message)
            val (prefix, name, playerName) = matchResult!!.destructured
            if (stripColorCodes(name.lowercase()) == Config.botName.lowercase()) {
                val newPrefix = if (prefix == "§2Guild") "§2Bridge" else "§3Bridge"
                val color = if (Config.bridgeColor == 16) "§z" else EnumChatFormatting.values()[Config.bridgeColor]
                event.message.siblings[0] = ChatComponentText(
                    "$newPrefix > $color$playerName§f: "
                )
                event.message.siblings[1] = ChatComponentText(
                    event.message.siblings[1].unformattedText.replace("$playerName: ", "")
                ).setChatStyle(event.message.siblings[1].chatStyle.createShallowCopy())
            }
        }
    }
}