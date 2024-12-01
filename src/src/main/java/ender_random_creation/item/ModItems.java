package ender_random_creation.item;

import ender_random_creation.Ender_random_creation_Mod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

	public static final String TEST1_TAB_STRING="creativetab.test1_tab";

    //获取注册表
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Ender_random_creation_Mod.Modid);
	
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Ender_random_creation_Mod.Modid);
	public static final RegistryObject<Item> EnderSword = REGISTRY.register("ender_sword", () -> new EnderSword());
	public static final RegistryObject<Item> EndPickaxe = REGISTRY.register("ender_pickaxe", () -> new EndPickaxe());
	public static final RegistryObject<Item> EnderCore = REGISTRY.register("ender_core", () -> new EnderCore());
	public static final RegistryObject<Item> EnderAmulet = REGISTRY.register("ender_amulet", () -> new EnderAmulet());
	public static final RegistryObject<Item> EnderWrapAroundShield = REGISTRY.register("ender_wrap_around_shield", () -> new EnderWrapAroundShield());
	
	public static final RegistryObject<CreativeModeTab>TEST1_TAB = CREATIVE_MODE_TABS.register("test1_tab",
            ()-> CreativeModeTab.builder()
            .icon(()->new ItemStack(EnderCore.get()))
            .title(Component.translatable(TEST1_TAB_STRING))
            .displayItems((pParameters, pOutput) -> {
                pOutput.accept(EnderSword.get());
                pOutput.accept(EndPickaxe.get());
                pOutput.accept(EnderCore.get());
                pOutput.accept(EnderAmulet.get());
                pOutput.accept(EnderWrapAroundShield.get());
            })
            .build());
	
}
