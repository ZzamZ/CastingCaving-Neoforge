package net.zam.castingcaving.common.enchantment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import net.zam.castingcaving.util.enchant.FireworkRocketEntityAttacher;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.item.component.FireworkExplosion;
import it.unimi.dsi.fastutil.ints.IntList;

public record FireworksEnchantment(LevelBasedValue.Constant level) implements EnchantmentEntityEffect {

    public static final MapCodec<FireworksEnchantment> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.INT.fieldOf("level").forGetter(e -> (int) e.level.value())
            ).apply(instance, intVal ->
                    new FireworksEnchantment(LevelBasedValue.constant(intVal))
            )
    );

    @Override
    public void apply(ServerLevel serverLevel, int lvl, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 hitPos) {
        if (!(entity instanceof LivingEntity target)) return;

        // Build an ItemStack that actually has "flight" > 0 and at least one explosion,
        // so the rocket will ascend and produce normal fireworks visuals.
        ItemStack fireworkStack = createFireworkStackWithRandomExplosions(lvl);

        // Spawn our custom rocket that overrides the "sit" pose and kills the passenger on explosion
        FireworkRocketEntityAttacher rocket = new FireworkRocketEntityAttacher(
                serverLevel,
                entity.getX(),
                entity.getY(),
                entity.getZ(),
                fireworkStack
        );

        // Make the mob ride the rocket
        target.startRiding(rocket);

        // Optional initial upward boost
        target.setDeltaMovement(target.getDeltaMovement().add(0, 0.2 + (lvl * 0.1), 0));

        serverLevel.addFreshEntity(rocket);
    }

    // Creates a fireworks ItemStack with a random flight value and some random explosions
    private ItemStack createFireworkStackWithRandomExplosions(int enchantLevel) {
        ItemStack stack = new ItemStack(Items.FIREWORK_ROCKET);
        Random rand = new Random();

        // Random flight from 1..(1+enchantLevel), or pick your own formula
        byte flight = (byte) (1 + rand.nextInt(enchantLevel + 1));

        // Build random explosions
        List<FireworkExplosion> explosions = new ArrayList<>();
        int explosionCount = 1 + rand.nextInt(enchantLevel + 1);

        for (int i = 0; i < explosionCount; i++) {
            FireworkExplosion.Shape shape = switch (rand.nextInt(5)) {
                case 0 -> FireworkExplosion.Shape.SMALL_BALL;
                case 1 -> FireworkExplosion.Shape.LARGE_BALL;
                case 2 -> FireworkExplosion.Shape.STAR;
                case 3 -> FireworkExplosion.Shape.CREEPER;
                default -> FireworkExplosion.Shape.BURST;
            };

            int[] colorsArray = new int[rand.nextInt(3) + 1];
            for (int c = 0; c < colorsArray.length; c++) {
                colorsArray[c] = rand.nextInt(0xFFFFFF);
            }

            int[] fadeColorsArray = rand.nextBoolean() ? new int[rand.nextInt(2) + 1] : new int[0];
            for (int c = 0; c < fadeColorsArray.length; c++) {
                fadeColorsArray[c] = rand.nextInt(0xFFFFFF);
            }

            FireworkExplosion explosion = new FireworkExplosion(
                    shape,
                    IntList.of(colorsArray),
                    IntList.of(fadeColorsArray),
                    rand.nextBoolean(),
                    rand.nextBoolean()
            );
            explosions.add(explosion);
        }

        // Store flight + explosions in the rocket's data
        stack.set(DataComponents.FIREWORKS, new Fireworks(flight, explosions));
        return stack;
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
