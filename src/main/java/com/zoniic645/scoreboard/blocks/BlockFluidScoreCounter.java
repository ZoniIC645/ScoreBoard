package com.zoniic645.scoreboard.blocks;

import com.feed_the_beast.ftblib.lib.data.ForgeTeam;
import com.feed_the_beast.ftblib.lib.data.Universe;
import com.zoniic645.scoreboard.compat.top.TOPInfoProvider;
import com.zoniic645.scoreboard.tileentity.TileEntityFluidScoreCounter;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class BlockFluidScoreCounter extends Block implements TOPInfoProvider {
    public BlockFluidScoreCounter() {
        super(Material.ANVIL);
        setRegistryName("blockfluidscorecounter");
        setUnlocalizedName("BlockFluidScoreCounter");
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        TileEntity te = world.getTileEntity(data.getPos());
        if (te instanceof TileEntityFluidScoreCounter) {
            // If we are sure that the entity there is correct we can proceed:
            TileEntityFluidScoreCounter tileEntityFluidScoreCounter = (TileEntityFluidScoreCounter) te;
            // First add a horizontal line showing the clock item followed by current contents of the counter in the tile entity
            if (tileEntityFluidScoreCounter.getTeam() != null) {
                probeInfo.horizontal()
                        .item(new ItemStack(Items.CLOCK))
                        .text(TextFormatting.GREEN + "Counter: " + tileEntityFluidScoreCounter.getScore());
                probeInfo.horizontal()
                        .text(TextFormatting.AQUA + "Team: " + tileEntityFluidScoreCounter.getTeam().getTitle().getFormattedText());
            } else {
                probeInfo.horizontal()
                        .text(TextFormatting.AQUA + "Don't have team yet");
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
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
            if (playerIn.getHeldItemMainhand() != null) {
                ItemStack itemStack = playerIn.getHeldItemMainhand();
                FluidStack fluidStack = FluidUtil.getFluidContained(itemStack);
                if (fluidStack != null) {
                    FluidUtil.getFluidHandler(itemStack).drain(fluidStack.amount, true);
                    getTileEntity(worldIn, pos).fill(fluidStack, true);
                    playerIn.setHeldItem(hand, FluidUtil.getFluidHandler(itemStack).getContainer());
                }
            }
            return false;
        } else
            return false;
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
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityFluidScoreCounter();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    //타일엔티티내놔
    private TileEntityFluidScoreCounter getTileEntity(World world, BlockPos pos) {
        return (TileEntityFluidScoreCounter) world.getTileEntity(pos);
    }
}
