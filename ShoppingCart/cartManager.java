import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CartFileManager {
    private static final String CART_FILE_PATH = "cart.txt";
    private static final String PURCHASE_HISTORY_FILE_PATH = "purchase_history.txt";
    private Scanner scanner;

    public CartFileManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public void displayGoodsInformation() {
        try {
            List<String> lines = readFromFile(GOODS_FILE_PATH);
            System.out.println("商品信息：");
            for (String line : lines) {
                String[] data = line.split(",");
                if (data.length >= 8) {
                    String name = data[1];
                    String model = data[4];
                    int quantity = Integer.parseInt(data[7]);
                    double price = Double.parseDouble(data[6]);

                    System.out.println("商品名称：" + name);
                    System.out.println("商品类别：" + model);
                    System.out.println("商品数量：" + quantity);
                    System.out.println("商品售价：" + price);
                    System.out.println("-------------------------");
                }
            }
        } catch (IOException e) {
            System.out.println("显示商品信息失败: " + e.getMessage());
        }
    }

    public boolean addToCart(String product, int quantity) {
        try {
            String cartItem = product + "," + quantity;
            appendToFile(CART_FILE_PATH, cartItem);
            System.out.println("商品已加入购物车");
            return true;
        } catch (IOException e) {
            System.out.println("添加商品到购物车失败: " + e.getMessage());
        }
        return false;
    }

    public boolean removeFromCart(String product) {
        System.out.println("警告：你正在尝试从购物车中移除商品 " + product);
        System.out.print("确认是否继续移除操作？ (输入 'yes' 或 'no'): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            try {
                List<String> lines = readFromFile(CART_FILE_PATH);
                List<String> updatedLines = new ArrayList<>();
                boolean removed = false;

                for (String line : lines) {
                    String[] data = line.split(",");
                    if (data.length >= 2 && data[0].equalsIgnoreCase(product)) {
                        removed = true;
                    } else {
                        updatedLines.add(line);
                    }
                }

                if (removed) {
                    writeToFile(CART_FILE_PATH, updatedLines);
                    System.out.println("商品已从购物车中移除");
                    return true;
                } else {
                    System.out.println("购物车中不存在该商品");
                }
            } catch (IOException e) {
                System.out.println("从购物车中移除商品失败: " + e.getMessage());
            }
        } else {
            System.out.println("移除操作已取消");
        }

        return false;
    }

    public boolean updateQuantity(String product, int newQuantity) {
        try {
            List<String> lines = readFromFile(CART_FILE_PATH);
            List<String> updatedLines = new ArrayList<>();
            boolean updated = false;

            for (String line : lines) {
                String[] data = line.split(",");
                if (data.length >= 2 && data[0].equalsIgnoreCase(product)) {
                    updated = true;
                    updatedLines.add(data[0] + "," + newQuantity);
                } else {
                    updatedLines.add(line);
                }
            }

            if (updated) {
                writeToFile(CART_FILE_PATH, updatedLines);
                System.out.println("商品数量已更新");
                return true;
            } else {
                System.out.println("购物车中不存在该商品");
            }
        } catch (IOException e) {
            System.out.println("修改购物车商品数量失败: " + e.getMessage());
        }

        return false;
    }

    public String getPurchasedItemsFromCart() {
        StringBuilder items = new StringBuilder();
        try {
            List<String> lines = readFromFile(CART_FILE_PATH);
            for (String line : lines) {
                String[] data = line.split(",");
                if (data.length >= 2) {
                    String product = data[0];
                    int quantity = Integer.parseInt(data[1]);
                    items.append(product).append(" (数量: ").append(quantity).append(")").append(", ");
                }
            }
        } catch (IOException e) {
            System.out.println("获取购买商品清单失败: " + e.getMessage());
        }
        return items.toString();
    }

    public void checkout(String username) {
        try {
            List<String> cartLines = readFromFile(CART_FILE_PATH);
            List<String> purchaseHistoryLines = new ArrayList<>();

            for (String cartLine : cartLines) {
                String[] cartData = cartLine.split(",");
                if (cartData.length >= 2) {
                    String product = cartData[0];
                    int quantity = Integer.parseInt(cartData[1]);
                    String historyLine = username + "," + product + "," + quantity;
                    purchaseHistoryLines.add(historyLine);
                }
            }

            appendToFile(PURCHASE_HISTORY_FILE_PATH, purchaseHistoryLines);
            clearFile(CART_FILE_PATH);
            System.out.println("付款成功！感谢您的购买！");
        } catch (IOException e) {
            System.out.println("结账过程中出现错误: " + e.getMessage());
        }
    }

    public void viewPurchaseHistory() {
        try {
            List<String> historyLines = readFromFile(PURCHASE_HISTORY_FILE_PATH);
            for (String historyLine : historyLines) {
                String[] historyData = historyLine.split(",");
                if (historyData.length >= 3) {
                    String username = historyData[0];
                    String product = historyData[1];
                    int quantity = Integer.parseInt(historyData[2]);

                    System.out.println("用户名：" + username + ", 商品：" + product + ", 数量：" + quantity);
                }
            }
        } catch (IOException e) {
            System.out.println("获取购买历史失败: " + e.getMessage());
        }
    }

    private List<String> readFromFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private void writeToFile(String filePath, List<String> content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : content) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    private void appendToFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(content);
            writer.newLine();
        }
    }

    private void clearFile(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Clear the file by writing nothing
        }
    }
}
