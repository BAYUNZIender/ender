package ender_random_creation;

import ender_random_creation.client.MModelLoader;
import ender_random_creation.item.ModItems;
import ender_random_creation.mst.MSTLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Ender_random_creation_Mod.Modid)
public class Ender_random_creation_Mod {

	public static final String Modid = "ender_random_creation";
	
	public Ender_random_creation_Mod() {
				
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new MModelLoader());
		
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		ModItems.REGISTRY.register(bus);
		ModItems.CREATIVE_MODE_TABS.register(bus);
	}
	
}
