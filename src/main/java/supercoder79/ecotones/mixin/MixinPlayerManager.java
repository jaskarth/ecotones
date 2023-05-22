package supercoder79.ecotones.mixin;

import com.google.common.hash.Hashing;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.advancement.EcotonesCriteria;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;

@Mixin(PlayerManager.class)
public class MixinPlayerManager {
    @Inject(method = "onPlayerConnect", at = @At("TAIL"))
    private void handlePlayerConnection(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
        data.writeBoolean(player.getServerWorld().getChunkManager().getChunkGenerator() instanceof EcotonesChunkGenerator);
        data.writeLong(Hashing.sha256().hashLong(player.getServerWorld().getSeed()).asLong());
        data.writeBoolean(player.server.isDedicated()); // hacks hacks hacks

        ServerPlayNetworking.send(player, Ecotones.WORLD_TYPE, data);

        // Handle advancement
        if (player.getServerWorld().getChunkManager().getChunkGenerator() instanceof EcotonesChunkGenerator) {
            EcotonesCriteria.ENTER_ECOTONES_WORLD.trigger(player);
        }
    }
}
