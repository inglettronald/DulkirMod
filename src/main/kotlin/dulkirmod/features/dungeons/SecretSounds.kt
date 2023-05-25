package dulkirmod.features.dungeons

import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.DulkirConfig
import dulkirmod.events.EntityRemovedEvent
import dulkirmod.utils.TabListUtils
import net.minecraft.block.Block
import net.minecraft.client.audio.SoundCategory
import net.minecraft.entity.item.EntityItem
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntitySkull
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object SecretSounds {
    private var lastSound: Long = 0L

    enum class Secrets(val value: String) {
        SPIRIT_LEAP("Spirit Leap"),
        DECOY("Decoy"),
        INFLATABLE_JERRY("Inflatable Jerry"),
        TREASURE_TALISMAN("Treasure Talisman")
    }
    @SubscribeEvent
    fun onInteract(event: PlayerInteractEvent) {
        if (!DulkirConfig.secretClickSounds) return
        if (TabListUtils.area != "Dungeon") return
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) return


        val blockType: Block = mc.theWorld.getBlockState(event.pos).block ?: return // get the type of block

        if (blockType != Blocks.chest && blockType != Blocks.trapped_chest && blockType != Blocks.skull && blockType != Blocks.lever) { // check if it's a secret
            return
        }

        if (blockType == Blocks.skull) { // it may be a wither essence skull
            var skullId: String? = null

            try {
                skullId = (mc.theWorld.getTileEntity(event.pos) as TileEntitySkull).playerProfile.id.toString()
            } catch (ignored: NullPointerException) {} // it doesn't have a playerID

            if (skullId == null || skullId != "26bb1a8d-7c66-31c6-82d5-a9c04c94fb02") { // check if it is a wither essence player head
                return
            }
        }
        playSound()
    }

    // Inspiration: AtonAddons - https://github.com/FloppaCoding/AtonAddons/blob/main/src/main/kotlin/atonaddons/module/impl/dungeon/SecretChime.kt
    private var drops = listOf(
        "Health Potion VIII Splash Potion", //"§5Health Potion VIII Splash Potion"
        "Healing Potion 8 Splash Potion",
        "Healing Potion VIII Splash Potion",
        "Decoy", //"§aDecoy"
        "Inflatable Jerry", //  "§fInflatable Jerry"
        "Spirit Leap", // "§9Spirit Leap"
        "Trap", // "§aTrap"
        "Training Weights", // "§aTraining Weights"
        "Defuse Kit", // "§aDefuse Kit"
        "Dungeon Chest Key", // "§9Dungeon Chest Key"
        "Treasure Talisman", // Name: "§9Treasure Talisman"
        "Revive Stone",
    )
    @SubscribeEvent
    fun onRemoveEntity(event: EntityRemovedEvent) {
        if (!DulkirConfig.secretClickSounds) return
        if (TabListUtils.area != "Dungeon") return
        if (event.entity !is EntityItem) return
        if (mc.thePlayer.getDistanceToEntity(event.entity) > 6) return
        val item: ItemStack = event.entity.entityItem

        var secretFlag = false
        for (d in drops) {
            if (item.displayName.contains(d))
                secretFlag = true
        }
        if (secretFlag)
            playSound()
    }

    fun playSound() {
        if (System.currentTimeMillis() - lastSound > 50) { // don't kill ears
            val prevNote = mc.gameSettings.getSoundLevel(SoundCategory.RECORDS)
            mc.gameSettings.setSoundLevel(SoundCategory.RECORDS, 1f)
            mc.thePlayer.playSound("note.pling", 1f * DulkirConfig.secretSoundVolume, 1f)
            lastSound = System.currentTimeMillis()
            mc.gameSettings.setSoundLevel(SoundCategory.RECORDS, prevNote)
        }
    }
}