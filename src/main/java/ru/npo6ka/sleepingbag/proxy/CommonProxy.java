package ru.npo6ka.sleepingbag.proxy;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.FMLCommonHandler;
import ru.npo6ka.sleepingbag.EventHandler;
import ru.npo6ka.sleepingbag.ItemsRegister;

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
