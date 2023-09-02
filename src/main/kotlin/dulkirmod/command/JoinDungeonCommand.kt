package dulkirmod.command

import dulkirmod.config.DulkirConfig
import dulkirmod.utils.TextUtils
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender
import java.util.*

class JoinDungeonCommand : ClientCommandBase("joindungeon") {
    @Throws(CommandException::class)
    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        val list : List<String> = args[0].split("")
        var floorType : String = list[0]
        var floorNumber : String = list[1]
        var num : Int = 0
        // Try statement so message is consistent if user gives bad input
        try {
            if (floorNumber.toInt() in 1..7) {
                num = floorNumber.toInt()
            }
        } catch (_: NumberFormatException) {}

        if (DulkirConfig.dungeonCommandConfirm) {
            TextUtils.info("§6Running command: $floorType$floorNumber")
        }
        TextUtils.sendMessage("/joindungeon ${parse(floorType)}_FLOOR_${parse(num)}")
    }

    private fun parse(num: Int): String {
        return when (num) {
            1 -> {
                "ONE"
            }
            2 -> {
                "TWO"
            }
            3 -> {
                "THREE"
            }
            4 -> {
                "FOUR"
            }
            5 -> {
                "FIVE"
            }
            6 -> {
                "SIX"
            }
            7 -> {
                "SEVEN"
            }
            else -> {
                TextUtils.info("§cCan't identify floor number.")
                TextUtils.info("§cUsage: /joindungeon f5|m3|f7|m4 ")
                ""
            }
        }
    }


    private fun parse(floor: String): String {
        return when (floor.lowercase()){
            "f" -> {
                "CATACOMBS"
            }
            "m" -> {
                "MASTER_CATACOMBS"
            }
            else -> {
                TextUtils.info("§cCan't identify dungeon type.")
                TextUtils.info("§cUsage: /joindungeon f5|m3|f7|m4 ")
                ""
            }
        }
    }
}
