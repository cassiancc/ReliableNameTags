package cc.cassian.reliablenametags;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.core.HolderLookup;
import org.jetbrains.annotations.Nullable;

import static cc.cassian.reliablenametags.ReliableNameTags.CONFIG;

public class RecipeEnabledCondition implements ResourceCondition {
  public static MapCodec<RecipeEnabledCondition> CODEC = MapCodec.unit(RecipeEnabledCondition::new);

  @Override
  public ResourceConditionType<?> getType() {
    return ReliableNameTags.BACKPORTED_NAME_TAG_RECIPE;
  }

  @Override
  public boolean test(HolderLookup.@Nullable Provider wrapperLookup) {
    return CONFIG.enableBackportedCraftingRecipe;
  }
}
