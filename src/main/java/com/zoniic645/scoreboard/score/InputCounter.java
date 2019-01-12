package com.zoniic645.scoreboard.score;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class InputCounter<T> {
	
	public static final InputCounter<ItemStack> ITEM = new InputCounter<>("item");
	public static final InputCounter<Fluid> FLUID = new InputCounter<>("fluid");

    private HashMap<T, Long> countMap = new HashMap<>();
    private String name;
    
    private InputCounter(String name) {
    	this.name = name;
    }
    
    public void addCount(T input, int count) {
    	if(countMap.containsKey(input)) {
            countMap.replace(input, getCount(input) + count);
    	} else {
            countMap.put(input, (long) count);
    	}
    }

    @SuppressWarnings("unchecked")
	public void readFromNBT(NBTTagCompound nbt) {
    	NBTTagList list = nbt.getTagList(name, Constants.NBT.TAG_LIST);
    	NBTTagCompound comp;
    	for(int i = 0; i < list.tagCount(); i++) {
    		comp = list.getCompoundTagAt(i);
            if(name.equals("item")) {
                ResourceLocation resourcelocation = new ResourceLocation(comp.getString("name"));
                Item item = Item.REGISTRY.getObject(resourcelocation);
            	ItemStack stack = new ItemStack(item, 1, comp.getInteger("metadata"));
            	countMap.put((T) stack, comp.getLong("count"));
            } else if(name.equals("fluid")) {
            	countMap.put((T) FluidRegistry.getFluid(comp.getString("name")), comp.getLong("count"));
            }
    	}
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    	NBTTagList list = new NBTTagList();
    	
    	Iterator<T> iter = countMap.keySet().iterator();
    	while(iter.hasNext()) {
    		NBTTagCompound comp = new NBTTagCompound();
            if(name.equals("item")) {
            	ItemStack stack = (ItemStack) iter.next();
            	comp.setString("name", stack.getItem().getRegistryName().toString());
            	comp.setInteger("metadata", stack.getMetadata());
            	comp.setLong("count", countMap.get(stack));
            } else if(name.equalsIgnoreCase("fluid")) {
            	Fluid fluid = (Fluid) iter.next();
            	comp.setString("name", FluidRegistry.getFluidName(fluid));
            	comp.setLong("count", countMap.get(fluid));
            }
    	}
    	compound.setTag(name, list);
    	
        return compound;
    }

    //화로돚거
    public long getCount(T input) {
    	if(input instanceof ItemStack) {
    		ItemStack stack = (ItemStack) input;
	        for (Map.Entry<T, Long> entry : countMap.entrySet()) {
	            if (compareItemStacks(stack, (ItemStack) entry.getKey())) {
	                System.out.println(stack.getUnlocalizedName() + ":" + entry.getValue());
	                return entry.getValue();
	            }
	        }
	        return 0;
    	}
    	return countMap.get(input);
    }

    //화로돚거
    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == OreDictionary.WILDCARD_VALUE || stack2.getMetadata() == stack1.getMetadata());
    }
}
