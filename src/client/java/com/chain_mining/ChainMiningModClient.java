package com.chain_mining;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;

import org.lwjgl.glfw.GLFW;

public class ChainMiningModClient implements ClientModInitializer {
	private static final KeyBinding R_KEY = KeyBindingHelper.registerKeyBinding(
			new KeyBinding("chain_mine", GLFW.GLFW_KEY_R, KeyBinding.Category.MISC)
	);

	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (R_KEY.wasPressed()) {
				// TODO: 呼叫你的連鎖挖礦邏輯
			}
		});
	}
}