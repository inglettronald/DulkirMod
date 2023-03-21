package dulkirmod.command

import dulkirmod.utils.TextUtils
import net.minecraft.command.ICommandSender

class SpawnParticlesCommand : ClientCommandBase("sp") {
    override fun processCommand(sender: ICommandSender?, args: Array<out String>?) {
        TextUtils.sendMessage("/particle flame 84 18 95 1 1 1 1 100")
        TextUtils.sendMessage("/particle flame 57 18 125 1 1 1 1 100")
        TextUtils.sendMessage("/particle flame 26 18 95 1 1 1 1 100")
        TextUtils.sendMessage("/particle flame 27 18 60 1 1 1 1 100")
        TextUtils.sendMessage("/particle flame 84 18 56 1 1 1 1 100")
    }
}