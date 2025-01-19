package ru.npo6ka.sleepingbag.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import ru.npo6ka.sleepingbag.ExtendedPlayer;
import ru.npo6ka.sleepingbag.ItemsRegister;
import ru.npo6ka.sleepingbag.blocks.BlockSleepingBag;

public class ItemSleepingBag extends Item {

    public static final String TAG_POSITION = "player_pos_sleaping_bag";

    /**
     * bedDirections
     */
    public static final int[][] field_149981_a;

    public ItemSleepingBag() {
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        this.setSleepingBag(
                itemstack,
                entityplayer,
                world,
                (int) entityplayer.posX,
                (int) entityplayer.posY,
                (int) entityplayer.posZ);
        return itemstack;
    }

    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, final int x,
            int y, final int z, final int side, final float px, final float py, final float pz) {
        ++y;
        return side == 1 && this.setSleepingBag(itemstack, entityplayer, world, x, y, z);
    }

    public boolean setSleepingBag(final ItemStack itemstack, final EntityPlayer entityplayer, final World world,
            final int x, final int y, final int z) {
        if (world.isRemote) {
            return true;
        }
        final int side = 1;
        if (!this.canPlayerSetBad(itemstack, entityplayer, world, side, x, y, z)) {
            return false;
        }
        if (!World.doesBlockHaveSolidTopSurface(world, x, y - 1, z)) {
            return false;
        }
        Integer offsetX = 0;
        Integer offsetZ = 0;
        final int dir = MathHelper.floor_double(entityplayer.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        if (dir == 0) {
            offsetZ = -1;
        } else if (dir == 1) {
            offsetX = 1;
        } else if (dir == 2) {
            offsetZ = 1;
        } else if (dir == 3) {
            offsetX = -1;
        }
        if (this.canPlayerSetBad(itemstack, entityplayer, world, side, x + offsetX, y, z + offsetZ)) {
            return this.setBlockSleepingBag(itemstack, entityplayer, world, side, x, y, z, dir, offsetX, offsetZ);
        }
        offsetX *= -1;
        offsetZ *= -1;
        if (this.canPlayerSetBad(itemstack, entityplayer, world, side, x + offsetX, y, z + offsetZ)) {
            return this.setBlockSleepingBag(itemstack, entityplayer, world, side, x, y, z, dir, offsetX, offsetZ);
        }
        if (offsetX == 0) {
            offsetX = 1;
            offsetZ = 0;
        } else {
            offsetZ = 1;
            offsetX = 0;
        }
        if (this.canPlayerSetBad(itemstack, entityplayer, world, side, x + offsetX, y, z + offsetZ)) {
            return this.setBlockSleepingBag(itemstack, entityplayer, world, side, x, y, z, dir, offsetX, offsetZ);
        }
        offsetX *= -1;
        offsetZ *= -1;
        return this.canPlayerSetBad(itemstack, entityplayer, world, side, x + offsetX, y, z + offsetZ)
                && this.setBlockSleepingBag(itemstack, entityplayer, world, side, x, y, z, dir, offsetX, offsetZ);
    }

    public boolean canPlayerSetBad(final ItemStack itemstack, final EntityPlayer entityplayer, final World world,
            final int side, final int x, final int y, final int z) {
        return entityplayer.canPlayerEdit(x, y, z, side, itemstack) && world.isAirBlock(x, y, z);
    }

    public boolean canPlayerSleep(final World world, final EntityPlayer entityplayer, int x, final int y, int z) {
        if (entityplayer.isPlayerSleeping() || !entityplayer.isEntityAlive()
                || !entityplayer.worldObj.provider.isSurfaceWorld()) {
            return false;
        }
        if (entityplayer.worldObj.isDaytime()) {
            entityplayer.addChatComponentMessage(new ChatComponentTranslation("tile.bed.noSleep"));
            return false;
        }
        final int i1 = world.getBlockMetadata(x, y, z);
        final Block bed = world.getBlock(x, y, z);
        if (!(bed instanceof BlockSleepingBag)) {
            return false;
        }
        if (!BlockSleepingBag.isBlockHeadOfBed(i1)) {
            final int j1 = BlockSleepingBag.getDirection(i1);
            x += ItemSleepingBag.field_149981_a[j1][0];
            z += ItemSleepingBag.field_149981_a[j1][1];
        }
        if (Math.abs(entityplayer.posX - x) > 3.0 || Math.abs(entityplayer.posY - y) > 2.0
                || Math.abs(entityplayer.posZ - z) > 3.0) {
            return false;
        }
        final double d0 = 9.0;
        final double d2 = 5.0;
        final List<EntityMob> list = entityplayer.worldObj.getEntitiesWithinAABB(
                EntityMob.class,
                AxisAlignedBB.getBoundingBox(x - d0, y - d2, z - d0, x + d0, y + d2, z + d0));
        if (!list.isEmpty()) {
            entityplayer.addChatComponentMessage(new ChatComponentTranslation("tile.bed.notSafe"));
            return false;
        }
        return true;
    }

    public boolean setBlockSleepingBag(final ItemStack itemstack, final EntityPlayer entityplayer, final World world,
            final int side, final int x, final int y, final int z, int dir, final Integer offsetX,
            final Integer offsetZ) {
        final BlockSleepingBag blockbed = ItemsRegister.sleepingBagBlock;
        if (offsetZ == 1) {
            dir = 0;
        } else if (offsetX == -1) {
            dir = 1;
        } else if (offsetZ == -1) {
            dir = 2;
        } else if (offsetX == 1) {
            dir = 3;
        }
        world.setBlock(x, y, z, blockbed, dir, 3);
        if (world.getBlock(x, y, z) == blockbed) {
            world.setBlock(x + offsetX, y, z + offsetZ, blockbed, dir + 8, 3);
        }
        if (!this.canPlayerSleep(world, entityplayer, x, y, z)) {
            if (world.getBlock(x, y, z) == blockbed) {
                world.setBlockToAir(x, y, z);
            }
            if (world.getBlock(x + offsetX, y, z + offsetZ) == blockbed) {
                world.setBlockToAir(x + offsetX, y, z + offsetZ);
            }
            return false;
        }
        --itemstack.stackSize;
        storeOriginalPosition(entityplayer, x, y, z);
        return true;
    }

    private static void storeOriginalPosition(final EntityPlayer entity, final int x, final int y, final int z) {
        if (entity != null) {
            final ExtendedPlayer ep = ExtendedPlayer.get(entity);
            ep.setSleepingFlag(1);
            ep.setBedCoord(new ChunkCoordinates(x, y, z));
            ep.setLastCoord(
                    new ChunkCoordinates(
                            (int) Math.floor(entity.posX),
                            (int) entity.posY,
                            (int) Math.floor(entity.posZ)));
            ep.setlastSpawnCoord(entity.getBedLocation(entity.worldObj.provider.dimensionId));
        }
    }

    static {
        field_149981_a = new int[][] { { 0, 1 }, { -1, 0 }, { 0, -1 }, { 1, 0 } };
    }
}
