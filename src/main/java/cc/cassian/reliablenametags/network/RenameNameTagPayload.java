package cc.cassian.reliablenametags.network;

import cc.cassian.reliablenametags.ReliableNameTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;

public record RenameNameTagPayload(String customName) implements CustomPacketPayload {

	public static final Type<RenameNameTagPayload> TYPE = new Type<>(ReliableNameTags.of("rename_name_tag"));

	public static final StreamCodec<RegistryFriendlyByteBuf, RenameNameTagPayload> CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, RenameNameTagPayload::customName, RenameNameTagPayload::new);

	public static void apply(RenameNameTagPayload payload, ServerPlayNetworkingContext context) {
		final String customName = payload.customName();
		var player = context.player();

		context.server().execute(() -> {
			final ItemStack itemStack = player.getMainHandItem();
			if (itemStack != null) {
				var multiplier = ReliableNameTags.CONFIG.renameCostPerWholeStack ? 1 : itemStack.getCount();
				var cost = ReliableNameTags.CONFIG.renameCost * multiplier;
				if (customName.isEmpty()) {
					itemStack.remove(DataComponents.CUSTOM_NAME);
				} else if (ReliableNameTags.canAfford(player, cost)) {
					player.setExperienceLevels(player.experienceLevel - cost);
					itemStack.set(DataComponents.CUSTOM_NAME, ReliableNameTags.parse(customName));
				}
			}
		});
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return RenameNameTagPayload.TYPE;
	}
}
