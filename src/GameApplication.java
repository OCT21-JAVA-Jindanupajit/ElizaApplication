import java.util.Scanner;

public class GameApplication {
    public static void main(String[] args) {
        System.out.println("Choose your own adventure");
        System.out.print("    You found a dragon, do you want to kill him ? (yes/no): ");
        Scanner kb = new Scanner(System.in);
        switch(kb.nextLine().toLowerCase()) {
            case "yes":
                System.out.println("You die!");
                break;
            case "no":
                System.out.println("Congratulations!, you got a pet dragon");
                break;
            default:
                System.out.println("You are nut!, try again!");
        }
    }
}
