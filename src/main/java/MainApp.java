import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        // Scanner allows the user to enter information
        Scanner input = new Scanner(System.in);   
        Login login = new Login();   
        
         // --- REGISTRATION SECTION ---
        System.out.println("=== USER REGISTRATION ===");
        System.out.print("Enter a username: ");
        String username = input.nextLine();

        System.out.print("Enter a password: ");
        String password = input.nextLine();

        System.out.print("Enter your SA phone number (+27...): ");
        String phone = input.nextLine();
        
        String regResult = login.registerUser(username, password, phone);
        System.out.println(regResult);

        if (!regResult.equals("User registered successfully.")) {
            System.out.println("Registration failed. Exiting.");
            return;
        }
           // --- LOGIN SECTION ---
        System.out.println("\n=== USER LOGIN ===");
        System.out.print("Enter your username: ");
        String loginUsername = input.nextLine();

        System.out.print("Enter your password: ");
        String loginPassword = input.nextLine();

        boolean loggedIn = login.loginUser(loginUsername, loginPassword);
        System.out.println(login.returnLoginStatus(loggedIn));

        if (!loggedIn) {
            System.out.println("Login failed. Exiting.");
            return;
        }

        // --- MESSAGING (only if logged in) ---
        System.out.println("\nWelcome to QuickChat.");

        System.out.print("How many messages do you want to send? ");
        int numMessages = Integer.parseInt(input.nextLine());

        int messageCounter = 0;

        // Main menu loop
        boolean running = true;
        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            int menuChoice = Integer.parseInt(input.nextLine());

            switch (menuChoice) {

                case 1: // Send Messages
                    if (messageCounter >= numMessages) {
                        System.out.println("You have reached your message limit of " + numMessages + ".");
                        break;
                    }

                    messageCounter++;

                    System.out.print("Enter recipient cell number: ");
                    String recipient = input.nextLine();

                    System.out.print("Enter your message: ");
                    String messageText = input.nextLine();

                    Message msg = new Message(recipient, messageText, messageCounter);

                    // Validate recipient
                    System.out.println(msg.checkRecipientCell());

                    // Validate message length
                    String lengthCheck = msg.checkMessageLength();
                    System.out.println(lengthCheck);

                    if (!lengthCheck.equals("Message ready to send.")) {
                        System.out.println("Message not sent due to length issue.");
                        messageCounter--; // don't count this attempt
                        break;
                    }

                    // Show hash
                    System.out.println("Message Hash: " + msg.getMessageHash());

                    // Send options
                    System.out.println("\n1) Send Message");
                    System.out.println("2) Disregard Message");
                    System.out.println("3) Store Message");
                    System.out.print("Choose: ");
                    int sendChoice = Integer.parseInt(input.nextLine());

                    String sendResult = msg.sentMessage(sendChoice);
                    System.out.println(sendResult);

                    // Display full message details if sent
                    if (sendChoice == 1) {
                        System.out.println("\n--- MESSAGE DETAILS ---");
                        System.out.println("Message ID:   " + msg.getMessageID());
                        System.out.println("Message Hash: " + msg.getMessageHash());
                        System.out.println("Recipient:    " + msg.getRecipient());
                        System.out.println("Message:      " + msg.getMessage());
                    }
                    break;

                case 2: // Coming Soon
                    System.out.println("Coming Soon.");
                    break;

                case 3: // Quit
                    System.out.println("\n--- ALL SENT MESSAGES ---");
                    System.out.println(Message.printMessages());
                    System.out.println("Total messages sent: " + Message.returnTotalMessages());
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }

        input.close();
    }     
}
