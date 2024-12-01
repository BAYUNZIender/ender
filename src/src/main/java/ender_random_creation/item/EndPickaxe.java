package ender_random_creation.item;

import java.util.List;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EndPickaxe extends PickaxeItem {

	public EndPickaxe() {
		super(new Tier() {
			public int getUses() {
				return 0;
			}

			public float getSpeed() {
				return 0.8f;
			}

			public float getAttackDamageBonus() {
				return 1f;
			}

			public int getLevel() {
				return 5;
			}

			public int getEnchantmentValue() {
				return 0;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of();
			}
		}, 3, -3f, new Item.Properties().durability(2800).fireResistant());
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public void appendHoverText(ItemStack p_41421_, Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
		super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
		p_41423_.add(Component.translatable(I18n.get("information.ender_pickaxe")));
	}
	
	@SubscribeEvent
	public void clickEvent(LeftClickBlock e) {
		if (e.getItemStack().is(this)) {
			e.getItemStack().setDamageValue(e.getItemStack().getDamageValue() + 1);
			e.getLevel().setBlock(e.getPos(), Blocks.AIR.defaultBlockState(), 1);
		}
	}

}
