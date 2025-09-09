import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Welcome to the Banking System ===");

        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Login");
            System.out.println("2. Create Account");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int mainChoice;
            try {
                mainChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (mainChoice) {
                case 1:
                    System.out.print("Enter Account Number: ");
                    String loginAccountNumber = scanner.nextLine();
                    System.out.print("Enter PIN: ");
                    String loginPin = scanner.nextLine();

                    Account account = bank.login(loginAccountNumber, loginPin);
                    if (account == null) {
                        System.out.println("Invalid account number or PIN.");
                        break;
                    }

                    while (true) {
                        System.out.println("\n=== Banking Menu ===");
                        System.out.println("1. Balance Inquiry");
                        System.out.println("2. Deposit");
                        System.out.println("3. Withdraw");
                        System.out.println("4. Update Account");
                        System.out.println("5. Delete Account");
                        System.out.println("6. Logout");
                        System.out.print("Choose an option: ");
                        int choice;
                        try {
                            choice = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a number.");
                            continue;
                        }

                        switch (choice) {
                            case 1:
                                System.out.printf("Current Balance: $%.2f%n", account.getBalance());
                                break;
                            case 2:
                                System.out.print("Enter deposit amount: $");
                                double depositAmount;
                                try {
                                    depositAmount = Double.parseDouble(scanner.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid amount. Please enter a valid number.");
                                    break;
                                }
                                if (account.deposit(depositAmount)) {
                                    System.out.println("Deposit successful.");
                                    bank.saveAccounts();
                                } else {
                                    System.out.println("Deposit failed. Amount must be positive.");
                                }
                                break;
                            case 3:
                                System.out.print("Enter withdrawal amount: $");
                                double withdrawAmount;
                                try {
                                    withdrawAmount = Double.parseDouble(scanner.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid amount. Please enter a valid number.");
                                    break;
                                }
                                if (account.withdraw(withdrawAmount)) {
                                    System.out.println("Withdrawal successful.");
                                    bank.saveAccounts();
                                } else {
                                    System.out.println("Withdrawal failed. Check amount and minimum balance.");
                                }
                                break;
                            case 4:
                                System.out.print("Enter new PIN: ");
                                String newPin = scanner.nextLine();
                                System.out.print("Enter new Balance: ");
                                double newBalance;
                                try {
                                    newBalance = Double.parseDouble(scanner.nextLine());
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid balance. Please enter a valid number.");
                                    break;
                                }
                                if (bank.updateAccount(account.getAccountNumber(), newPin, newBalance)) {
                                    System.out.println("Account updated successfully.");
                                    account.setPin(newPin);
                                    account.setBalance(newBalance);
                                } else {
                                    System.out.println("Account update failed.");
                                }
                                break;
                            case 5:
                                System.out.print("Are you sure you want to delete your account (yes/no)? ");
                                String confirmation = scanner.nextLine();
                                if (confirmation.equalsIgnoreCase("yes")) {
                                    if (bank.deleteAccount(account.getAccountNumber())) {
                                        System.out.println("Account deleted successfully.");
                                        account = null; 
                                        break; 
                                    } else {
                                        System.out.println("Account deletion failed.");
                                    }
                                } else {
                                    System.out.println("Account deletion cancelled.");
                                }
                                break;
                            case 6:
                                System.out.println("Logging out.");
                                break;
                            default:
                                System.out.println("Invalid option. Try again.");
                        }
                        if (choice == 6 || account == null) {
                            break;
                        }
                    }
                    break;
                case 2:
                    System.out.print("Enter new Account Number: ");
                    String newAccountNumber = scanner.nextLine();
                    System.out.print("Enter new PIN: ");
                    String newAccountPin = scanner.nextLine();
                    System.out.print("Enter initial Balance: ");
                    double initialBalance;
                    try {
                        initialBalance = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid balance. Please enter a valid number.");
                        break;
                    }
                    if (bank.createAccount(newAccountNumber, newAccountPin, initialBalance)) {
                        System.out.println("Account created successfully.");
                    } else {
                        System.out.println("Account creation failed. Account number might already exist.");
                    }
                    break;
                case 3:
                    System.out.println("Thank you for using the Banking System!");
                    bank.saveAccounts();
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
