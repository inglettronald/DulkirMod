package dulkirmod.command

import dulkirmod.config.DulkirConfig
import dulkirmod.utils.TextUtils
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender

class DynamicKeyCommand: ClientCommandBase("dk"){
    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        if (args.isEmpty()) {
            TextUtils.info("§6Usage: /dk set <command args>")
            return
        }
        if (args[0] == "help") {
            TextUtils.info("§6§lDynamic Keybind Info")
            TextUtils.info("§7 - There's a keybind setting inside your minecraft controls you can use in order" +
                    " to make a chat macro for a particular in game command. This only works for commands.", prefix = false)
            TextUtils.info("§7 - Usage: /dk set <command args>", prefix = false)
            TextUtils.info("§7 (i made this cuz I have a mouse button that i use for a bunch of different useful " +
                    "actions depending upon what I'm doing, so don't worry if this feature doesn't apply to you lol)", prefix = false)
            return
        }
        if (args[0] == "set") {
            val builder = StringBuilder()
            for (i in 1 until args.size) {
                if (i == args.size - 1)
                    builder.append(args[i])
                else
                    builder.append("${args[i]} ")
            }
            TextUtils.info("§6Registered command: /${builder}")
            DulkirConfig.dynamicCommandString = builder.toString()
            return
        }
        TextUtils.info("§6Usage: /dk set <command args>")
    }
}