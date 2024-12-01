package ender_random_creation;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Ender_random_creation_Mod.Modid,bus=Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

	@SubscribeEvent
    public static void gatherData(GatherDataEvent event){
		DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        
        generator.addProvider(true, new ModRecipes(output));
        generator.addProvider(event.includeClient(), new ModItemModelGen(output, event.getExistingFileHelper()));
        //throw new RuntimeException();
	}
	
}
