package com.github.mim1q.convenientnametags.network;

import com.github.mim1q.convenientnametags.ConvenientNameTags;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.text.Text;

public record RenameNameTagPayload(String customName) implements CustomPayload {

  public static final Id<RenameNameTagPayload> ID = new Id<>(ConvenientNameTags.createId("rename_name_tag"));

  public static final PacketCodec<RegistryByteBuf, RenameNameTagPayload> CODEC = PacketCodec.tuple(PacketCodecs.STRING, RenameNameTagPayload::customName, RenameNameTagPayload::new);

  public static void apply(RenameNameTagPayload payload, ServerPlayNetworking.Context context) {
    final String customName = payload.customName();
    var player = context.player();

    context.server().execute(() -> {
      final ItemStack itemStack = player.getMainHandStack();
      if (ConvenientNameTags.CONFIG.denylist.stream().anyMatch(customName::contains)) {
        player.sendMessage(Text.translatable("message.convenientnametags.denylisted"));
        return;
      }
      if (itemStack != null) {
        var multiplier = ConvenientNameTags.CONFIG.renameCostPerWholeStack ? 1 : itemStack.getCount();
        var cost = ConvenientNameTags.CONFIG.renameCost * multiplier;
        if (customName.isEmpty()) {
          itemStack.remove(DataComponentTypes.CUSTOM_NAME);
        } else if (player.experienceLevel >= cost) {
          player.setExperienceLevel(player.experienceLevel - cost);
          itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.of(customName));
        }
      }
    });
  }

  @Override
  public Id<? extends CustomPayload> getId() {
    return RenameNameTagPayload.ID;
  }
}
