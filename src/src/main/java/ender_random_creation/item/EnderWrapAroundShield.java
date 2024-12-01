package ender_random_creation.item;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EnderWrapAroundShield extends Item {

	public EnderWrapAroundShield() {
		super(new Properties().durability(-1));
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void setDamage(ItemStack stack, int damage) {}
	
	@SubscribeEvent
	public void livingKnockBack(LivingKnockBackEvent evt) {
		LivingEntity entity = evt.getEntity();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			
			if (player.getPersistentData().getBoolean("isSave")) {
				evt.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void livingAttack(LivingAttackEvent evt) {
		LivingEntity entity = evt.getEntity();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (player.getPersistentData().getBoolean("isSave")) {
				if (evt.getSource().getEntity() != null && evt.getSource().getEntity() instanceof LivingEntity) {
					((LivingEntity) evt.getSource().getEntity()).hurt(evt.getSource().getEntity().damageSources().magic(), evt.getAmount() * 2);
				}
				evt.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void livingDeath(LivingDeathEvent evt) {
		LivingEntity entity = evt.getEntity();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (player.getPersistentData().getBoolean("isSave")) {
				evt.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void livingTick(LivingTickEvent evt) {
		LivingEntity entity = evt.getEntity();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			
			boolean has = false;
			
			NonNullList<ItemStack> stacks = player.inventoryMenu.getItems();
			for (ItemStack stack : stacks) {
				if (!stack.isEmpty() && stack.is(this) && stack.getOrCreateTag().getBoolean("useing")) {
					player.getPersistentData().putBoolean("isSave", true);
					has = true;
					break;
				}
			}

			if (!has) player.getPersistentData().putBoolean("isSave", false);
			
			if (player.getPersistentData().getBoolean("isSave")) {
				entity.setHealth(player.getPersistentData().getFloat("health"));
			}
		}
	}
	
	@SubscribeEvent
	public void clientTick(ClientTickEvent evt) {}
	
	@SubscribeEvent
	public void guiOpen(ScreenEvent.Opening evt) {
		Minecraft mc = Minecraft.getInstance();
		
		Player player = (Player) mc.player;
		
		if (player == null) return;
		
		if (player.getPersistentData().getBoolean("isSave")) {
			if (evt.getNewScreen() instanceof DeathScreen) {
				//evt.setCanceled(true);
			}
		}
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
		ItemStack stack = p_41433_.getItemInHand(p_41434_);
		stack.getOrCreateTag().putBoolean("useing", !stack.getOrCreateTag().getBoolean("useing"));
		p_41433_.getPersistentData().putFloat("health", p_41433_.getHealth());
		return InteractionResultHolder.success(p_41433_.getItemInHand(p_41434_));
	}
	
	@Override
	public void appendHoverText(ItemStack p_41421_, Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
		super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
		p_41423_.add(Component.translatable(I18n.get("information.ender_wrap_around_shield")));
	}
	
}
