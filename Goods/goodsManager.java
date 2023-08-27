import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GoodsFileManager {
    private static final String GOODS_FILE_PATH = "goods.txt";

    public void addGoods(int id, String name, String manufacturer, String productionDate, String model, double cost, double price, int quantity) {
        try {
            String goodsInfo = id + "," + name + "," + manufacturer + "," + productionDate + "," + model + "," + cost + "," + price + "," + quantity;
            appendToFile(GOODS_FILE_PATH, goodsInfo);
            System.out.println("商品信息添加成功");
        } catch (IOException e) {
            System.out.println("商品信息添加失败: " + e.getMessage());
        }
    }

    public void updateGoods(int id, String option, String newValue) {
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

    public void deleteGoods(int id) {
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

    public void queryGoods(int id) {
        try {
            List<String> lines = readFromFile(GOODS_FILE_PATH);
            for (String line : lines) {
                String[] data = line.split(",");
                if (data.length >= 8 && Integer.parseInt(data[0]) == id) {
                    System.out.println("商品编号: " + id);
                    System.out.println("商品名称: " + data[1]);
                    System.out.println("生产厂家: " + data[2]);
                    System.out.println("生产日期: " + data[3]);
                    System.out.println("型号: " + data[4]);
                    System.out.println("进货价: " + data[5]);
                    System.out.println("零售价格: " + data[6]);
                    System.out.println("数量: " + data[7]);
                    System.out.println();
                    return;
                }
            }
            System.out.println("未找到匹配的商品");
        } catch (IOException e) {
            System.out.println("商品信息查询失败: " + e.getMessage());
        }
    }

    public void listAllGoods() {
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
                    System.out.println("数量: " + data[7]);
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
