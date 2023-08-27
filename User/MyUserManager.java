package org.example.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MyUserManager {
    private static final String USERS_FILE_PATH = "users.txt";
    private Map<String, String> users;

    public MyUserManager() {
        users = loadUsersFromFile();
    }

    private Map<String, String> loadUsersFromFile() {
        Map<String, String> userMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String username = parts[0];
                    String password = parts[1];
                    userMap.put(username, password);
                }
            }
        } catch (IOException e) {
            System.out.println("加载用户数据失败: " + e.getMessage());
        }
        return userMap;
    }

    private void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE_PATH))) {
            for (Map.Entry<String, String> entry : users.entrySet()) {
                String username = entry.getKey();
                String password = entry.getValue();
                writer.write(username + "," + password);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("保存用户数据失败: " + e.getMessage());
        }
    }

    public boolean registerUser(String username, String password, String phoneNumber, String email) {
        if (users.containsKey(username)) {
            System.out.println("用户名已存在");
            return false;
        }

        users.put(username, password);
        saveUsersToFile();
        System.out.println("注册成功");
        return true;
    }

    public boolean login(String username, String password) {
        if (users.containsKey(username)) {
            String storedPassword = users.get(username);
            if (password.equals(storedPassword)) {
                System.out.println("登录成功");
                return true;
            } else {
                System.out.println("密码不正确。");
            }
        } else {
            System.out.println("用户名不存在。");
        }
        return false;
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        if (users.containsKey(username)) {
            String storedPassword = users.get(username);
            if (oldPassword.equals(storedPassword)) {
                users.put(username, newPassword);
                saveUsersToFile();
                System.out.println("密码修改成功");
                return true;
            } else {
                System.out.println("原密码不正确");
            }
        } else {
            System.out.println("用户名不存在");
        }
        return false;
    }

    public boolean resetPassword(String username, String newPassword) {
        if (users.containsKey(username)) {
            users.put(username, newPassword);
            saveUsersToFile();
            System.out.println("密码重置成功");
            return true;
        } else {
            System.out.println("用户名不存在");
        }
        return false;
    }
}
