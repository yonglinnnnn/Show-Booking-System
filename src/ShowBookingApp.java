import java.sql.*;
import java.io.*;
import java.util.*;

class ShowBookingApp {
    static Scanner sc = new Scanner(System.in);

    public static void clearCmd() throws InterruptedException, IOException, SQLException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    public static void main(String[] arg) {
        try {
            int menuInput;
            BookingSystem bookingSys = new BookingSystem();
            do {
                clearCmd();
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
                        clearCmd();
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
                            String inputLine = sc.nextLine().toUpperCase();
                            ;
                            try (Scanner lineScanner = new Scanner(inputLine)) {
                                if (lineScanner.hasNext()) {
                                    adminCommand = lineScanner.next().toLowerCase();
                                } else {
                                    adminCommand = "";
                                }
                            }

                            switch (adminCommand) {
                                case "setup":
                                    clearCmd();
                                    try {
                                        String[] getInputs = inputLine.split(" ");
                                        bookingSys.setupShow(Integer.parseInt(getInputs[1]),
                                                Integer.parseInt(getInputs[2]), Integer.parseInt(getInputs[3]),
                                                Integer.parseInt(getInputs[4]));
                                    } catch (Exception e) {
                                        System.out.println("An error occurred: " + e.getMessage());
                                    }
                                    break;
                                case "view":
                                    clearCmd();
                                    try {
                                        String[] getShowNum = inputLine.split(" ");
                                        bookingSys.viewShow(Integer.parseInt(getShowNum[1]));
                                    } catch (Exception e) {
                                        System.out.println("An error occurred: " + e.getMessage());
                                    }
                                    break;
                                case "exit":
                                    break;
                                default:
                                    clearCmd();
                                    System.out.println("Please enter a valid command....");
                                    System.out.println("------------------------");
                                    break;
                            }
                        } while (!adminCommand.equals("exit"));
                        break;
                    case 2:
                        clearCmd();
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
                            String inputLine = sc.nextLine().toUpperCase();
                            try (Scanner lineScanner = new Scanner(inputLine)) {
                                if (lineScanner.hasNext()) {
                                    custCommand = lineScanner.next().toLowerCase();
                                } else {
                                    custCommand = "";
                                }
                            }
                            switch (custCommand) {
                                case "availability":
                                    try {
                                        String[] getShowNum = inputLine.split(" ");
                                        bookingSys.displayAvailableSeats(Integer.parseInt(getShowNum[1]));
                                    } catch (Exception e) {
                                        System.out.println("An error occurred: " + e.getMessage());
                                    }
                                    break;
                                case "book":
                                    clearCmd();
                                    try {
                                        String[] getBookingDetails = inputLine.split(" ");
                                        String seatNumbers = getBookingDetails[3];
                                        String[] seatToArr = seatNumbers.split(",");
                                        bookingSys.bookSeats(Integer.parseInt(getBookingDetails[1]),
                                                getBookingDetails[2],
                                                seatToArr);
                                    } catch (Exception e) {
                                        System.out.println("An error occurred: " + e.getMessage());
                                    }
                                    break;
                                case "cancel":
                                    clearCmd();
                                    try {
                                        String[] getCancelDetails = inputLine.split(" ");
                                        bookingSys.cancelBooking(getCancelDetails[1].toLowerCase(), getCancelDetails[2]);
                                    } catch (Exception e) {
                                        System.out.println("An error occurred: " + e.getMessage());
                                    }
                                    break;
                                default:
                                    clearCmd();
                                    System.out.println("Please enter a valid command....");
                                    System.out.println("------------------------");
                                    break;
                            }

                        } while (!custCommand.equals("exit"));
                        break;
                    case 3:
                        clearCmd();
                        System.out.println("\t--------------------------------------------");
                        System.out.println("\t\tTHANK YOU!");
                        System.out.println("\t--------------------------------------------");
                        return;
                    default:
                        clearCmd();
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
