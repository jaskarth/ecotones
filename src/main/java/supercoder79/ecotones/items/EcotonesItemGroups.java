package supercoder79.ecotones.items;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public final class EcotonesItemGroups {
    public static final ItemGroup ECOTONES = FabricItemGroupBuilder.create(new Identifier("ecotones", "ecotones"))
            .icon(() -> new ItemStack(EcotonesItems.ECOTONES_BOOK))
            .build();

    public static void init() {

    }
}
