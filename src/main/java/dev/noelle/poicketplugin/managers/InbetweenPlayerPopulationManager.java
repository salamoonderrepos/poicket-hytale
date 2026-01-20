package dev.noelle.poicketplugin.managers;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Holder;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.AddPlayerToWorldEvent;
import com.hypixel.hytale.server.core.event.events.player.DrainPlayerFromWorldEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import dev.noelle.poicketplugin.components.markers.ApplySicknessTimerMarker;
import dev.noelle.poicketplugin.components.markers.RemoveSicknessTimerMarker;
import dev.noelle.poicketplugin.components.timers.SicknessTimerComponent;

import java.util.Collection;
import java.util.HashSet;

import static com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil.getLogger;


public class InbetweenPlayerPopulationManager{
    private static ComponentType<EntityStore, ApplySicknessTimerMarker> applySicknessTimerMarkerComponentType;
    private static ComponentType<EntityStore, RemoveSicknessTimerMarker> removeSicknessTimerMarkerComponentType;


    public static void initializeComponentType(
            ComponentType<EntityStore, ApplySicknessTimerMarker> applyct,
            ComponentType<EntityStore, RemoveSicknessTimerMarker> removect

            )
    {
        applySicknessTimerMarkerComponentType = applyct;
        removeSicknessTimerMarkerComponentType = removect;
    }

    public static void playerEntersWorld(AddPlayerToWorldEvent addPlayerToWorldEvent) {
        Holder<EntityStore> holder = addPlayerToWorldEvent.getHolder();
        getLogger().info("Player joined new world!!");
        if (!(addPlayerToWorldEvent.getWorld().getName().contains("Inbetween"))) {
            holder.addComponent(removeSicknessTimerMarkerComponentType, new RemoveSicknessTimerMarker());
            return;
        }
        getLogger().info("It's the inbetween!!");

        holder.addComponent(applySicknessTimerMarkerComponentType, new ApplySicknessTimerMarker());
        // switch to this once unified world is setup


    }



}
