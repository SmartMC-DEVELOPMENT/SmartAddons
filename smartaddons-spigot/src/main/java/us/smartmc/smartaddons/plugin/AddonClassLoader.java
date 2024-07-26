
package us.smartmc.smartaddons.plugin;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddonClassLoader extends URLClassLoader {

    private static AddonClassLoader addonClassLoader;

    public final static Map<String, File> pluginsJars = new HashMap<>();
    private final static Map<String, AbstractAddonInfo> modulesInfo = new HashMap<>();
    private final static Map<String, IAddonPlugin> loadedModules = new HashMap<>();

    public final static HashMap<String, AddonPlugin> plugins = new HashMap<>();

    public AddonClassLoader(URL[] urls) {
        super(urls, AddonPlugin.class.getClassLoader());
    }

    @Override
    protected void addURL(URL url) {
        super.addURL(url);
    }

    public static void unregisterAddon(String addonName) {
        String className = plugins.get(addonName).getClass().getName();
        plugins.remove(addonName);
        loadedModules.remove(className);
        modulesInfo.remove(addonName);
        pluginsJars.remove(addonName);
    }

    private static String loadAddonInfo(File file) throws Exception {
        URL jarUrl = file.toURI().toURL();
        URL[] urls = new URL[]{jarUrl};
        URLClassLoader jarLoader = new URLClassLoader(urls);
        JsonObject jsonObject = readJsonObject(jarLoader, "plugin.json");
        String mainClass = jsonObject.get("main").getAsString();
        String name = jsonObject.get("name").getAsString();
        JsonArray dependenciesArray = jsonObject.has("dependencies") ? jsonObject.getAsJsonArray("dependencies") : new JsonArray();
        List<String> dependencies = new ArrayList<>();
        for (JsonElement dependency : dependenciesArray) {
            dependencies.add(dependency.getAsString());
        }
        modulesInfo.put(name, new AbstractAddonInfo(file, mainClass, dependencies));
        return mainClass;
    }

    public static void loadModulesJars(File[] files) {
        for (File file : files) {
            if (file.getName().endsWith(".jar")) {
                try {
                    String infoId = loadAddonInfo(file);
                    System.out.println("Parsing file " + file.getName());
                    System.out.println("LOADMODULESJAR " + infoId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for (String moduleName : modulesInfo.keySet()) {
            try {
                System.out.println("LOADMODULESJAR 2 " + moduleName);
                loadModuleWithDependencies(moduleName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadModuleWithDependencies(String moduleName) {
        if (loadedModules.containsKey(moduleName)) {
            return; // Already loaded
        }
        AbstractAddonInfo moduleInfo = modulesInfo.get(moduleName);
        System.out.println("TRIED TO OBTAIN ADDON INFO OF " + moduleName);
        for (String dependency : moduleInfo.getDependencies()) {
            loadModuleWithDependencies(dependency); // Ensure dependencies are loaded first
        }
        loadPluginJar(moduleInfo.getFile(), true);
    }


    public static void loadPluginJar(File file, boolean start) {
        try {
            URL jarUrl = file.toURI().toURL();
            AddonClassLoader mainLoader = getAddonClassLoader(new URL[]{jarUrl});

            URL[] urls = new URL[]{jarUrl};
            URLClassLoader jarLoader = new URLClassLoader(urls);
            JsonObject jsonObject = readJsonObject(jarLoader, "plugin.json");
            String mainClass = jsonObject.get("main").getAsString();
            if (loadedModules.containsKey(mainClass)) return;

            // Cargar la clase principal desde el JAR usando el AddonClassLoader
            Class<?> loadedClass = mainLoader.loadClass(mainClass);

            // Verificar que implemente la interfaz IAddonPlugin
            if (!IAddonPlugin.class.isAssignableFrom(loadedClass)) {
                throw new IllegalArgumentException("La clase principal no implementa IAddonPlugin");
            }

            // Crear una instancia de la clase principal
            AddonPlugin plugin = (AddonPlugin) loadedClass.getDeclaredConstructor().newInstance();

            // Establecer la carpeta de datos
            Field dataFolderField = plugin.getClass().getSuperclass().getDeclaredField("dataFolder");
            dataFolderField.setAccessible(true);
            File dataFolder = new File(file.getParentFile(), plugin.getInfo().name());
            dataFolderField.set(plugin, dataFolder);
            dataFolderField.setAccessible(false);

            pluginsJars.put(plugin.getInfo().name(), file);

            // Iniciar el plugin si es necesario
            if (start) {
                plugin.start();
            }

            // Registrar el módulo cargado
            loadedModules.put(mainClass, plugin);
            plugins.put(plugin.getInfo().name(), plugin);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar el plugin desde el archivo JAR");
        }
    }

    private static JsonObject readJsonObject(URLClassLoader loader, String resourcePath) {
        InputStream jsonStream = loader.getResourceAsStream(resourcePath);
        if (jsonStream == null) {
            System.out.println("Resource not found: " + resourcePath);
            return null;
        }

        // Lee el InputStream y crea un InputStreamReader
        Reader reader = new InputStreamReader(jsonStream);

        // Usa JsonParser para parsear el contenido del Reader
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(reader);

        // Convierte el JsonElement a JsonObject
        return jsonElement.getAsJsonObject();
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        return super.getResourceAsStream(name);
    }

    private static AddonClassLoader getAddonClassLoader(URL[] urls) {
        if (addonClassLoader == null) {
            addonClassLoader = new AddonClassLoader(urls);
            return addonClassLoader;
        }
        for (URL url : urls) {
            addonClassLoader.addURL(url);
        }
        return addonClassLoader;
    }

}