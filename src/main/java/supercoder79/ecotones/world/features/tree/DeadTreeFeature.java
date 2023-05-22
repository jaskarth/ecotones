package supercoder79.ecotones.world.features.tree;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import supercoder79.ecotones.blocks.EcotonesBlocks;
import supercoder79.ecotones.world.features.EcotonesFeature;
import supercoder79.ecotones.world.features.config.SimpleTreeFeatureConfig;

import java.util.List;
import java.util.Objects;

public class DeadTreeFeature extends EcotonesFeature<SimpleTreeFeatureConfig> {

	public DeadTreeFeature(Codec<SimpleTreeFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(FeatureContext<SimpleTreeFeatureConfig> context) {
		StructureWorldAccess world = context.getWorld();
		BlockPos pos = context.getOrigin();
		Random random = context.getRandom();
		SimpleTreeFeatureConfig config = context.getConfig();

		BlockState downState = world.getBlockState(pos.down());
		if (!(world.getBlockState(pos).isAir() && (downState.isOf(Blocks.SAND) || downState.isOf(Blocks.GRASS_BLOCK)))) {
			// Spawn 1/2 as often in red rock biomes, TODO: extract this out
			if (downState.isOf(EcotonesBlocks.RED_ROCK) && world.getBlockState(pos).isAir()) {
				if (random.nextBoolean()) {
					return false;
				}
			} else {
				return false;
			}
		}

		int trunkHeight = random.nextInt(8) + 9;
		int scaledTrunkHeight = MathHelper.floor((double) trunkHeight * 0.618D);

		int branchCount = Math.min(1, MathHelper.floor(1.382D + Math.pow(1.0D * (double) trunkHeight / 13.0D, 2.0D)));

		int maxExtent = pos.getY() + scaledTrunkHeight;
		int yProgress = trunkHeight - 5;
		List<BranchPosition> list = Lists.newArrayList();
		list.add(new BranchPosition(pos.up(yProgress), maxExtent));

		for(; yProgress >= 0; --yProgress) {
			float heightProgress = this.getHeightProgress(trunkHeight, yProgress);

			if (heightProgress >= 0.0F) {
				for(int i = 0; i < branchCount; ++i) {
					double branchDirection = 1.0D * (double) heightProgress * ((double) random.nextFloat() + 0.328D);
					double randomTheta = (double) (random.nextFloat() * 2.0F) * Math.PI;
					double localX = branchDirection * Math.sin(randomTheta) + 0.5D;
					double localZ = branchDirection * Math.cos(randomTheta) + 0.5D;
					BlockPos local = pos.add((int) localX, (yProgress - 1), (int) localZ);
					BlockPos upperLocal = local.up(5);

					if (this.tryMakeBranch(world, random, local, upperLocal, false, config)) {
						int branchX = pos.getX() - local.getX();
						int branchZ = pos.getZ() - local.getZ();
						double branchLength = (double) local.getY() - Math.sqrt(branchX * branchX + branchZ * branchZ) * 0.381D;
						int finalBranchLength = branchLength > (double) maxExtent ? maxExtent : (int) branchLength;
						BlockPos endPos = new BlockPos(pos.getX(), finalBranchLength, pos.getZ());
						if (this.tryMakeBranch(world, random, endPos, local, false, config)) {
							list.add(new BranchPosition(local, endPos.getY()));
						}
					}
				}
			}
		}

		// Make trunk
		this.tryMakeBranch(world, random, pos, pos.up(scaledTrunkHeight), true, config);

		// Make other branches
		this.makeBranches(world, random, trunkHeight, pos, list, config);

		return true;
	}

	private boolean tryMakeBranch(ModifiableTestableWorld world, Random random, BlockPos start, BlockPos end, boolean make, SimpleTreeFeatureConfig config) {
		if (make || !Objects.equals(start, end)) {
			BlockPos blockPos = end.add(-start.getX(), -start.getY(), -start.getZ());
			int longestSide = this.getLongestSide(blockPos);
			float sideX = (float) blockPos.getX() / (float) longestSide;
			float sideY = (float) blockPos.getY() / (float) longestSide;
			float sideZ = (float) blockPos.getZ() / (float) longestSide;

			for (int side = 0; side <= longestSide; ++side) {
				BlockPos currPos = start.add((int) (0.5F + (float) side * sideX), (int) (0.5F + (float) side * sideY), (int) (0.5F + (float) side * sideZ));
				if (make) {
					world.setBlockState(currPos, config.woodState.with(PillarBlock.AXIS, this.getLogAxis(start, currPos)), 3);
				} else if (!canReplace(world, currPos)) {
					return false;
				}
			}

		}

		return true;
	}

	public static boolean canReplace(TestableWorld world, BlockPos pos) {
		return TreeFeature.isAirOrLeaves(world, pos) || isReplaceablePlant(world, pos);
	}

	private static boolean isReplaceablePlant(TestableWorld world, BlockPos pos) {
		return world.testBlockState(pos, AbstractBlock.AbstractBlockState::isReplaceable);
	}

	private float getHeightProgress(int trunkHeight, int y) {
		if ((float) y < (float)trunkHeight * 0.3F) {
			return -1.0F;
		} else {
			float scaledTrunkHeight = (float)trunkHeight / 2.0F;
			float scaledY = scaledTrunkHeight - (float) y;
			float progress = MathHelper.sqrt(scaledTrunkHeight * scaledTrunkHeight - scaledY * scaledY);
			if (scaledY == 0.0F) {
				progress = scaledTrunkHeight;
			} else if (Math.abs(scaledY) >= scaledTrunkHeight) {
				return 0.0F;
			}

			return progress * 0.5F;
		}
	}

	private boolean isHighEnough(int treeHeight, int height) {
		return (double)height >= (double)treeHeight * 0.2D;
	}

	private int getLongestSide(BlockPos offset) {
		int x = MathHelper.abs(offset.getX());
		int y = MathHelper.abs(offset.getY());
		int z = MathHelper.abs(offset.getZ());
		return Math.max(x, Math.max(y, z));
	}

	private Direction.Axis getLogAxis(BlockPos branchStart, BlockPos branchEnd) {
		Direction.Axis axis = Direction.Axis.Y;
		int scaledX = Math.abs(branchEnd.getX() - branchStart.getX());
		int scaledZ = Math.abs(branchEnd.getZ() - branchStart.getZ());
		int maxAxis = Math.max(scaledX, scaledZ);

		if (maxAxis > 0) {
			if (scaledX == maxAxis) {
				axis = Direction.Axis.X;
			} else {
				axis = Direction.Axis.Z;
			}
		}

		return axis;
	}

	private void makeBranches(ModifiableTestableWorld world, Random random, int treeHeight, BlockPos treePos, List<BranchPosition> branches, SimpleTreeFeatureConfig config) {
		for (BranchPosition branchPosition : branches) {
			int endY = branchPosition.getEndY();
			BlockPos startPos = new BlockPos(treePos.getX(), endY, treePos.getZ());
			if (!startPos.equals(branchPosition.node.getCenter()) && this.isHighEnough(treeHeight, endY - treePos.getY())) {
				this.tryMakeBranch(world, random, startPos, branchPosition.node.getCenter(), true, config);
			}
		}

	}

	static class BranchPosition {
		private final FoliagePlacer.TreeNode node;
		private final int endY;

		public BranchPosition(BlockPos pos, int endY) {
			this.node = new FoliagePlacer.TreeNode(pos, 0, false);
			this.endY = endY;
		}

		public int getEndY() {
			return this.endY;
		}
	}
}