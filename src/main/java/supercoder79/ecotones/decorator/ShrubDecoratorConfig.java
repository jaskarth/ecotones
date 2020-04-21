package supercoder79.ecotones.decorator;

import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.world.gen.decorator.DecoratorConfig;

public class ShrubDecoratorConfig implements DecoratorConfig {
    public int targetCount;

    public ShrubDecoratorConfig(int targetCount) {
        this.targetCount = targetCount;
    }

    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
//        return new Dynamic(ops, ops.createString("targetCount"), ops.createInt(this.targetCount)));
        return null;
    }

    public static ShrubDecoratorConfig deserialize(Dynamic<?> dynamic) {
        int i = dynamic.get("targetCount").asInt(0);
        return new ShrubDecoratorConfig(i);
    }
}