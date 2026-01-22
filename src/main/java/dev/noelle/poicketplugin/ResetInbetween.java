package dev.noelle.poicketplugin;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import dev.noelle.poicketplugin.components.markers.ApplySicknessTimerMarker;
import dev.noelle.poicketplugin.managers.PlayableInbetweenManager;

import javax.annotation.Nonnull;

/**
 * This is an example command that will simply print the name of the plugin in chat when used.
 */
public class ResetInbetween extends CommandBase {
    private final String pluginName;
    private final String pluginVersion;
    public ComponentType<EntityStore, ApplySicknessTimerMarker> applySicknessTimerMarkerComponentType;

    public ResetInbetween(String pluginName, String pluginVersion, ComponentType<EntityStore, ApplySicknessTimerMarker> annoyingstupidtypethingy) {
        super("test", "Prints a test message from the " + pluginName + " plugin.");
        this.setPermissionGroup(GameMode.Adventure); // Allows the command to be used by anyone, not just OP
        this.pluginName = pluginName;
        this.pluginVersion = pluginVersion;
        this.applySicknessTimerMarkerComponentType = annoyingstupidtypethingy;
    }

    @Override
    protected void executeSync(@Nonnull CommandContext ctx) {
        PlayableInbetweenManager.resetWorld(Universe.get());
        ctx.sendMessage(Message.translation("server.poicket.commands.resetInbetween"));
    }
}