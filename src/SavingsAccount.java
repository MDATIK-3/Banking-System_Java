public class SavingsAccount extends Account {
    private static final double MIN_BALANCE = 100.0;

    public SavingsAccount(String accountNumber, String pin, double initialBalance) {
        super(accountNumber, pin, initialBalance);
    }

    @Override
    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return false;
        }
        updateBalance(amount);
        System.out.printf("Successfully deposited $%.2f. New balance: $%.2f%n", amount, getBalance());
        return true;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return false;
        }
        if (getBalance() - amount < MIN_BALANCE) {
            System.out.println("Insufficient balance. Minimum balance of $100 required.");
            return false;
        }
        updateBalance(-amount);
        System.out.printf("Successfully withdrew $%.2f. New balance: $%.2f%n", amount, getBalance());
        return true;
    }
}