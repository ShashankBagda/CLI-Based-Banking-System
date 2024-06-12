import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class BankClient {
 public static final String ANSI_RESET = "\u001B[0m";
 public static final String ANSI_RED = "\u001B[31m";
 public static final String ANSI_GREEN = "\u001B[32m";
 public static final String ANSI_BLUE = "\u001B[34m";
 public static final String ANSI_PURPLE = "\033[1;95m";

 public static void main(String[] args) {
  try (
   Socket server = new Socket("localhost", 12345);
   DataInputStream dataInputStream = new DataInputStream(server.getInputStream());
   DataOutputStream dataOutputStream = new DataOutputStream(server.getOutputStream());
   Scanner scanner = new Scanner(System.in)) {

  System.out.println(ANSI_PURPLE + 
  "    ___                         __ _              __                        ____                 __  \n" +
  "   /   |   ____   ____ _ ___   / /( )_____       / /____ _ _   __ ____ _   / __ ) ____ _ ____   / /__\n" +
  "  / /| |  / __ \\ / __ `// _ \\ / / |// ___/  __  / // __ `/| | / // __ `/  / __  |/ __ `// __ \\ / //_/\n" +
  " / ___ | / / / // /_/ //  __// /   (__  )  / /_/ // /_/ / | |/ // /_/ /  / /_/ // /_/ // / / // ,<   \n" +
  "/_/  |_|/_/ /_/ \\__, / \\___//_/   /____/   \\____/ \\__,_/  |___/ \\__,_/  /_____/ \\__,_//_/ /_//_/|_|  \n" +
  "               /____/                                                                                \n\n" + ANSI_RESET);

  System.out.println(ANSI_GREEN + 
  "  _____  _  _               _     _____                       _                _  \n" +
  " /  __ \\| |(_)             | |   |_   _|                     (_)              | | \n" +
  " | /  \\/| | _   ___  _ __  | |_    | |  ___  _ __  _ __ ___   _  _ __    __ _ | | \n" +
  " | |    | || | / _ \\| '_ \\ | __|   | | / _ \\| '__|| '_ ` _ \\ | || '_ \\  / _` || | \n" +
  " | \\__/\\| || ||  __/| | | || |_    | ||  __/| |   | | | | | || || | | || (_| || | \n" +
  "  \\____/|_||_| \\___||_| |_| \\__|   \\_/ \\___||_|   |_| |_| |_||_||_| |_| \\__,_||_| \n" +
  "                                                                                  \n"+ ANSI_RESET);

   String userTypePrompt = dataInputStream.readUTF();
   System.out.println(userTypePrompt);
   String userType = scanner.nextLine();
   dataOutputStream.writeUTF(userType);
   dataOutputStream.flush();

   if (userType.equalsIgnoreCase("admin")) {
    handleAdmin(dataInputStream, dataOutputStream, scanner);
   } else if (userType.equalsIgnoreCase("customer")) {
    handleCustomer(dataInputStream, dataOutputStream, scanner);
   } else {
    System.out.println(ANSI_RED + "Invalid user type." + ANSI_RESET);
   }
  } catch (IOException e) {
   e.printStackTrace();
  }
 }

 private static void handleAdmin(DataInputStream dataInputStream, DataOutputStream dataOutputStream, Scanner scanner) throws IOException {
  String adminOptions = dataInputStream.readUTF();
  System.out.println(adminOptions);
  String adminChoice = scanner.nextLine();
  dataOutputStream.writeUTF(adminChoice);
  dataOutputStream.flush();

  if (adminChoice.equals("1")) {
   System.out.println(dataInputStream.readUTF());
   String accountNumber = scanner.nextLine();
   dataOutputStream.writeUTF(accountNumber);
   dataOutputStream.flush();

   String response = dataInputStream.readUTF();
   System.out.println(response);
  } else if (adminChoice.equals("2")) {
   System.out.println(dataInputStream.readUTF());
   String name = scanner.nextLine();
   dataOutputStream.writeUTF(name);
   dataOutputStream.flush();

   System.out.println(dataInputStream.readUTF());
   String phoneNumber = scanner.nextLine();
   dataOutputStream.writeUTF(phoneNumber);
   dataOutputStream.flush();

   System.out.println(dataInputStream.readUTF());
   String address = scanner.nextLine();
   dataOutputStream.writeUTF(address);
   dataOutputStream.flush();

   System.out.println(dataInputStream.readUTF());
   String gmail = scanner.nextLine();
   dataOutputStream.writeUTF(gmail);
   dataOutputStream.flush();

   System.out.println(dataInputStream.readUTF());
   String panNumber = scanner.nextLine();
   dataOutputStream.writeUTF(panNumber);
   dataOutputStream.flush();

   System.out.println(dataInputStream.readUTF());
   String aadharNumber = scanner.nextLine();
   dataOutputStream.writeUTF(aadharNumber);
   dataOutputStream.flush();

   System.out.println(dataInputStream.readUTF());
   String openingBalance = scanner.nextLine();
   dataOutputStream.writeUTF(openingBalance);
   dataOutputStream.flush();

   String response = dataInputStream.readUTF();
   System.out.println(response);
  } else {
   // System.out.println(ANSI_RED + "Invalid option." + ANSI_RESET);
  }
 }

 private static void handleCustomer(DataInputStream dataInputStream, DataOutputStream dataOutputStream, Scanner scanner) throws IOException {
  System.out.println(dataInputStream.readUTF());
  String accountNumber = scanner.nextLine();
  dataOutputStream.writeUTF(accountNumber);
  dataOutputStream.flush();

  System.out.println(dataInputStream.readUTF());
  String phoneNumber = scanner.nextLine();
  dataOutputStream.writeUTF(phoneNumber);
  dataOutputStream.flush();

  String authResponse = dataInputStream.readUTF();
  System.out.println(authResponse);

  if (authResponse.contains("successful")) {
   System.out.println(dataInputStream.readUTF());
   String customerChoice = scanner.nextLine();
   dataOutputStream.writeUTF(customerChoice);
   dataOutputStream.flush();

   switch (customerChoice) {
    case "1":
     System.out.println(dataInputStream.readUTF());
     String addAmount = scanner.nextLine();
     dataOutputStream.writeUTF(addAmount);
     dataOutputStream.flush();
     System.out.println(dataInputStream.readUTF());
     break;
    case "2":
     System.out.println(dataInputStream.readUTF());
     String withdrawAmount = scanner.nextLine();
     dataOutputStream.writeUTF(withdrawAmount);
     dataOutputStream.flush();
     System.out.println(dataInputStream.readUTF());
     break;
    case "3":
     System.out.println(dataInputStream.readUTF());
     String receiverAccountNumber = scanner.nextLine();
     dataOutputStream.writeUTF(receiverAccountNumber);
     dataOutputStream.flush();
     System.out.println(dataInputStream.readUTF());
     String transferAmount = scanner.nextLine();
     dataOutputStream.writeUTF(transferAmount);
     dataOutputStream.flush();
     System.out.println(dataInputStream.readUTF());
     break;
    case "4":
     System.out.println(dataInputStream.readUTF());
     break;
    case "5":
     System.out.println(dataInputStream.readUTF());
     break;
    default:
     System.out.println(ANSI_RED + "Invalid option." + ANSI_RESET);
     break;
   }
  } else {
   System.out.println(ANSI_RED + "Authentication failed." + ANSI_RESET);
  }
 }
}
