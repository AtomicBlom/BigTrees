package com.github.atomicblom.fourxtree.world;

import com.github.atomicblom.fourxtree.utility.Logger;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBirchTree;
import net.minecraftforge.common.IPlantable;
import java.util.Random;

public class WorldGenLargeBirchTree extends WorldGenBirchTree
{
    private static final IBlockState LOG = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, EnumType.BIRCH);
    private static final IBlockState LEAF = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, EnumType.BIRCH).withProperty(BlockOldLeaf.CHECK_DECAY, Boolean.valueOf(false));

    public WorldGenLargeBirchTree(boolean notify)
    {
        super(notify, false);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        final int treeHeight = rand.nextInt(5) + 21;

        if (position.getY() < 1 || position.getY() + treeHeight + 1 > 256)
        {
            return false;
        }

        if (!isValid(worldIn, position, treeHeight)) return false;

        final BlockPos down = position.down();
        final IBlockState state = worldIn.getBlockState(down);
        final boolean isSoil = state.getBlock().canSustainPlant(state, worldIn, down, EnumFacing.UP, (IPlantable) Blocks.SAPLING);

        //Technically the height-check is already done above?
        if (!isSoil || position.getY() >= worldIn.getHeight() - treeHeight - 1)
        {
            return false;
        }

        Logger.info("Spawning tree @ %s", position);

        state.getBlock().onPlantGrow(state, worldIn, down, position);
        final MutableBlockPos blockpos = new MutableBlockPos();
        for (int y = position.getY() + (treeHeight / 3); y <= position.getY() + treeHeight; ++y)
        {
            final int bleah = (y - position.getY()) - (treeHeight / 3) * 2;
            final float v = bleah / (treeHeight / 3.0f);
            final int radius = (int)(Math.abs(Math.cos(v)) * 4);

            final int deltaY = y - (position.getY() + treeHeight);

            //final int radius = 3 - deltaY / 2;

            for (int x = position.getX() - radius; x <= position.getX() + radius; ++x)
            {
                final int deltaX = x - position.getX();

                for (int z = position.getZ() - radius; z <= position.getZ() + radius; ++z)
                {
                    final int deltaZ = z - position.getZ();

                    if (rand.nextDouble() * (deltaX * deltaX + deltaZ * deltaZ) < (radius * radius) * 0.5 && (Math.abs(deltaX) != radius || Math.abs(deltaZ) != radius || rand.nextInt(2) != 0 && deltaY != 0))
                    {
                        blockpos.setPos(x, y, z);
                        final IBlockState blockState = worldIn.getBlockState(blockpos);

                        if (blockState.getBlock().isAir(blockState, worldIn, blockpos))
                        {
                            setBlockAndNotifyAdequately(worldIn, blockpos, LEAF);
                        }
                    }
                }
            }
        }

        blockpos.setPos(position.down());
        for (int y = 0; y < treeHeight-2; ++y)
        {
            blockpos.move(EnumFacing.UP);
            final IBlockState blockState = worldIn.getBlockState(blockpos);

            if (blockState.getBlock().isAir(blockState, worldIn, blockpos) || blockState.getBlock().isLeaves(blockState, worldIn, blockpos))
            {
                setBlockAndNotifyAdequately(worldIn, blockpos, LOG);
            }
        }

        return true;
    }

    private boolean isValid(World worldIn, BlockPos position, int treeHeight)
    {
        final MutableBlockPos pos = new MutableBlockPos();

        for (int y = position.getY(); y <= position.getY() + 1 + treeHeight; ++y)
        {
            int treeRadius = 1;

            if (y == position.getY())
            {
                treeRadius = 0;
            }

            if (y >= position.getY() + 1 + treeHeight - 2)
            {
                treeRadius = 2;
            }

            for (int x = position.getX() - treeRadius; x <= position.getX() + treeRadius; ++x)
            {
                for (int z = position.getZ() - treeRadius; z <= position.getZ() + treeRadius; ++z)
                {
                    if (y >= 0 && y < worldIn.getHeight())
                    {
                        if (!isReplaceable(worldIn, pos.setPos(x, y, z)))
                        {
                            return false;
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}