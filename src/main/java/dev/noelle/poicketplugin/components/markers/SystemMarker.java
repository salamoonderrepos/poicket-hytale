package dev.noelle.poicketplugin.components.markers;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jspecify.annotations.Nullable;

public class SystemMarker implements Component<EntityStore> {
    boolean disable;
    public SystemMarker(){
        this.disable = false;
    }
    public SystemMarker(SystemMarker clone){
        this.disable = clone.disable;
    }
    public void disable(){
        this.disable = true;
    }
    public void enable(){
        this.disable = false;
    }
    public boolean isDisabled(){
        return this.disable;
    }
    @Override
    public @Nullable Component clone() {
        return new SystemMarker(this);
    }
}
