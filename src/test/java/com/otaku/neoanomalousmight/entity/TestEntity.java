package com.otaku.neoanomalousmight.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class TestEntity extends Mob {
    private static final EntityDataAccessor<Boolean> DATA_NO_AI = SynchedEntityData.defineId(TestEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_SILENT = SynchedEntityData.defineId(TestEntity.class, EntityDataSerializers.BOOLEAN);
    
    public TestEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
        this.setNoAi(true);
        this.setSilent(true);
        this.setInvulnerable(false);
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.0D);
    }
    
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_NO_AI, false);
        this.entityData.define(DATA_SILENT, false);
    }
    
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("NoAI", this.isNoAi());
        compoundTag.putBoolean("Silent", this.isSilent());
    }
    
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setNoAi(compoundTag.getBoolean("NoAI"));
        this.setSilent(compoundTag.getBoolean("Silent"));
    }
    
    @Override
    public void setNoAi(boolean noAi) {
        super.setNoAi(noAi);
        this.entityData.set(DATA_NO_AI, noAi);
    }
    
    @Override
    public boolean isNoAi() {
        return this.entityData.get(DATA_NO_AI);
    }
    
    @Override
    public void setSilent(boolean silent) {
        super.setSilent(silent);
        this.entityData.set(DATA_SILENT, silent);
    }
    
    @Override
    public boolean isSilent() {
        return this.entityData.get(DATA_SILENT);
    }
    
    @Override
    public boolean canCollideWith(Entity entity) {
        return super.canCollideWith(entity) && !(entity instanceof LivingEntity && this.isAlliedTo(entity));
    }
    
    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        // 暂时移除OUT_OF_WORLD伤害免疫逻辑，使用默认实现
        return super.isInvulnerableTo(damageSource);
    }
    
    public static boolean checkSpawnRules(EntityType<TestEntity> entityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, net.minecraft.util.RandomSource randomSource) {
        return serverLevelAccessor.getBlockState(blockPos.below()).isValidSpawn(serverLevelAccessor, blockPos.below(), entityType);
    }
}