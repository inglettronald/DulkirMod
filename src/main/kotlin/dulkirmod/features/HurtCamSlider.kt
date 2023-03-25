package dulkirmod.features

import dulkirmod.DulkirMod.Companion.mc
import dulkirmod.config.DulkirConfig
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.MathHelper

object HurtCamSlider {
    fun renderHurt(partialTicks: Float): Boolean {
        if (!DulkirConfig.hurtCamSlider) return false
        if (mc.renderViewEntity is EntityLivingBase) {
            val entitylivingbase = mc.renderViewEntity as EntityLivingBase
            var f: Float = (entitylivingbase.hurtTime.toFloat() - partialTicks)
            if (entitylivingbase.health <= 0.0f) {
                val f1: Float = entitylivingbase.deathTime.toFloat() + partialTicks
                GlStateManager.rotate(40.0f - 8000.0f / (f1 + 200.0f), 0.0f, 0.0f, 1.0f)
            }
            if (f < 0.0f) {
                return true
            }
            f /= entitylivingbase.maxHurtTime.toFloat()
            f = MathHelper.sin(f * f * f * f * Math.PI.toFloat())
            val f2 = entitylivingbase.attackedAtYaw
            GlStateManager.rotate(-f2, 0.0f, 1.0f, 0.0f)
            GlStateManager.rotate(-f * 14.0f * DulkirConfig.hurtCamIntensity, 0.0f, 0.0f, 1.0f)
            GlStateManager.rotate(f2, 0.0f, 1.0f, 0.0f)
        }
        return true
    }
}