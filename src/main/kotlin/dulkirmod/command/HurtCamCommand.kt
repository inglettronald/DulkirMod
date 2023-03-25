package dulkirmod.command

import dulkirmod.config.DulkirConfig
import dulkirmod.utils.TextUtils
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender

class HurtCamCommand : ClientCommandBase("ouch") {
    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        DulkirConfig.hurtCamIntensity = 7f
        TextUtils.info("§6§lOUCH! THAT HURT!")
    }
}