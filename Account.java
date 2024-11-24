package banking;

public class Account extends Customer {
    private int balance;
    private int accountNumber;

    public Account(String fName, String lName, int accountNumber, int balance) {
        setFirstName(fName);
        setLastName(lName);
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public int getAccountNum() {
        return accountNumber;
    }

    public void deposit(double depositAmount) {
        balance += depositAmount;
    }

    public void withdraw(double withdrawAmount) {
        if (withdrawAmount > balance) {
            // Notify insufficient balance
            throw new IllegalArgumentException("Insufficient balance for withdrawal");
        }
        balance -= withdrawAmount;
    }
    public void transfer(Account destinationAccount, int amount) {
        if (amount > balance) {
            // Notify insufficient balance
            throw new IllegalArgumentException("Insufficient balance for transfer");
        }
        balance -= amount;
        destinationAccount.deposit(amount);
    }
    }
