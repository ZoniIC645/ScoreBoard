package com.zoniic645.scoreboard.score;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.Map;

public class ItemScore {
    private static final ItemScore SCORE = new ItemScore();

    public static ItemScore instance() {
        return SCORE;
    }

    private static HashMap<ItemStack, Integer> ItemBasedScores = new HashMap<>();

    public static void addScore(ItemStack stack, int score) {
        ItemBasedScores.put(stack, score);
    }

    //점수 받아옴
    public static int getScore(ItemStack stack) {
        //화로 돚거임
        for (Map.Entry<ItemStack, Integer> entry : ItemBasedScores.entrySet()) {
            if (compareItemStacks(stack, entry.getKey())) {
                return entry.getValue();
            }
        }
        return 0;
    }

    //화로 돚거임2
    private static boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == OreDictionary.WILDCARD_VALUE || stack2.getMetadata() == stack1.getMetadata());
    }
}
