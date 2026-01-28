package com.example;

import net.fabricmc.api.ClientModInitializer;

public class ExampleModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// 在這裡或用 ClientTickEvents 去偵測 keyBinding.wasPressed()
	}
}