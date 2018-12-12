package com.zoniic645.scoreboard;

import com.zoniic645.scoreboard.Blocks.BlockScoreCounter;
import com.zoniic645.scoreboard.TileEntity.TileEntityScoreCounter;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ModContents {
    public static final BlockScoreCounter ScoreCounter = new BlockScoreCounter();
    public static final Item ItemScoreCounter = new ItemBlock(ScoreCounter).setRegistryName(ScoreCounter.getRegistryName());

    public static void registerBlock(IForgeRegistry<Block> registry) {
        registry.register(ScoreCounter);
    }

    public static void registerItem(IForgeRegistry<Item> registry) {
        registry.register(ItemScoreCounter);
    }

    public static void reigsterModels() {
        ScoreBoard.proxy.registerModel(ItemScoreCounter);
    }

    public static void registerTileEntity() {
        GameRegistry.registerTileEntity(TileEntityScoreCounter.class, ScoreCounter.getRegistryName());
    }

    public static void registerScore() {
        ItemScore.addScore(new ItemStack(Items.APPLE), 3);
        ItemScore.addScore(new ItemStack(Items.ARROW), 4);
        ItemScore.addScore(new ItemStack(Items.DYE, 1, 7), 5);
        ItemScore.addScore(new ItemStack(Items.DYE, 1, 8), 6);
    }
}
