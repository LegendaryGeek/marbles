package geek.marbles.mixens;

import geek.marbles.entity.MarbleEntity;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created by Robin Seifert on 12/22/2021.
 */
@Mixin(ServerGamePacketListenerImpl.class)
public class ItemAttackDisconnectMixen
{
    @Shadow
    ServerPlayer player;

    @Inject(at = @At("HEAD"), method = "handleInteract", cancellable = true)
    private void handleInteract(@NotNull ServerboundInteractPacket serverboundInteractPacket, CallbackInfo info)
    {
        ServerLevel serverlevel = player.getLevel();
        final Entity entity = serverboundInteractPacket.getTarget(serverlevel);

        if(entity instanceof MarbleEntity) {
            info.cancel();
        }
    }
}
