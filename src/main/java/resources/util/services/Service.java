package resources.util.services;

public abstract class Service {

    protected abstract void executeService() throws Exception;

    protected abstract void startService() throws Exception;

    protected abstract void endService();
}
