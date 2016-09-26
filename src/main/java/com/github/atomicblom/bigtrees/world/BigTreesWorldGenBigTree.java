package com.github.atomicblom.bigtrees.world;

import com.github.atomicblom.bigtrees.utility.Logger;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import java.util.Random;


public class BigTreesWorldGenBigTree extends WorldGenBigTree
{
    public BigTreesWorldGenBigTree(boolean notify)
    {
        super(notify);
    }

    //FIXME: I don't think we need depth if we reinstantiate the object each level.
    int depth = 0;

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        depth++;
        world = worldIn;
        basePos = position;
        this.rand = new Random(rand.nextLong());
        scaleWidth = 1.4D;

        if (heightLimit == 0)
        {
            heightLimit = 21 + this.rand.nextInt(6);
        }

        if (!validTreeLocation())
        {
            depth--;
            if (depth == 0)
            {
                world = null; //Fix vanilla Mem leak, holds latest world
            }
            return false;
        }
        else
        {
            generateLeafNodeList();
            generateLeaves();
            generateTrunk();
            generateLeafNodeBases();
            depth--;
            if (depth == 0)
            {
                world = null; //Fix vanilla Mem leak, holds latest world
            }
            return true;
        }
    }
}
