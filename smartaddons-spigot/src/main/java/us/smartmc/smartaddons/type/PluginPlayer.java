package us.smartmc.smartaddons.type;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public abstract class PluginPlayer<P> implements IPluginPlayer<P> {

    private static final HashMap<Object, PluginPlayer<?>> registry = new HashMap<>();

    protected final P player;

    public PluginPlayer(P player) {
        this.player = player;
        registry.put(player, this);
    }

    @Override
    public P getPlayer() {
        return player;
    }

    public static <T extends PluginPlayer<?>, P> T get(Class<T> type, P player) {
        if (!registry.containsKey(player)) {
            try {
                type.getDeclaredConstructors()[0].newInstance(player);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return (T) registry.get(player);
    }

}
