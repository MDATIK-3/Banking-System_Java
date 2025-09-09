import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@SuppressWarnings("unchecked")
public class Bank {
    private Map<String, Account> accounts;
    private static final String DATA_FILE = "data/accounts.json";

    public Bank() {
        accounts = new HashMap<>();
        loadAccounts();
    }

    private void loadAccounts() {
        try (FileReader reader = new FileReader(DATA_FILE)) {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            for (Object obj : jsonArray) {
                JSONObject jsonAccount = (JSONObject) obj;
                String accountNumber = (String) jsonAccount.get("accountNumber");
                String pin = (String) jsonAccount.get("pin");
                double balance = ((Number) jsonAccount.get("balance")).doubleValue();
                accounts.put(accountNumber, new SavingsAccount(accountNumber, pin, balance));
            }
        } catch (Exception e) {
            System.out.println("Error loading accounts: " + e.getMessage());
            accounts.put("1001", new SavingsAccount("1001", "1234", 1000.0));
        }
    }

    public void saveAccounts() {
        JSONArray jsonArray = new JSONArray();

        for (Account account : accounts.values()) {
            JSONObject jsonAccount = new JSONObject();
            jsonAccount.put("accountNumber", account.getAccountNumber());
            jsonAccount.put("pin", account.getPin());
            jsonAccount.put("balance", account.getBalance());
            jsonArray.add(jsonAccount);
        }

        try (FileWriter writer = new FileWriter(DATA_FILE)) {
            writer.write(jsonArray.toJSONString());
        } catch (Exception e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    public Account login(String accountNumber, String pin) {
        Account account = accounts.get(accountNumber);
        return (account != null && account.validatePin(pin)) ? account : null;
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public boolean createAccount(String accountNumber, String pin, double initialBalance) {
        if (accounts.containsKey(accountNumber)) {
            return false;
        }
        accounts.put(accountNumber, new SavingsAccount(accountNumber, pin, initialBalance));
        saveAccounts();
        return true;
    }

    public boolean updateAccount(String accountNumber, String newPin, double newBalance) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            return false;
        }
        account.setPin(newPin);
        account.setBalance(newBalance);
        saveAccounts();
        return true;
    }

    public boolean deleteAccount(String accountNumber) {
        if (!accounts.containsKey(accountNumber)) {
            return false;
        }
        accounts.remove(accountNumber);
        saveAccounts();
        return true;
    }
}
