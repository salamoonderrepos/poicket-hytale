package dev.noelle.poicketplugin;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.AddPlayerToWorldEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerMouseButtonEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import dev.noelle.poicketplugin.components.markers.ApplySicknessTimerMarker;
import dev.noelle.poicketplugin.components.markers.RemoveSicknessTimerMarker;
import dev.noelle.poicketplugin.components.timers.SicknessTimerComponent;
import dev.noelle.poicketplugin.ecs.BedExplosion;
import dev.noelle.poicketplugin.ecs.NoDeathInTheInbetween;
import dev.noelle.poicketplugin.managers.PlayableInbetweenManager;
import dev.noelle.poicketplugin.systems.SicknessSystem;
import dev.noelle.poicketplugin.managers.InbetweenPlayerPopulationManager;
import dev.noelle.poicketplugin.systems.TimerSystem;

public class PoicketMainPlugin extends JavaPlugin {
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public ComponentType<EntityStore, SicknessTimerComponent> sicknessTimerComponentType;
    public ComponentType<EntityStore, ApplySicknessTimerMarker> applySicknessTimerMarkerComponentType;
    public ComponentType<EntityStore, RemoveSicknessTimerMarker> removeSicknessTimerMarkerComponentType;

    public PoicketMainPlugin(JavaPluginInit init) {
        super(init);
        LOGGER.atInfo().log("Hello from %s version %s", this.getName(), this.getManifest().getVersion().toString());
    }
    @Override
    protected void start(){
        PlayableInbetweenManager.initialize();
    }
    @Override
    protected void setup() {
        getEventRegistry().registerGlobal(AddPlayerToWorldEvent.class, InbetweenPlayerPopulationManager::playerEntersWorld);

        getEntityStoreRegistry().registerSystem(new NoDeathInTheInbetween());
        sicknessTimerComponentType = this.getEntityStoreRegistry().registerComponent(SicknessTimerComponent.class, SicknessTimerComponent::new);
        applySicknessTimerMarkerComponentType = this.getEntityStoreRegistry().registerComponent(ApplySicknessTimerMarker.class, ApplySicknessTimerMarker::new);
        removeSicknessTimerMarkerComponentType = this.getEntityStoreRegistry().registerComponent(RemoveSicknessTimerMarker.class, RemoveSicknessTimerMarker::new);

        InbetweenPlayerPopulationManager.initializeComponentType(applySicknessTimerMarkerComponentType, removeSicknessTimerMarkerComponentType);



        this.getCommandRegistry().registerCommand(new ExampleCommand(this.getName(), this.getManifest().getVersion().toString(), applySicknessTimerMarkerComponentType));
        getEntityStoreRegistry().registerSystem(new BedExplosion());
        getEntityStoreRegistry().registerSystem(
                new TimerSystem<>(
                        sicknessTimerComponentType
                )
        );
        getEntityStoreRegistry().registerSystem(
                new SicknessSystem(
                        sicknessTimerComponentType,
                        applySicknessTimerMarkerComponentType,
                        removeSicknessTimerMarkerComponentType,
                        "DimensionalSicknessStatus"
                )
        );







    }
}
