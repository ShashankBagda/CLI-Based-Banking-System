import java.net.*;
import java.io.*;

public class BankServer 
{
    // ANSI color codes
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\033[1;95m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static void main(String[] args) throws Exception 
    {
        @SuppressWarnings("resource")
        ServerSocket server = new ServerSocket(12345);
        System.out.println(ANSI_PURPLE + "This is Banking Server" + ANSI_RESET);

        while (true) 
        {
            Socket connectionsSocket = server.accept();
            DataInputStream dataInputStream = new DataInputStream(connectionsSocket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(connectionsSocket.getOutputStream());

            while (true) 
            {
                // Ask for Bank Account number and phone number
                dataOutputStream.writeUTF(ANSI_BLUE  + "\nEnter your Bank Account number:" + ANSI_RESET);
                dataOutputStream.flush();
                String accountNumber = dataInputStream.readUTF();

                if (accountNumber.equalsIgnoreCase("exit")) 
                {
                    dataOutputStream.writeUTF("STOP");
                    dataOutputStream.flush();
                    break;
                }

                dataOutputStream.writeUTF(ANSI_BLUE + "Enter your phone number:" + ANSI_RESET);
                dataOutputStream.flush();
                String phoneNumber = dataInputStream.readUTF();

                if (phoneNumber.equalsIgnoreCase("exit")) 
                {
                    dataOutputStream.writeUTF("STOP");
                    dataOutputStream.flush();
                    break;
                }

                // Display received information on the server's terminal
                System.out.println("\nData Received from client:");
                System.out.println("Bank Account Number: " + accountNumber);
                System.out.println("Phone Number: " + phoneNumber);

                // Check if the file exists in the Customers/ folder
                File accountFile = new File("Customers/" + accountNumber + ".txt");
                if (accountFile.exists()) 
                {
                    System.out.println(ANSI_GREEN + "\nBank Account found: " + accountNumber + ANSI_RESET);
                    dataOutputStream.writeUTF(ANSI_GREEN + "\nBank Account found: " + accountNumber + ANSI_RESET);
                } 
                else 
                {
                    System.out.println(ANSI_RED + "\nBank Account not found: " + accountNumber + ANSI_RESET);
                    dataOutputStream.writeUTF(ANSI_RED + "\nBank Account not found: " + accountNumber + " Do you want to create a new Bank Account? (yes/no)" + ANSI_RESET);
                    dataOutputStream.flush();
                    String response = dataInputStream.readUTF();

                    if (response.equalsIgnoreCase("yes")) 
                    {
                        // Create the Bank Account file
                        PrintWriter writer = new PrintWriter(new FileWriter(accountFile));
                        writer.println("Bank Account Number: " + accountNumber);
                        writer.println("Phone Number: " + phoneNumber);
                        writer.close();

                        System.out.println(ANSI_GREEN + "\nBank Account created: " + accountNumber + ANSI_RESET);
                        dataOutputStream.writeUTF(ANSI_GREEN + "\nBank Account created successfully: " + accountNumber + ANSI_RESET);
                    } else {
                        dataOutputStream.writeUTF("\nPlease Re-Enter your Bank Account Details.");
                    }
                }
                dataOutputStream.flush();
            }

            dataInputStream.close();
            dataOutputStream.close();
            connectionsSocket.close();
        }
    }
}
