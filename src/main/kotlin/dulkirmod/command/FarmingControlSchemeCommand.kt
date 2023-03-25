package dulkirmod.command

import dulkirmod.config.DulkirConfig
import dulkirmod.utils.TextUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.settings.KeyBinding
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender

class FarmingControlSchemeCommand : ClientCommandBase("farmcontrols") {
    private var enabled = false
    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        toggleControls()
    }

    companion object {
        private var enabled = false

        /**
         * Method to do the brunt work of the command. This is separate, so I can also let the user set a
         * keybind to do the same thing.
         */
        fun toggleControls() {
            val minecraft = Minecraft.getMinecraft()
            val breakingKey: KeyBinding = minecraft.gameSettings.keyBindAttack
            val jumpKey: KeyBinding = minecraft.gameSettings.keyBindJump
            if (!enabled) {
                KeyBinding.setKeyBindState(breakingKey.keyCode, false)
                breakingKey.keyCode = 57 // 57 = space key code

                KeyBinding.setKeyBindState(jumpKey.keyCode, false)
                jumpKey.keyCode = -100 // -100 = Left click key code
                minecraft.gameSettings.mouseSensitivity = 0.0f
            } else {
                KeyBinding.setKeyBindState(breakingKey.keyCode, false)
                breakingKey.keyCode = -100 // -100 = Left click key code

                KeyBinding.setKeyBindState(jumpKey.keyCode, false)
                jumpKey.keyCode = 57 // 57 = space key code
                minecraft.gameSettings.mouseSensitivity = DulkirConfig.defaultSens / 2
            }

            // Save the changes to the control settings
            minecraft.gameSettings.saveOptions()
            minecraft.gameSettings.loadOptions()

            enabled = !enabled
            TextUtils.toggledMessage("Farming Controls", enabled)
        }
    }
}