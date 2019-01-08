package com.zoniic645.scoreboard.score;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.Map;

public class CountedItem extends WorldSavedData {
    private static final String DATA_NAME = "scoredrop";

    private static HashMap<ItemStack, Long> ItemCounted = new HashMap<>();

    public CountedItem() {
        super(DATA_NAME);
    }

    public static void setItemCounted(ItemStack stack) {
        ItemCounted.put(stack, (long) 0);
    }

    public static void countItem(ItemStack stack) {
        if (ItemCounted.containsKey(stack)) {
            ItemCounted.replace(stack, getItemCount(stack) + 1);
        }
        else{
            setItemCounted(stack);
        }
    }

    //화로돚거3
    public static long getItemCount(ItemStack stack) {
        for (Map.Entry<ItemStack, Long> entry : ItemCounted.entrySet()) {
            if (compareItemStacks(stack, entry.getKey())) {
                System.out.println(stack.getUnlocalizedName() + ":" + entry.getValue());
                return entry.getValue();
            }
        }
        return 0;
    }

    //화로돚거4
    private static boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == OreDictionary.WILDCARD_VALUE || stack2.getMetadata() == stack1.getMetadata());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return compound;
    }
}
