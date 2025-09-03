package resources.util.services;

/**
 * Abstract class representing a generic service with lifecycle methods.
 * <p>
 * The {@link Service} class defines the structure for services that have a defined lifecycle,
 * including methods to start, execute, and end the service.
 * Subclasses must provide implementations for these abstract methods.
 * <p>
 * Usage:
 * <pre>
 *     class MyService extends Service {
 *         // Implement abstract methods
 *     }
 * </pre>
 *
 * @author Kevin Tan
 */
public abstract class Service {

    public abstract String executeService(String... inputs) throws Exception;

    public abstract String startService();

    public abstract String endService();
}
