package com.github.atomicblom.bigtrees.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import java.util.Random;

public class BigTreesWorldGenTrees extends WorldGenTrees
{
    //private static final ThreadLocal<WorldGenBigTree> alternateTreeGen = new ThreadLocal<>();

    private final boolean notify;

    public BigTreesWorldGenTrees(boolean notify)
    {
        super(notify);

        this.notify = notify;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        /*WorldGenBigTree worldGenBigTree = alternateTreeGen.get();
        if (worldGenBigTree == null) {
            worldGenBigTree = new BigTreesWorldGenBigTree(notify);
            alternateTreeGen.set(worldGenBigTree);
        }*/
        return new BigTreesWorldGenBigTree(notify).generate(worldIn, rand, position);
    }
}