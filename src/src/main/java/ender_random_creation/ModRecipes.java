package ender_random_creation;

import java.util.function.Consumer;

import ender_random_creation.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

public class ModRecipes extends RecipeProvider{

	public ModRecipes(PackOutput p_248933_) {
		super(p_248933_);
	}

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> p_251297_) {
    	System.out.println("be build");
    	
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EnderCore.get())
        .pattern("AAA")
        .pattern("ABA")
        .pattern("AAA")
        .define('A', Items.GOLD_INGOT)
        .define('B', Items.ENDER_PEARL)
        .group(Ender_random_creation_Mod.Modid)
        .save(p_251297_);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EnderAmulet.get())
        .pattern("AAA")
        .pattern("ABA")
        .pattern("AAA")
        .define('A', Items.IRON_INGOT)
        .define('B', ModItems.EnderCore.get())
        .group(Ender_random_creation_Mod.Modid)
        .save(p_251297_);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EndPickaxe.get())
        .pattern("ABA")
        .pattern(" C ")
        .pattern(" C ")
        .define('A', Items.ENDER_PEARL)
        .define('B', ModItems.EnderCore.get())
        .define('C', Items.STICK)
        .group(Ender_random_creation_Mod.Modid)
        .save(p_251297_);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EnderSword.get())
        .pattern(" A ")
        .pattern(" A ")
        .pattern(" B ")
        .define('A', Items.ENDER_PEARL)
        .define('B', ModItems.EnderCore.get())
        .group(Ender_random_creation_Mod.Modid)
        .save(p_251297_);
	}

}
