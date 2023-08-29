package com.jaskarth.ecotones.asm;

import com.google.common.collect.ImmutableList;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.transformer.IMixinTransformer;
import org.spongepowered.asm.mixin.transformer.ext.IExtensionRegistry;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

/**
 * The Sacred Text
 */
public final class TransformerMixinPlugin implements IMixinConfigPlugin {
    private static final Logger LOGGER = LogManager.getLogger(TransformerMixinPlugin.class);

    @Override
    public void onLoad(String mixinPackage) {
        FabricLoader loader = FabricLoader.getInstance();
        MappingResolver mappings = loader.getMappingResolver();

        if (loader.isDevelopmentEnvironment()) {
            ClassTransformer transformer = CommandSourceClassVisitor.createTransformer(mappings);

            try {
                this.registerClassTransformer(transformer);
            } catch (ReflectiveOperationException e) {
                LOGGER.error("Failed to apply class transformer!", e);
            }
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return ImmutableList.of();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    private void registerClassTransformer(ClassTransformer transformer) throws ReflectiveOperationException {
        HackerVoice.imIn().registerTransformer(transformer);
    }

    /**
     * This is where the terrible things happen. Don't look.
     */
    private record HackerVoice(Object delegate, Field mixinTransformerField) {
        static HackerVoice imIn() throws ReflectiveOperationException {
            Class<?> knotClassLoader = Class.forName("net.fabricmc.loader.impl.launch.knot.KnotClassLoader");
            Class<?> knotClassDelegate = Class.forName("net.fabricmc.loader.impl.launch.knot.KnotClassDelegate");

            Field delegateField = knotClassLoader.getDeclaredField("delegate");
            delegateField.setAccessible(true);

            Field mixinTransformerField = knotClassDelegate.getDeclaredField("mixinTransformer");
            mixinTransformerField.setAccessible(true);

            Object delegate = delegateField.get(HackerVoice.class.getClassLoader());

            return new HackerVoice(delegate, mixinTransformerField);
        }

        void registerTransformer(ClassTransformer transformer) throws IllegalAccessException {
            IMixinTransformer mixinTransformer = (IMixinTransformer) this.mixinTransformerField.get(this.delegate);
            if (mixinTransformer == null) {
                throw new IllegalStateException("mixin transformer not yet initialized!");
            }

            this.mixinTransformerField.set(this.delegate, new ProxyMixinTransformer(mixinTransformer, transformer));
        }
    }

    private record ProxyMixinTransformer(IMixinTransformer delegate, ClassTransformer transformer) implements IMixinTransformer {
        @Override
        public void audit(MixinEnvironment environment) {
            this.delegate.audit(environment);
        }

        @Override
        public List<String> reload(String mixinClass, ClassNode classNode) {
            return this.delegate.reload(mixinClass, classNode);
        }

        @Override
        public boolean computeFramesForClass(MixinEnvironment environment, String name, ClassNode classNode) {
            return this.delegate.computeFramesForClass(environment, name, classNode);
        }

        @Override
        public byte[] transformClassBytes(String name, String transformedName, byte[] basicClass) {
            byte[] bytes = this.delegate.transformClassBytes(name, transformedName, basicClass);
            bytes = this.transformer.transform(name, transformedName, bytes);
            return bytes;
        }

        @Override
        public byte[] transformClass(MixinEnvironment environment, String name, byte[] classBytes) {
            return this.delegate.transformClass(environment, name, classBytes);
        }

        @Override
        public boolean transformClass(MixinEnvironment environment, String name, ClassNode classNode) {
            return this.delegate.transformClass(environment, name, classNode);
        }

        @Override
        public byte[] generateClass(MixinEnvironment environment, String name) {
            return this.delegate.generateClass(environment, name);
        }

        @Override
        public boolean generateClass(MixinEnvironment environment, String name, ClassNode classNode) {
            return this.delegate.generateClass(environment, name, classNode);
        }

        @Override
        public IExtensionRegistry getExtensions() {
            return this.delegate.getExtensions();
        }
    }
}