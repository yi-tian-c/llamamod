package com.yi_tian.entity;

public class ModEntityType {
//    private final FeatureSet requiredFeatures;
//    private final ModEntityType.EntityFactory<T> m_factory;
//    private final ImmutableSet<Block> m_canSpawnInside;
//    private final SpawnGroup m_spawnGroup;
//    private final boolean m_spawnableFarFromPlayer;
//    private final boolean m_saveable;
//    private final boolean m_summonable;
//    private final boolean m_fireImmune;
//    private final int m_maxTrackDistance;
//    private final int m_trackTickInterval;
//    private final EntityDimensions m_dimensions;
//
//    public ModEntityType(
//            ModEntityType.EntityFactory<T> factory,
//            SpawnGroup spawnGroup,
//            boolean saveable,
//            boolean summonable,
//            boolean fireImmune,
//            boolean spawnableFarFromPlayer,
//            ImmutableSet<Block> canSpawnInside,
//            EntityDimensions dimensions,
//            int maxTrackDistance,
//            int trackTickInterval,
//            FeatureSet requiredFeatures
//    ) {
//        this.m_factory = factory;
//        this.m_spawnGroup = spawnGroup;
//        this.m_spawnableFarFromPlayer = spawnableFarFromPlayer;
//        this.m_saveable = saveable;
//        this.m_summonable = summonable;
//        this.m_fireImmune = fireImmune;
//        this.m_canSpawnInside = canSpawnInside;
//        this.m_dimensions = dimensions;
//        this.m_maxTrackDistance = maxTrackDistance;
//        this.m_trackTickInterval = trackTickInterval;
//        this.requiredFeatures = requiredFeatures;
//    }
//    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
//        return Registry.register(Registries.ENTITY_TYPE, id, type.build(id));
//    }
//    @Override
//    public FeatureSet getRequiredFeatures() {
//        return this.requiredFeatures;
//    }
//
//    @Override
//    public @Nullable T downcast(Entity obj) {
//        return null;
//    }
//
//    @Override
//    public Class<? extends Entity> getBaseClass() {
//        return null;
//    }
//    public interface EntityFactory<T extends Entity> {
//        T create(EntityType<T> type, World world);
//    }
//    public static class Builder<T extends Entity> implements net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType.Builder<T> {
//        private final ModEntityType.EntityFactory<T> factory;
//        private final SpawnGroup spawnGroup;
//        private ImmutableSet<Block> canSpawnInside = ImmutableSet.of();
//        private boolean saveable = true;
//        private boolean summonable = true;
//        private boolean fireImmune;
//        private boolean spawnableFarFromPlayer;
//        private int maxTrackingRange = 5;
//        private int trackingTickInterval = 3;
//        private EntityDimensions dimensions = EntityDimensions.changing(0.6F, 1.8F);
//        private FeatureSet requiredFeatures = FeatureFlags.VANILLA_FEATURES;
//
//        private Builder(ModEntityType.EntityFactory<T> factory, SpawnGroup spawnGroup) {
//            this.factory = factory;
//            this.spawnGroup = spawnGroup;
//            this.spawnableFarFromPlayer = spawnGroup == SpawnGroup.CREATURE || spawnGroup == SpawnGroup.MISC;
//        }
//
//        public static <T extends Entity> ModEntityType.Builder<T> create(ModEntityType.EntityFactory<T> factory, SpawnGroup spawnGroup) {
//            return new ModEntityType.Builder<>(factory, spawnGroup);
//        }
//
//        public static <T extends Entity> ModEntityType.Builder<T> create(SpawnGroup spawnGroup) {
//            return new ModEntityType.Builder<>((type, world) -> null, spawnGroup);
//        }
//
//        public ModEntityType.Builder<T> setDimensions(float width, float height) {
//            this.dimensions = EntityDimensions.changing(width, height);
//            return this;
//        }
//
//        public ModEntityType.Builder<T> disableSummon() {
//            this.summonable = false;
//            return this;
//        }
//
//        public ModEntityType.Builder<T> disableSaving() {
//            this.saveable = false;
//            return this;
//        }
//
//        public ModEntityType.Builder<T> makeFireImmune() {
//            this.fireImmune = true;
//            return this;
//        }
//
//        /**
//         * Allows this type of entity to spawn inside the given block, bypassing the default
//         * wither rose, sweet berry bush, cactus, and fire-damage-dealing blocks for
//         * non-fire-resistant mobs.
//         *
//         * <p>{@code minecraft:prevent_mob_spawning_inside} tag overrides this.
//         * With this setting, fire resistant mobs can spawn on/in fire damage dealing blocks,
//         * and wither skeletons can spawn in wither roses. If a block added is not in the default
//         * blacklist, the addition has no effect.
//         */
//        public ModEntityType.Builder<T> allowSpawningInside(Block... blocks) {
//            this.canSpawnInside = ImmutableSet.copyOf(blocks);
//            return this;
//        }
//
//        public ModEntityType.Builder<T> spawnableFarFromPlayer() {
//            this.spawnableFarFromPlayer = true;
//            return this;
//        }
//
//        public ModEntityType.Builder<T> maxTrackingRange(int maxTrackingRange) {
//            this.maxTrackingRange = maxTrackingRange;
//            return this;
//        }
//
//        public ModEntityType.Builder<T> trackingTickInterval(int trackingTickInterval) {
//            this.trackingTickInterval = trackingTickInterval;
//            return this;
//        }
//
//        public ModEntityType.Builder<T> requires(FeatureFlag... features) {
//            this.requiredFeatures = FeatureFlags.FEATURE_MANAGER.featureSetOf(features);
//            return this;
//        }
//
//        public ModEntityType<T> build(String id) {
//            if (this.saveable) {
//                Util.getChoiceType(TypeReferences.ENTITY_TREE, id);
//            }
//
//            return new ModEntityType<>(
//                    this.factory,
//                    this.spawnGroup,
//                    this.saveable,
//                    this.summonable,
//                    this.fireImmune,
//                    this.spawnableFarFromPlayer,
//                    this.canSpawnInside,
//                    this.dimensions,
//                    this.maxTrackingRange,
//                    this.trackingTickInterval,
//                    this.requiredFeatures
//            );
//        }
//    }
}
