package dev.noelle.poicketplugin.systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.component.system.tick.TickingSystem;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import dev.noelle.poicketplugin.components.timers.LivingInWorldTimerComponent;
import dev.noelle.poicketplugin.managers.ComponentQueueManager;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil.getLogger;

public class ComponentQueueSystem extends TickingSystem<EntityStore> {

    public ComponentQueueSystem(){
        super();
    }

    @Override
    public void tick(float dt, int index, @NonNull Store<EntityStore> store) {
        //nothin yet

    }

}
