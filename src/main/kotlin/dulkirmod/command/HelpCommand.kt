package dulkirmod.command

import net.minecraft.client.Minecraft
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting

class HelpCommand : ClientCommandBase("dulkirhelp") {
    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(
            ChatComponentText(
                EnumChatFormatting.GOLD.toString() + "" + EnumChatFormatting.BOLD + " HI THIS IS DULKIRMOD!"
            )
        )
        Minecraft.getMinecraft().thePlayer.addChatMessage(
            ChatComponentText(
                EnumChatFormatting.GRAY.toString() + "/enchantrune - toggles enchant rune visibility."
            )
        )
        Minecraft.getMinecraft().thePlayer.addChatMessage(
            ChatComponentText(
                EnumChatFormatting.GRAY.toString() + "/fairy - toggles healer fairy visibility."
            )
        )
    }
}