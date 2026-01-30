package com.chain_mining.api;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;

public interface ServerPlayerState {
    ServerPlayerEntity chainMining$getEntity();
    HashSet<BlockPos> chainMining$getPosSet();
    HashSet<BlockPos> chainMining$drainPosSet();
    boolean chainMining$isPressed();
    void chainMining$press(boolean key);
}
