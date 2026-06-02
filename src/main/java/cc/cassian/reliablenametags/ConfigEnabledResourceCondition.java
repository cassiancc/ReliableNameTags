package cc.cassian.reliablenametags;

import java.util.List;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import net.minecraft.core.HolderLookup;
import net.minecraft.util.ExtraCodecs;

import net.minecraft.resources.RegistryOps;

import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import org.jetbrains.annotations.Nullable;

public record ConfigEnabledResourceCondition(List<String> options) implements ResourceCondition {
  public static final MapCodec<ConfigEnabledResourceCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
    ExtraCodecs.NON_EMPTY_STRING.listOf().fieldOf("options").forGetter(ConfigEnabledResourceCondition::options)
  ).apply(instance, ConfigEnabledResourceCondition::new));

  public ConfigEnabledResourceCondition(String... configOptions) {
    this(List.of(configOptions));
  }

  @Override
  public ResourceConditionType<?> getType() {
    return ReliableNameTags.CONFIG_ENABLED;
  }

  @Override
  @SuppressWarnings("all")
  public boolean test(HolderLookup.@Nullable Provider registryLookup) {
    for (String option : options) {
      var value = ((TrackedValue<Boolean>) ReliableNameTags.CONFIG.getValue(List.of(option))).value();
      if (value == false) return false;
    }
    return true;
  }

}