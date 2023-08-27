import java.util.Scanner;

public class CartOperation {
    private Scanner scanner;
    private CartFileManager cartManager;

    public CartOperation(Scanner scanner, CartFileManager cartManager) {
        this.scanner = scanner;
        this.cartManager = cartManager;
    }

    public void displayGoodsInformation() {
        System.out.println("***显示商品信息***");
        cartManager.displayGoodsInformation();
    }

    public void addToCart() {
        System.out.println("***将商品加入购物车***");
        System.out.print("请输入商品名称：");
        String product = scanner.nextLine();

        System.out.print("请输入商品数量：");
        int quantity = Integer.parseInt(scanner.nextLine());

        boolean success = cartManager.addToCart(product, quantity);

        if (success) {
            System.out.println("商品已成功加入购物车！");
        } else {
            System.out.println("添加商品到购物车失败。");
        }
    }

    public void removeFromCart() {
        System.out.println("***从购物车中移除商品***");
        System.out.print("请输入商品名称：");
        String product = scanner.nextLine();

        boolean success = cartManager.removeFromCart(product);

        if (success) {
            System.out.println("商品已成功从购物车中移除！");
        } else {
            System.out.println("从购物车中移除商品失败。");
        }
    }

    public void updateQuantity() {
        System.out.println("***修改购物车中的商品数量***");
        System.out.print("请输入商品名称：");
        String product = scanner.nextLine();

        System.out.print("请输入新的商品数量：");
        int newQuantity = Integer.parseInt(scanner.nextLine());

        boolean success = cartManager.updateQuantity(product, newQuantity);

        if (success) {
            System.out.println("商品数量已成功更新！");
        } else {
            System.out.println("更新购物车中的商品数量失败。");
        }
    }

    public void checkout(String username) {
        System.out.println("***结账***");
        System.out.println("请选择支付方式：");
        System.out.println("1. 支付宝");
        System.out.println("2. 微信");
        System.out.println("3. 银行卡");
        System.out.print("请输入支付方式编号: ");

        int paymentMethod = Integer.parseInt(scanner.nextLine());

        switch (paymentMethod) {
            case 1:
                System.out.println("支付宝支付成功！");
                break;
            case 2:
                System.out.println("微信支付成功！");
                break;
            case 3:
                System.out.println("银行卡支付成功！");
                break;
            default:
                System.out.println("无效的支付方式");
                return;
        }
        cartManager.checkout(username);
    }

    public void viewPurchaseHistory() {
        System.out.println("***查看购物历史***");
        cartManager.viewPurchaseHistory();
    }
}
