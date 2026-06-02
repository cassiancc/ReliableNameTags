package cc.cassian.reliablenametags.network;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public record ServerPlayNetworkingContext(ServerPlayer player, MinecraftServer server) {
}
