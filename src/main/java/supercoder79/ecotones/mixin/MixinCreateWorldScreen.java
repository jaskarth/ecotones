package supercoder79.ecotones.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.MoreOptionsDialog;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.resource.DataPackSettings;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.LevelInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public abstract class MixinCreateWorldScreen extends Screen {
    protected MixinCreateWorldScreen(Text title) {
        super(title);
    }

    @Shadow private Difficulty field_24290;

    @Shadow private TextFieldWidget levelNameField;

    @Shadow private String saveDirectoryName;

    @Shadow private GameRules gameRules;

    @Shadow public boolean hardcore;

    @Shadow @Final public MoreOptionsDialog moreOptionsDialog;

    @Shadow protected abstract boolean method_29696();

    @Shadow protected DataPackSettings field_25479;

    @Inject(method = "init", at = @At("TAIL"))
    public void addEcotonesButton(CallbackInfo ci) {
        this.addButton(new ButtonWidget(this.width / 2 - 155, this.height - 58, 150, 20, new LiteralText(Formatting.DARK_GREEN + "Make Ecotones World"), (buttonWidget) -> {
            this.createLevelEcotones();
        }));
    }

    private void createLevelEcotones() {
        this.client.openScreen(null);
        if (this.method_29696()) {
            GeneratorOptions generatorOptions = this.moreOptionsDialog.getGeneratorOptions(this.hardcore);
            LevelInfo levelInfo2;
            if (generatorOptions.isDebugWorld()) {
                GameRules gameRules = new GameRules();
                gameRules.get(GameRules.DO_DAYLIGHT_CYCLE).set(false, null);
                levelInfo2 = new LevelInfo(this.levelNameField.getText().trim(), GameMode.SPECTATOR, false, Difficulty.PEACEFUL, true, gameRules, DataPackSettings.SAFE_MODE);
            } else {
                levelInfo2 = new LevelInfo(this.levelNameField.getText().trim(), GameMode.SPECTATOR, this.hardcore, this.field_24290, !this.hardcore, this.gameRules, this.field_25479);
            }

            this.client.method_29607(this.saveDirectoryName, levelInfo2, this.moreOptionsDialog.method_29700(), generatorOptions);
        }
    }
}
