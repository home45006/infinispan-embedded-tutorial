package org.infinispan.tutorial.demo;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;

import java.util.Scanner;

/**
 * @author yangyong
 */
public class Demo1 {
    public static void main(String[] args) {

        System.setProperty("nodeName", "node-1");
        // Create a clustered configuration and a default cache named test
        GlobalConfiguration global = GlobalConfigurationBuilder.defaultClusteredBuilder().build();

        // Create the cache manager
        DefaultCacheManager cacheManager = new DefaultCacheManager(global);

        // Clustered mode invalidation sync. Can also be async
        Configuration config = new ConfigurationBuilder().clustering().cacheMode(CacheMode.DIST_SYNC).build();

        // Define a cache configuration
        cacheManager.defineConfiguration("test", config);

        // Retrieve the cache
//        Cache<String, String> cache = cacheManager.getCache("test");

        System.out.println("is coordinator " + cacheManager.isCoordinator());
        Scanner scanner = new Scanner(System.in);
        String next = "";

        // Enter 'q' option to exit
        while (!next.equals("q")) {
//            System.out.println(
//                    "Invalidation mode simple tutorial\n" +
//                            "=================================\n" +
//                            "`p` to put a key/value \n" +
//                            "`pe` to put a key/value for external read \n" +
//                            "`r` to remove a key \n" +
//                            "`g` to get a key \n" +
//                            "`q` to exit \n");
            System.out.println("Enter an option: ");
            next = scanner.next();

//            switch (next) {
//                case "p":
//                    putKeyValue(scanner, cache);
//                    break;
//                case "pe":
//                    putForExternalReadKeyValue(scanner, cache);
//                    break;
//                case "r":
//                    removeKey(scanner, cache);
//                    break;
//                case "g":
//                    getKey(scanner, cache);
//                    break;
//
//                default:
//            }
        }

        // Goodbye!
        System.out.println("Good bye");

        // Stop the cache manager
        cacheManager.stop();
    }

    private static void putKeyValue(Scanner scanner, Cache<String, String> cache) {
        System.out.println("# p - Put key/value \n");
        String key = readUserInput("Enter a key: ", scanner);
        System.out.println("\n");
        String value = readUserInput("Enter a value: ", scanner);
        // Will put the key/value pair and invalidate the key/value pair that may exist in other nodes
        cache.put(key, value);
        System.out.println(String.format("[%s, %s] added", key, value));
    }

    private static void putForExternalReadKeyValue(Scanner scanner, Cache<String, String> cache) {
        System.out.println("# pe - Put for external read key/value \n");
        String key = readUserInput("Enter a key: ", scanner);
        System.out.println("\n");
        String value = readUserInput("Enter a value: ", scanner);

        //Put for external read won't invalidate the value if it's present in another node.
        cache.putForExternalRead(key, value);
        System.out.println(String.format("[%s, %s] added for external read", key, value));
    }

    private static void removeKey(Scanner scanner, Cache<String, String> cache) {
        System.out.println("# r - Remove key \n");
        String key = readUserInput("Enter a key: ", scanner);
        // Will remove the key/value pair if such exists and invalidate the key/value pair that may exist in other nodes
        cache.remove(key);
        System.out.println(String.format("%s key has been removed", key));
    }

    private static void getKey(Scanner scanner, Cache<String, String> cache) {
        System.out.println("# g -> Get key \n");
        String key = readUserInput("Enter a key: ", scanner);
        String value = cache.get(key);
        System.out.println(String.format("%s key value is %s", key, value));
    }

    private static String readUserInput(String message, Scanner scanner) {
        System.out.println(message);
        return scanner.next();
    }
}
