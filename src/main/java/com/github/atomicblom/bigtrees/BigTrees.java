package com.github.atomicblom.bigtrees;

import com.github.atomicblom.bigtrees.utility.Logger;
import com.github.atomicblom.bigtrees.utility.Reference;
import com.github.atomicblom.bigtrees.world.BigTreesWorldGenTrees;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class BigTrees
{
    @Mod.Instance
    public static BigTrees INSTANCE;

    @Mod.EventHandler
    public void onPreInitialization(FMLPreInitializationEvent event) {
        Logger.info("Finding Biomes class");
        final Optional<Field> field = Arrays.stream(Biome.class.getDeclaredFields())
                .filter(x -> x.getType() == WorldGenTrees.class)
                .findFirst();
        if (!field.isPresent()) {
            throw new RuntimeException("Unable to locate tree generator within Biome class");
        }

        Logger.info("Changing tree generator");
        try
        {
            EnumHelper.setFailsafeFieldValue(field.get(), null, new BigTreesWorldGenTrees(true));
        } catch (Exception e) {
            Logger.severe("Unable to set the tree generator.");
            throw new RuntimeException(e);
        }

        Logger.info("Initialization complete.");
    }
}
