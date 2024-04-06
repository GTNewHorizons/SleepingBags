package ru.npo6ka.sleepingbag.proxy;

import net.minecraftforge.common.*;

import cpw.mods.fml.common.*;
import ru.npo6ka.sleepingbag.*;

public class CommonProxy {

    public void preInit() {
        ItemsRegister.init();
    }

    public void init() {
        final EventHandler ev = new EventHandler();
        MinecraftForge.EVENT_BUS.register(ev);
        FMLCommonHandler.instance().bus().register(ev);
    }

    public void postInit() {}
}
