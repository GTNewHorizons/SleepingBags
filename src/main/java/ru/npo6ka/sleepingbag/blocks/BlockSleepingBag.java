package ru.npo6ka.sleepingbag.blocks;

import java.util.*;

import net.minecraft.block.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;

import cpw.mods.fml.common.*;
import cpw.mods.fml.relauncher.*;
import ru.npo6ka.sleepingbag.*;

public class BlockSleepingBag extends BlockBed {

    @SideOnly(Side.CLIENT)
    private IIcon[] field_149980_b;

    @SideOnly(Side.CLIENT)
    private IIcon[] field_149982_M;

    @SideOnly(Side.CLIENT)
    private IIcon[] field_149983_N;

    private int renderType;

    public BlockSleepingBag() {
        this.renderType = 0;
    }

    public int getRenderType() {
        return this.renderType;
    }

    public void setRenderId(final int type) {
        this.renderType = type;
    }

    public ChunkCoordinates getBedSpawnPosition(final IBlockAccess world, final int x, final int y, final int z,
            final EntityPlayer player) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER && world instanceof World) {
            return ExtendedPlayer.get(player).getLastCoord();
        }
        return null;
    }

    public boolean onBlockActivated(final World world, int x, final int y, int z, final EntityPlayer player,
            final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        if (world.isRemote) {
            return true;
        }
        int i1 = world.getBlockMetadata(x, y, z);
        if (!isBlockHeadOfBed(i1)) {
            final int j1 = getDirection(i1);
            x += BlockSleepingBag.field_149981_a[j1][0];
            z += BlockSleepingBag.field_149981_a[j1][1];
            if (world.getBlock(x, y, z) != this) {
                return true;
            }
            i1 = world.getBlockMetadata(x, y, z);
        }
        if (!world.provider.canRespawnHere() || world.getBiomeGenForCoords(x, z) == BiomeGenBase.hell) {
            double d2 = x + 0.5;
            double d3 = y + 0.5;
            double d4 = z + 0.5;
            world.setBlockToAir(x, y, z);
            final int k1 = getDirection(i1);
            x += BlockSleepingBag.field_149981_a[k1][0];
            z += BlockSleepingBag.field_149981_a[k1][1];
            if (world.getBlock(x, y, z) == this) {
                world.setBlockToAir(x, y, z);
                d2 = (d2 + x + 0.5) / 2.0;
                d3 = (d3 + y + 0.5) / 2.0;
                d4 = (d4 + z + 0.5) / 2.0;
            }
            world.newExplosion(
                    (Entity) null,
                    (double) (x + 0.5f),
                    (double) (y + 0.5f),
                    (double) (z + 0.5f),
                    5.0f,
                    true,
                    true);
            return true;
        }
        if (func_149976_c(i1)) {
            EntityPlayer entityplayer1 = null;
            final List<EntityPlayer> pl_list = world.playerEntities;
            for (final EntityPlayer entityplayer2 : pl_list) {
                if (entityplayer2.isPlayerSleeping()) {
                    final ChunkCoordinates chunkcoordinates = entityplayer2.playerLocation;
                    if (chunkcoordinates.posX != x || chunkcoordinates.posY != y || chunkcoordinates.posZ != z) {
                        continue;
                    }
                    entityplayer1 = entityplayer2;
                }
            }
            if (entityplayer1 != null) {
                player.addChatComponentMessage(
                        (IChatComponent) new ChatComponentTranslation("tile.bed.occupied", new Object[0]));
                return true;
            }
            func_149979_a(world, x, y, z, false);
        }
        final EntityPlayer.EnumStatus enumstatus = player.sleepInBedAt(x, y, z);
        if (enumstatus == EntityPlayer.EnumStatus.OK) {
            func_149979_a(world, x, y, z, true);
            return true;
        }
        if (enumstatus == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW) {
            player.addChatComponentMessage(
                    (IChatComponent) new ChatComponentTranslation("tile.bed.noSleep", new Object[0]));
        } else if (enumstatus == EntityPlayer.EnumStatus.NOT_SAFE) {
            player.addChatComponentMessage(
                    (IChatComponent) new ChatComponentTranslation("tile.bed.notSafe", new Object[0]));
        }
        return true;
    }

    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return isBlockHeadOfBed(p_149650_1_) ? Item.getItemById(0) : ItemsRegister.sleepingBagItem;
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return ItemsRegister.sleepingBagItem;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, final int p_149691_2_) {
        if (p_149691_1_ == 0) {
            p_149691_1_ = 1;
        }
        final int k = getDirection(p_149691_2_);
        final int l = Direction.bedDirection[k][p_149691_1_];
        final int i1 = isBlockHeadOfBed(p_149691_2_) ? 1 : 0;
        return ((i1 != 1 || l != 2) && (i1 != 0 || l != 3))
                ? ((l != 5 && l != 4) ? this.field_149983_N[i1] : this.field_149982_M[i1])
                : this.field_149980_b[i1];
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.field_149983_N = new IIcon[] { p_149651_1_.registerIcon(this.getTextureName() + "_feet_top"),
                p_149651_1_.registerIcon(this.getTextureName() + "_head_top") };
        this.field_149980_b = new IIcon[] { p_149651_1_.registerIcon(this.getTextureName() + "_feet_end"),
                p_149651_1_.registerIcon(this.getTextureName() + "_head_end") };
        this.field_149982_M = new IIcon[] { p_149651_1_.registerIcon(this.getTextureName() + "_feet_side"),
                p_149651_1_.registerIcon(this.getTextureName() + "_head_side") };
    }

    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_,
            final int p_149719_4_) {
        this.func_149978_e();
    }

    private void func_149978_e() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.25f, 1.0f);
    }

    public boolean isBed(final IBlockAccess world, final int x, final int y, final int z,
            final EntityLivingBase player) {
        return this == ItemsRegister.sleepingBagBlock;
    }

    public void setBedOccupied(final IBlockAccess world, final int x, final int y, final int z,
            final EntityPlayer player, final boolean occupied) {
        final Block bad = world.getBlock(x, y, z);
        if (bad == ItemsRegister.sleepingBagBlock) {
            ExtendedPlayer.get(player).setWakeUpFlag(true);
            final int dir = this.getBedDirection(world, x, y, z);
            int xOffset = x;
            int zOffset = z;
            switch (dir) {
                case 0: {
                    --zOffset;
                    break;
                }
                case 1: {
                    ++xOffset;
                    break;
                }
                case 2: {
                    ++zOffset;
                    break;
                }
                case 3: {
                    --xOffset;
                    break;
                }
            }
            if (world.getBlock(xOffset, y, zOffset) == ItemsRegister.sleepingBagBlock) {
                int i = player.inventory.currentItem;
                if (i < 0 || i >= 36) {
                    return;
                }
                if (player.inventory.mainInventory[i] != null) {
                    for (i = 9; i < 35 && player.inventory.mainInventory[i] != null; ++i) {}
                }
                if (player.capabilities.isCreativeMode && world instanceof World) {
                    ((World) world).setBlockToAir(xOffset, y, zOffset);
                    ((World) world).setBlockToAir(x, y, z);
                    return;
                }
                if (player.inventory.mainInventory[i] == null && world instanceof World) {
                    ((World) world).setBlockToAir(xOffset, y, zOffset);
                    ((World) world).setBlockToAir(x, y, z);
                    player.inventory.mainInventory[i] = new ItemStack(ItemsRegister.sleepingBagItem);
                } else {
                    bad.removedByPlayer((World) world, player, x, y, z, false);
                }
            }
        }
    }
}
