package banking;

import java.io.IOException;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedList<Account> accounts = new LinkedList<>();

        ReadAccounts readAccounts = new ReadAccounts("Accounts.csv");
        try {
            LinkedList<String> firstNames = readAccounts.getFirstNames();
            LinkedList<String> lastNames = readAccounts.getLastNames();
            LinkedList<Integer> accountNumbers = readAccounts.getAccounts();
            LinkedList<Integer> balances = readAccounts.getBalances();

            for (int i = 0; i < firstNames.size(); i++) {
                Account account = new Account(firstNames.get(i), lastNames.get(i), accountNumbers.get(i), balances.get(i));
                accounts.add(account);
            }

            for (Account account : accounts) {
                System.out.println("Account Number: " + account.getAccountNum());
                System.out.println("First Name: " + account.getFirstName());
                System.out.println("Last Name: " + account.getLastName());
                System.out.println("Balance: " + account.getBalance());
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Error reading accounts: " + e.getMessage());
        }
    }
}
