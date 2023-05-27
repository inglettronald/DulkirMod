package dulkirmod.features

import dulkirmod.config.DulkirConfig
import dulkirmod.utils.TabListUtils
import dulkirmod.utils.WorldRenderUtils
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Color

object StarredMobBoxes {
    @SubscribeEvent
    fun render(event: RenderLivingEvent.Post<*>) {
        if (!DulkirConfig.starredBoxes) return
        if (TabListUtils.area != "Dungeon") return
        val name = event.entity.name ?: return
        if (name.startsWith("§6✯ ") && name.endsWith("§c❤")) {
            val newPos = WorldRenderUtils.fixRenderPos(event.x, event.y, event.z)
            val x = newPos[0]
            val y = newPos[1]
            val z = newPos[2]

            if ("Spider" in name) {
//                        x - 0.625,
//                        y - 1,
//                        z - 0.625,
//                        x + 0.625,
//                        y - 0.25,
//                        z + 0.625
                WorldRenderUtils.drawCustomBox(
                    x - .625,
                    1.25,
                    y - 1,
                    .75,
                    z - .625,
                    1.25,
                    Color(15, 247, 236, 255),
                    3f,
                    phase = false
                )
            } else if ("Fels" in name || "Withermancer" in name) {
                    //AxisAlignedBB(x - 0.5, y - 3, z - 0.5, x + 0.5, y, z + 0.5),
                WorldRenderUtils.drawCustomBox(
                    x - .5,
                    1.0,
                    y - 3,
                    3.0,
                    z - .5,
                    1.0,
                    Color(15, 247, 236, 255),
                    3f,
                    phase = false
                )
            } else {
                    //AxisAlignedBB(x - 0.5, y - 2, z - 0.5, x + 0.5, y, z + 0.5),
                WorldRenderUtils.drawCustomBox(
                    x - .5,
                    1.0,
                    y - 2,
                    2.0,
                    z - .5,
                    1.0,
                    Color(15, 247, 236, 255),
                    3f,
                    phase = false
                )
            }
        }
    }
}