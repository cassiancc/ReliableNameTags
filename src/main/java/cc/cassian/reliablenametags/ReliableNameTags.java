package cc.cassian.reliablenametags;

import cc.cassian.reliablenametags.config.ModConfig;
import cc.cassian.reliablenametags.network.RenameNameTagPayload;
import cc.cassian.reliablenametags.recipe.ReliableNameTagRecipes;
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
  public static final ResourceConditionType<ConfigEnabledResourceCondition> CONFIG_ENABLED = ResourceConditionType.create(of("config"), ConfigEnabledResourceCondition.CODEC);

  @Override
  public void onInitialize() {
    ResourceConditions.register(CONFIG_ENABLED);
    PayloadTypeRegistry.playC2S().register(RenameNameTagPayload.ID, RenameNameTagPayload.CODEC);
    ServerPlayNetworking.registerGlobalReceiver(RenameNameTagPayload.ID, RenameNameTagPayload::apply);
    ReliableNameTagRecipes.touch();
  }

  public static ResourceLocation of(String path) {
    return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
  }
}
