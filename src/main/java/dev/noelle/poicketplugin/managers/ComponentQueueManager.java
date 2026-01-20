package dev.noelle.poicketplugin.managers;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class ComponentQueueManager {
    private static LinkedHashMap<PlayerRef, ComponentType> queue;
    public ComponentQueueManager(){
        queue = new LinkedHashMap<>();
    }
    public static void addToComponentQueue(PlayerRef b, ComponentType a){
        queue.put(b, a);
    }
    public static LinkedHashMap<PlayerRef, ComponentType> getQueue(){
        return queue;
    }

}
