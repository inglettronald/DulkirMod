package dulkirmod.mixins;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dulkirmod.DulkirMod;

@Mixin(World.class)
public class MixinWorld {

    @Inject(method = "spawnParticle(IZDDDDDD[I)V", at = @At("HEAD"), cancellable = true)
    public void onInitGui(int particleID, boolean p_175720_2_, double xCood, double yCoord, double zCoord,
                          double xOffset, double yOffset, double zOffset, int[] p_175720_15_, CallbackInfo ci) {
        if (particleID == 25 && DulkirMod.Companion.getConfig().getHideEnchantRune()) {
            ci.cancel();
        } else if (particleID == 34 && DulkirMod.Companion.getConfig().getHideHeartParticles()) {
            ci.cancel();
        }
    }
}
