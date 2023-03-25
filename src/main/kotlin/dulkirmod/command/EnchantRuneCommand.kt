package dulkirmod.command

import dulkirmod.DulkirMod.Companion.config
import dulkirmod.utils.TextUtils
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender

class EnchantRuneCommand : ClientCommandBase("enchantrune") {
    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        config.hideEnchantRune = !config.hideEnchantRune
        TextUtils.toggledMessage("Enchant Rune Hider", config.hideEnchantRune)
        config.save()
    }
}