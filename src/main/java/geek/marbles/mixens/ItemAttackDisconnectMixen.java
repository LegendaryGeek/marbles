package geek.marbles.mixens;

import geek.marbles.entity.MarbleEntity;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by Robin Seifert on 12/22/2021.
 */
@Mixin(targets = "net.minecraft.server.network.ServerGamePacketListenerImpl$1")
public class ItemAttackDisconnectMixen
{
    @Shadow
    private Entity entity;

    @Inject(at = @At("HEAD"), method = "onAttack()V", cancellable = true)
    private void lgmarbles_onAttack2(CallbackInfo ci) {
        System.out.println("Attack");
        if(this.entity instanceof MarbleEntity) {
            ci.cancel();
        }
    }
}
