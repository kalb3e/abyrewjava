
package com.kbd.abyssal_reawoken.entities;

import com.kbd.abyssal_reawoken.fams.GhoulFamily;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class GhoulEntity extends Drowned {

    public GhoulEntity(EntityType<? extends Drowned> type, Level level) {
        super(type, level);
    }

    public GhoulFamily getFamily() {
        return GhoulFamily.getFamily(getType());
    }

    public static AttributeSupplier.Builder createAttributes(GhoulFamily family) {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D * family.healthScale)
                .add(Attributes.MOVEMENT_SPEED, 0.23D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D * family.damageScale)
                .add(Attributes.FOLLOW_RANGE, 35.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0D));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        targetSelector.addGoal(1, new HurtByTargetGoal(this));
        targetSelector.addGoal(
                2,
                new NearestAttackableTargetGoal<>(this, Player.class, true)
        );
        targetSelector.addGoal(
                3,
                new NearestAttackableTargetGoal<>(
                        this,
                        LivingEntity.class,
                        10,
                        true,
                        false,
                        this::isAdditionalTarget
                )
        );
    }

    private boolean isAdditionalTarget(LivingEntity target) {
        if (target == this || target instanceof Player) {
            return false;
        }

        GhoulFamily family = getFamily();
        if (family == null) {
            return false;
        }

        ResourceLocation targetId =
                BuiltInRegistries.ENTITY_TYPE.getKey(target.getType());

        return family.getAdditionalTargets().contains(targetId);
    }

    @Override
    public void tick() {
        super.tick();

        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }

        GhoulFamily family = getFamily();
        if (family == null) {
            return;
        }

        GhoulFamily.DimensionTransformation transformation =
                family.getDimensionTransformation();

        if (transformation == null
                || !serverLevel.dimension().location().equals(transformation.dimension())) {
            return;
        }

        replaceEntity(serverLevel, this, transformation.replacement());
    }

    public static boolean replaceEntity(
            ServerLevel level,
            Entity original,
            ResourceLocation replacementId
    ) {
        EntityType<?> replacementType =
                BuiltInRegistries.ENTITY_TYPE.getOptional(replacementId).orElse(null);

        if (replacementType == null || replacementType == original.getType()) {
            return false;
        }

        Entity replacement = replacementType.create(level);
        if (replacement == null) {
            return false;
        }

        replacement.moveTo(
                original.getX(),
                original.getY(),
                original.getZ(),
                original.getYRot(),
                original.getXRot()
        );

        if (!level.addFreshEntity(replacement)) {
            return false;
        }

        original.discard();
        return true;
    }
}
