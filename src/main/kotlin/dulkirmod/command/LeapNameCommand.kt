package dulkirmod.command

import dulkirmod.DulkirMod
import dulkirmod.config.Config
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText

class LeapNameCommand : ClientCommandBase("hl") {
    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        if (args.isEmpty()) {
            DulkirMod.mc.thePlayer.addChatMessage(
                ChatComponentText("${DulkirMod.CHAT_PREFIX} §6Please give a username argument (case sensitive) for who you want to be highlighted.")
            )
            return
        }

        val username = args[0]
        Config.highlightLeapName = username
        DulkirMod.mc.thePlayer.addChatMessage(
            ChatComponentText("${DulkirMod.CHAT_PREFIX} §6Selected Leap Highlight for username: §f$username§6.")
        )
    }
}