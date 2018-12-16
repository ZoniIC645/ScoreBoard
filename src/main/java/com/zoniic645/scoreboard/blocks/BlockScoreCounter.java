package com.zoniic645.scoreboard.blocks;

import com.feed_the_beast.ftblib.lib.data.ForgeTeam;
import com.feed_the_beast.ftblib.lib.data.Universe;
import com.zoniic645.scoreboard.tileentity.TileEntityScoreCounter;

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
            if (hand == EnumHand.MAIN_HAND) {
                ForgeTeam team = Universe.get().getPlayer(playerIn.getGameProfile()).team; //team을 이걸 클릭한 플레이어의 팀으로 설정
                if (getTileEntity(worldIn, pos).getTeam() == null) { //클릭한 타일엔티티가 팀스코어를 가지고 있는지 확인한다
                	getTileEntity(worldIn, pos).setTeam(team); //팀스코어 설정
                    playerIn.sendMessage(new TextComponentString("team set to : " + team.getUID())); //팀 ID출력
                } else {
                    playerIn.sendMessage(new TextComponentString("it alredy have team : " + getTileEntity(worldIn, pos).getTeam().getUID()));
                }
            }
            return true;
        } else {
            return false;
        }
    }

}
