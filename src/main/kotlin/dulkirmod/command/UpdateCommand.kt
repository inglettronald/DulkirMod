package dulkirmod.command

import dulkirmod.features.UpdateNotificationFeature
import dulkirmod.utils.TextUtils
import net.minecraft.command.ICommandSender

class UpdateCommand : ClientCommandBase("updatedulkir") {
    override fun processCommand(sender: ICommandSender?, args: Array<String>) {
        when (val a = args.singleOrNull()) {
            "check" -> {
                UpdateNotificationFeature.checkUpdate(true)
            }

            null -> {
                TextUtils.info("Usage: /updatedulkir check")
            }

            else -> {
                UpdateNotificationFeature.downloadUpdate(a)
            }
        }
    }
}