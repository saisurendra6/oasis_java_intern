import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

enum TransactionType {
    WITHDRAWL,
    DEPOSIT,
    TRANSFER,
}

class User {
    final String userId;
    private final int pin;
    private float balance;
    private ArrayList<Transaction> transactions = new ArrayList<Transaction>();

    public User(String userId, int pin) {
        this.userId = userId;
        this.pin = pin;
        this.balance = 25000;
        addTransaction(2500, TransactionType.DEPOSIT, userId);
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public float getBalance() {
        return balance;
    }

    public boolean validateUser(int inp) {
        return (inp == pin);
    }

    private void addTransaction(float amount, TransactionType type, String baseId) {
        Date date = new Date();
        transactions.add(
                new Transaction(type + "_" + baseId + "__" + date.getTime(), date.toString(), type, amount, balance));
    }

    public float withdrawl(float amount) {
        if (amount > balance) {
            System.out.println("Can't withdrawl amount more than your balance.");
            return balance;
        }
        balance -= amount;
        addTransaction(amount, TransactionType.WITHDRAWL, userId);
        return balance;
    }

    public float deposit(float amount) {
        balance += amount;
        addTransaction(amount, TransactionType.DEPOSIT, userId);
        return balance;
    }

    public float transferFunds(float amount, String receiverUid) {
        if (amount > balance) {
            System.out.println("Can't transfer amount more than your balance.");
            return balance;
        }
        balance -= amount;
        addTransaction(amount, TransactionType.TRANSFER, User.getTransferBaseStr(userId, receiverUid));
        return balance;
    }

    public static String getTransferBaseStr(String uId1, String uId2) {
        if (uId1.hashCode() > uId2.hashCode())
            return uId1 + "_" + uId2;
        else
            return uId2 + "_" + uId1;
    }
}

final class Transaction {
    String tractionId;
    String timeStr;
    TransactionType type;
    float amount;
    float remainBal;

    public Transaction(String tractionId, String timeStr, TransactionType type, float amount, float remainBal) {
        this.tractionId = tractionId;
        this.timeStr = timeStr;
        this.type = type;
        this.amount = amount;
        this.remainBal = remainBal;
    }

    @Override
    public String toString() {
        return "Transaction [tractionId=" + tractionId + ", timeStr=" + timeStr + ", type=" + type + ", amount="
                + amount + ", remainBal=" + remainBal + "]";
    }
}

public class AtmInterface {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String cardNo;
        int pin;
        User user;

        int choice = 0;
        float amount;

        HashMap<String, User> userMap = new HashMap<String, User>();
        userMap.put("123", new User("123", 1234));
        userMap.put("456", new User("456", 4567));
        userMap.put("789", new User("789", 7890));
        userMap.put("246", new User("246", 2468));

        System.out.println(
                "\n\t\t\t\t ----------------------- \n\t\t\t\t|\tMANA ATM\t|\t\t\n\t\t\t\t ----------------------- \n");
        System.out.println("Welcome to mana ATM\nEnter Card Number(user ID): ");
        cardNo = scanner.next();
        if (!userMap.containsKey(cardNo)) {
            System.out.println("invalid user ID\nExiting...");
            scanner.close();
            return;
        }
        user = userMap.get(cardNo);
        System.out.println("Enter pin: ");
        pin = scanner.nextInt();
        if (!user.validateUser(pin)) {
            System.out.println("Incorrect Pin\nExiting...");
            scanner.close();
            return;
        }

        while (choice != 5) {
            System.out.println("1. Transactions\n2. Withdrawl\n3. Deposit\n4. Transfer\n5. Quit\nEnter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Your Transactions: ");
                    // System.out.println(user.getTransactions());
                    System.out.println("Transaction ID\t\t\t\tTime\t\t\t\tremainig\tamount");
                    for (Transaction transaction : user.getTransactions()) {
                        System.out.println(transaction.tractionId + "\t\t" + transaction.timeStr + "\t"
                                + transaction.remainBal + "/-\t" + transaction.amount + "/-");
                    }
                    break;
                case 2:
                    System.out.println("Enter the amount to withdraw: ");
                    amount = scanner.nextFloat();
                    if (amount <= 0) {
                        System.out.println("Amount should be greater than zero.");
                        break;
                    }
                    System.out.println("remaining balance: " + user.withdrawl(amount));
                    break;
                case 3:
                    System.out.println("enter the amount to deposit: ");
                    amount = scanner.nextFloat();
                    if (amount <= 0) {
                        System.out.println("Amount should be greater than zero.");
                        break;
                    }
                    System.out.println("remaining balance: " + user.deposit(amount));
                    break;
                case 4:
                    System.out.println("enter the receiver user ID: ");
                    String receiverId = scanner.next();
                    if (!userMap.containsKey(receiverId))
                        System.out.println("canot find user");
                    else if (receiverId == user.userId)
                        System.out.println("You cannot transfer money into same account.");
                    else {
                        System.out.println("Enter the amount to transfer: ");
                        amount = scanner.nextFloat();
                        if (amount <= 0) {
                            System.out.println("Amount should be greater than zero.");
                            break;
                        }
                        System.out.println("remaining balance: " + user.transferFunds(amount, receiverId));
                    }

                    break;

                case 5:
                    break;

                default:
                    System.out.println("enter a valid input");
                    break;
            }
        }
        scanner.close();
        System.out.println("Thank You :-)\nVisit Again\nExiting...");

    }
}
