package com.github.atomicblom.fourxtree;

import com.github.atomicblom.fourxtree.blocks.BlockAltNewLeaf;
import com.github.atomicblom.fourxtree.blocks.BlockAltOldLeaf;
import com.github.atomicblom.fourxtree.utility.Logger;
import com.github.atomicblom.fourxtree.utility.Reference;
import com.github.atomicblom.fourxtree.world.WorldGenLargeBirchTree;
import com.github.atomicblom.fourxtree.world.WorldGenSmallTreeOverride;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ExistingSubstitutionException;
import net.minecraftforge.fml.common.registry.GameRegistry;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class FourXTree
{
    @Mod.Instance
    public static FourXTree INSTANCE;

    @Mod.EventHandler
    public void onPreInitialization(FMLPreInitializationEvent event) {
        setMinecraftField(Biome.class, new WorldGenSmallTreeOverride(false), "TREE_FEATURE", "field_76757_N");
        setMinecraftField(BiomeForest.class, new WorldGenLargeBirchTree(false), "BIRCH_TREE", "field_150630_aD");
        setMinecraftField(BiomeForest.class, new WorldGenLargeBirchTree(false), "SUPER_BIRCH_TREE", "field_150629_aC");

        try
        {
            GameRegistry.addSubstitutionAlias("leaves2", GameRegistry.Type.BLOCK, new BlockAltNewLeaf().setUnlocalizedName("leaves").setRegistryName("minecraft", "leaves2"));
            GameRegistry.addSubstitutionAlias("leaves", GameRegistry.Type.BLOCK, new BlockAltOldLeaf().setUnlocalizedName("leaves").setRegistryName("minecraft", "leaves"));
        } catch (ExistingSubstitutionException e)
        {
            e.printStackTrace();
        }

        Logger.info("Initialization complete.");
    }

    private void setMinecraftField(Class<?> clazz, Object newValue, String develName, String obfuscated) {
        Logger.info("Finding field %s in class %s", develName, clazz.getName());
        final Optional<Field> field = Arrays.stream(clazz.getDeclaredFields())
                .filter(x -> x.getName() == develName || x.getName() == obfuscated)
                .findFirst();
        if (!field.isPresent()) {
            throw new RuntimeException(String.format("Unable to locate field %s in class %s", develName, clazz.getName()));
        }

        Logger.info("Changing field value");
        try
        {
            EnumHelper.setFailsafeFieldValue(field.get(), null, newValue);
        } catch (Exception e) {
            Logger.severe("Unable to set field value.");
            throw new RuntimeException(e);
        }
    }
}
