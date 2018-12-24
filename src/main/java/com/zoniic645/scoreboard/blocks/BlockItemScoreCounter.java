package com.zoniic645.scoreboard.blocks;

import com.feed_the_beast.ftblib.lib.data.ForgeTeam;
import com.feed_the_beast.ftblib.lib.data.Universe;
import com.zoniic645.scoreboard.compat.top.TOPInfoProvider;
import com.zoniic645.scoreboard.tileentity.TileEntityItemScoreCounter;
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

public class BlockItemScoreCounter extends Block implements TOPInfoProvider {
    public BlockItemScoreCounter() {
        super(Material.ANVIL);
        setRegistryName("blockitemscorecounter");
        setUnlocalizedName("BlockItemScoreCounter");
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        TileEntity te = world.getTileEntity(data.getPos());
        if (te instanceof TileEntityItemScoreCounter) {
            // If we are sure that the entity there is correct we can proceed:
            TileEntityItemScoreCounter tileEntityItemScoreCounter = (TileEntityItemScoreCounter) te;
            // First add a horizontal line showing the clock item followed by current contents of the counter in the tile entity
            if(tileEntityItemScoreCounter.getTeam() != null) {
                probeInfo.horizontal()
                        .text(TextFormatting.AQUA + "Team: " + tileEntityItemScoreCounter.getTeam().getTitle().getFormattedText());
                probeInfo.horizontal()
                        .item(new ItemStack(Items.CLOCK))
                        .text(TextFormatting.GREEN + "Counter: " + tileEntityItemScoreCounter.getScore());
            }
            else {
                probeInfo.horizontal()
                        .text(TextFormatting.AQUA + "Don't have team yet");
            }
        }
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityItemScoreCounter();
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    //타일엔티티내놔
    private TileEntityItemScoreCounter getTileEntity(World world, BlockPos pos) {
        return (TileEntityItemScoreCounter) world.getTileEntity(pos);
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
            return false;
        } else {
            return false;
        }
    }

}
