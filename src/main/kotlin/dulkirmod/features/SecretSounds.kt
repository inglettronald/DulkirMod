package dulkirmod.features

import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.DulkirConfig
import dulkirmod.utils.TabListUtils.isInDungeons
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.tileentity.TileEntitySkull
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object SecretSounds {
    private var lastSound: Long = 0L
    @SubscribeEvent
    fun onInteract(event: PlayerInteractEvent) {
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            return
        }
        if (!DulkirConfig.secretClickSounds) {
            return
        }
        if (!isInDungeons) {
            return
        }


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

    private fun playSound() {
        if (System.currentTimeMillis() - lastSound > 50) { // don't kill ears
            mc.thePlayer.playSound(
                "random.break",
                1f * DulkirConfig.secretSoundVolume,
                1f
            )
            lastSound = System.currentTimeMillis()
        }
    }
}