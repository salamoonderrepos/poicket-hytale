package dev.noelle.poicketplugin.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerMouseButtonEvent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import dev.noelle.poicketplugin.components.markers.ApplySicknessTimerMarker;
import dev.noelle.poicketplugin.components.markers.RemoveSicknessTimerMarker;
import dev.noelle.poicketplugin.components.timers.SicknessTimerComponent;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import static com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil.getLogger;

public class SicknessSystem extends EntityTickingSystem<EntityStore> {
    private final ComponentType<EntityStore, SicknessTimerComponent> sicknessTimerType;
    private final ComponentType<EntityStore, ApplySicknessTimerMarker> applySicknessTimerMarkerComponentType;
    private final ComponentType<EntityStore, RemoveSicknessTimerMarker> removeSicknessTimerMarkerComponentType;
    private final String effectID;
    public SicknessSystem(
            ComponentType<EntityStore, SicknessTimerComponent> t,
            ComponentType<EntityStore, ApplySicknessTimerMarker> a,
            ComponentType<EntityStore, RemoveSicknessTimerMarker> b,
            String effect){
        super();
        sicknessTimerType = t;
        applySicknessTimerMarkerComponentType = a;
        removeSicknessTimerMarkerComponentType = b;
        effectID = effect;
    }
    private void addSicknessEffect(String effectId, Ref<EntityStore> ref, CommandBuffer<EntityStore> buffer){
        EntityEffect sicknessEffect = new EntityEffect(effectId);
        EffectControllerComponent controller = buffer.getComponent(ref, EffectControllerComponent.getComponentType());
        controller.addInfiniteEffect(
                ref,
                EntityEffect.getAssetMap().getIndex(effectId),
                sicknessEffect,
                buffer

        );

    }
    private void removeSicknessEffect(String effectId, Ref<EntityStore> ref, CommandBuffer<EntityStore> buffer){
        EffectControllerComponent controller = buffer.getComponent(ref, EffectControllerComponent.getComponentType());
        assert controller != null;
        controller.removeEffect(
                ref,
                EntityEffect.getAssetMap().getIndex(effectId),
                buffer

        );

    }
    private void applySickness(Ref<EntityStore> ref, CommandBuffer<EntityStore> buffer){
        buffer.addComponent(ref, sicknessTimerType);



    }
    private void removeSickness(Ref<EntityStore> ref, CommandBuffer<EntityStore> buffer){
        removeSicknessEffect(effectID, ref, buffer);


        SicknessTimerComponent timer = buffer.getComponent(ref, sicknessTimerType);
        if (doesComponentExist(timer)){
            timer.pause();
            timer.markForCompletion();
        }


    }
    private boolean doesComponentExist(Component a){
        return a != null;
    }
    private void handleTimer(SicknessTimerComponent sicknessTimerComponent, Ref<EntityStore> ref, CommandBuffer<EntityStore> buffer){

        if (!sicknessTimerComponent.isDone()) return;

        addSicknessEffect(effectID, ref, buffer);

        getLogger().info("Marked timer for completion!");
        sicknessTimerComponent.markForCompletion();


    }
    @Override
    public void tick(float dt, int index, @NonNull ArchetypeChunk<EntityStore> chunk, @NonNull Store<EntityStore> store, @NonNull CommandBuffer<EntityStore> commandBuffer) {
        // hytale runs at 30 ticks a second
        // mutliply elapsed time by 30 to get how many seconds have passed
        Ref<EntityStore> ref = chunk.getReferenceTo(index);
        Player player = store.getComponent(ref, Player.getComponentType());

        SicknessTimerComponent sicknessTimerComponent = store.getComponent(ref, sicknessTimerType);
        ApplySicknessTimerMarker applySicknessTimerMarkerComponent = store.getComponent(ref, applySicknessTimerMarkerComponentType);
        RemoveSicknessTimerMarker removeSicknessTimerMarkerComponent = store.getComponent(ref, removeSicknessTimerMarkerComponentType);
        //getLogger().info("checking for component existances");
        if (doesComponentExist(sicknessTimerComponent)){
            getLogger().info("Timer being handled!");
            handleTimer(sicknessTimerComponent, ref, commandBuffer);
        }
        if (doesComponentExist(applySicknessTimerMarkerComponent)){
            getLogger().info("Found marker component! Applying sickness!");
            applySickness(ref, commandBuffer);
            commandBuffer.removeComponent(ref, applySicknessTimerMarkerComponentType);
        }
        if (doesComponentExist(removeSicknessTimerMarkerComponent)){
            getLogger().info("Found marker component! Removing sickness!");
            removeSickness(ref, commandBuffer);
            commandBuffer.removeComponent(ref, removeSicknessTimerMarkerComponentType);
        }



    }

    @Override
    public @Nullable Query<EntityStore> getQuery() {
        return Query.or(removeSicknessTimerMarkerComponentType, applySicknessTimerMarkerComponentType, sicknessTimerType);
    }
}
