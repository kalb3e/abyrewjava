
package com.kbd.abyssal_reawoken.blocks;

import com.kbd.abyssal_reawoken.ModDataComponents;
import com.kbd.abyssal_reawoken.blockentity.EnergyPedestalBlockEntity;
import com.kbd.abyssal_reawoken.items.EnergyItems;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;

public class EnergyPedestalBlock extends BaseEntityBlock {

    public static final MapCodec<EnergyPedestalBlock> CODEC =
            simpleCodec(EnergyPedestalBlock::new);

    public static final DirectionProperty FACING =
            BlockStateProperties.HORIZONTAL_FACING;

    public EnergyPedestalBlock(Properties properties) {
        super(properties.noOcclusion());

        registerDefaultState(
                stateDefinition.any().setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    protected MapCodec<? extends EnergyPedestalBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder
    ) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(
                FACING,
                context.getHorizontalDirection().getOpposite()
        );
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(
                FACING,
                rotation.rotate(state.getValue(FACING))
        );
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected net.minecraft.world.level.block.RenderShape getRenderShape(
            BlockState state
    ) {
        return net.minecraft.world.level.block.RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EnergyPedestalBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useItemOn(
            ItemStack heldStack,
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hit
    ) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (!(level.getBlockEntity(pos)
                instanceof EnergyPedestalBlockEntity pedestal)) {
            return InteractionResult.PASS;
        }

        if (heldStack.isEmpty()
                || heldStack.is(EnergyItems.ENERGY_PEDESTAL_ITEM.get())) {
            return InteractionResult.PASS;
        }

        ItemStack inserted = heldStack.copyWithCount(1);
        ItemStack previous = pedestal.getHeldItem().copy();

        pedestal.setHeldItem(inserted);

        if (!previous.isEmpty()) {
            if (!player.getInventory().add(previous)) {
                player.drop(previous, false);
            }
        }

        if (!player.getAbilities().instabuild) {
            heldStack.shrink(1);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hit
    ) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (!(level.getBlockEntity(pos)
                instanceof EnergyPedestalBlockEntity pedestal)
                || !pedestal.hasHeldItem()) {
            return InteractionResult.PASS;
        }

        ItemStack extracted = pedestal.getHeldItem().copy();
        pedestal.clearHeldItem();

        if (!player.getInventory().add(extracted)) {
            player.drop(extracted, false);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected List<ItemStack> getDrops(
            BlockState state,
            LootParams.Builder params
    ) {
        List<ItemStack> drops = new ArrayList<>();

        BlockEntity blockEntity = params.getOptionalParameter(
                net.minecraft.world.level.storage.loot.parameters.LootContextParams.BLOCK_ENTITY
        );

        int storedPE = 0;

        if (blockEntity instanceof EnergyPedestalBlockEntity pedestal) {
            storedPE = pedestal.getPE();

            if (pedestal.hasHeldItem()) {
                drops.add(pedestal.getHeldItem().copy());
            }
        }

        ItemStack pedestalStack = EnergyItems.createStack(storedPE);
        pedestalStack.set(
                ModDataComponents.POTENTIAL_ENERGY,
                Math.max(0, Math.min(storedPE, EnergyItems.MAX_PE))
        );

        drops.add(pedestalStack);
        return drops;
    }

    @Override
    public void setPlacedBy(
            Level level,
            BlockPos pos,
            BlockState state,
            LivingEntity placer,
            ItemStack stack
    ) {
        super.setPlacedBy(level, pos, state, placer, stack);

        if (level.isClientSide()) {
            return;
        }

        if (level.getBlockEntity(pos)
                instanceof EnergyPedestalBlockEntity pedestal) {
            int storedPE = stack.getOrDefault(
                    ModDataComponents.POTENTIAL_ENERGY,
                    0
            );

            pedestal.setPE(storedPE);
        }
    }
}
