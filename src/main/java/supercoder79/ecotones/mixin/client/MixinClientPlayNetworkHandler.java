package supercoder79.ecotones.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenWrittenBookS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.client.ClientRegistrySyncState;
import supercoder79.ecotones.items.EcotonesItems;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class MixinClientPlayNetworkHandler implements ClientPlayPacketListener {
    @Shadow @Final private MinecraftClient client;

    // TODO: use a better mixin
    @Inject(method = "onOpenWrittenBook", at = @At("HEAD"), cancellable = true)
    public void openEcotonesBook(OpenWrittenBookS2CPacket packet, CallbackInfo ci) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        ItemStack itemStack = this.client.player.getStackInHand(packet.getHand());
        if (itemStack.isOf(EcotonesItems.ECOTONES_BOOK)) {
            this.client.setScreen(new BookScreen(new BookScreen.WrittenBookContents(itemStack)));
            ci.cancel();
        }
    }

    @Inject(method = "onGameJoin", at = @At("RETURN"))
    private void handleGameJoinEcotones(GameJoinS2CPacket packet, CallbackInfo ci) {
        if (ClientRegistrySyncState.state == ClientRegistrySyncState.State.WAITING) {
            Ecotones.REGISTRY = MinecraftClient.getInstance().world.getRegistryManager().get(RegistryKeys.BIOME);
            ClientRegistrySyncState.state = ClientRegistrySyncState.State.SYNCED;
        }
    }
}
