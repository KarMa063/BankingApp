package banking;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private List<Account> globalAccounts;
    private StringBuilder sbAllData = new StringBuilder();

    private JButton showAllButton = new JButton("Show All");
    private JButton depositButton = new JButton("Deposit");
    private JButton withdrawButton = new JButton("Withdraw");
    private JButton transferButton = new JButton("Transfer");

    private JTextField AccountDeposit = new JTextField();
    private JTextField AmountDeposit = new JTextField();
    private JTextField AccountWithdraw = new JTextField();
    private JTextField AmountWithdraw = new JTextField();
    private JTextField Acc1Transfer = new JTextField();
    private JTextField Acc2Transfer = new JTextField();
    private JTextField AmountTransfer = new JTextField();

    public GUI(List<Account> accounts) {
        super("Banking System");
        globalAccounts = accounts;

        // Populate sbAllData
        for (Account acc : globalAccounts) {
            sbAllData.append(acc.getFirstName()).append(" ").append(acc.getLastName())
                    .append(" - Account: ").append(acc.getAccountNum()).append(", Balance: ").append(acc.getBalance()).append("\n");
        }
        getContentPane().setLayout(null);

        showAllButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        showAllButton.setBounds(20, 44, 100, 20);
        getContentPane().add(showAllButton);

        depositButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        depositButton.setBounds(20, 100, 100, 20);
        getContentPane().add(depositButton);

        withdrawButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        withdrawButton.setBounds(20, 150, 100, 20);
        getContentPane().add(withdrawButton);

        transferButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        transferButton.setBounds(20, 200, 100, 20);
        getContentPane().add(transferButton);
        AccountDeposit.setFont(new Font("Tahoma", Font.PLAIN, 15));

        // Set placeholders for text fields with FocusListener
        AccountDeposit.setText("Enter Account Number");
        AccountDeposit.setBounds(150, 100, 167, 20);
        AccountDeposit.addFocusListener(new PlaceholderFocusListener(AccountDeposit, "Enter Account Number"));
        getContentPane().add(AccountDeposit);
        AmountDeposit.setFont(new Font("Tahoma", Font.PLAIN, 15));

        AmountDeposit.setText("Enter Deposit Amount");
        AmountDeposit.setBounds(339, 97, 167, 20);
        AmountDeposit.addFocusListener(new PlaceholderFocusListener(AmountDeposit, "Enter Deposit Amount"));
        getContentPane().add(AmountDeposit);
        AccountWithdraw.setFont(new Font("Tahoma", Font.PLAIN, 15));

        AccountWithdraw.setText("Enter Account Number");
        AccountWithdraw.setBounds(150, 150, 167, 20);
        AccountWithdraw.addFocusListener(new PlaceholderFocusListener(AccountWithdraw, "Enter Account Number"));
        getContentPane().add(AccountWithdraw);
        AmountWithdraw.setFont(new Font("Tahoma", Font.PLAIN, 15));

        AmountWithdraw.setText("Enter Withdraw Amount");
        AmountWithdraw.setBounds(339, 153, 180, 20);
        AmountWithdraw.addFocusListener(new PlaceholderFocusListener(AmountWithdraw, "Enter Withdraw Amount"));
        getContentPane().add(AmountWithdraw);
        Acc1Transfer.setFont(new Font("Tahoma", Font.PLAIN, 15));

        Acc1Transfer.setText("From Account Number");
        Acc1Transfer.setBounds(150, 200, 167, 20);
        Acc1Transfer.addFocusListener(new PlaceholderFocusListener(Acc1Transfer, "From Account Number"));
        getContentPane().add(Acc1Transfer);
        Acc2Transfer.setFont(new Font("Tahoma", Font.PLAIN, 15));

        Acc2Transfer.setText("To Account Number");
        Acc2Transfer.setBounds(339, 203, 167, 20);
        Acc2Transfer.addFocusListener(new PlaceholderFocusListener(Acc2Transfer, "To Account Number"));
        getContentPane().add(Acc2Transfer);
        AmountTransfer.setFont(new Font("Tahoma", Font.PLAIN, 15));

        AmountTransfer.setText("Enter Transfer Amount");
        AmountTransfer.setBounds(529, 203, 174, 20);
        AmountTransfer.addFocusListener(new PlaceholderFocusListener(AmountTransfer, "Enter Transfer Amount"));
        getContentPane().add(AmountTransfer);

        HandlerClass handler = new HandlerClass();
        showAllButton.addActionListener(handler);
        depositButton.addActionListener(handler);
        withdrawButton.addActionListener(handler);
        transferButton.addActionListener(handler);
    }

    private class HandlerClass implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == showAllButton) {
                JOptionPane.showMessageDialog(null, sbAllData.toString(), "All Account Data", JOptionPane.PLAIN_MESSAGE);
            } else if (e.getSource() == depositButton) {
                try {
                    int accNum = Integer.parseInt(AccountDeposit.getText().trim());
                    int depositAmount = Integer.parseInt(AmountDeposit.getText().trim());

                    Account depositAccount = findAccount(accNum);
                    if (depositAccount != null) {
                        try {
                            depositAccount.deposit(depositAmount);
                            updateBalances(); // Update balances after deposit
                            writeAccountsToCSV(globalAccounts, "accounts.csv"); // Write changes back to CSV
                            JOptionPane.showMessageDialog(null, "Deposit Successful!", "Deposit", JOptionPane.INFORMATION_MESSAGE);
                        } catch (IllegalArgumentException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Deposit Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Account not found", "Deposit Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input format", "Deposit Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == withdrawButton) {
                try {
                    int accNum = Integer.parseInt(AccountWithdraw.getText().trim());
                    int withdrawAmount = Integer.parseInt(AmountWithdraw.getText().trim());

                    Account withdrawAccount = findAccount(accNum);
                    if (withdrawAccount != null) {
                        try {
                            withdrawAccount.withdraw(withdrawAmount);
                            updateBalances(); // Update balances after withdrawal
                            writeAccountsToCSV(globalAccounts, "accounts.csv"); // Write changes back to CSV
                            JOptionPane.showMessageDialog(null, "Withdrawal Successful!", "Withdrawal", JOptionPane.INFORMATION_MESSAGE);
                        } catch (IllegalArgumentException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Withdrawal Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Account not found", "Withdrawal Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input format", "Withdrawal Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == transferButton) {
                try {
                    int accNum1 = Integer.parseInt(Acc1Transfer.getText().trim());
                    int accNum2 = Integer.parseInt(Acc2Transfer.getText().trim());
                    int transferAmountValue = Integer.parseInt(AmountTransfer.getText().trim());

                    Account transferFromAccount = findAccount(accNum1);
                    Account transferToAccount = findAccount(accNum2);

                    if (transferFromAccount != null && transferToAccount != null) {
                        try {
                            transferFromAccount.transfer(transferToAccount, transferAmountValue);
                            updateBalances(); // Update balances after transfer
                            writeAccountsToCSV(globalAccounts, "accounts.csv"); // Write changes back to CSV
                            JOptionPane.showMessageDialog(null, "Transfer Successful!", "Transfer", JOptionPane.INFORMATION_MESSAGE);
                        } catch (IllegalArgumentException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Transfer Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "One or both accounts not found", "Transfer Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input format", "Transfer Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private Account findAccount(int accountNumber) {
        for (Account account : globalAccounts) {
            if (account.getAccountNum() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    private void updateBalances() {
        sbAllData.setLength(0);
        for (Account account : globalAccounts) {
            sbAllData.append("Account Number: ").append(account.getAccountNum())
                    .append("\nFirst Name: ").append(account.getFirstName())
                    .append("\nLast Name: ").append(account.getLastName())
                    .append("\nBalance: ").append(account.getBalance())
                    .append("\n\n");
        }
    }

    private void writeAccountsToCSV(List<Account> accounts, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Account account : accounts) {
                writer.write(account.getFirstName() + "," +
                        account.getLastName() + "," +
                        account.getAccountNum() + "," +
                        account.getBalance() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class PlaceholderFocusListener implements FocusListener {
        private JTextField textField;
        private String placeholder;

        public PlaceholderFocusListener(JTextField textField, String placeholder) {
            this.textField = textField;
            this.placeholder = placeholder;
        }

        @Override
        public void focusGained(FocusEvent e) {
            if (textField.getText().equals(placeholder)) {
                textField.setText("");
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (textField.getText().isEmpty()) {
                textField.setText(placeholder);
            }
        }
    }

    public static void main(String[] args) {
        List<Account> accounts = retrieveAccountsFromCSV("accounts.csv");

        GUI gui = new GUI(accounts);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(700, 400);
        gui.setVisible(true);
    }

    private static List<Account> retrieveAccountsFromCSV(String filename) {
        List<Account> accounts = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4) {
                    String firstName = data[0].trim();
                    String lastName = data[1].trim();
                    int accountNumber = Integer.parseInt(data[2].trim());
                    int balance = Integer.parseInt(data[3].trim());
                    accounts.add(new Account(firstName, lastName, accountNumber, balance));
                } else {
                    System.err.println("Invalid data format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;
    }
}