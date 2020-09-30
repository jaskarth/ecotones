package supercoder79.ecotones.world.treedecorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.VineBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.decorator.TreeDecorator;
import net.minecraft.world.gen.decorator.TreeDecoratorType;
import net.minecraft.world.gen.feature.Feature;

import java.util.List;
import java.util.Random;
import java.util.Set;

// Leaves from the vine
// Falling so slow.
// Like fragile, tiny shells
// Drifting in the foam.
// Little soldier boy
// Comes marching home.
// Brave soldier boy
// Come marching home...
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

   public void generate(WorldAccess world, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions, Set<BlockPos> set, BlockBox box) {
      for (BlockPos pos : leavesPositions) {
         BlockPos local;
         int length = this.baseLength + random.nextInt(this.randLength + 1);
         if (random.nextInt(this.chance) == 0) {
            local = pos.west();
            if (Feature.method_27370(world, local)) {
               this.generateVine(world, local, VineBlock.EAST, length, set, box);
            }
         }

         if (random.nextInt(this.chance) == 0) {
            local = pos.east();
            if (Feature.method_27370(world, local)) {
               this.generateVine(world, local, VineBlock.WEST,length, set, box);
            }
         }

         if (random.nextInt(this.chance) == 0) {
            local = pos.north();
            if (Feature.method_27370(world, local)) {
               this.generateVine(world, local, VineBlock.SOUTH,length, set, box);
            }
         }

         if (random.nextInt(this.chance) == 0) {
            local = pos.south();
            if (Feature.method_27370(world, local)) {
               this.generateVine(world, local, VineBlock.NORTH,length, set, box);
            }
         }

      }
   }

   private void generateVine(ModifiableTestableWorld world, BlockPos pos, BooleanProperty booleanProperty, int length, Set<BlockPos> set, BlockBox blockBox) {
      this.placeVine(world, pos, booleanProperty, set, blockBox);

      for(pos = pos.down(); Feature.method_27370(world, pos) && length > 0; --length) {
         this.placeVine(world, pos, booleanProperty, set, blockBox);
         pos = pos.down();
      }

   }
}