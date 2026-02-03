package com.chain_mining.mixin;

import com.chain_mining.api.PlayerState;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashSet;

@Mixin(ServerPlayerEntity.class)
public class PlayerStateMixin implements PlayerState {
    @Unique
    private HashSet<BlockPos> posSet = new HashSet<>();

    @Unique
    private boolean key;

    @Override
    public ServerPlayerEntity chainMining$getEntity() {
        return (ServerPlayerEntity) (Object) this;
    }

    @Override
    public HashSet<BlockPos> chainMining$getPosSet() {
        return posSet;
    }

    @Override
    public HashSet<BlockPos> chainMining$drainPosSet() {
        var posSet = this.posSet;
        this.posSet = new HashSet<>();
        return posSet;
    }

    @Override
    public boolean chainMining$isPressed() {
        return key;
    }

    @Override
    public void chainMining$press(boolean key) {
        this.key = key;
    }
}