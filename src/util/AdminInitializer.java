package util;

import model.Admin;
import service.GameDataManager;

public class AdminInitializer {
    private AdminInitializer() {
    }

    public static void registerDefaultAdmins(GameDataManager manager) {
        manager.registerUser(new Admin("\u996d\u56e2linj44", "\u996d\u56e2linj44", "070530"));
        manager.registerUser(new Admin("\u7ea2\u7cd6guoy10", "\u90ed\u6021\u59a7", "123456"));
    }
}
