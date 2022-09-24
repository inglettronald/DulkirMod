package dulkirmod.command

import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender

abstract class ClientCommandBase protected constructor(private val name: String) : CommandBase() {
    override fun getCommandName(): String {
        return name
    }

    override fun getCommandUsage(sender: ICommandSender): String {
        return "/$name"
    }

    override fun canCommandSenderUseCommand(sender: ICommandSender): Boolean {
        return true
    }
}