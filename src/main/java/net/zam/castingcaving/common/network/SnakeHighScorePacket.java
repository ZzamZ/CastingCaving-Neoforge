package net.zam.castingcaving.common.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.zam.castingcaving.CastingCaving;

public class SnakeHighScorePacket implements CustomPacketPayload {
    public static final Type<SnakeHighScorePacket> TYPE = new Type<>(CastingCaving.id("snake_high_score"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SnakeHighScorePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            SnakeHighScorePacket::getScore,
            SnakeHighScorePacket::new
    );

    private final int score;

    public SnakeHighScorePacket(int score) {
        this.score = score;
    }

    @Override
    public Type<SnakeHighScorePacket> type() {
        return TYPE;
    }

    public static void handle(SnakeHighScorePacket packet, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) ctx.player();
            if (player != null) {
                CastingCaving.LOGGER.info("Received score: " + packet.score + " for player: " + player.getName().getString());
                CastingCaving.SNAKE_HIGH_SCORE_TRIGGER.get().trigger(player, packet.score);
            }
        });
    }

    public int getScore() {
        return score;
    }
}