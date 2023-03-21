package dulkirmod.features

import dulkirmod.DulkirMod.Companion.config
import dulkirmod.DulkirMod.Companion.mc
import net.minecraft.client.entity.AbstractClientPlayer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.item.ItemStack
import net.minecraft.util.MathHelper
import kotlin.math.exp
import kotlin.math.pow

/**
 * Module to change the appearance of held items.
 *
 * This module uses the EntityLivingBase and ItemRenderer Mixins to function.
 * Because only this module and no others are supposed to modify their behavior direct references are used instead of
 * forge events.
 *
 * @author Aton - THANK YOU
 */
object ItemAnimations {

    /**
     * Directly referenced hook for the itemTransform Inject in the ItemRenderer Mixin.
     * Takes care of scaling and positioning the held item.
     */
    fun itemTransforHook(equipProgress: Float, swingProgress: Float): Boolean {
        if (!config.customAnimations) return false
        val newSize = (0.4f * exp(config.customSize))
        val newX = (0.56f * (1 + config.customX))
        val newY = (-0.52f * (1 - config.customY))
        val newZ = (-0.71999997f * (1 + config.customZ))
        GlStateManager.translate(newX, newY, newZ)
        GlStateManager.translate(0.0f, equipProgress * -0.6f, 0.0f)

        //Rotation
        GlStateManager.rotate(config.customPitch, 1.0f, 0.0f, 0.0f)
        GlStateManager.rotate(config.customYaw, 0.0f, 1f, 0f)
        GlStateManager.rotate(config.customRoll, 0f, 0f, 1f)

        GlStateManager.rotate(45f, 0.0f, 1f, 0f)

        val f = MathHelper.sin(swingProgress * swingProgress * Math.PI.toFloat())
        val f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * Math.PI.toFloat())
        GlStateManager.rotate(f * -20.0f, 0.0f, 1.0f, 0.0f)
        GlStateManager.rotate(f1 * -20.0f, 0.0f, 0.0f, 1.0f)
        GlStateManager.rotate(f1 * -80.0f, 1.0f, 0.0f, 0.0f)
        GlStateManager.scale(newSize, newSize, newSize)
        return true
    }

    /**
     * Directly referenced by the ItemRendereMixin. If enabled will scale the item swing animation.
     * Returns whether custom animation was performed.
     */
    fun scaledSwing(swingProgress: Float): Boolean {
        if (!config.customAnimations || !config.doesScaleSwing) return false
        val scale = exp(config.customSize)
        val f = -0.4f * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * Math.PI.toFloat()) * scale
        val f1 = 0.2f * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * Math.PI.toFloat() * 2.0f) * scale
        val f2 = -0.2f * MathHelper.sin(swingProgress * Math.PI.toFloat()) * scale
        GlStateManager.translate(f, f1, f2)
        return true
    }

    /**
     * Directly referenced by the ItemRendereMixin. If enabled will scale the potion drink animation.
     * Returns whether custom animation was performed.
     */
    fun rotationlessDrink(clientPlayer: AbstractClientPlayer, partialTicks: Float): Boolean {
        if (!config.customAnimations || config.drinkingSelector != 1) return false
        val f: Float = clientPlayer.itemInUseCount.toFloat() - partialTicks + 1.0f
        val f1: Float = f / mc.thePlayer.heldItem.maxItemUseDuration.toFloat()
        var f2 = MathHelper.abs(MathHelper.cos(f / 4.0f * 3.1415927f) * 0.1f)
        if (f1 >= 0.8f) {
            f2 = 0.0f
        }
        GlStateManager.translate(0.0f, f2, 0.0f)
        return true
    }

    /**
     * Directly referenced by the ItemRendereMixin. If enabled will scale the potion drink animation.
     * Returns whether custom animation was performed.
     */
    fun scaledDrinking(clientPlayer: AbstractClientPlayer, partialTicks: Float, itemToRender: ItemStack): Boolean {
        if (!config.customAnimations || config.drinkingSelector != 2) return false
        val f: Float = clientPlayer.itemInUseCount.toFloat() - partialTicks + 1.0f
        val f1: Float = f / itemToRender.maxItemUseDuration.toFloat()
        var f2 = MathHelper.abs(MathHelper.cos(f / 4.0f * Math.PI.toFloat()) * 0.1f)

        if (f1 >= 0.8f) {
            f2 = 0.0f
        }

        // Transform to correct rotation center
        val newX = (0.56f * (1 + config.customX)).toFloat()
        val newY = (-0.52f * (1 - config.customY)).toFloat()
        val newZ = (-0.71999997f * (1 + config.customZ)).toFloat()
        GlStateManager.translate(-0.56f, 0.52f, 0.71999997f)
        GlStateManager.translate(newX, newY, newZ)

        GlStateManager.translate(0.0f, f2, 0.0f)
        val f3 = 1.0f - f1.toDouble().pow(27.0).toFloat()
        GlStateManager.translate(f3 * 0.6f, f3 * -0.5f, f3 * 0.0f)
        GlStateManager.rotate(f3 * 90.0f, 0.0f, 1.0f, 0.0f)
        GlStateManager.rotate(f3 * 10.0f, 1.0f, 0.0f, 0.0f)
        GlStateManager.rotate(f3 * 30.0f, 0.0f, 0.0f, 1.0f)

        // Transform back
        GlStateManager.translate(0.56f, -0.52f, -0.71999997f)
        GlStateManager.translate(-newX, -newY, -newZ)
        return true
    }

}