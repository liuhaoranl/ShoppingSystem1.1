package org.example;


public class Main {
    private static MyUserManager userManager;
    private static boolean loggedIn = false;

    public static void main(String[] args) {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer();
        databaseInitializer.initUserFile(); // Initialize user data from files
        userManager = new MyUserManager(databaseInitializer.getUserDataFile()); // Pass the user data file to MyUserManager
        mainMenu();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("***欢迎你的到来***");
        while (true) {
            clearScreen();
            System.out.println("***请选择你的身份***");
            System.out.println("1.管理员  2.用户  3.退出系统");
    
            if (scanner.hasNextLine()) {
                String userInput1 = scanner.nextLine();
                switch (userInput1) {
                    case "1":
                        administrator();
                        break;
                    case "2":
                        user();
                        break;
                    case "3":
                        scanner.close();
                        return;
                    default:
                        System.out.println("无效的选项，请重新输入。");
                        break;
                }
            } else {
                System.out.println("无效的输入。");
            }
        }
    }

    private static void administrator() {
        administratorOperation administratorOperation = new administratorOperation();
        administratorOperation.initializeAdminAccount();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                //clearScreen();
                System.out.println("***欢迎进入管理员系统***");
                System.out.println("***1.管理员登录***");
                System.out.println("***2.管理员密码修改***");
                System.out.println("***3.客户管理***");
                System.out.println("***4.商品管理***");
                System.out.println("***q/Q返回上一级菜单***e/E返回主菜单***");

                String userInput2 = scanner.nextLine();

                switch (userInput2) {
                    case "1": // 登录
                        loginAdmin(scanner, administratorOperation);
                        break;
                    case "2": // 密码管理
                        managePassword(scanner, administratorOperation);
                        break;
                    case "3": // 客户管理
                        manageUser(scanner, administratorOperation);
                        break;
                    case "4": // 商品管理
                        manageGoods(scanner);
                        break;
                    case "q":
                    case "Q":
                        return;
                    case "e":
                    case "E":
                        mainMenu();
                        break;
                    default:
                        System.out.println("无效的选项，请重新输入。");
                        break;
                }
            }
        }
    }

    private static void loginAdmin(Scanner scanner, administratorOperation administratorOperation) {
        System.out.println("***管理员登录子菜单***");
        System.out.println("输入用户名");
        String name = scanner.nextLine();
        System.out.println("输入密码");
        String password = scanner.nextLine();
        administratorOperation.loginAdmin(name, password);
    }

    private static void managePassword(Scanner scanner, administratorOperation administratorOperation) {
        System.out.println("***管理员密码修改子菜单***");
        System.out.println("输入用户名");
        String name = scanner.nextLine();
        System.out.println("输入新密码");
        String newPassword = scanner.nextLine();
        administratorOperation.changeAdminPassword(name, newPassword);
    }

    private static void manageUser(Scanner scanner, administratorOperation administratorOperation) {
        while (true) {
            //clearScreen();
            System.out.println("***用户管理子菜单***");
            System.out.println("***1.列出用户信息***");
            System.out.println("***2.重置用户密码***");
            System.out.println("***3.删除用户信息***");
            System.out.println("***4.查询用户信息***");
            System.out.println("***q/Q返回上一级菜单***e/E返回主菜单***");

            String userInput3 = scanner.nextLine();

            switch (userInput3) {
                case "1":
                    administratorOperation.displayAllUsers();
                    break;
                case "2":
                    resetUserPassword(scanner, administratorOperation);
                    break;
                case "3":
                    System.out.println("输入用户名");
                    userInput3 = scanner.nextLine();
                    administratorOperation.deleteUser(userInput3);
                    break;
                case "4":
                    System.out.println("输入用户名");
                    userInput3 = scanner.nextLine();
                    administratorOperation.getUserInfo(userInput3);
                    break;
                case "q":
                case "Q":
                    return;
                case "e":
                case "E":
                    mainMenu();
                    break;
                default:
                    System.out.println("无效的选项，请重新输入。");
                    break;
            }
        }
    }

    private static void resetUserPassword(Scanner scanner, administratorOperation administratorOperation) {
        System.out.println("输入用户名");
        String name = scanner.nextLine();
        System.out.println("输入新密码");
        String newPassword = scanner.nextLine();
        administratorOperation.resetUserPassword(name, newPassword);
    }

    private static void manageGoods(Scanner scanner) {
        while (true) {
            System.out.println("***商品管理子菜单***");
            System.out.println("***请选择***");
            goodsOperation goodsOperation = new goodsOperation(scanner);
            System.out.println("***1.添加商品信息***");
            System.out.println("***2.修改商品信息***");
            System.out.println("***3.删除商品信息***");
            System.out.println("***4.查询商品信息***");
            System.out.println("***5.列出所有商品信息***");
            System.out.println("***q/Q返回上一级菜单***e/E返回主菜单***");

            String userInput4 = scanner.nextLine();

            switch (userInput4) {
                case "1":
                    goodsOperation.addGoodsInformation();
                    break;
                case "2":
                    goodsOperation.modifyGoodsInformation();
                    break;
                case "3":
                    goodsOperation.deleteGoodsInformation();
                    break;
                case "4":
                    goodsOperation.queryGoodsInformation();
                    break;
                case "5":
                    goodsOperation.listAllGoodsInformation();
                    break;
                case "q":
                case "Q":
                    return;
                case "e":
                case "E":
                    mainMenu();
                    break;
                default:
                    System.out.println("无效的选项，请重新输入。");
                    break;
            }
        }
    }

    private static void user() {
        try (Scanner scanner = new Scanner(System.in)) {
            userOperation userOperation = new userOperation(scanner, userManager);
            while (true) {
                System.out.println("***欢迎进入购物系统***");
                System.out.println("***请选择***");
                System.out.println("***1.用户注册***");
                System.out.println("***2.用户登录***");
                System.out.println("***3.用户密码管理 ***");
                System.out.println("***4.购物***");
                System.out.println("***5.退出登录***");
                System.out.println("***q/Q返回上一级菜单***e/E返回主菜单***");
                String userInput5 = scanner.nextLine();

                switch (userInput5) {
                    case "1": // 注册
                        userOperation.registerUser();
                        break;
                    case "2": // 登录
                        loggedIn = userOperation.loginUser();
                        break;
                    case "3": // 密码管理
                        if (loggedIn) {
                            manageUserPassword(scanner, userManager, userOperation);
                        } else {
                            System.out.println("请先登录用户账号。");
                        }
                        break;
                    case "4": // 购物
                        if (loggedIn) {
                            shoppingMenu(userManager, userOperation, scanner);
                        } else {
                            System.out.println("请先登录用户账号。");
                        }
                        break;
                    case "5":
                        loggedIn = false;
                        return;
                    case "q":
                    case "Q":
                        loggedIn = false;
                        return;
                    case "e":
                    case "E":
                        loggedIn = false;
                        mainMenu();
                        break;
                    default:
                        System.out.println("无效的选项，请重新输入。");
                        break;
                }
            }
        }
    }

    
private static void manageUserPassword(Scanner scanner, MyUserManager userManager, userOperation userOperation) {
    while (true) {
        //clearScreen();
        System.out.println("***用户密码管理子菜单***");
        System.out.println("***请选择***");
        System.out.println("***1.修改密码***");
        System.out.println("***2.重置密码***");
        System.out.println("***q/Q返回上一级菜单***e/E返回主菜单***");

        if (scanner.hasNextLine()) {
            String userInput6 = scanner.nextLine();

            switch (userInput6) {
                case "1":
                    userOperation.changePassword();
                    break;
                case "2":
                    userOperation.resetPassword();
                    break;
                case "q":
                case "Q":
                    return;
                case "e":
                case "E":
                    mainMenu();
                    break;
                default:
                    System.out.println("无效的选项，请重新输入。");
                    break;
            }
        } else {
            System.out.println("无效的输入。");
        }
    }
}

    private static void shoppingMenu(MyUserManager userManager, userOperation userOperation, Scanner scanner) {
        cartManager cartManager = new cartManager();
        cartOperation cartOperation = new cartOperation(scanner, cartManager);
        String username = userOperation.getUsername(); // 保存登录的用户名
    
        while (true) {
            System.out.println("***欢迎进入购物系统***");
            System.out.println("1.查看商品");
            System.out.println("2.将商品加入购物车");
            System.out.println("3.从购物车中移除商品");
            System.out.println("4.修改购物车中的商品数量");
            System.out.println("5.模拟结账");
            System.out.println("6.查看用户的购物历史");
            System.out.println("q/Q返回上一级菜单");
            System.out.println("e/E返回主菜单");
            System.out.print("请选择操作: ");
    
            if (scanner.hasNextLine()) {
                String userInput7 = scanner.nextLine(); // 读取用户输入
    
                switch (userInput7) {
                    case "1":
                        cartOperation.displayGoodsInformation();
                        break;
                    case "2":
                        cartOperation.addToCart();
                        break;
                    case "3":
                        cartOperation.removeFromCart();
                        break;
                    case "4":
                        cartOperation.updateQuantity();
                        break;
                    case "5":
                        cartOperation.checkout(username);
                        break;
                    case "6":
                        cartOperation.viewPurchaseHistory();
                        break;
                    case "q":
                    case "Q":
                        return;
                    case "e":
                    case "E":
                        mainMenu();
                        break;
                    default:
                        System.out.println("无效的选项，请重新输入。");
                        break;
                }
            } else {
                System.out.println("无效的输入。");
                scanner.nextLine(); // 清空无效的输入行
            }
        }
    }
    
}
