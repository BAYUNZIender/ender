package ender_random_creation.client;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import ender_random_creation.item.EnderSword;
import ender_random_creation.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Holder.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.ForgeItemModelShaper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class MModelLoader {

	@SubscribeEvent
	public void clientTick(ClientTickEvent evt) {
		if (evt.phase == Phase.START) {
			load();
			MinecraftForge.EVENT_BUS.unregister(this);
		}
	}
	
	public void load() {
		Minecraft mc = Minecraft.getInstance();
		
		mc.getEntityRenderDispatcher().getSkinMap().forEach((s, renderer) -> {
			if (renderer instanceof LivingEntityRenderer) {
				((LivingEntityRenderer) renderer).addLayer(new RenderLayer((RenderLayerParent) renderer) {

					private static final ResourceLocation WITHER_ARMOR_LOCATION = new ResourceLocation("textures/entity/wither/wither_armor.png");
			
					protected float xOffset(float p_117702_) {
						return Mth.cos(p_117702_ * 0.02F) * 3.0F;
					}

					protected ResourceLocation getTextureLocation() {
						return WITHER_ARMOR_LOCATION;
					}

					protected EntityModel model() {
						return ((LivingEntityRenderer) renderer).getModel();
					}

					@Override
					public void render(PoseStack p_116970_, MultiBufferSource p_116971_, int p_116972_, Entity entity, float p_116974_, float p_116975_, float p_116976_, float p_116977_, float p_116978_, float p_116979_) {
						if (entity instanceof Player) {
							Player player = (Player) entity;
							if (player.getPersistentData().getBoolean("isSave")) {
								float f = (float)entity.tickCount + p_116976_;
								EntityModel entitymodel = this.model();
								entitymodel.prepareMobModel(entity, p_116974_, p_116975_, p_116976_);
								this.getParentModel().copyPropertiesTo(entitymodel);
								VertexConsumer vertexconsumer = p_116971_.getBuffer(RenderType.energySwirl(this.getTextureLocation(), this.xOffset(f) % 1.0F, f * 0.01F % 1.0F));
								entitymodel.setupAnim(entity, p_116974_, p_116975_, p_116977_, p_116978_, p_116979_);
								entitymodel.renderToBuffer(p_116970_, vertexconsumer, p_116972_, OverlayTexture.NO_OVERLAY, 0.5F, 0.0F, 0.5F, 1.0F);     
							}
						}
					}
					
				});
			}
		});
		
		ItemRenderer ir = mc.getItemRenderer();
		ForgeItemModelShaper immf = (ForgeItemModelShaper) ir.getItemModelShaper();
		BakedModel parent = immf.getItemModel(Items.WOODEN_PICKAXE);
		BakedModel model = new BakedModel() {

			@Override
			public List<BakedQuad> getQuads(BlockState p_235039_, Direction p_235040_, RandomSource p_235041_) {
				return new ArrayList();
			}

			@Override
			public boolean useAmbientOcclusion() {
				return false;
			}

			@Override
			public boolean isGui3d() {
				return false;
			}

			@Override
			public boolean usesBlockLight() {
				return false;
			}

			@Override
			public boolean isCustomRenderer() {
				return true;
			}

			@Override
			public TextureAtlasSprite getParticleIcon() {
				return parent.getParticleIcon();
			}

			@Override
			public ItemOverrides getOverrides() {
				return new MItemOverrides();
			}
			
		};
		
		Field field = ForgeItemModelShaper.class.getDeclaredFields()[1];
		field.setAccessible(true);
		try {
			Map<Holder.Reference<Item>, BakedModel> models = (Map<Reference<Item>, BakedModel>) field.get(immf);
			models.put(ForgeRegistries.ITEMS.getDelegateOrThrow(EnderSword.item), model);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
}
