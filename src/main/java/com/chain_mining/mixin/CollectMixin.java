package com.chain_mining.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class CollectMixin {
    @Inject(method =
            "dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)V",
            at = @At("HEAD"), cancellable = true
    )
    private static void chainMining$giveToInventory(
            BlockState state, World world, BlockPos pos, @Nullable BlockEntity blockEntity,
            Entity entity, ItemStack tool, CallbackInfo ci
    ) {
        if (world.isClient() || !(entity instanceof ServerPlayerEntity player))
            return;
        for (var stack : Block.getDroppedStacks(state, (ServerWorld) world, pos, blockEntity, player, tool))
            player.giveOrDropStack(stack);
        ci.cancel();
    }
}