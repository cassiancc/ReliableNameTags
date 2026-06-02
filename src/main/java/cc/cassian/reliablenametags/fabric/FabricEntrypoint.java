//? fabric {
package cc.cassian.reliablenametags.fabric;

import cc.cassian.reliablenametags.ReliableNameTags;
import cc.cassian.reliablenametags.config.ModConfig;
import cc.cassian.reliablenametags.network.RenameNameTagPayload;
import cc.cassian.reliablenametags.network.ServerPlayNetworkingContext;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;

public class FabricEntrypoint implements ModInitializer {
	public static final ResourceConditionType<ConfigEnabledResourceCondition> CONFIG_ENABLED = ResourceConditionType.create(ReliableNameTags.of("config"), ConfigEnabledResourceCondition.CODEC);

	@Override
	public void onInitialize() {
		ReliableNameTags.touch();
		ResourceConditions.register(CONFIG_ENABLED);
		//~ if >26 'playC2S' -> 'serverboundPlay' {
		PayloadTypeRegistry.serverboundPlay().register(RenameNameTagPayload.TYPE, RenameNameTagPayload.CODEC);
		//~}
		ServerPlayNetworking.registerGlobalReceiver(RenameNameTagPayload.TYPE, (RenameNameTagPayload payload, ServerPlayNetworking.Context context) -> RenameNameTagPayload.apply(payload, new ServerPlayNetworkingContext(context.player(), context.server())));
	}
}
//?}