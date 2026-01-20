package dev.noelle.poicketplugin.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import dev.noelle.poicketplugin.components.timers.LivingInWorldTimerComponent;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import static com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil.getLogger;

public class TimerSystem<T extends LivingInWorldTimerComponent> extends EntityTickingSystem<EntityStore> {
    private final ComponentType<EntityStore, T> timerType;
    public TimerSystem(ComponentType<EntityStore, T> type){
        super();
        timerType = type;
    }
    @Override
    public void tick(float dt, int index, @NonNull ArchetypeChunk<EntityStore> chunk, @NonNull Store<EntityStore> store, @NonNull CommandBuffer<EntityStore> commandBuffer) {
        // hytale runs at 30 ticks a second
        // mutliply elapsed time by 30 to get how many seconds have passed

        Ref<EntityStore> ref = chunk.getReferenceTo(index);
        T timerComponent = chunk.getComponent(index, timerType);

        // okay so basically
        // tick down the timer each tick
        // once the timer is done, stop messing with it until some other system marks it for completion
        if (timerComponent != null) {
            if ((!timerComponent.isPaused()) && (!timerComponent.isDone())) {
                //getLogger().info("Timer was ticked down! This works!");
                //getLogger().info("The type has this many ticks left: " + timerComponent.getTicksUntilComplete());
                timerComponent.tickDown();
            }
            if (timerComponent.isMarkedForCompletion()) {
                getLogger().info("Removing marked timer!");
                commandBuffer.removeComponent(ref, timerType);
            }
        }
    }

    @Override
    public @Nullable Query getQuery() {
        return Query.and(timerType);
    }
}
