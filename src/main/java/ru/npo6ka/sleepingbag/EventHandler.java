package ru.npo6ka.sleepingbag;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.EntityEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EventHandler {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderPlayer(final RenderPlayerEvent.Pre e) {
        final EntityPlayer player = e.entityPlayer;
        if (player.isPlayerSleeping()) {
            GL11.glTranslated(0.0, -0.3, 0.0);
        }
    }

    @SubscribeEvent
    public void onEntityConstruct(final EntityEvent.EntityConstructing e) {
        if (e.entity instanceof EntityPlayer player) {
            ExtendedPlayer.register(player);
        }
    }

    @SubscribeEvent
    public void onTick(final TickEvent.ServerTickEvent e) {
        if (e.phase == TickEvent.Phase.END) {
            final World world = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();
            final List<EntityPlayer> pl_list = world.playerEntities;
            for (final EntityPlayer player : pl_list) {
                final ExtendedPlayer ep = ExtendedPlayer.get(player);
                if (ep.isSleeping() != 0) {
                    if (ep.isSleeping() == 2) {
                        final ChunkCoordinates badCoord = ep.getBedCoord();
                        final Block bad = world.getBlock(badCoord.posX, badCoord.posY, badCoord.posZ);
                        bad.onBlockActivated(
                                world,
                                badCoord.posX,
                                badCoord.posY,
                                badCoord.posZ,
                                player,
                                1,
                                0.5f,
                                0.25f,
                                0.5f);
                        ep.setSleepingFlag(0);
                    } else {
                        ep.setSleepingFlag(2);
                    }
                }
                if (ExtendedPlayer.get(player).isWakeUp()) {
                    final ChunkCoordinates coords = ExtendedPlayer.get(player).getlastSpawnCoord();
                    player.setSpawnChunk(coords, false, player.worldObj.provider.dimensionId);
                    ExtendedPlayer.get(player).setWakeUpFlag(false);
                }
            }
        }
    }
}
