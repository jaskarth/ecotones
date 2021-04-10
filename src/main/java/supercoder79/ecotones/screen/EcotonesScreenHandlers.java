package supercoder79.ecotones.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;
import supercoder79.ecotones.Ecotones;
import supercoder79.ecotones.util.RegistryReport;

public final class EcotonesScreenHandlers {
    public static final ScreenHandlerType<SapDistilleryScreenHandler> SAP_DISTILLERY = register("sap_distillery", SapDistilleryScreenHandler::new);
    public static void init() {
        // NO-OP due to registering in clinit
    }

    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String name, ScreenHandlerRegistry.SimpleClientHandlerFactory<T> screenHandler) {
        RegistryReport.increment("Screen Handler");
        return ScreenHandlerRegistry.registerSimple(Ecotones.id(name), screenHandler);
    }
}
