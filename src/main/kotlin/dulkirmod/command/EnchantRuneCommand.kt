package dulkirmod.command

import dulkirmod.DulkirMod.Companion.config
import dulkirmod.DulkirMod.Companion.mc
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting

class EnchantRuneCommand : ClientCommandBase("enchantrune") {
    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        mc.thePlayer.addChatMessage(
            ChatComponentText(
                EnumChatFormatting.BLUE.toString() + "" + "Enchant rune hider toggled - now: " +
                        EnumChatFormatting.GREEN + !config.hideEnchantRune
            )
        )
        config.hideEnchantRune = !config.hideEnchantRune

        config.markDirty()
        config.writeData()
    }
}