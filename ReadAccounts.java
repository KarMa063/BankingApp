package banking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class ReadAccounts {
    private String URL;

    public ReadAccounts(String URL) {
        this.URL = URL;
    }

    public LinkedList<String> getFirstNames() throws IOException {
        LinkedList<String> firstNames = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new FileReader(URL));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            firstNames.add(data[0].trim());
        }
        reader.close();
        return firstNames;
    }

    public LinkedList<String> getLastNames() throws IOException {
        LinkedList<String> lastNames = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new FileReader(URL));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            lastNames.add(data[1].trim());
        }
        reader.close();
        return lastNames;
    }

    public LinkedList<Integer> getAccounts() throws IOException {
        LinkedList<Integer> accounts = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new FileReader(URL));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            accounts.add(Integer.parseInt(data[2].trim()));
        }
        reader.close();
        return accounts;
    }

    public LinkedList<Integer> getBalances() throws IOException {
        LinkedList<Integer> balances = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new FileReader(URL));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            balances.add(Integer.parseInt(data[3].trim()));
        }
        reader.close();
        return balances;
    }
}
