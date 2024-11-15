import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;


class User {
    private String userId;
    private String userPin;
    private double balance;
    private List<String> transactionHistory;

   
    public User(String userId, String userPin, double balance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPin() {
        return userPin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addToTransactionHistory(String transaction) {
        transactionHistory.add(transaction);
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }
}


class ATM {
    private User user;
    private Scanner scanner;

   
    public ATM(User user) {
        this.user = user;
        this.scanner = new Scanner(System.in);
    }

 
    public void showMenu() {
        boolean quit = false;
        while (!quit) {
            System.out.println("\nATM Menu");
            System.out.println("1. Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    showTransactionHistory();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }
        }
    }

   
    private void showTransactionHistory() {
        System.out.println("\nTransaction History");
        List<String> transactions = user.getTransactionHistory();
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (String transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    
    private void withdraw() {
        System.out.print("\nEnter the amount to withdraw: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); 

        if (amount <= 0) {
            System.out.println("Invalid amount! Please try again.");
            return;
        }

        if (amount > user.getBalance()) {
            System.out.println("Insufficient balance! Cannot withdraw.");
            return;
        }

        user.setBalance(user.getBalance() - amount);
        user.addToTransactionHistory("Withdraw: -" + amount);
        System.out.println("Withdrawn " + amount + " successfully.");
        System.out.println("Remaining Balance: " + user.getBalance());
    }

    private void deposit() {
        System.out.print("\nEnter the amount to deposit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); 

        if (amount <= 0) {
            System.out.println("Invalid amount! Please try again.");
            return;
        }

        user.setBalance(user.getBalance() + amount);
        user.addToTransactionHistory("Deposit: +" + amount);
        System.out.println("Deposited " + amount + " successfully.");
        System.out.println("Remaining Balance: " + user.getBalance());
    }

    
    private void transfer() {
        System.out.print("\nEnter the recipient's user ID: ");
        String recipientId = scanner.nextLine();
        System.out.print("Enter the amount to transfer: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        if (amount <= 0) {
            System.out.println("Invalid amount! Please try again.");
            return;
        }

        if (amount > user.getBalance()) {
            System.out.println("Insufficient balance! Cannot transfer.");
            return;
        }

        user.setBalance(user.getBalance() - amount);
        user.addToTransactionHistory("Transfer to " + recipientId + ": -" + amount);

        System.out.println("Transferred " + amount + " to user ID: " + recipientId + " successfully.");
        System.out.println("Remaining Balance: " + user.getBalance());
    }
}


public class ATMInterface {
    public static void main(String[] args) {
        
        User user = new User("123456", "7890", 0.0);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your user ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter your user PIN: ");
        String userPin = scanner.nextLine();

        if (userId.equals(user.getUserId()) && userPin.equals(user.getUserPin())) {
           
            ATM atm = new ATM(user);
            atm.showMenu();
        } else {
            System.out.println("Authentication failed! Invalid user ID or PIN.");
        }

        scanner.close();
    }
}