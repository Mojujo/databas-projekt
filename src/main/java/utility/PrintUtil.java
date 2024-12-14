package utility;

public class PrintUtil {

    public static void welcomeMessage() {
        System.out.println("""
                |===================================|
                |     Slovakia Auto-Mobile Group    |
                |                                   |
                |               LOGIN:              |
                |===================================|""");
    }

    public static void optionPrintAdmin() {
        System.out.println("""
                --------------------
                1. Edit roles
                2. Edit employees
                --------------------
                """);
    }

    public static void optionPrintRole() {
        System.out.println("""
                -------------------
                1. Insert role
                2. Update role
                3. Delete role
                4. Find role
                5. List roles
                6. Go back
                7. Quit
                -------------------
                """);
    }

    public static void optionPrintEmployee() {
        System.out.println("""
                -------------------
                1. Insert employee
                2. Update employee
                3. Delete employee
                4. Find employee
                5. List employees
                6. Go back
                7. Quit
                -------------------
                """);
    }

    public static void optionPrintProfile() {
        System.out.println("""
                -------------------
                1. View profile
                -------------------
                """);
    }
}
