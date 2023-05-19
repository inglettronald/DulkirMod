package dulkirmod.mixins;

import dulkirmod.features.HurtCamSlider;
import dulkirmod.features.ViewBobbing;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

    @Inject(method = "hurtCameraEffect", at = @At("HEAD"), cancellable = true)
    private void hurtCameraEffect(float partialTicks, CallbackInfo ci) {
        if (HurtCamSlider.INSTANCE.renderHurt(partialTicks))
            ci.cancel();
    }

    @Inject(method = "setupViewBobbing", at = @At("HEAD"), cancellable = true)
    private void modifyViewBobbing(float partialTicks, CallbackInfo ci) {
        if (ViewBobbing.INSTANCE.renderBob(partialTicks))
            ci.cancel();
    }
}
