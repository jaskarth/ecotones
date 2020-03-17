package supercoder79.ecotones.sound;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Sounds {
    //Sound credit: https://freesound.org/people/dobroide/sounds/132993/
    public static SoundEvent BEACH_LOOP;

    public static void init() {
        BEACH_LOOP = Registry.register(Registry.SOUND_EVENT, new Identifier("ecotones", "beach_loop"), new SoundEvent(new Identifier("ecotones", "beach_loop")));
    }
}
