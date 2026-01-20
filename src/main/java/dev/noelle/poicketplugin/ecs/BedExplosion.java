package dev.noelle.poicketplugin.ecs;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.EntityEventSystem;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.InteractionManager;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.ecs.PlaceBlockEvent;
import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.universe.world.worldgen.WorldGenLoadException;
import dev.noelle.poicketplugin.managers.PlayableInbetweenManager;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import static com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil.getLogger;

public class BedExplosion extends EntityEventSystem<EntityStore, PlaceBlockEvent> {
    public BedExplosion() {
        super(PlaceBlockEvent.class);
    }

    @Override
    public void handle(int i, @NonNull ArchetypeChunk<EntityStore> archetypeChunk, @NonNull Store<EntityStore> store, @NonNull CommandBuffer<EntityStore> commandBuffer, @NonNull PlaceBlockEvent event) {
        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(i);
        Player player = store.getComponent(ref, Player.getComponentType());
        InteractionManager manager = store.getComponent(ref, InteractionModule.get().getInteractionManagerComponent());
        World playerworld = player.getWorld();
        //getLogger().info("Checkn if ur inbetween");
        getLogger().info(playerworld.toString());
        getLogger().info(playerworld.getWorldConfig().toString());
        getLogger().info(playerworld.getWorldConfig().getWorldGenProvider().toString());

        if (playerworld.getName().contains("Inbetween")){
            //getLogger().info("Yes you are in the inbetween!");
            //getLogger().info("Checkn if u got the right item in hand");
            if (event.getItemInHand().getItemId().equals("Furniture_Crude_Bed")){
                // the only way to get to here would be if they interacted with a block
                player.sendMessage(Message.raw("I can't figure out how to make the beds explode. Still, no beds."));
                event.setCancelled(true);
            } else {
                getLogger().info(event.getItemInHand().getItemId());
            }
        }
    }

    @Override
    public @Nullable Query<EntityStore> getQuery() {
        return Query.any();
    }
}
