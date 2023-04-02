package dulkirmod.features

import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.DulkirConfig
import dulkirmod.utils.TabListUtils
import dulkirmod.utils.TitleUtils
import dulkirmod.utils.Utils
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

var oldKill = -1
var oldChampionXp = -1.0
var oldID = ""

fun brokenHypeNotif() {
    if (!DulkirConfig.notifyHype) return

    var kill = -1
    var championXp = -1.0
    var id = ""

    if (mc.thePlayer == null) return

    val stack: ItemStack = mc.thePlayer.heldItem ?: return

    // get info about held item
    if (stack.hasTagCompound()) {
        val tag: NBTTagCompound = stack.tagCompound
        if (tag.hasKey("ExtraAttributes", 10) && tag.hasKey("display", 10)) {
            val ea: NBTTagCompound = tag.getCompoundTag("ExtraAttributes")
            if (ea.hasKey("id", 8)) {
                id = ea.getString("id")
            }
            if (ea.hasKey("stats_book", 99)) {
                kill = ea.getInteger("stats_book")
            }
            if (ea.hasKey("champion_combat_xp", 99)) {
                championXp = ea.getDouble("champion_combat_xp")
            }
        }
    }
    // check if a wither blade, then check if same id
    if (!(id matches "(HYPERION|ASTRAEA|SCYLLA|VALKYRIE)".toRegex())) {
        return
    } else if (id != oldID) {
        // Check if this is a valid item for testing whether bestiary is broken.
        // That is, to be specific, check that it has champion and book of stats.
        // If it doesn't, don't reset because it can't be used anyway.
        if (kill == -1 || championXp == -1.0) {
            return
        }
        // If we get here this is a new item that is legitimate for testing bugged xp, in theory.
        oldID = id
        oldKill = kill
        oldChampionXp = championXp
        return
    }

    // If this section of the code is reached, then we have the same item, and we can check for updated stats
    if (oldKill != kill && oldChampionXp == championXp && TabListUtils.area != "Private Island") {
        mc.thePlayer.playSound("random.anvil_land", 1f * DulkirConfig.bestiaryNotifVol, 0f)
        val color = Utils.getColorString(DulkirConfig.bestiaryNotifColor)
        TitleUtils.drawStringForTime("${color}Hype Broken", 5000)
    }
    // update item regardless of whether it is bugged or not
    oldKill = kill
    oldChampionXp = championXp
}
