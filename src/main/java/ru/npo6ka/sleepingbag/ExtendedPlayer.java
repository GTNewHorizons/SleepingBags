package ru.npo6ka.sleepingbag;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedPlayer implements IExtendedEntityProperties {

    public static final String EXT_PROP_NAME = "ExtendedPlayer";
    private ChunkCoordinates lastCoord;
    private ChunkCoordinates lastSpawnCoord;
    private boolean isWakeUp;
    private int isSleeping;
    private ChunkCoordinates bedCoord;

    public ExtendedPlayer(final EntityPlayer player) {
        this.isWakeUp = false;
    }

    public void saveNBTData(final NBTTagCompound compound) {}

    public void loadNBTData(final NBTTagCompound compound) {}

    public void init(final Entity entity, final World world) {}

    public static final void register(final EntityPlayer player) {
        player.registerExtendedProperties("ExtendedPlayer", (IExtendedEntityProperties) new ExtendedPlayer(player));
    }

    public static final ExtendedPlayer get(final EntityPlayer player) {
        return (ExtendedPlayer) player.getExtendedProperties("ExtendedPlayer");
    }

    public ChunkCoordinates getLastCoord() {
        return this.lastCoord;
    }

    public void setLastCoord(final ChunkCoordinates coord) {
        this.lastCoord = coord;
    }

    public ChunkCoordinates getlastSpawnCoord() {
        return this.lastSpawnCoord;
    }

    public void setlastSpawnCoord(final ChunkCoordinates coord) {
        this.lastSpawnCoord = coord;
    }

    public void setWakeUpFlag(final boolean val) {
        this.isWakeUp = val;
    }

    public boolean isWakeUp() {
        return this.isWakeUp;
    }

    public void setSleepingFlag(final int val) {
        this.isSleeping = val;
    }

    public int isSleeping() {
        return this.isSleeping;
    }

    public ChunkCoordinates getBedCoord() {
        return this.bedCoord;
    }

    public void setBedCoord(final ChunkCoordinates val) {
        this.bedCoord = val;
    }
}
