package dulkirmod.mixins;

import dulkirmod.config.DulkirConfig;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerArmorBase.class)
public abstract class MixinLayerArmorBase<T extends ModelBiped> implements LayerRenderer<EntityLivingBase> {
    @Inject(method = "renderGlint", at = @At("HEAD"), cancellable = true)
    private void renderGlintOverride(CallbackInfo ci) {
        if (DulkirConfig.INSTANCE.getCancelArmorGlint())
            ci.cancel();
    }

}
