import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.*;

class ShowBookingApp {

    static Scanner sc = new Scanner(System.in);

    public static void clearscreen() throws InterruptedException, IOException, SQLException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    public static void main(String[] arg) {
        try {
            int menuInput;
            BookingSystem bookingSys = new BookingSystem();
            do {
                clearscreen();
                System.out.println("------------------------------------------");
                System.out.println("--WELCOME TO TICKET BOOKING SYSTEM--");
                System.out.println("------------------------------------------");
                System.out.println("1. Admin Panel");
                System.out.println("2. Customer Panel");
                System.out.println("3. Exit");
                System.out.println("------------------------------------------");
                System.out.println("Enter your choice (1/2/3):");
                menuInput = sc.nextInt();
                switch (menuInput) {
                    case 1:
                        clearscreen();
                        String adminCommand = "";
                        do {
                            System.out.println("-------Admin panel------");
                            System.out.println("Please type in the command:");
                            System.out.println("------------------------------------------");
                            System.out.println(
                                    "Setup <Show Number> <Number of Rows> <Number of seats per row> <Cancellation window in minutes>");
                            System.out.println("View <Show Number>");
                            System.out.println("Exit");
                            System.out.println("------------------------------------------");
                            System.out.println("Enter command...:");
                            String inputLine = sc.nextLine(); // Read the whole line of input
                            try (Scanner lineScanner = new Scanner(inputLine)) {
                                if (lineScanner.hasNext()) {
                                    adminCommand = lineScanner.next().toLowerCase(); // Extract the first word
                                } else {
                                    adminCommand = ""; // If no input provided
                                }
                            }

                            switch (adminCommand) {
                                case "setup":
                                    clearscreen();
                                    try {
                                        String[] getInputs = inputLine.split(" ");
                                        bookingSys.setupShow(Integer.parseInt(getInputs[1]),
                                                Integer.parseInt(getInputs[2]), Integer.parseInt(getInputs[3]),
                                                Integer.parseInt(getInputs[4]));
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid command. Please enter valid numeric values.");
                                    }
                                    break;
                                case "view":
                                    clearscreen();
                                    String[] getShowNum = inputLine.split(" ");
                                    bookingSys.viewShow(Integer.parseInt(getShowNum[1]));
                                    break;
                                case "exit":
                                    break; // Exit the loop
                                default:
                                    clearscreen();
                                    System.out.println("Please enter a valid command....");
                                    System.out.println("------------------------");
                                    break;
                            }
                        } while (!adminCommand.equals("exit"));
                        break;
                    case 2:
                        clearscreen();
                        String custCommand = "";
                        do {
                            System.out.println("-----Customer panel------");
                            System.out.println("Please type in the command:");
                            System.out.println("Availability <Show Number>");
                            System.out.println("Book <Show Number> <Phone#> <Comma separated list of seats>");
                            System.out.println("Cancel <Ticket#> <Phone#>");
                            System.out.println("Exit");
                            System.out.println("------------------------------------------");
                            System.out.println("Enter your command...:");
                            System.out.print("");
                            String inputLine = sc.nextLine();
                            try (Scanner lineScanner = new Scanner(inputLine)) {
                                if (lineScanner.hasNext()) {
                                    custCommand = lineScanner.next().toLowerCase();
                                } else {
                                    custCommand = "";
                                }
                            }
                            switch (custCommand) {
                                case "availability":
                                    // clearscreen();
                                    String[] getShowNum = inputLine.split(" ");
                                    bookingSys.displayAvailableSeats(Integer.parseInt(getShowNum[1]));
                                    // Handle setup logic
                                    break;
                                case "book":
                                    clearscreen();
                                    String[] getBookingDetails = inputLine.split(" ");
                                    String seatNumbers = getBookingDetails[3];
                                    String[] seatToArr = seatNumbers.split(",");
                                    bookingSys.bookSeats(Integer.parseInt(getBookingDetails[1]), getBookingDetails[2],
                                            seatToArr);
                                    break;
                                case "cancel":
                                    clearscreen();
                                    String[] getCancelDetails = inputLine.split(" ");
                                    bookingSys.cancelBooking(getCancelDetails[1], getCancelDetails[2]);
                                    break;
                                default:
                                    clearscreen();
                                    System.out.println("Please enter a valid command....");
                                    System.out.println("------------------------");
                                    break;
                            }

                        } while (!custCommand.equals("exit"));
                        break;
                    case 3:
                        clearscreen();
                        System.out.println("\t--------------------------------------------");
                        System.out.println("\t\tTHANK YOU!");
                        System.out.println("\t--------------------------------------------");
                        return;
                    default:
                        clearscreen();
                        System.out.println("Please enter a valid command....");
                        break;
                }
            } while (menuInput != 3);
        } catch (Exception e) {
            if (!e.getMessage().equals(null)) {
                System.out.print("Exception Occured");
            }
        }
    }
}
