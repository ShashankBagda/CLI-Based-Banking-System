import java.net.*;
import java.io.*;
import java.util.*;

public class BankServer {
    // ANSI color codes
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\033[1;95m";
    public static final String ANSI_BLUE = "\u001B[34m";

    private static int accountNumberCounter;

    public static void main(String[] args) {
        try {
            ensureCustomersDirectory();
            @SuppressWarnings("resource")
            ServerSocket server = new ServerSocket(12345);
            System.out.println(ANSI_PURPLE + " - - - Welcome to ICT Bank - - Server Terminal - - - \n" + ANSI_RESET);

            while (true) {
                Socket connectionSocket = server.accept();
                //Socket connectionSocket = new Socket("192.168.183.244", 12345);
                handleClient(connectionSocket);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static void ensureCustomersDirectory() {
        File dir = new File("Customers");
        if (!dir.exists()) {
            dir.mkdirs();
        } else {
            File[] files = dir.listFiles();
            int maxAccountNumber = 0;
            if (files != null) {
                for (File file : files) {
                    String fileName = file.getName();
                    if (fileName.endsWith(".txt")) {
                        // Extract the account number from the file name
                        String accountNumberStr = fileName.substring(0, fileName.length() - 4); // Remove ".txt" extension
                        int accountNumber = Integer.parseInt(accountNumberStr);
                        if (accountNumber > maxAccountNumber) {
                            maxAccountNumber = accountNumber;
                        }
                    }
                }
            }
            // Set the account number counter to the next available number
            accountNumberCounter = maxAccountNumber + 1;
        }
    }

    private static void handleClient(Socket connectionSocket) {
        try (DataInputStream dataInputStream = new DataInputStream(connectionSocket.getInputStream());
             DataOutputStream dataOutputStream = new DataOutputStream(connectionSocket.getOutputStream())) {

            dataOutputStream.writeUTF(ANSI_BLUE + "Are you an Admin or a Customer? (admin/customer):" + ANSI_RESET);
            dataOutputStream.flush();
            String userType = dataInputStream.readUTF();

            if (userType.equalsIgnoreCase("admin")) 
            {
                System.out.println("User Set : Admin");
                handleAdmin(dataInputStream, dataOutputStream);
            } 
            else if (userType.equalsIgnoreCase("customer")) 
            {
                System.out.println("User Set : Customer");
                handleCustomer(dataInputStream, dataOutputStream);
            } 
            else 
            {
                dataOutputStream.writeUTF(ANSI_RED + "Invalid option. Please restart the program." + ANSI_RESET);
                System.out.println(ANSI_RED + "Invalid option. Please restart the program." + ANSI_RESET);
                dataOutputStream.flush();
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static void handleAdmin(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(ANSI_BLUE + "Admin Options:\n1. Search Customer\n2. Create New Account\nChoose an option (1/2):" + ANSI_RESET);
        dataOutputStream.flush();
        String option = dataInputStream.readUTF();

        if (option.equals("1")) 
        {
            System.out.println("Searching Customer");
            searchCustomer(dataInputStream, dataOutputStream);
        } 
        else if (option.equals("2")) 
        {
            System.out.println("Opening New Account");
            createNewAccount(dataInputStream, dataOutputStream);
        } else {
            dataOutputStream.writeUTF(ANSI_RED + "Invalid option." + ANSI_RESET);
            System.out.println(ANSI_RED + "Invalid option." + ANSI_RESET);
            dataOutputStream.flush();
        }
    }

    private static void searchCustomer(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(ANSI_BLUE + "Enter Bank Account number:" + ANSI_RESET);
        dataOutputStream.flush();
        
        try {
            String accountNumber = dataInputStream.readUTF();
            System.out.println(ANSI_BLUE + "Entered Bank Account Number: " + accountNumber + ANSI_RESET);
            File accountFile = new File("Customers/" + accountNumber + ".txt");
            if (accountFile.exists()) {
                StringBuilder accountInfo = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new FileReader(accountFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        accountInfo.append(line).append("\n");
                    }
                }

                dataOutputStream.writeUTF(ANSI_GREEN + "Account Information:\n" + accountInfo.toString() + ANSI_RESET);
                System.out.println(ANSI_GREEN + "Account Information:\n" + accountInfo.toString() + ANSI_RESET);
            } else {
                dataOutputStream.writeUTF(ANSI_RED + "Bank Account not found: " + accountNumber + ANSI_RESET);
                System.out.println(ANSI_RED + "Bank Account not found: " + accountNumber + ANSI_RESET);
            }
            dataOutputStream.flush();
            // Other reading operations
        } catch (EOFException e) {
            dataOutputStream.writeUTF(ANSI_RED + "Connection closed unexpectedly. Please try again." + ANSI_RESET);
            System.out.println(ANSI_RED + "Connection closed unexpectedly. Please try again." + ANSI_RESET);
            dataOutputStream.flush();
        }
        
        

        
    }

    private static void createNewAccount(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(ANSI_BLUE + "Enter Name:" + ANSI_RESET);
        dataOutputStream.flush();
        String name = dataInputStream.readUTF();

        dataOutputStream.writeUTF(ANSI_BLUE + "Enter Phone Number:" + ANSI_RESET);
        dataOutputStream.flush();
        String phoneNumber = dataInputStream.readUTF();

        dataOutputStream.writeUTF(ANSI_BLUE + "Enter Address:" + ANSI_RESET);
        dataOutputStream.flush();
        String address = dataInputStream.readUTF();

        dataOutputStream.writeUTF(ANSI_BLUE + "Enter Gmail:" + ANSI_RESET);
        dataOutputStream.flush();
        String gmail = dataInputStream.readUTF();

        dataOutputStream.writeUTF(ANSI_BLUE + "Enter PAN Number:" + ANSI_RESET);
        dataOutputStream.flush();
        String panNumber = dataInputStream.readUTF();

        dataOutputStream.writeUTF(ANSI_BLUE + "Enter Aadhar Card Number:" + ANSI_RESET);
        dataOutputStream.flush();
        String aadharNumber = dataInputStream.readUTF();

        dataOutputStream.writeUTF(ANSI_BLUE + "Enter Account Opening Balance:" + ANSI_RESET);
        dataOutputStream.flush();
        String openingBalance = dataInputStream.readUTF();

        String accountNumber = String.valueOf(accountNumberCounter++);
        File accountFile = new File("Customers/" + accountNumber + ".txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(accountFile))) {
            writer.println("Account Number: " + accountNumber);
            writer.println("Name: " + name);
            writer.println("Phone Number: " + phoneNumber);
            writer.println("Address: " + address);
            writer.println("Gmail: " + gmail);
            writer.println("PAN Number: " + panNumber);
            writer.println("Aadhar Number: " + aadharNumber);
            writer.println("Balance: " + openingBalance);
            writer.println("Transaction History:");
        }

        dataOutputStream.writeUTF(ANSI_GREEN + "Account created successfully. Account Number: " + accountNumber + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Account created successfully. Account Number: " + accountNumber + ANSI_RESET);
        dataOutputStream.flush();
    }

    private static void handleCustomer(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(ANSI_BLUE + "Enter your Bank Account number:" + ANSI_RESET);
        dataOutputStream.flush();
        String accountNumber = dataInputStream.readUTF();

        File accountFile = new File("Customers/" + accountNumber + ".txt");
        if (accountFile.exists()) {
            dataOutputStream.writeUTF(ANSI_BLUE + "Enter your phone number:" + ANSI_RESET);
            dataOutputStream.flush();
            String phoneNumber = dataInputStream.readUTF();

            Map<String, String> accountDetails = new HashMap<>();
            boolean authenticated = false;

            try (BufferedReader reader = new BufferedReader(new FileReader(accountFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] details = line.split(": ");
                    if (details.length == 2) {
                        accountDetails.put(details[0], details[1]);
                    }
                    if (line.contains("Phone Number: " + phoneNumber)) {
                        authenticated = true;
                    }
                }
            }

            if (authenticated) {
                dataOutputStream.writeUTF(ANSI_GREEN + "Authentication successful." + ANSI_RESET);
                System.out.println(ANSI_GREEN + "Authentication successful." + ANSI_RESET);
                dataOutputStream.flush();

                dataOutputStream.writeUTF(ANSI_BLUE + "Customer Options:\n1. Add Money\n2. Withdraw Money\n3. Transfer Money\n4. View Transaction History\n5. View Balance\nChoose an option (1-5):" + ANSI_RESET);
                dataOutputStream.flush();
                String option = dataInputStream.readUTF();

                switch (option) {
                    case "1":
                        addMoney(dataInputStream, dataOutputStream, accountNumber, accountDetails);
                        break;
                    case "2":
                        withdrawMoney(dataInputStream, dataOutputStream, accountNumber, accountDetails);
                        break;
                    case "3":
                        transferMoney(dataInputStream, dataOutputStream, accountNumber, accountDetails);
                        break;
                    case "4":
                        viewTransactionHistory(dataOutputStream, accountNumber);
                        break;
                    case "5":
                        viewBalance(dataOutputStream, accountDetails);
                        break;
                    default:
                        // dataOutputStream.writeUTF(ANSI_RED + "Invalid option." + ANSI_RESET);
                        break;
                }
                dataOutputStream.flush();
            } else {
                dataOutputStream.writeUTF(ANSI_RED + "Authentication failed. Please try again." + ANSI_RESET);
                System.out.println(ANSI_RED + "Authentication failed. Please try again." + ANSI_RESET);
                dataOutputStream.flush();
            }
        } else {
            dataOutputStream.writeUTF(ANSI_RED + "Bank Account not found." + ANSI_RESET);
            System.out.println(ANSI_RED + "Bank Account not found." + ANSI_RESET);
            dataOutputStream.flush();
        }
    }

    private static void addMoney(DataInputStream dataInputStream, DataOutputStream dataOutputStream, String accountNumber, Map<String, String> accountDetails) throws IOException {
        dataOutputStream.writeUTF(ANSI_BLUE + "Enter amount to add:" + ANSI_RESET);
        dataOutputStream.flush();
        double addAmount = Double.parseDouble(dataInputStream.readUTF());
        double newBalance = Double.parseDouble(accountDetails.get("Balance")) + addAmount;
        updateBalance(accountNumber, newBalance, "Added " + addAmount);
        dataOutputStream.writeUTF(ANSI_GREEN + "Amount added successfully. New Balance: " + newBalance + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Amount added successfully. New Balance: " + newBalance + ANSI_RESET);
    }

    private static void withdrawMoney(DataInputStream dataInputStream, DataOutputStream dataOutputStream, String accountNumber, Map<String, String> accountDetails) throws IOException {
        dataOutputStream.writeUTF(ANSI_BLUE + "Enter amount to withdraw:" + ANSI_RESET);
        dataOutputStream.flush();
        double withdrawAmount = Double.parseDouble(dataInputStream.readUTF());
        double currentBalance = Double.parseDouble(accountDetails.get("Balance"));
        if (currentBalance >= withdrawAmount) {
            double newBalance = currentBalance - withdrawAmount;
            updateBalance(accountNumber, newBalance, "Withdrew " + withdrawAmount);
            dataOutputStream.writeUTF(ANSI_GREEN + "Amount withdrawn successfully. New Balance: " + newBalance + ANSI_RESET);
            System.out.println(ANSI_GREEN + "Amount withdrawn successfully. New Balance: " + newBalance + ANSI_RESET);
        } else {
            dataOutputStream.writeUTF(ANSI_RED + "Insufficient balance." + ANSI_RESET);
            System.out.println(ANSI_RED + "Insufficient balance." + ANSI_RESET);
        }
    }

    private static void transferMoney(DataInputStream dataInputStream, DataOutputStream dataOutputStream, String accountNumber, Map<String, String> accountDetails) throws IOException {
        dataOutputStream.writeUTF(ANSI_BLUE + "Enter receiver's account number:" + ANSI_RESET);
        dataOutputStream.flush();
        String receiverAccountNumber = dataInputStream.readUTF();
        File receiverAccountFile = new File("Customers/" + receiverAccountNumber + ".txt");

        if (receiverAccountFile.exists()) {
            dataOutputStream.writeUTF(ANSI_BLUE + "Enter amount to transfer:" + ANSI_RESET);
            dataOutputStream.flush();
            double transferAmount = Double.parseDouble(dataInputStream.readUTF());
            double senderBalance = Double.parseDouble(accountDetails.get("Balance"));

            if (senderBalance >= transferAmount) {
                double newSenderBalance = senderBalance - transferAmount;
                updateBalance(accountNumber, newSenderBalance, "Transferred " + transferAmount + " to " + receiverAccountNumber);

                double receiverBalance = getBalance(receiverAccountNumber);
                double newReceiverBalance = receiverBalance + transferAmount;
                updateBalance(receiverAccountNumber, newReceiverBalance, "Received " + transferAmount + " from Account Number : " + accountNumber);

                dataOutputStream.writeUTF(ANSI_GREEN + "Amount transferred successfully. New Balance: " + newSenderBalance + ANSI_RESET);
                System.out.println(ANSI_GREEN + "Amount transferred successfully. New Balance: " + newSenderBalance + ANSI_RESET);
            } else {
                dataOutputStream.writeUTF(ANSI_RED + "Insufficient balance." + ANSI_RESET);
                System.out.println(ANSI_RED + "Insufficient balance." + ANSI_RESET);
            }
        } else {
            dataOutputStream.writeUTF(ANSI_RED + "Receiver's account not found. Transfer Failed!" + ANSI_RESET);
            System.out.println(ANSI_RED + "Receiver's account not found. Transfer Failed!" + ANSI_RESET);
        }
    }

    private static void viewTransactionHistory(DataOutputStream dataOutputStream, String accountNumber) throws IOException {
        dataOutputStream.writeUTF(ANSI_GREEN + "Transaction History:\n" + getTransactionHistory(accountNumber) + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Transaction History:\n" + getTransactionHistory(accountNumber) + ANSI_RESET);
    }

    private static void viewBalance(DataOutputStream dataOutputStream, Map<String, String> accountDetails) throws IOException {
        dataOutputStream.writeUTF(ANSI_GREEN + "Your current balance: " + accountDetails.get("Balance") + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Your current balance: " + accountDetails.get("Balance") + ANSI_RESET);
    }

    private static void updateBalance(String accountNumber, double newBalance, String transactionDetail) throws IOException {
        File accountFile = new File("Customers/" + accountNumber + ".txt");
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(accountFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Balance:")) {
                    lines.add("Balance: " + newBalance);
                } else if (line.startsWith("Transaction History:")) {
                    lines.add(line);
                    lines.add(transactionDetail);
                } else {
                    lines.add(line);
                }
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(accountFile))) {
            for (String l : lines) {
                writer.println(l);
            }
        }
    }

    private static double getBalance(String accountNumber) throws IOException {
        File accountFile = new File("Customers/" + accountNumber + ".txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(accountFile))) {
            String line;
            double balance = 0.0;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Balance:")) {
                    balance = Double.parseDouble(line.split(": ")[1]);
                    break;
                }
            }
            return balance;
        }
    }

    private static String getTransactionHistory(String accountNumber) throws IOException {
        File accountFile = new File("Customers/" + accountNumber + ".txt");
        StringBuilder history = new StringBuilder();
        boolean transactionHistoryStarted = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(accountFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (transactionHistoryStarted) {
                    history.append(line).append("\n");
                }
                if (line.equals("Transaction History:")) {
                    transactionHistoryStarted = true;
                }
            }
        }
        return history.toString();
    }
}
