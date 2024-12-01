
package ender_random_creation.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

import java.awt.Color;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import org.joml.Matrix4f;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

public class EnderSword extends SwordItem {
	
	public static EnderSword item;
	private final Multimap<Attribute, AttributeModifier> defaultModifiers;
	
	public EnderSword() {
		super(new Tier() {
			public int getUses() {
				return 0;
			}

			public float getSpeed() {
				return 0.8f;
			}

			public float getAttackDamageBonus() {
				return 16f;
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
		}, 3, -3f, new Item.Properties().fireResistant());
		
		MinecraftForge.EVENT_BUS.register(this);
		
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double) 14, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double) 0.8, AttributeModifier.Operation.ADDITION));
		this.defaultModifiers = builder.build();
		
		item = this;
	}

	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot p_43274_) {
	      return p_43274_ == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(p_43274_);
	}
	
	@Override
	public int getMaxDamage(ItemStack stack) {
		return -1;
	}
	
	public static final IClientItemExtensions iClientItemExtensions = new IClientItemExtensions() {
		
		@Override
		public BlockEntityWithoutLevelRenderer getCustomRenderer() {
			return new BlockEntityWithoutLevelRenderer(null, new EntityModelSet()) {
				private static final Random RANDOM = new Random(31100L);
				
				int[] data = new int[] {
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 
						0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 
						0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 
						0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 
						0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 
						0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 
						0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 
						0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 
						0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 
						0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 
						1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 
						1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
						1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
				};
				
				public void renderByItem(ItemStack p_108830_, ItemDisplayContext p_270899_, PoseStack p_108832_, MultiBufferSource p_108833_, int p_108834_, int p_108835_) {
				      Item item = p_108830_.getItem();
				      p_108832_.pushPose();
				      p_108832_.scale(1.0F, -1.0F, -1.0F);
				      
				      Matrix4f matrix4f = p_108832_.last().pose();
				      
				      VertexConsumer vertexconsumer1 = ItemRenderer.getFoilBufferDirect(p_108833_, RenderType.endPortal(), false, p_108830_.hasFoil());
				      
				      int[] imageData = data;
				      float drawSize = 1;
				      int size = (int) Math.sqrt(imageData.length);
				        
				      this.renderData(imageData, size, drawSize, p_270899_, p_108832_, 0, 0.15F, matrix4f, vertexconsumer1);
				      
				      p_108832_.popPose();
				}

				public void renderData(int[] imageData, int size, float drawSize, ItemDisplayContext type, PoseStack matrixStackIn, float i2, float p_228883_3_, Matrix4f p_228883_4_, VertexConsumer p_228883_5_) {
					float f = (RANDOM.nextFloat() * 0.5F + 0.1F) * p_228883_3_;
					float f1 = (RANDOM.nextFloat() * 0.5F + 0.4F) * p_228883_3_;
					float f2 = (RANDOM.nextFloat() * 0.5F + 0.5F) * p_228883_3_;
					
					Color color = new Color(f, f1, f2);
					float[] hsb = Color.RGBtoHSB(color.getRed(), color.getBlue(), color.getGreen(), null);
					hsb[1] = 1f;
					hsb[2] = 0.85f;
					color = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
					f = color.getRed();
					f1 = color.getGreen();
					f2 = color.getBlue();
					
					float sizeForItem = drawSize / (float) size;
					
					boolean isInHead = 
							(type == ItemDisplayContext.FIRST_PERSON_LEFT_HAND 
							|| type == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND 
							|| type == ItemDisplayContext.THIRD_PERSON_LEFT_HAND 
							|| type == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND);
					
					for (float i = 0; i < imageData.length; i++) {
						if (imageData[(int) i] == 0) continue;
						float y = i / size;
						float x = i % size;
						if (isInHead) {
							matrixStackIn.translate(0, -0.3, 0.1);
							//matrixStackIn.rotateAround(new , MAX_STACK_SIZE, MAX_BAR_WIDTH, EAT_DURATION)
							//matrixStackIn.mulPose(new Quaternion(45, 0, 0, true));
							matrixStackIn.mulPose(Axis.XP.rotationDegrees(45));
							renderBox(p_228883_4_, p_228883_5_, sizeForItem, 0.5f, y * sizeForItem - 1.2f, x * sizeForItem -0.5f, f, f1, f2);
							//matrixStackIn.mulPose(new Quaternion(-45, 0, 0, true));
							matrixStackIn.mulPose(Axis.XP.rotationDegrees(-45));
							matrixStackIn.translate(0, 0.3, -0.1);
						} else renderBox(p_228883_4_, p_228883_5_, sizeForItem, x * sizeForItem, y * sizeForItem - 1, -0.5f, f, f1, f2);
					}
				}
				
				private void renderBox(Matrix4f matrix4f, VertexConsumer builder, float size, float x, float y, float z, float f, float f1, float f2) {
					size /= 2;
					this.renderFace(matrix4f, builder, -size + x, size + x, -size + y, size + y, size + z, size + z, size + z, size + z, f, f1, f2, Direction.SOUTH);
					this.renderFace(matrix4f, builder, -size + x, size + x, size + y, -size + y, -size + z, -size + z, -size + z, -size + z, f, f1, f2, Direction.NORTH);
					this.renderFace(matrix4f, builder, size + x, size + x, size + y, -size + y, -size + z, size + z, size + z, -size + z, f, f1, f2, Direction.EAST);
					this.renderFace(matrix4f, builder, -size + x, -size + x, -size + y, size + y, -size + z, size + z, size + z, -size + z, f, f1, f2, Direction.WEST);
					this.renderFace(matrix4f, builder, -size + x, size + x, -size + y, -size + y, -size + z, -size + z, size + z, size + z, f, f1, f2, Direction.DOWN);
					this.renderFace(matrix4f, builder, -size + x, size + x, size + y, size + y, size + z, size + z, -size + z, -size + z, f, f1, f2, Direction.UP);
				}
				
				private void renderFace(Matrix4f matrix4f, VertexConsumer builder, float x, float w, float y, float h, float z, float p_228884_9_, float p_228884_10_, float p_228884_11_, float colorF, float colorS, float colorT, Direction p_228884_15_) {
					builder.vertex(matrix4f, x, y, z).color(colorF, colorS, colorT, 1.0F).endVertex();
					builder.vertex(matrix4f, w, y, p_228884_9_).color(colorF, colorS, colorT, 1.0F).endVertex();
					builder.vertex(matrix4f, w, h, p_228884_10_).color(colorF, colorS, colorT, 1.0F).endVertex();
					builder.vertex(matrix4f, x, h, p_228884_11_).color(colorF, colorS, colorT, 1.0F).endVertex();
				}
				
				private void renderCube(Matrix4f p_254024_, VertexConsumer p_173693_) {
					float f = this.getOffsetDown();
					float f1 = this.getOffsetUp();  
					this.renderFace(p_254024_, p_173693_, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
					this.renderFace(p_254024_, p_173693_, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
					this.renderFace(p_254024_, p_173693_, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
					this.renderFace(p_254024_, p_173693_, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
					this.renderFace(p_254024_, p_173693_, 0.0F, 1.0F, f, f, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
					this.renderFace(p_254024_, p_173693_, 0.0F, 1.0F, f1, f1, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
				}

				private void renderFace(Matrix4f p_254247_, VertexConsumer p_254390_, float p_254147_, float p_253639_, float p_254107_, float p_254109_, float p_254021_, float p_254458_, float p_254086_, float p_254310_, Direction p_253619_) {
					p_254390_.vertex(p_254247_, p_254147_, p_254107_, p_254021_).endVertex();
					p_254390_.vertex(p_254247_, p_253639_, p_254107_, p_254458_).endVertex();
					p_254390_.vertex(p_254247_, p_253639_, p_254109_, p_254086_).endVertex();
					p_254390_.vertex(p_254247_, p_254147_, p_254109_, p_254310_).endVertex();
				}
				
				protected float getOffsetUp() {
					return 1.0F;
				}

				protected float getOffsetDown() {
					return 0.0F;
				}
				
			};
		}
		
	};
	
	@Override
	public void appendHoverText(ItemStack p_41421_, Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
		super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
		p_41423_.add(Component.translatable(I18n.get("information.ender_sword")));
	}
	
	@SubscribeEvent
	public void livingAttack(LivingAttackEvent evt) {
		if (evt.getSource().getEntity() != null) {
			Entity entity = evt.getSource().getEntity();
			if (entity instanceof LivingEntity) {
				if (((LivingEntity) entity).getMainHandItem().isEmpty()
						&& ((LivingEntity) entity).getMainHandItem().getItem() == this) {
					evt.setCanceled(true);
				}
			}
		}
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
		if (entity instanceof LivingEntity) {
			((LivingEntity) entity).setHealth(((LivingEntity) entity).getHealth() - 15);
			((LivingEntity) entity).hurtTime = ((LivingEntity) entity).hurtDuration;
			double d0 = player.getX() - entity.getX();

            double d1;
            for(d1 = player.getZ() - entity.getZ(); d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D) {
               d0 = (Math.random() - Math.random()) * 0.01D;
            }

            ((LivingEntity) entity).knockback((double)0.4F, d0, d1);
		}
		return true;
	}
	
	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(iClientItemExtensions);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
		
		Vec3 vec3 = p_41433_.getDeltaMovement();
		double d3 = vec3.horizontalDistance();
	      
		
		Vec3 view = p_41433_.getViewVector(1);
		Vec3 pos = p_41433_.getEyePosition();
		Vec3 ppos = p_41433_.getPosition(1);
		Random random = new Random();
		for (float i = 0; i < 200 && i <= p_41433_.totalExperience / 30; i++) {
			pos = pos.add(view);
			BlockPos bp = new BlockPos(new Vec3i((int) pos.x, (int) pos.y, (int) pos.z));
			if (!p_41432_.getBlockState(bp).isAir()) {
				p_41433_.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
				
				for (int i2 = 0; i2 < 100; i2++) {
					double d0 = p_41433_.getX() + vec3.x;
					double d2 = p_41433_.getZ() + vec3.z;
					double d1 = p_41433_.getY() + vec3.y + random.nextDouble(20) / 10;
					p_41432_.addParticle(ParticleTypes.PORTAL, d0 - vec3.x * 0.25D + random.nextDouble() * 0.6D - 0.3D, d1 - vec3.y * 0.25D - 0.5D, d2 - vec3.z * 0.25D + random.nextDouble() * 0.6D - 0.3D, vec3.x, vec3.y, vec3.z);
				}
				
				p_41433_.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
				p_41433_.setPos(new Vec3((int) pos.x, (int) pos.y + 1, (int) pos.z));
				
				for (int i2 = 0; i2 < 100; i2++) {
					double d0 = p_41433_.getX() + vec3.x;
					double d2 = p_41433_.getZ() + vec3.z;
					double d1 = p_41433_.getY() + vec3.y + random.nextDouble(20) / 10;
					p_41432_.addParticle(ParticleTypes.PORTAL, d0 - vec3.x * 0.25D + random.nextDouble() * 0.6D - 0.3D, d1 - vec3.y * 0.25D - 0.5D, d2 - vec3.z * 0.25D + random.nextDouble() * 0.6D - 0.3D, vec3.x, vec3.y, vec3.z);
				}
				
				p_41433_.totalExperience -= i * 30;
				
				p_41433_.getCooldowns().addCooldown(this, 3 * 20);
				
				break;
			}
			if (i == 199) {
				p_41433_.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
				
				for (int i2 = 0; i2 < 100; i2++) {
					double d0 = p_41433_.getX() + vec3.x;
					double d2 = p_41433_.getZ() + vec3.z;
					double d1 = p_41433_.getY() + vec3.y + random.nextDouble(20) / 10;
					p_41432_.addParticle(ParticleTypes.PORTAL, d0 - vec3.x * 0.25D + random.nextDouble() * 0.6D - 0.3D, d1 - vec3.y * 0.25D - 0.5D, d2 - vec3.z * 0.25D + random.nextDouble() * 0.6D - 0.3D, vec3.x, vec3.y, vec3.z);
				}
				
				p_41433_.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
				p_41433_.setPos(pos);
				
				for (int i2 = 0; i2 < 100; i2++) {
					double d0 = p_41433_.getX() + vec3.x;
					double d2 = p_41433_.getZ() + vec3.z;
					double d1 = p_41433_.getY() + vec3.y + random.nextDouble(20) / 10;
					p_41432_.addParticle(ParticleTypes.PORTAL, d0 - vec3.x * 0.25D + random.nextDouble() * 0.6D - 0.3D, d1 - vec3.y * 0.25D - 0.5D, d2 - vec3.z * 0.25D + random.nextDouble() * 0.6D - 0.3D, vec3.x, vec3.y, vec3.z);
				}
				
				p_41433_.totalExperience -= i * 30;
				
				p_41433_.getCooldowns().addCooldown(this, 3 * 20);
				
				break;
			}
		}
		return InteractionResultHolder.success(p_41433_.getItemInHand(p_41434_));
	}
	
}
