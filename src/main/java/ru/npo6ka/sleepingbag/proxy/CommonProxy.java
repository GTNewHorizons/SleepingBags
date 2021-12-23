package ru.npo6ka.sleepingbag.proxy;

import ru.npo6ka.sleepingbag.*;
import net.minecraftforge.common.*;
import cpw.mods.fml.common.*;

public class CommonProxy
{
    public void preInit() {
        ItemsRegister.init();
    }
    
    public void init() {
        final EventHandler ev = new EventHandler();
        MinecraftForge.EVENT_BUS.register((Object)ev);
        FMLCommonHandler.instance().bus().register((Object)ev);
    }
    
    public void postInit() {
    }
}
