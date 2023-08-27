import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DatabaseInitializer {
    private static final String USERS_FILE_PATH = "users.txt";
    private static final String ADMIN_FILE_PATH = "admin.txt";
    private static final String GOODS_FILE_PATH = "goods.txt";
    private static final String CART_FILE_PATH = "cart.txt";
    private static final String PURCHASE_HISTORY_FILE_PATH = "purchase_history.txt";

    public void initUserDatabase() {
        try {
            createFileIfNotExists(USERS_FILE_PATH);
            System.out.println("Users database initialized successfully!");
        } catch (IOException e) {
            System.out.println("Failed to initialize users database: " + e.getMessage());
        }
    }

    public void initAdminDatabase() {
        try {
            createFileIfNotExists(ADMIN_FILE_PATH);
            System.out.println("Admin database initialized successfully!");
        } catch (IOException e) {
            System.out.println("Failed to initialize admin database: " + e.getMessage());
        }
    }

    public void initGoodsDatabase() {
        try {
            createFileIfNotExists(GOODS_FILE_PATH);
            System.out.println("Goods database initialized successfully!");
        } catch (IOException e) {
            System.out.println("Failed to initialize goods database: " + e.getMessage());
        }
    }

    public void initCartDatabase() {
        try {
            createFileIfNotExists(CART_FILE_PATH);
            System.out.println("Cart database initialized successfully!");
        } catch (IOException e) {
            System.out.println("Failed to initialize cart database: " + e.getMessage());
        }
    }

    public void initPurchaseHistoryDatabase() {
        try {
            createFileIfNotExists(PURCHASE_HISTORY_FILE_PATH);
            System.out.println("Purchase history database initialized successfully!");
        } catch (IOException e) {
            System.out.println("Failed to initialize purchase history database: " + e.getMessage());
        }
    }

    private void createFileIfNotExists(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

}
