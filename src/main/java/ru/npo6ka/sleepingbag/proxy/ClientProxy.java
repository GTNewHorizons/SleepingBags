package ru.npo6ka.sleepingbag.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import ru.npo6ka.sleepingbag.BlockRenderer;
import ru.npo6ka.sleepingbag.ItemsRegister;

public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
        super.init();
        final BlockRenderer blockRenderer = new BlockRenderer();
        RenderingRegistry.registerBlockHandler(blockRenderer);
        ItemsRegister.sleepingBagBlock.setRenderId(blockRenderer.getRenderId());
    }
}
