package dev.noelle.poicketplugin.ecs;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.UUIDComponent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.ecs.DamageBlockEvent;
import com.hypixel.hytale.server.core.event.events.ecs.PlaceBlockEvent;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageEventSystem;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageModule;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.universe.world.worldgen.WorldGenLoadException;
import dev.noelle.poicketplugin.managers.PlayableInbetweenManager;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import static com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil.getLogger;

public class NoDeathInTheInbetween extends DamageEventSystem {

    @Override
    public void handle(int i, @NonNull ArchetypeChunk<EntityStore> chunk, @NonNull Store<EntityStore> store, @NonNull CommandBuffer<EntityStore> buffer, @NonNull Damage damage) {
        Ref<EntityStore> targetRef = chunk.getReferenceTo(i);
        UUIDComponent component = store.getComponent(targetRef, UUIDComponent.getComponentType());
        Player playercomp = store.getComponent(targetRef, Player.getComponentType());
        int inexplicableExplosionIndex = DamageCause.getAssetMap().getIndex("InexplicableExplosion");
        int inbetweenEventualDeathIndex = DamageCause.getAssetMap().getIndex("InbetweenEventualDeath");
        int damageCause = damage.getDamageCauseIndex();
        assert component != null;
        if (playercomp != null) {
            if (playercomp.getWorld().getName().contains("Inbetween")) {
                if ((damageCause != inexplicableExplosionIndex) && (damageCause != inbetweenEventualDeathIndex)) {
                    damage.setAmount(0);
                    EntityStatMap a = store.getComponent(targetRef, EntityStatMap.getComponentType());
                }
            }
        }
    }

    @Override
    @Nullable
    public Query<EntityStore> getQuery() {
        return Query.and();
    }

    @Override
    @Nullable
    public SystemGroup<EntityStore> getGroup() {
        return DamageModule.get().getFilterDamageGroup();
    }
}
