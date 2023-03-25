package dulkirmod.command

import dulkirmod.config.DulkirConfig
import dulkirmod.utils.TabListUtils
import dulkirmod.utils.TextUtils
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender

class LeapNameCommand : ClientCommandBase("hl") {
    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        if (args.isEmpty()) {
            TextUtils.info("§6Please give a username or class argument for who you want to be highlighted.")
            TextUtils.info("§7 - Class argument will take the first person tab list with that class.", false)
            TextUtils.info("§7 - Example: §f/hl h§7, §f/hl tank§7, or §f/hl Tazboi§7.", false)
            TextUtils.info("§7 - This command will need to be ran again if some person of class §fX §7leaves and a new one joins.", false)
            return
        }

        val foundPlayer = when (val username = args[0].lowercase()) {
            "h", "healer" -> findUserNameFor("(Healer", true)
            "b", "berserk" -> findUserNameFor("(Berserk", true)
            "m", "mage" -> findUserNameFor("(Mage", true)
            "t", "tank" -> findUserNameFor("(Tank", true)
            "a", "archer" -> findUserNameFor("(Archer", true)
            else -> findUserNameFor(username, false)
        }
        if (foundPlayer) {
            TextUtils.info("§6Selected Leap Highlight for username: §f${DulkirConfig.highlightLeapName}§6.")
        }
    }
    private fun findUserNameFor(input: String, isClassName: Boolean): Boolean {
        val scoreboardList = TabListUtils.fetchTabEntries().mapNotNull {
            it.displayName?.unformattedText
        }
        if (isClassName) {
            for (l in scoreboardList) {
                if (l.contains(input)) {
                    val strArr = l.split(" ")
                    DulkirConfig.highlightLeapName = strArr[1]
                    return true
                }
            }
            
            TextUtils.info("§6Couldn't find anyone playing this class.")
            return false
        } else {
            for (l in scoreboardList) {
                val strArr = l.split(" ")
                // another safety check, probably not necessary but oh well
                if (strArr.size < 2) continue
                val username = strArr[1]
                if (username.lowercase() == input.lowercase()) {
                    DulkirConfig.highlightLeapName = username
                    return true
                }
            }
            TextUtils.info("§6Couldn't find anyone with this username on tab list.")
            return false
        }
    }

}