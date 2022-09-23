package com.dulkirmod.mixin;

import com.dulkirmod.Settings;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderManager.class)
public class MixinRendererManager {

    @Inject(method = "doRenderEntity", at = @At("HEAD"), cancellable = true)
    public void doRender(
            Entity entity,
            double x,
            double y,
            double z,
            float entityYaw,
            float partialTicks,
            boolean p_147939_10_,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if(!Settings.HealerFairy) return;
        if (entity instanceof EntityArmorStand) {
            if (((EntityArmorStand) entity).getHeldItem() != null && ((EntityArmorStand) entity).getHeldItem().getItem() == Items.skull) {
                ItemStack stack = ((EntityArmorStand) entity).getHeldItem();
                if (stack.hasTagCompound() && stack.getTagCompound().hasKey("SkullOwner")) {
                    NBTTagCompound skullOwner = stack.getTagCompound().getCompoundTag("SkullOwner");
                    if (skullOwner.hasKey("Properties")) {
                        NBTTagCompound properties = skullOwner.getCompoundTag("Properties");
                        if (properties.hasKey("textures")) {
                            //if (properties.getTagList("textures", 10).tagCount() >= 1) {
                                if ("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTZjM2UzMWNmYzY2NzMzMjc1YzQyZmNmYjVkOWE0NDM0MmQ2NDNiNTVjZDE0YzljNzdkMjczYTIzNTIifX19"
                                        .equals(properties.getTagList("textures", 10).getCompoundTagAt(0).getString("Value")))
                                    cir.cancel();
                            //}
                        }

                    }

                }

            }
        }
    }
}