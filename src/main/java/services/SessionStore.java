package services;

import entities.Customer;

public class SessionStore {
    private static boolean admin = false;
    private static Customer user;

    public static Customer getUser() {
        return user;
    }

    public static void setUser(Customer user) throws Exception {
        if (user == null) {
            throw new Exception("User doesn't exists!");
        }
        SessionStore.user = user;
    }

    public static boolean isAdmin() {
        return admin;
    }

    public static void setAdmin(boolean admin) {
        SessionStore.admin = admin;
    }

    private SessionStore() {}
}
