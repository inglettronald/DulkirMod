package dulkirmod.utils

import dulkirmod.DulkirMod.Companion.mc
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.GlStateManager.disableTexture2D
import net.minecraft.client.renderer.GlStateManager.enableTexture2D
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.Vec3
import org.lwjgl.opengl.GL11


class WorldRenderUtils {

    companion object {
        fun render(
            location: Vec3,
            text: String,
            depthTest: Boolean = true,
            scale: Float = 1f,
            showDistance: Boolean = false,
            shadow: Boolean = false,
            renderBlackBox: Boolean = true,
        ) {
            if (!depthTest) {
                GL11.glDisable(GL11.GL_DEPTH_TEST)
                GL11.glDepthMask(false)
            }
            GlStateManager.pushMatrix()
            GlStateManager.enableBlend()
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
            GlStateManager.translate(
                location.xCoord - mc.renderManager.viewerPosX,
                location.yCoord - mc.renderManager.viewerPosY,
                location.zCoord - mc.renderManager.viewerPosZ
            )
            GlStateManager.color(1f, 1f, 1f, 0.5f)
            GlStateManager.rotate(-mc.renderManager.playerViewY, 0.0f, 1.0f, 0.0f)
            GlStateManager.rotate(mc.renderManager.playerViewX, 1.0f, 0.0f, 0.0f)
            GlStateManager.scale(-scale / 25, -scale / 25, scale / 25)

            if (renderBlackBox) {
                val j = mc.fontRendererObj.getStringWidth(text) / 2
                disableTexture2D()
                val worldRenderer = Tessellator.getInstance().worldRenderer
                worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR)
                worldRenderer.pos((-j - 1).toDouble(), (-1).toDouble(), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex()
                worldRenderer.pos((-j - 1).toDouble(), 8.toDouble(), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex()
                worldRenderer.pos((j + 1).toDouble(), 8.toDouble(), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex()
                worldRenderer.pos((j + 1).toDouble(), (-1).toDouble(), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex()
                Tessellator.getInstance().draw()
                enableTexture2D()
            }

            if (shadow) {
                mc.fontRendererObj.drawStringWithShadow(
                    text,
                    -mc.fontRendererObj.getStringWidth(text) / 2f,
                    0f,
                    0
                )
            } else {
                mc.fontRendererObj.drawString(
                    text,
                    -mc.fontRendererObj.getStringWidth(text) / 2,
                    0,
                    0
                )
            }

            // for waypoints
            if (showDistance) {
                val distance = "§e${mc.thePlayer.positionVector.distanceTo(location).toInt()}m"
                GlStateManager.translate(0.0, 8.66, 0.0)
                GlStateManager.scale(.66, .66, .66)

                if (renderBlackBox) {
                    val j = mc.fontRendererObj.getStringWidth(distance) / 2
                    disableTexture2D()
                    val worldRenderer = Tessellator.getInstance().worldRenderer
                    worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR)
                    worldRenderer.pos((-j - 1).toDouble(), (-1).toDouble(), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f)
                        .endVertex()
                    worldRenderer.pos((-j - 1).toDouble(), 8.toDouble(), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex()
                    worldRenderer.pos((j + 1).toDouble(), 8.toDouble(), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex()
                    worldRenderer.pos((j + 1).toDouble(), (-1).toDouble(), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f)
                        .endVertex()
                    Tessellator.getInstance().draw()
                    enableTexture2D()
                }

                if (shadow) {
                    mc.fontRendererObj.drawStringWithShadow(
                        distance,
                        -mc.fontRendererObj.getStringWidth(distance) / 2f,
                        0f,
                        0
                    )
                } else {
                    mc.fontRendererObj.drawString(
                        distance,
                        -mc.fontRendererObj.getStringWidth(distance) / 2,
                        0,
                        0
                    )
                }
            }

            GlStateManager.color(1f, 1f, 1f)
            GlStateManager.disableBlend()
            GlStateManager.popMatrix()
            if (!depthTest) {
                GL11.glEnable(GL11.GL_DEPTH_TEST)
                GL11.glDepthMask(true)
            }
        }
    }
}