package dulkirmod.command

import dulkirmod.DulkirMod
import dulkirmod.config.Config
import dulkirmod.utils.TabListUtils
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText

class LeapNameCommand : ClientCommandBase("hl") {
    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        if (args.isEmpty()) {
            DulkirMod.mc.thePlayer.addChatMessage(
                ChatComponentText("${DulkirMod.CHAT_PREFIX} §6Please give a username or class argument for who you want to be highlighted.")
            )
            DulkirMod.mc.thePlayer.addChatMessage(
                ChatComponentText("${DulkirMod.CHAT_PREFIX} §7 - Class argument will take the first person tab list with that class.")
            )
            DulkirMod.mc.thePlayer.addChatMessage(
                ChatComponentText("${DulkirMod.CHAT_PREFIX} §7 - example: §f/hl h§7, §f/hl tank§7, or §f/hl Tazboi§7.")
            )
            DulkirMod.mc.thePlayer.addChatMessage(
                ChatComponentText("${DulkirMod.CHAT_PREFIX} §7 - This command will need to be ran again if some person of class §fX §7leaves and a new one joins.")
            )
            return
        }

        val username = args[0].lowercase()

        var isClassName = true
        var foundPlayer = when (username) {
            "h", "healer" -> findUserNameFor("(Healer", true)
            "b", "berserk" -> findUserNameFor("(Berserk", true)
            "m", "mage" -> findUserNameFor("(Mage", true)
            "t", "tank" -> findUserNameFor("(Tank", true)
            "a", "archer" -> findUserNameFor("(Archer", true)
            else -> {
                isClassName = false
                findUserNameFor(username, false)
            }
        }

        if (foundPlayer) {
            DulkirMod.mc.thePlayer.addChatMessage(
                ChatComponentText("${DulkirMod.CHAT_PREFIX} §6Selected Leap Highlight for username: §f${Config.highlightLeapName}§6.")
            )
        }
    }
    private fun findUserNameFor(input: String, isClassName: Boolean): Boolean {
        val scoreboardList: List<String?> = TabListUtils.fetchTabEntires().map {
            it.displayName?.unformattedText
        }
        if (isClassName) {
            for (l in scoreboardList) {
                if (l != null && l.contains(input)) {
                    val strArr = l.split(" ")
                    Config.highlightLeapName = strArr[1]
                    return true
                }
            }
            DulkirMod.mc.thePlayer.addChatMessage(
                ChatComponentText("${DulkirMod.CHAT_PREFIX} §6Couldn't find anyone playing this class.")
            )
            return false
        } else {
            for (l in scoreboardList) {
                if (l == null) continue
                val strArr = l.split(" ")
                // another safety check, probably not necessary but oh well
                if (strArr.size < 2) continue
                val username = strArr[1]
                if (username.lowercase() == input.lowercase()) {
                    Config.highlightLeapName = username
                    return true
                }
            }
            DulkirMod.mc.thePlayer.addChatMessage(
                ChatComponentText("${DulkirMod.CHAT_PREFIX} §6Couldn't find anyone with this username on tab list.")
            )
            return false
        }
    }

}