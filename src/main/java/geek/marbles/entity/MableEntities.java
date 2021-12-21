package geek.marbles.entity;

import geek.marbles.Marbles;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by Robin Seifert on 12/21/2021.
 */
public class MableEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Marbles.ID);

    public static final RegistryObject<EntityType<MarbleEntity>> MARBLE = ENTITIES.register("marble",
            () -> EntityType.Builder.<MarbleEntity>of(MarbleEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(64).build("mable"));
}
