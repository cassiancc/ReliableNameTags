//? fabric {
package cc.cassian.reliablenametags.fabric;

import java.util.List;

import cc.cassian.reliablenametags.ReliableNameTags;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.RegistryOps;
import net.minecraft.util.ExtraCodecs;

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
    return FabricEntrypoint.CONFIG_ENABLED;
  }

	public boolean test(RegistryOps.@org.jspecify.annotations.Nullable RegistryInfoLookup registryInfo) {
		return ReliableNameTags.test(options);
	}

	public boolean test(HolderLookup.@Nullable Provider registryLookup) {
		return ReliableNameTags.test(options);
	}

}
//?}