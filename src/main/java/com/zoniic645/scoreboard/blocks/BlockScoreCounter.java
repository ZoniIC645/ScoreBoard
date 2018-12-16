package com.zoniic645.scoreboard.blocks;

import com.feed_the_beast.ftblib.lib.data.ForgeTeam;
import com.feed_the_beast.ftblib.lib.data.Universe;
import com.zoniic645.scoreboard.TeamScore;
import com.zoniic645.scoreboard.theoneprobecompatibility.TOPInfoProvider;
import com.zoniic645.scoreboard.tileentity.TileEntityScoreCounter;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BlockScoreCounter extends Block implements TOPInfoProvider {
    public BlockScoreCounter() {
        super(Material.ANVIL);
        setRegistryName("blockscorecounter");
        setUnlocalizedName("BlockScoreCounter");
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        TileEntity te = world.getTileEntity(data.getPos());
        if (te instanceof TileEntityScoreCounter) {
            // If we are sure that the entity there is correct we can proceed:
            TileEntityScoreCounter tileEntityScoreCounter = (TileEntityScoreCounter) te;
            // First add a horizontal line showing the clock item followed by current contents of the counter in the tile entity
            probeInfo.horizontal()
                    .item(new ItemStack(Items.CLOCK))
                    .text(TextFormatting.GREEN + "Counter: " + tileEntityScoreCounter.getScore());
        }
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
                if (team.getUID() == 0) {
                    playerIn.sendMessage(new TextComponentString("you haven't joined team yet"));
                } else if (getTileEntity(worldIn, pos).getTeam() == null) { //클릭한 타일엔티티가 팀스코어를 가지고 있는지 확인한다
                    getTileEntity(worldIn, pos).setTeam(team); //팀스코어 설정
                    playerIn.sendMessage(new TextComponentString("team set to : " + team.getUID())); //팀 ID출력
                } else {
                    playerIn.sendMessage(new TextComponentString("it alredy have team. score : " + getTileEntity(worldIn, pos).getScore()));
                }
            }
            return true;
        } else {
            return false;
        }
    }

}