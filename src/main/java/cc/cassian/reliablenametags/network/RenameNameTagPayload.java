package cc.cassian.reliablenametags.network;

import cc.cassian.reliablenametags.ReliableNameTags;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;

public record RenameNameTagPayload(String customName) implements CustomPacketPayload {

  public static final Type<RenameNameTagPayload> ID = new Type<>(ReliableNameTags.of("rename_name_tag"));

  public static final StreamCodec<RegistryFriendlyByteBuf, RenameNameTagPayload> CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, RenameNameTagPayload::customName, RenameNameTagPayload::new);

  public static void apply(RenameNameTagPayload payload, ServerPlayNetworking.Context context) {
    final String customName = payload.customName();
    var player = context.player();

    context.server().execute(() -> {
      final ItemStack itemStack = player.getMainHandItem();
      if (ReliableNameTags.CONFIG.denylist.stream().anyMatch(customName::contains)) {
        player.sendSystemMessage(Component.translatable("message.reliablenametags.denylisted"));
        return;
      }
      if (itemStack != null) {
        var multiplier = ReliableNameTags.CONFIG.renameCostPerWholeStack ? 1 : itemStack.getCount();
        var cost = ReliableNameTags.CONFIG.renameCost * multiplier;
        if (customName.isEmpty()) {
          itemStack.remove(DataComponents.CUSTOM_NAME);
        } else if (player.experienceLevel >= cost) {
          player.setExperienceLevels(player.experienceLevel - cost);
          itemStack.set(DataComponents.CUSTOM_NAME, Component.nullToEmpty(customName));
        }
      }
    });
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return RenameNameTagPayload.ID;
  }
}
