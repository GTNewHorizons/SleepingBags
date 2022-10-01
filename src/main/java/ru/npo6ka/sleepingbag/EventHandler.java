package ru.npo6ka.sleepingbag;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.eventhandler.*;
import cpw.mods.fml.common.gameevent.*;
import cpw.mods.fml.relauncher.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.*;
import org.lwjgl.opengl.*;

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
        if (e.entity instanceof EntityPlayer) {
            ExtendedPlayer.register((EntityPlayer) e.entity);
        }
    }

    @SubscribeEvent
    public void onTick(final TickEvent.ServerTickEvent e) {
        if (e.phase == TickEvent.Phase.END) {
            final World world =
                    FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();
            final List<EntityPlayer> pl_list = world.playerEntities;
            for (final EntityPlayer player : pl_list) {
                final ExtendedPlayer ep = ExtendedPlayer.get(player);
                if (ep.isSleeping() != 0) {
                    if (ep.isSleeping() == 2) {
                        final ChunkCoordinates badCoord = ep.getBedCoord();
                        final Block bad = world.getBlock(badCoord.posX, badCoord.posY, badCoord.posZ);
                        bad.onBlockActivated(
                                world, badCoord.posX, badCoord.posY, badCoord.posZ, player, 1, 0.5f, 0.25f, 0.5f);
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
