package com.github.atomicblom.bigtrees;

import com.github.atomicblom.bigtrees.utility.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class BigTrees
{
    @Mod.Instance
    public static BigTrees INSTANCE;

    public void onPreInitialization(FMLPreInitializationEvent event) {

    }
}
