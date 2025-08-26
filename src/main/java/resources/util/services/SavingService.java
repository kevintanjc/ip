package resources.util.services;

public class SavingService extends Service {
    String filePath;

    protected void executeService() {
        // Implementation for saving data to the specified file path
        System.out.println("Saving data to: " + filePath);
        // Add actual saving logic here
    }

    public SavingService(String filePath) {
        this.filePath = filePath;
        executeService();
    }
}
