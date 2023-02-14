package dulkirmod.command

import dulkirmod.utils.TextUtils
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender

class HelpCommand : ClientCommandBase("dulkirhelp") {
    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        TextUtils.info("§6§l HI THIS IS DULKIRMOD!", false)
        TextUtils.info("  §7/enchantrune - toggles enchant rune visibility.", false)
        TextUtils.info("  §7/fairy - toggles healer fairy visibility.", false)
        TextUtils.info("  §7/hl - helps change highlighted leap player on the fly.", false)
    }
}