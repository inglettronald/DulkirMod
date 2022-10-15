package dulkirmod.command

import dulkirmod.DulkirMod
import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.Config
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText

class JoinDungeonCommand : ClientCommandBase("joindungeon") {
    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        var arguments = args.contentToString().replace("[", "").replace("]", "").replace(",","")
        var type = ""
        var num = ""
        if (args[0] == "master_catacombs") {
            type = "M"
        }
        else if (args[0] == "catacombs") {
            type = "F"
        }

        // Try statement so message is consistent if user gives bad input
        try {
            if (args[1].toInt() in 1..7) {
                num = args[1]
            }
        } catch (e: NumberFormatException) { }

        if(Config.dungeonCommandConfirm) {
            mc.thePlayer.addChatMessage(
                ChatComponentText("${DulkirMod.CHAT_PREFIX} §6Running command: $type$num")
            )
        }
        mc.thePlayer.sendChatMessage("/joindungeon $arguments")
    }
}