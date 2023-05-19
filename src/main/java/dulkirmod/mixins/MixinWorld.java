package dulkirmod.mixins;

import dulkirmod.DulkirMod;
import dulkirmod.events.EntityRemovedEvent;
import dulkirmod.features.dungeons.DragonFeatures;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public class MixinWorld {

    @Inject(method = "spawnParticle(IZDDDDDD[I)V", at = @At("HEAD"), cancellable = true)
    public void onInitGui(int particleID, boolean p_175720_2_, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int[] p_175720_15_, CallbackInfo ci) {
        DragonFeatures.INSTANCE.handleNewParticle(particleID, xCoord, yCoord, zCoord);

        if (particleID == 25 && DulkirMod.Companion.getConfig().getHideEnchantRune()) {
            ci.cancel();
        } else if (particleID == 34 && DulkirMod.Companion.getConfig().getHideHeartParticles()) {
            ci.cancel();
        }
    }

    // Source: Aton addons https://github.com/FloppaCoding/AtonAddons/blob/main/src/main/java/atonaddons/mixins/MixinWorld.java
    @Inject(method = "removeEntity", at = @At("HEAD"))
    public void onEntityRemoved(Entity entityIn, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new EntityRemovedEvent(entityIn));
    }
}
