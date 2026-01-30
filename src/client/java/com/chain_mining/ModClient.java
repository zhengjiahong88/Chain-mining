package com.chain_mining;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;

import org.lwjgl.glfw.GLFW;

public class ModClient implements ClientModInitializer {
	private static final KeyBinding KEY =
			KeyBindingHelper.registerKeyBinding(new KeyBinding("連鎖挖礦", GLFW.GLFW_KEY_R, KeyBinding.Category.MISC));

	private boolean isPressed;

	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			var isPress = KEY.isPressed();
			if (isPressed == isPress) return;
			isPressed = isPress;
			ClientPlayNetworking.send(new KeyStatePayload(isPress));
		});
	}
}