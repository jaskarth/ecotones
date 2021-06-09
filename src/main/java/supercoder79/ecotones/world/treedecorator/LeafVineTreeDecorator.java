package supercoder79.ecotones.world.treedecorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;

public class LeafVineTreeDecorator extends TreeDecorator {
   public static final Codec<LeafVineTreeDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
           Codec.INT.fieldOf("chance").forGetter(decorator -> decorator.chance),
           Codec.INT.fieldOf("base_length").forGetter(decorator -> decorator.baseLength),
           Codec.INT.fieldOf("rand_length").forGetter(decorator -> decorator.randLength)
   ).apply(instance, LeafVineTreeDecorator::new));
   private final int chance;
   private final int baseLength;
   private final int randLength;

   public LeafVineTreeDecorator(int chance, int baseLength, int randLength) {
      this.chance = chance;
      this.baseLength = baseLength;
      this.randLength = randLength;
   }

   protected TreeDecoratorType<?> getType() {
      return EcotonesTreeDecorators.LEAF_VINE;
   }

   @Override
   public void generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions) {
      for (BlockPos pos : leavesPositions) {
         BlockPos local;
         int length = this.baseLength + random.nextInt(this.randLength + 1);
         if (random.nextInt(this.chance) == 0) {
            local = pos.west();
            if (Feature.isAir(world, local)) {
               this.generateVine(world, local, VineBlock.EAST, length, replacer);
            }
         }

         if (random.nextInt(this.chance) == 0) {
            local = pos.east();
            if (Feature.isAir(world, local)) {
               this.generateVine(world, local, VineBlock.WEST,length, replacer);
            }
         }

         if (random.nextInt(this.chance) == 0) {
            local = pos.north();
            if (Feature.isAir(world, local)) {
               this.generateVine(world, local, VineBlock.SOUTH,length, replacer);
            }
         }

         if (random.nextInt(this.chance) == 0) {
            local = pos.south();
            if (Feature.isAir(world, local)) {
               this.generateVine(world, local, VineBlock.NORTH,length, replacer);
            }
         }

      }
   }

   private void generateVine(TestableWorld world, BlockPos pos, BooleanProperty facing, int length, BiConsumer<BlockPos, BlockState> replacer) {
      placeVine(replacer, pos, facing);

      for(pos = pos.down(); Feature.isAir(world, pos) && length > 0; --length) {
         placeVine(replacer, pos, facing);
         pos = pos.down();
      }

   }
}