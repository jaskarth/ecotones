package supercoder79.ecotones.world.decorator;

import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.world.gen.decorator.DecoratorConfig;

public class ShrubDecoratorConfig implements DecoratorConfig {
    public double targetCount;

    public ShrubDecoratorConfig(double targetCount) {
        this.targetCount = targetCount;
    }

    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
//        return new Dynamic(ops, ops.createString("targetCount"), ops.createInt(this.targetCount)));
        return null;
    }

    public static ShrubDecoratorConfig deserialize(Dynamic<?> dynamic) {
        double i = dynamic.get("targetCount").asDouble(0);
        return new ShrubDecoratorConfig(i);
    }
}