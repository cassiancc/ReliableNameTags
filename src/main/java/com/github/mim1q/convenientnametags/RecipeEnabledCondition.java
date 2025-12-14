package com.github.mim1q.convenientnametags;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.Nullable;

import static com.github.mim1q.convenientnametags.ConvenientNameTags.CONFIG;

public class RecipeEnabledCondition implements ResourceCondition {
  public static MapCodec<RecipeEnabledCondition> CODEC = MapCodec.unit(RecipeEnabledCondition::new);

  @Override
  public ResourceConditionType<?> getType() {
    return ConvenientNameTags.RECIPE_ENABLED_CONDITION_RESOURCE_CONDITION_TYPE;
  }

  @Override
  public boolean test(RegistryWrapper.@Nullable WrapperLookup wrapperLookup) {
    return CONFIG.enableCraftingRecipe;
  }
}
