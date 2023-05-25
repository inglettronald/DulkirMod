package dulkirmod.mixins;

import dulkirmod.config.DulkirConfig;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

    @ModifyConstant(method = "hurtCameraEffect", constant = @Constant(floatValue = 14.0f))
    private float hurtCameraEffect(float constant) {
        return constant * DulkirConfig.INSTANCE.getHurtCamIntensity();
    }
}
