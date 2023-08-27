import java.io.*;
import java.util.Optional;

public class AdministratorFileManager {
    private static final String ADMIN_FILE_PATH = "admin.txt";
    private static final String USERS_FILE_PATH = "users.txt";

    public boolean initializeAdminAccount() {
        try {
            if (!isFileExists(ADMIN_FILE_PATH)) {
                writeToFile(ADMIN_FILE_PATH, "admin,ynuinfo#777");
                return true;
            }
        } catch (IOException e) {
            System.out.println("管理员账号初始化失败: " + e.getMessage());
        }
        return false;
    }

    public boolean loginAdmin(String username, String password) {
        try {
            String storedCredentials = readFromFile(ADMIN_FILE_PATH);
            if (storedCredentials != null) {
                String[] credentials = storedCredentials.split(",");
                if (credentials.length == 2 && credentials[0].equals(username) && credentials[1].equals(password)) {
                    System.out.println("管理员登录成功");
                    return true;
                } else {
                    System.out.println("管理员密码不正确");
                }
            } else {
                System.out.println("管理员用户名不存在");
            }
        } catch (IOException e) {
            System.out.println("管理员登录失败: " + e.getMessage());
        }
        return false;
    }

    public boolean changeAdminPassword(String username, String newPassword) {
        try {
            String storedCredentials = readFromFile(ADMIN_FILE_PATH);
            if (storedCredentials != null) {
                String[] credentials = storedCredentials.split(",");
                if (credentials[0].equals(username)) {
                    writeToFile(ADMIN_FILE_PATH, username + "," + newPassword);
                    System.out.println("管理员密码修改成功");
                    return true;
                } else {
                    System.out.println("管理员用户名不存在");
                }
            } else {
                System.out.println("管理员用户名不存在");
            }
        } catch (IOException e) {
            System.out.println("管理员密码修改失败: " + e.getMessage());
        }
        return false;
    }

    public boolean resetUserPassword(String username, String newPassword) {
        try {
            String fileContent = readFromFile(USERS_FILE_PATH);
            String[] lines = fileContent.split(System.lineSeparator());
            boolean updated = false;

            for (int i = 0; i < lines.length; i++) {
                String[] userData = lines[i].split(",");
                if (userData.length >= 2 && userData[0].equals(username)) {
                    lines[i] = username + "," + newPassword;
                    updated = true;
                    break;
                }
            }

            if (updated) {
                writeToFile(USERS_FILE_PATH, String.join(System.lineSeparator(), lines));
                System.out.println("用户密码重置成功");
                return true;
            } else {
                System.out.println("用户名不存在");
            }
        } catch (IOException e) {
            System.out.println("用户密码重置失败: " + e.getMessage());
        }
        return false;
    }

    // ... Other methods

    private boolean isFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    private String readFromFile(String filePath) throws IOException {
        if (isFileExists(filePath)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                return content.toString();
            }
        }
        return null;
    }

    private void writeToFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }
}
