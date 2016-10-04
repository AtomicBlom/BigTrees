package com.github.atomicblom.fourxtree;

import com.github.atomicblom.fourxtree.blocks.BlockAltNewLeaf;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ExistingSubstitutionException;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.Type;

@Mod.EventBusSubscriber
public class Events
{
    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event ) {

    }
}
