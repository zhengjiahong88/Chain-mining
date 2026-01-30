package com.chain_mining;

import com.chain_mining.api.ServerPlayerState;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.PacketCodecs;

import java.util.HashSet;

public class Mod implements ModInitializer {
	public static final String ID = "chain_mining";

	private static HashSet<ServerPlayerState> chainingPlayers = new HashSet<>();

	@Override
	public void onInitialize() {
		PayloadTypeRegistry.playC2S().register(KeyStatePayload.ID,
				PacketCodecs.BOOLEAN.xmap(KeyStatePayload::new, KeyStatePayload::pressed));

		ServerPlayNetworking.registerGlobalReceiver(KeyStatePayload.ID,
				(payload, context) -> ((ServerPlayerState) context.player()).chainMining$press(payload.pressed()));

		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
			var playerState = (ServerPlayerState)player;
			if (world.isClient() || !playerState.chainMining$isPressed()) return;
			var posSet = playerState.chainMining$getPosSet();
			var block = state.getBlock();
			for (int dx = -1; dx <= 1; dx++) for (int dy = -1; dy <= 1; dy++) for (int dz = -1; dz <= 1; dz++) {
				if (dx == 0 && dy == 0 && dz == 0) continue; // 跳過自己
				var next = pos.add(dx, dy, dz);
				if (block == world.getBlockState(next).getBlock()) posSet.add(next);
			}
			if (!posSet.isEmpty()) chainingPlayers.add(playerState);
		});

		ServerTickEvents.END_SERVER_TICK.register(server -> {
			var chainingPlayers = Mod.chainingPlayers;
			Mod.chainingPlayers = new HashSet<>();
			chainingPlayers.forEach(chainingPlayer ->
					chainingPlayer.chainMining$drainPosSet()
							.forEach(chainingPlayer.chainMining$getEntity().interactionManager::tryBreakBlock));
		});
	}
}