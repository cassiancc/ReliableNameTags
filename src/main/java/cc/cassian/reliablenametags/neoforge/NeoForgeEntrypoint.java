//? neoforge {
/*package cc.cassian.reliablenametags.neoforge;

import cc.cassian.reliablenametags.ReliableNameTags;
import cc.cassian.reliablenametags.network.RenameNameTagPayload;
import cc.cassian.reliablenametags.network.ServerPlayNetworkingContext;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.function.Supplier;

@Mod(ReliableNameTags.MOD_ID)
@EventBusSubscriber(modid = ReliableNameTags.MOD_ID)
public class NeoForgeEntrypoint {
	public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS =
			DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, ReliableNameTags.MOD_ID);

	public static final Supplier<MapCodec<ConfigEnabledResourceCondition>> CONFIG =
			CONDITION_CODECS.register("config", () -> ConfigEnabledResourceCondition.CODEC);

	public NeoForgeEntrypoint(IEventBus bus) {
		CONDITION_CODECS.register(bus);
	}

	@SubscribeEvent
	public static void register(RegisterEvent event) {
		if (event.getRegistryKey().equals(Registries.RECIPE_SERIALIZER)) {
			ReliableNameTags.touch();
		}
	}

	@SubscribeEvent
	public static void register(RegisterPayloadHandlersEvent event) {
		var registrar = event.registrar("1");
		registrar.playToServer(RenameNameTagPayload.TYPE, RenameNameTagPayload.CODEC, (payload, context)->{
			RenameNameTagPayload.apply(payload, new ServerPlayNetworkingContext((ServerPlayer) context.player(), context.player().level().getServer()));
		});
	}
}
*///?}
