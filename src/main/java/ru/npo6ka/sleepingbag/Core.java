package ru.npo6ka.sleepingbag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import ru.npo6ka.sleepingbag.proxy.CommonProxy;

@Mod(modid = Core.MODID, version = Core.VERSION)
public class Core {

    public static final String MODID = "sleepingbag";
    public static final String VERSION = Tags.VERSION;
    public static final Logger LOGGER;

    @Mod.Instance
    public static Core INSTANCE;

    @SidedProxy(
            clientSide = "ru.npo6ka.sleepingbag.proxy.ClientProxy",
            serverSide = "ru.npo6ka.sleepingbag.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent e) {
        Core.proxy.preInit();
    }

    @Mod.EventHandler
    public void init(final FMLInitializationEvent e) {
        Core.proxy.init();
    }

    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent e) {
        Core.proxy.postInit();
    }

    static {
        LOGGER = LogManager.getLogger(Core.MODID);
    }
}
