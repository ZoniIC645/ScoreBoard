package com.zoniic645.scoreboard.Blocks;

import com.zoniic645.scoreboard.TileEntity.TileEntityScoreCounter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockScoreCounter extends Block {
    public BlockScoreCounter() {
        super(Material.ANVIL);
        setRegistryName("blockscorecounter");
        setUnlocalizedName("BlockScoreCounter");
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityScoreCounter();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    //타일엔티티내놔
    private TileEntityScoreCounter getTileEntity(World world, BlockPos pos) {
        return (TileEntityScoreCounter) world.getTileEntity(pos);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {

            return true;
        } else return false;
    }

}
