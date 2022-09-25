package dulkirmod.mixins;

import dulkirmod.features.ItemAnimations;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {ItemRenderer.class})
public class ItemRendererMixin {

    @Inject(method = {"transformFirstPersonItem(FF)V"}, at = @At("HEAD"), cancellable = true)
    public void itemTransform(float equipProgress, float swingProgress, CallbackInfo ci) {
        if (ItemAnimations.INSTANCE.itemTransforHook(equipProgress, swingProgress)) ci.cancel();
    }

    @Inject(method = {"doItemUsedTransformations"}, at = @At("HEAD"), cancellable = true)
    public void useTransform(float swingProgress, CallbackInfo ci){
        if (ItemAnimations.INSTANCE.scaledSwing(swingProgress)) ci.cancel();
    }

    @Inject(method ={"performDrinking"}, at = @At("HEAD"), cancellable = true)
    public void drinkTransform(AbstractClientPlayer clientPlayer, float partialTicks, CallbackInfo ci) {
        if (ItemAnimations.INSTANCE.rotationlessDrink(clientPlayer, partialTicks)) ci.cancel();
    }
}
