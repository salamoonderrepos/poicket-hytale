package dev.noelle.poicketplugin.components.timers;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class LivingInWorldTimerComponent implements Component<EntityStore> {
    private int ticksUntilComplete;
    private boolean markedForCompletion;

    private boolean pause;
    public LivingInWorldTimerComponent(int tickstocompletion){
        this.ticksUntilComplete = tickstocompletion;
    }
    public LivingInWorldTimerComponent(LivingInWorldTimerComponent component){
        this.ticksUntilComplete = component.ticksUntilComplete;
        this.pause = false;
    }
    @Override
    public @Nullable Component<EntityStore> clone() {
        return new LivingInWorldTimerComponent(this);
    }

    public int getTicksUntilComplete() {
        return ticksUntilComplete;
    }

    public void tickDown() {
        ticksUntilComplete = ticksUntilComplete - 1;
    }

    @NotNull
    public boolean isPaused() {
        return pause;
    }
    public boolean isMarkedForCompletion() {
        return markedForCompletion;
    }
    public void markForCompletion() {
        markedForCompletion = true;
    }
    public void pause() {
        pause = true;
    }

    public void unpause() {
        pause = true;
    }

    public boolean isDone() {
        return ticksUntilComplete <= 0;
    }

}
