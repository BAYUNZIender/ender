package ender_random_creation.item;

import java.util.List;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EnderAmulet extends Item {

	public EnderAmulet() {
		super(new Item.Properties().durability(1800));
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void knockEvent(LivingKnockBackEvent evt) {
		if (evt.getEntity() instanceof Player) {
			Player player = (Player) evt.getEntity();
			NonNullList<ItemStack> stacks = player.inventoryMenu.getItems();
			for (ItemStack stack : stacks) {
				if (!stack.isEmpty() && stack.is(this) && stack.getOrCreateTag().getBoolean("useing")) {
					evt.setCanceled(true);
					break;
				}
			}
		}
	}
	
	@SubscribeEvent
	public void hurtEvent(LivingHurtEvent evt) {
		if (evt.getEntity() instanceof Player) {
			Player player = (Player) evt.getEntity();
			NonNullList<ItemStack> stacks = player.inventoryMenu.getItems();
			for (ItemStack stack : stacks) {
				if (!stack.isEmpty() && stack.is(this) && stack.getOrCreateTag().getBoolean("useing")) {
					evt.setAmount((float) (evt.getAmount() * 0.2));
					break;
				}
			}
		}
	}
	
	@Override
	public void inventoryTick(ItemStack stack, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
		super.inventoryTick(stack, p_41405_, p_41406_, p_41407_, p_41408_);
		if (p_41406_ instanceof Player) {
			Player player = (Player) p_41406_;
			if (player.hurtTime == 1) {
				player.playSound(SoundEvents.ITEM_BREAK, 1.0F, 1.0F);
			}
		}
		if (stack.getOrCreateTag().getBoolean("useing")) {
			stack.setDamageValue(stack.getDamageValue() + 1);
		}
		if (stack.getDamageValue() > stack.getMaxDamage()) {
			stack.grow(-1);
		}
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
		ItemStack stack = p_41433_.getItemInHand(p_41434_);
		stack.getOrCreateTag().putBoolean("useing", !stack.getOrCreateTag().getBoolean("useing"));
		return InteractionResultHolder.success(p_41433_.getItemInHand(p_41434_));
	}
	
	@Override
	public void appendHoverText(ItemStack p_41421_, Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
		super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
		p_41423_.add(Component.translatable(I18n.get("information.ender_amulet")));
	}

}
