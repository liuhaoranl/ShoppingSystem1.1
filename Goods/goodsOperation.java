import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GoodsFileManager {
    private static final String GOODS_FILE_PATH = "goods.txt";
    private Scanner scanner;

    public GoodsFileManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public void addGoodsInformation() {
        System.out.println("现在你在添加商品信息子菜单里.");
        System.out.print("请输入商品编号: ");
        int id = Integer.parseInt(this.scanner.nextLine());

        System.out.print("请输入商品名称: ");
        String name = this.scanner.nextLine();

        System.out.print("请输入生产厂家: ");
        String manufacturer = this.scanner.nextLine();

        System.out.print("请输入生产日期: ");
        String productionDate = this.scanner.nextLine();

        System.out.print("请输入型号: ");
        String model = this.scanner.nextLine();

        System.out.print("请输入进货价: ");
        double cost = Double.parseDouble(this.scanner.nextLine());

        System.out.print("请输入零售价格: ");
        double price = Double.parseDouble(this.scanner.nextLine());

        System.out.print("请输入商品数量: ");
        int quantity = Integer.parseInt(this.scanner.nextLine());

        try {
            String goodsInfo = id + "," + name + "," + manufacturer + "," + productionDate + "," + model + "," + cost + "," + price + "," + quantity;
            appendToFile(GOODS_FILE_PATH, goodsInfo);
            System.out.println("商品信息添加成功");
        } catch (IOException e) {
            System.out.println("商品信息添加失败: " + e.getMessage());
        }
    }

    public void modifyGoodsInformation() {
        System.out.println("现在你在修改商品信息子菜单里.");
        System.out.print("请输入要修改的商品编号: ");
        int id = Integer.parseInt(this.scanner.nextLine());

        System.out.println("请选择要修改的内容:");
        System.out.println("1. 商品名称");
        System.out.println("2. 生产厂家");
        System.out.println("3. 生产日期");
        System.out.println("4. 型号");
        System.out.println("5. 进货价");
        System.out.println("6. 零售价格");
        System.out.println("7. 商品数量");
        System.out.print("请选择选项: ");
        String option = this.scanner.nextLine();

        System.out.print("请输入新的值: ");
        String newValue = this.scanner.nextLine();

        try {
            List<String> lines = readFromFile(GOODS_FILE_PATH);
            for (int i = 0; i < lines.size(); i++) {
                String[] data = lines.get(i).split(",");
                if (data.length >= 8 && Integer.parseInt(data[0]) == id) {
                    int columnIndex = Integer.parseInt(option) - 1;
                    data[columnIndex] = newValue;
                    lines.set(i, String.join(",", data));
                    writeToFile(GOODS_FILE_PATH, lines);
                    System.out.println("商品信息修改成功");
                    return;
                }
            }
            System.out.println("未找到匹配的商品");
        } catch (IOException e) {
            System.out.println("商品信息修改失败: " + e.getMessage());
        }
    }

    public void deleteGoodsInformation() {
        System.out.println("现在你在删除商品信息子菜单里.");
        System.out.print("请输入要删除的商品编号: ");
        int id = Integer.parseInt(this.scanner.nextLine());

        try {
            List<String> lines = readFromFile(GOODS_FILE_PATH);
            boolean found = false;
            for (int i = 0; i < lines.size(); i++) {
                String[] data = lines.get(i).split(",");
                if (data.length >= 1 && Integer.parseInt(data[0]) == id) {
                    lines.remove(i);
                    found = true;
                    break;
                }
            }
            if (found) {
                writeToFile(GOODS_FILE_PATH, lines);
                System.out.println("商品信息删除成功");
            } else {
                System.out.println("未找到匹配的商品");
            }
        } catch (IOException e) {
            System.out.println("商品信息删除失败: " + e.getMessage());
        }
    }

    public void queryGoodsInformation() {
        System.out.println("现在你在查询商品信息子菜单里.");
        System.out.print("请输入要查询的商品关键字: ");
        String keyword = this.scanner.nextLine();

        try {
            List<String> lines = readFromFile(GOODS_FILE_PATH);
            for (String line : lines) {
                String[] data = line.split(",");
                if (data.length >= 8 && (data[1].contains(keyword) || data[2].contains(keyword))) {
                    System.out.println("商品编号: " + data[0]);
                    System.out.println("商品名称: " + data[1]);
                    System.out.println("生产厂家: " + data[2]);
                    System.out.println("生产日期: " + data[3]);
                    System.out.println("型号: " + data[4]);
                    System.out.println("进货价: " + data[5]);
                    System.out.println("零售价格: " + data[6]);
                    System.out.println("商品数量: " + data[7]);
                    System.out.println("-------------------------");
                }
            }
        } catch (IOException e) {
            System.out.println("商品信息查询失败: " + e.getMessage());
        }
    }

    public void listAllGoodsInformation() {
        System.out.println("现在你在列出所有商品信息子菜单里.");

        try {
            List<String> lines = readFromFile(GOODS_FILE_PATH);
            for (String line : lines) {
                String[] data = line.split(",");
                if (data.length >= 8) {
                    System.out.println("商品编号: " + data[0]);
                    System.out.println("商品名称: " + data[1]);
                    System.out.println("生产厂家: " + data[2]);
                    System.out.println("生产日期: " + data[3]);
                    System.out.println("型号: " + data[4]);
                    System.out.println("进货价: " + data[5]);
                    System.out.println("零售价格: " + data[6]);
                    System.out.println("商品数量: " + data[7]);
                    System.out.println("-------------------------");
                }
            }
        } catch (IOException e) {
            System.out.println("列出所有商品信息失败: " + e.getMessage());
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
}
