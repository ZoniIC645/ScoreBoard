package com.zoniic645.scoreboard.Blocks;

import com.feed_the_beast.ftblib.lib.data.ForgePlayer;
import com.feed_the_beast.ftblib.lib.data.Universe;
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
import net.minecraft.util.text.TextComponentString;
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
            //우클
            if (hand == EnumHand.MAIN_HAND) {
                //포지플레이어 받아서 넣어둠
                ForgePlayer forgePlayer = Universe.get().getPlayer(playerIn.getGameProfile());
                if (getTileEntity(worldIn, pos).teamscore == null) {
                    playerIn.sendMessage(new TextComponentString("set team to : " + forgePlayer.team.getID()));
                    //팀 세팅
                    getTileEntity(worldIn, pos).setTeam(forgePlayer.team);
                } else {
                    //이미 팀 있음
                    playerIn.sendMessage(new TextComponentString("already have team" + forgePlayer.team.getID()));
                    playerIn.sendMessage(new TextComponentString("score:" + getTileEntity(worldIn, pos).getScore()));
                }
            }
            return true;
        } else return false;
    }

}
