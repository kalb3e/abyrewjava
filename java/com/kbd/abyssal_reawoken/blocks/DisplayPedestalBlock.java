package com.kbd.abyssal_reawoken.blocks;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.ModDataComponents;
import com.kbd.abyssal_reawoken.blockentity.DisplayPedestalBlockEntity;
import com.kbd.abyssal_reawoken.blockentity.RitualPedestalBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import javax.annotation.Nullable;
import java.util.List;

@net.minecraft.MethodsReturnNonnullByDefault
@javax.annotation.ParametersAreNonnullByDefault
public class DisplayPedestalBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<DisplayPedestalBlock> CODEC = simpleCodec(DisplayPedestalBlock::new);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public DisplayPedestalBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() { return CODEC; }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) { return SHAPE; }

    @Override
    protected RenderShape getRenderShape(BlockState state) { return RenderShape.MODEL; }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DisplayPedestalBlockEntity(pos, state);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (hand == InteractionHand.OFF_HAND) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity == null) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        // Dynamically parse out item contents depending on which pedestal type is placed down
        ItemStack heldItem = ItemStack.EMPTY;
        if (blockEntity instanceof DisplayPedestalBlockEntity pedestal) {
            heldItem = pedestal.getHeldItem();
        } else if (blockEntity instanceof RitualPedestalBlockEntity tierPedestal) {
            heldItem = tierPedestal.getHeldItem();
        } else {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!level.isClientSide()) {
            /* Put one item onto the pedestal. */
            if (heldItem.isEmpty() && !stack.isEmpty()) {
                ItemStack displayStack = stack.copyWithCount(1);

                if (blockEntity instanceof DisplayPedestalBlockEntity pedestal) {
                    pedestal.setHeldItem(displayStack);
                } else if (blockEntity instanceof RitualPedestalBlockEntity tierPedestal) {
                    tierPedestal.setHeldItem(displayStack);
                }

                if (!player.isCreative()) {
                    stack.shrink(1);
                }
                return ItemInteractionResult.SUCCESS;
            }
            /* Take the item back. */
            if (!heldItem.isEmpty()) {
                if (!player.addItem(heldItem.copy())) {
                    player.drop(heldItem.copy(), false);
                }

                if (blockEntity instanceof DisplayPedestalBlockEntity pedestal) {
                    pedestal.setHeldItem(ItemStack.EMPTY);
                } else if (blockEntity instanceof RitualPedestalBlockEntity tierPedestal) {
                    tierPedestal.setHeldItem(ItemStack.EMPTY);
                }
                return ItemInteractionResult.SUCCESS;
            }
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            ItemStack heldItem = ItemStack.EMPTY;

            if (blockEntity instanceof DisplayPedestalBlockEntity pedestal) {
                heldItem = pedestal.getHeldItem();
            } else if (blockEntity instanceof RitualPedestalBlockEntity tierPedestal) {
                heldItem = tierPedestal.getHeldItem();
            }

            if (!heldItem.isEmpty()) {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), heldItem);
            }
            level.updateNeighbourForOutputSignal(pos, this);
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        ItemStack dropItem = new ItemStack(this.asItem());
        BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof DisplayPedestalBlockEntity pedestal) {
            CompoundTag tag = new CompoundTag();
            pedestal.saveAdditional(tag, builder.getLevel().registryAccess());
            tag.remove("PE");
            tag.remove("PotentialEnergy");
            if (!tag.isEmpty()) {
                dropItem.set(DataComponents.BLOCK_ENTITY_DATA, CustomData.of(tag));
            }
            dropItem.set(ModDataComponents.POTENTIAL_ENERGY.get(), pedestal.getPE());
        }
        return List.of(dropItem);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        ItemStack stack = super.getCloneItemStack(state, target, level, pos, player);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof DisplayPedestalBlockEntity pedestal) {
            CompoundTag tag = new CompoundTag();
            pedestal.saveAdditional(tag, player.level().registryAccess());
            tag.remove("PE");
            tag.remove("PotentialEnergy");
            if (!tag.isEmpty()) {
                stack.set(DataComponents.BLOCK_ENTITY_DATA, CustomData.of(tag));
            }
            stack.set(ModDataComponents.POTENTIAL_ENERGY.get(), pedestal.getPE());
        }
        return stack;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, AbyssalReawoken.RITUAL_PEDESTAL_BE.get(), (lvl, pos, blockState, blockEntity) -> {
            if (blockEntity instanceof DisplayPedestalBlockEntity pedestal) {
                pedestal.tick(lvl, pos, blockState);
            }
        });
    }
}
