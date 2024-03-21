package dulkirmod.utils

import dulkirmod.DulkirMod
import java.util.concurrent.Executor

object MinecraftExecutor : Executor {
    override fun execute(command: Runnable) {
        DulkirMod.mc.addScheduledTask(command)
    }
}
