import java.net.*;
import java.io.*;
import java.util.Scanner;

public class BankClient 
{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\033[1;95m";

    public static void main(String[] args) throws Exception 
    {
        Socket server = new Socket("localhost", 12345);
        DataInputStream dataInputStream = new DataInputStream(server.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(server.getOutputStream());
        Scanner scanner = new Scanner(System.in);

        System.out.println(ANSI_PURPLE + "This is Banking Client\n" + ANSI_RESET);

        while (true) {
            // Read and display server prompts, and send the required information
            String prompt = dataInputStream.readUTF();
            System.out.println(prompt);
            String accountNumber = scanner.nextLine();
            dataOutputStream.writeUTF(accountNumber);
            dataOutputStream.flush();

            if (accountNumber.equalsIgnoreCase("exit")) {
                break;
            }

            prompt = dataInputStream.readUTF();
            System.out.println(prompt);
            String phoneNumber = scanner.nextLine();
            dataOutputStream.writeUTF(phoneNumber);
            dataOutputStream.flush();

            if (phoneNumber.equalsIgnoreCase("exit")) {
                break;
            }

            // Read server response about Bank Account verification
            String response = dataInputStream.readUTF();
            System.out.println(response);

            if (response.contains("Bank Account not found")) 
            {
                //System.out.println("Do you want to create a new Bank Account? (yes/no)");
                String createAccount = scanner.nextLine();
                dataOutputStream.writeUTF(createAccount);
                dataOutputStream.flush();

                // Read the server's response
                response = dataInputStream.readUTF();
                System.out.println(response);

                if (createAccount.equalsIgnoreCase("no")) {
                    continue; // Allows the user to re-enter details
                }
            }
        }

        // Cleanup
        dataInputStream.close();
        dataOutputStream.close();
        server.close();
        scanner.close();
    }
}
