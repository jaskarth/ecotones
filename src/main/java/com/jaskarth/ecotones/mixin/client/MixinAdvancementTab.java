package com.jaskarth.ecotones.mixin.client;

import net.minecraft.advancement.Advancement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementTabType;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.jaskarth.ecotones.client.gui.advancement.EcotonesAdvancementTab;

@Mixin(AdvancementTab.class)
public class MixinAdvancementTab {
    @Inject(method = "create", at = @At("HEAD"), cancellable = true)
    private static void makeEcotonesTab(MinecraftClient client, AdvancementsScreen screen, int index, Advancement root, CallbackInfoReturnable<AdvancementTab> cir) {
        if (root.getId().getNamespace().equals("ecotones")) {
            if (root.getDisplay() == null) {
                cir.setReturnValue(null);
            } else {
                AdvancementTabType[] var4 = AdvancementTabType.values();
                int var5 = var4.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    AdvancementTabType advancementTabType = var4[var6];
                    if (index < advancementTabType.getTabCount()) {
                        cir.setReturnValue(new EcotonesAdvancementTab(client, screen, advancementTabType, index, root, root.getDisplay()));
                        return;
                    }

                    index -= advancementTabType.getTabCount();
                }

                cir.setReturnValue(null);
            }
        }
    }
}
