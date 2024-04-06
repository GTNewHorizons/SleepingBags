package ru.npo6ka.sleepingbag.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ru.npo6ka.sleepingbag.ExtendedPlayer;
import ru.npo6ka.sleepingbag.ItemsRegister;

public class BlockSleepingBag extends BlockBed {

    private IIcon[] iconEnd;
    private IIcon[] iconSide;
    private IIcon[] iconTop;
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
            final int side, final float subX, final float subY, final float subZ) {
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
                    (double) (x + 0.5),
                    (double) (y + 0.5),
                    (double) (z + 0.5),
                    5.0f,
                    true,
                    true);
            return true;
        }
        if (func_149976_c(i1)) { // isBedOccupied
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
                player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.occupied"));
                return true;
            }
            func_149979_a(world, x, y, z, false); // setBedOccupied
        }
        final EntityPlayer.EnumStatus enumstatus = player.sleepInBedAt(x, y, z);
        if (enumstatus == EntityPlayer.EnumStatus.OK) {
            func_149979_a(world, x, y, z, true); // setBedOccupied
            return true;
        }
        if (enumstatus == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW) {
            player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.noSleep"));
        } else if (enumstatus == EntityPlayer.EnumStatus.NOT_SAFE) {
            player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.notSafe"));
        }
        return true;
    }

    public Item getItemDropped(final int meta, final Random random, final int fortune) {
        return isBlockHeadOfBed(meta) ? Item.getItemById(0) : ItemsRegister.sleepingBagItem;
    }

    public Item getItem(final World worldIn, final int x, final int y, final int z) {
        return ItemsRegister.sleepingBagItem;
    }

    public IIcon getIcon(int side, final int meta) {
        if (side == 0) {
            side = 1;
        }
        final int k = getDirection(meta);
        final int l = Direction.bedDirection[k][side];
        final int i1 = isBlockHeadOfBed(meta) ? 1 : 0;
        return ((i1 != 1 || l != 2) && (i1 != 0 || l != 3))
                ? ((l != 5 && l != 4) ? this.iconTop[i1] : this.iconSide[i1])
                : this.iconEnd[i1];
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister reg) {
        this.iconTop = new IIcon[] { reg.registerIcon(this.getTextureName() + "_feet_top"),
                reg.registerIcon(this.getTextureName() + "_head_top") };
        this.iconEnd = new IIcon[] { reg.registerIcon(this.getTextureName() + "_feet_end"),
                reg.registerIcon(this.getTextureName() + "_head_end") };
        this.iconSide = new IIcon[] { reg.registerIcon(this.getTextureName() + "_feet_side"),
                reg.registerIcon(this.getTextureName() + "_head_side") };
    }

    public void setBlockBoundsBasedOnState(final IBlockAccess worldIn, final int x, final int y, final int z) {
        this.setBedBounds();
    }

    private void setBedBounds() {
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
