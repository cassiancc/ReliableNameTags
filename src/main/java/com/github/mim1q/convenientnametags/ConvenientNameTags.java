package com.github.mim1q.convenientnametags;

import com.github.mim1q.convenientnametags.config.ConvenientNameTagsConfig;
import com.github.mim1q.convenientnametags.network.RenameNameTagPayload;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConvenientNameTags implements ModInitializer {
  public static final String MOD_ID = "convenientnametags";
  public static final Logger LOGGER = LogManager.getLogger();
  public static final ConvenientNameTagsConfig CONFIG = ConvenientNameTagsConfig.createToml(FabricLoader.getInstance().getConfigDir(), "", MOD_ID, ConvenientNameTagsConfig.class);
  public static final ResourceConditionType<RecipeEnabledCondition> RECIPE_ENABLED_CONDITION_RESOURCE_CONDITION_TYPE = ResourceConditionType.create(createId("name_tag_recipe_enabled"), RecipeEnabledCondition.CODEC);

  @Override
  public void onInitialize() {
    LOGGER.info("Convenient Name Tags is initializing...");

    ResourceConditions.register(new ResourceConditionType<RecipeEnabledCondition>() {
      @Override
      public Identifier id() {
        return createId("name_tag_recipe_enabled");
      }

      @Override
      public MapCodec<RecipeEnabledCondition> codec() {
        return RecipeEnabledCondition.CODEC;
      }
    });
    PayloadTypeRegistry.playC2S().register(RenameNameTagPayload.ID, RenameNameTagPayload.CODEC);
    ServerPlayNetworking.registerGlobalReceiver(RenameNameTagPayload.ID, RenameNameTagPayload::apply);
  }

  public static Identifier createId(String path) {
    return Identifier.of(MOD_ID, path);
  }
}
