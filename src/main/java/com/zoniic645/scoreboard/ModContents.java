package com.zoniic645.scoreboard;

import com.zoniic645.scoreboard.blocks.BlockFluidScoreCounter;
import com.zoniic645.scoreboard.blocks.BlockItemScoreCounter;
import com.zoniic645.scoreboard.tileentity.TileEntityFluidScoreCounter;
import com.zoniic645.scoreboard.tileentity.TileEntityItemScoreCounter;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ModContents {
    public static final BlockItemScoreCounter ItemScoreCounter = new BlockItemScoreCounter();
    public static final Item ItemScoreCounter_Item = new ItemBlock(ItemScoreCounter).setRegistryName(ItemScoreCounter.getRegistryName());
    public static final BlockFluidScoreCounter FluidScoreCounter = new BlockFluidScoreCounter();
    public static final Item FluidScoreCounter_Item = new ItemBlock(FluidScoreCounter).setRegistryName(FluidScoreCounter.getRegistryName());

    public static void register(IForgeRegistry<Block> registry) {
        registry.register(ItemScoreCounter);
        registry.register(FluidScoreCounter);
        GameRegistry.registerTileEntity(TileEntityItemScoreCounter.class, ItemScoreCounter.getRegistryName());
        GameRegistry.registerTileEntity(TileEntityFluidScoreCounter.class, FluidScoreCounter.getRegistryName());
    }

    public static void registerItem(IForgeRegistry<Item> registry) {
        registry.register(ItemScoreCounter_Item);
        registry.register(FluidScoreCounter_Item);
    }

    public static void reigsterModels() {
        ScoreBoard.proxy.registerModel(ItemScoreCounter_Item);
        ScoreBoard.proxy.registerModel(FluidScoreCounter_Item);
    }
}
