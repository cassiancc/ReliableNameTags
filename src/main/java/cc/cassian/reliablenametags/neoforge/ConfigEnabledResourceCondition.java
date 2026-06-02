//? neoforge {
/*package cc.cassian.reliablenametags.neoforge;

import cc.cassian.reliablenametags.ReliableNameTags;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.List;

public record ConfigEnabledResourceCondition(List<String> options) implements ICondition {
	public static final MapCodec<ConfigEnabledResourceCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			ExtraCodecs.NON_EMPTY_STRING.listOf().fieldOf("options").forGetter(ConfigEnabledResourceCondition::options)
	).apply(instance, ConfigEnabledResourceCondition::new));

	public ConfigEnabledResourceCondition(String... configOptions) {
		this(List.of(configOptions));
	}

	@Override
	public boolean test(IContext iContext) {
		return ReliableNameTags.test(options);
	}

	@Override
	public MapCodec<? extends ICondition> codec() {
		return CODEC;
	}
}
*///?}