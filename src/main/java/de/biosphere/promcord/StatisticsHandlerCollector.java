package de.biosphere.promcord;

import io.prometheus.client.Collector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StatisticsHandlerCollector extends Collector {

    private final Promcord bot;
    private static final List<String> EMPTY_LIST = new ArrayList<>();

    public StatisticsHandlerCollector(final Promcord bot) {
        this.bot = bot;
    }

    @Override
    public List<MetricFamilySamples> collect() {
        final long rest_ping = bot.getJDA().getRestPing().complete();
        return Arrays.asList(buildGauge("discord_ping_websocket",
                "Time in milliseconds between heartbeat and the heartbeat ack response", bot.getJDA().getGatewayPing()),
                buildGauge("discord_ping_rest",
                        "The time in milliseconds that discord took to respond to a REST request.", rest_ping),
                buildGauge("discord_guilds", "Amount of all Guilds that the bot is connected to. ",
                        bot.getJDA().getGuilds().size()));
    }

    private MetricFamilySamples buildGauge(String name, String help, double value) {
        return new MetricFamilySamples(name, Type.GAUGE, help,
                Collections.singletonList(new MetricFamilySamples.Sample(name, EMPTY_LIST, EMPTY_LIST, value)));
    }
}
