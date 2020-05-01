package supercoder79.ecotones.mixin;

import com.mojang.datafixers.Dynamic;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.datafixer.NbtOps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import net.minecraft.world.level.LevelInfo;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import supercoder79.ecotones.world.generation.LevelGenUtil;
import supercoder79.ecotones.world.generation.WorldType;

import java.util.Random;

@Mixin(CreateWorldScreen.class)
public abstract class MixinCreateWorldScreen extends Screen {
    protected MixinCreateWorldScreen(Text title) {
        super(title);
    }

    @Shadow private boolean creatingLevel;

    @Shadow private TextFieldWidget seedField;

    @Shadow private Difficulty field_24290;

    @Shadow private TextFieldWidget levelNameField;

    @Shadow private String saveDirectoryName;

    @Shadow private GameRules gameRules;

    @Inject(method = "init", at = @At("TAIL"))
    public void addEcotonesButton(CallbackInfo ci) {
        this.addButton(new ButtonWidget(this.width / 2 - 155, this.height - 58, 150, 20, new LiteralText(Formatting.DARK_GREEN + "Make Ecotones World"), (buttonWidget) -> {
            this.createLevelEcotones();
        }));
    }

    private void createLevelEcotones() {
        this.client.openScreen(null);
        if (!this.creatingLevel) {
            this.creatingLevel = true;
            long l = (new Random()).nextLong();
            String string = this.seedField.getText();
            if (!StringUtils.isEmpty(string)) {
                try {
                    long m = Long.parseLong(string);
                    if (m != 0L) {
                        l = m;
                    }
                } catch (NumberFormatException var6) {
                    l = string.hashCode();
                }
            }

            LevelInfo info = new LevelInfo(this.levelNameField.getText().trim(),
                    l,
                    GameMode.CREATIVE,
                    true,
                    false,
                    this.field_24290,
                    LevelGenUtil.makeChunkGenerator(WorldType.generatorType, new Dynamic<>(NbtOps.INSTANCE, new CompoundTag())),
                    this.gameRules).enableCommands();

            this.client.startIntegratedServer(this.saveDirectoryName, info);
        }
    }
}
