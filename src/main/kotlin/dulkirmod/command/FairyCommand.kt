package dulkirmod.command

import dulkirmod.DulkirMod.Companion.config
import dulkirmod.DulkirMod.Companion.mc
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting

class FairyCommand : ClientCommandBase("fairy") {
    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        mc.thePlayer.addChatMessage(
            ChatComponentText(
                EnumChatFormatting.BLUE.toString() + "Healer fairy hider toggled - now: " +
                        EnumChatFormatting.GREEN + !config.hideHealerFairy
            )
        )
        config.hideHealerFairy = !config.hideHealerFairy

        config.markDirty()
        config.writeData()
    }
}