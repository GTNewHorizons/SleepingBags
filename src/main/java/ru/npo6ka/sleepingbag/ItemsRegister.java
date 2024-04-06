package ru.npo6ka.sleepingbag;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import ru.npo6ka.sleepingbag.blocks.BlockSleepingBag;
import ru.npo6ka.sleepingbag.items.ItemSleepingBag;

public final class ItemsRegister {

    public static Item sleepingBagItem;
    public static BlockSleepingBag sleepingBagBlock;

    public static void init() {
        sleepingBagItem = new ItemSleepingBag().setUnlocalizedName("sleepingBag")
                .setCreativeTab(CreativeTabs.tabDecorations).setTextureName("sleepingbag:sleeping_bag")
                .setMaxStackSize(1);
        GameRegistry.registerItem(sleepingBagItem, "sleepingBag");

        if (!Loader.isModLoaded("dreamcraft")) {
            GameRegistry.addRecipe(
                    new ItemStack(ItemsRegister.sleepingBagItem),
                    "   ",
                    "###",
                    "ccc",
                    '#',
                    new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE),
                    'c',
                    new ItemStack(Blocks.carpet, 1, OreDictionary.WILDCARD_VALUE));
        }

        sleepingBagBlock = (BlockSleepingBag) new BlockSleepingBag().setHardness(0.2f).setBlockName("sleepingBagBlock")
                .setBlockTextureName("sleepingbag:sleeping_bag");
        GameRegistry.registerBlock(sleepingBagBlock, "sleepingBagBlock");
    }
}
