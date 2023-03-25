package dulkirmod.command

import dulkirmod.DulkirMod
import net.minecraft.command.ICommandSender

class SettingsCommand : ClientCommandBase("dulkir") {
    override fun processCommand(sender: ICommandSender?, args: Array<out String>?) {
        DulkirMod.config.openGui()
    }
}