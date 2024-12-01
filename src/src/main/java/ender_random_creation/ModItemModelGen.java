package ender_random_creation;

import ender_random_creation.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelGen extends ItemModelProvider {
    public static final String GENERATED="item/generated";
    
    public ModItemModelGen(PackOutput output, ExistingFileHelper existingFileHelper){
    	super(output, Ender_random_creation_Mod.Modid,existingFileHelper);
    }

    @Override
    protected void registerModels() {
    	System.out.println("be register");
    	igm(ModItems.EnderAmulet);
    	igm(ModItems.EnderCore);
    	igm(ModItems.EnderSword);
    	igm(ModItems.EndPickaxe);
    }
    
    public void igm(RegistryObject<Item> ro) {
        itemGenerateModel(ro.get(),resourceItem(itemName(ro.get())));
    }
    
    public void itemGenerateModel(Item item, ResourceLocation texture){
        withExistingParent(itemName(item),GENERATED).texture("layer0",texture);
    }
    
    public String itemName(Item item){
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }
    
    public ResourceLocation resourceItem(String path){
        return new ResourceLocation(Ender_random_creation_Mod.Modid,"item/"+path);
    }
}
