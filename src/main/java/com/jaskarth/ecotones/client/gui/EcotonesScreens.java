package com.jaskarth.ecotones.client.gui;

import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import com.jaskarth.ecotones.screen.EcotonesScreenHandlers;
import com.jaskarth.ecotones.util.RegistryReport;

public final class EcotonesScreens {
    public static void init() {
        register(EcotonesScreenHandlers.SAP_DISTILLERY, SapDistilleryScreen::new);
        register(EcotonesScreenHandlers.FERTILIZER_SPREADER, FertilizerSpreaderScreen::new);
        register(EcotonesScreenHandlers.GRINDSTONE, GrindstoneScreen::new);
    }

    private static <H extends ScreenHandler, S extends Screen & ScreenHandlerProvider<H>> void register(ScreenHandlerType<? extends H> type, ScreenRegistry.Factory<H, S> screenFactory) {
        ScreenRegistry.register(type, screenFactory);
        RegistryReport.increment("Screen");
    }
}
