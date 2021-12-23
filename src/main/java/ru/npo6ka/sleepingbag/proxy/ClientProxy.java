package ru.npo6ka.sleepingbag.proxy;

import cpw.mods.fml.client.registry.*;
import ru.npo6ka.sleepingbag.*;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit() {
        super.preInit();
    }
    
    @Override
    public void init() {
        super.init();
        final BlockRenderer blockRenderer = new BlockRenderer();
        RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)blockRenderer);
        ItemsRegister.sleepingBagBlock.setRenderId(blockRenderer.getRenderId());
    }
    
    @Override
    public void postInit() {
        super.postInit();
    }
}
