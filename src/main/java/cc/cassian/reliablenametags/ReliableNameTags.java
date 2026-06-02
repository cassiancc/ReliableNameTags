package cc.cassian.reliablenametags;

import cc.cassian.reliablenametags.config.ModConfig;
import cc.cassian.reliablenametags.network.RenameNameTagPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReliableNameTags implements ModInitializer {
  public static final String MOD_ID = "reliablenametags";
  public static final Logger LOGGER = LogManager.getLogger();
  public static final ModConfig CONFIG = ModConfig.createToml(FabricLoader.getInstance().getConfigDir(), "", MOD_ID, ModConfig.class);
  public static final ResourceConditionType<RecipeEnabledCondition> BACKPORTED_NAME_TAG_RECIPE = ResourceConditionType.create(id("backported_name_tag_recipe"), RecipeEnabledCondition.CODEC);

  @Override
  public void onInitialize() {
    ResourceConditions.register(BACKPORTED_NAME_TAG_RECIPE);
    PayloadTypeRegistry.playC2S().register(RenameNameTagPayload.ID, RenameNameTagPayload.CODEC);
    ServerPlayNetworking.registerGlobalReceiver(RenameNameTagPayload.ID, RenameNameTagPayload::apply);
  }

  public static ResourceLocation id(String path) {
    return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
  }
}
