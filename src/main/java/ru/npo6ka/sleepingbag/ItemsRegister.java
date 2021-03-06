package ru.npo6ka.sleepingbag;

import ru.npo6ka.sleepingbag.blocks.*;
import ru.npo6ka.sleepingbag.items.*;
import net.minecraft.creativetab.*;
import cpw.mods.fml.common.registry.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public final class ItemsRegister
{
    public static Item sleepingBagItem;
    public static BlockSleepingBag sleepingBagBlock;
    
    public static void init() {
        GameRegistry.registerItem(ItemsRegister.sleepingBagItem = new ItemSleepingBag().setUnlocalizedName("sleepingBag").setCreativeTab(CreativeTabs.tabDecorations).setTextureName("sleepingbag:sleeping_bag").setMaxStackSize(1), "sleepingBag");
        GameRegistry.registerBlock((Block)(ItemsRegister.sleepingBagBlock = (BlockSleepingBag)new BlockSleepingBag().setHardness(0.2f).setBlockName("sleepingBagBlock").setBlockTextureName("sleepingbag:sleeping_bag")), "sleepingBagBlock");
    }
}
