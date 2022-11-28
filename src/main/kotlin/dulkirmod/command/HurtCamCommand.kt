package dulkirmod.command

import dulkirmod.DulkirMod
import dulkirmod.config.Config
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText

class HurtCamCommand : ClientCommandBase("ouch") {
    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        Config.hurtCamIntensity = 7f
        DulkirMod.mc.thePlayer.addChatMessage(
            ChatComponentText("${DulkirMod.CHAT_PREFIX} §6§lOUCH! THAT HURT!")
        )
    }
}