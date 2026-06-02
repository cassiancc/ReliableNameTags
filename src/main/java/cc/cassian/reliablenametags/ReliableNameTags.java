package cc.cassian.reliablenametags;

import cc.cassian.reliablenametags.config.ModConfig;
import cc.cassian.reliablenametags.recipe.ReliableNameTagRecipes;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import net.minecraft.resources.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ReliableNameTags {
  public static final String MOD_ID = "reliablenametags";
  public static final Logger LOGGER = LogManager.getLogger();
  public static final ModConfig CONFIG = ModConfig.createToml(Platform.getConfigDirectory(), "", MOD_ID, ModConfig.class);

  public static void touch() {
    ReliableNameTagRecipes.touch();
  }

  @SuppressWarnings("all")
  public static boolean test(List<String> options) {
    for (String option : options) {
      TrackedValue<?> value1 = ReliableNameTags.CONFIG.getValue(List.of(option));
      if (value1 == null) return false;
      var value = ((TrackedValue<Boolean>) value1).value();
      if (value == false) return false;
    }
    return true;
  }

  public static Identifier of(String path) {
    return Identifier.fromNamespaceAndPath(MOD_ID, path);
  }
}
