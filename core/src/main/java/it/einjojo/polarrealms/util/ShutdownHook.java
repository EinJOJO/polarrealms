package it.einjojo.polarrealms.util;

import it.einjojo.polarrealms.PolarRealmsClient;

/**
 * Designed to be executed in shutdown in the order of registration.
 * <pre><code>
 * execute():
 *  hook_3 --> hook_2 --> hook_1
 * </code></pre>
 *
 * @param runnable    runnable to be executed
 * @param description description of the hook, used for logging.
 * @param next        hook to be executed after this one
 * @param remaining   remaining number of hooks to be executed
 */
public record ShutdownHook(Runnable runnable, String description, ShutdownHook next, int remaining) {

    /**
     * Execute the shutdown hook chain.
     *
     * @param polarRealmsClient client instance (for logger)
     */
    public void execute(PolarRealmsClient polarRealmsClient) {
        int progress = 1;
        int total = remaining + 1;
        executeInternal(polarRealmsClient, progress, total);
    }

    /**
     * Executes the hook.
     *
     * @param client   client instance
     * @param progress current hook's position in the shutdown sequence
     * @param total    total number of hooks to be executed
     */
    private void executeInternal(PolarRealmsClient client, int progress, int total) {
        try {
            client.getLogger().info("[{}/{}] {}...", progress, total, description);
            runnable.run();
        } catch (Exception ex) {
            client.getLogger().error("[{}/{}] An error occurred while executing a shutdown hook", progress, total, ex);
        }
        if (next != null) {
            next.executeInternal(client, progress + 1, total);
        }
    }


}
