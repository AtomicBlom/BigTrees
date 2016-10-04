package com.github.atomicblom.fourxtree.world;

import com.google.common.collect.Lists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;
import java.util.List;
import java.util.Random;

public class WorldGenSmallTreeOverride extends WorldGenTrees
{
    private final boolean notify;
    private final List<WorldGenLargeOakTrees> childGenerators = Lists.newArrayList();
    private int depth = 0;
    public WorldGenSmallTreeOverride(boolean notify)
    {
        super(notify);

        this.notify = notify;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        depth++;

        while (depth > childGenerators.size()) {
            childGenerators.add(new WorldGenLargeOakTrees(notify));
        }

        boolean result = false;
        try {
            final WorldGenLargeOakTrees generator = childGenerators.get(depth - 1);
            result = generator.generate(worldIn, rand, position);
        } finally
        {
            depth--;
        }
        return result;
    }
}