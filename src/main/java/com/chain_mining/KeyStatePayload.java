package com.chain_mining;

import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record KeyStatePayload(boolean pressed) implements CustomPayload {

    public static final Id<KeyStatePayload> ID = new Id<>(Identifier.of(Mod.ID, "key_state"));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}