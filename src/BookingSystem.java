import java.util.*;

class BookingSystem {
    private Map<Integer, Show> shows;

    public BookingSystem() {
        shows = new HashMap<>();
    }

    public void setupShow(int showNumber, int numRows, int seatsPerRow, int cancellationWindow) {
        if (numRows <= 26 && seatsPerRow <= 10 && cancellationWindow <= 2) {
            Show show = new Show(showNumber, numRows, seatsPerRow, cancellationWindow);
            shows.put(showNumber, show);
            System.out.println("Show setup completed for Show Number: " + showNumber);
            shows.get(showNumber).getAllSeats();
        } else {
            System.out.println("Invalid setup parameters for the show.");
        }
    }

    public void viewShow(int showNumber) {
        if (shows.containsKey(showNumber)) {
            Show show = shows.get(showNumber);
            if (show != null) {
                System.out.println("--- Viewing details for Show Number: " + showNumber +  " ---");
                System.out.println("--- Show #, Ticket #, Phone #, Seat # ---");
                show.viewShow();
            }
        } else {
            System.out.println("Show not found.");
        }
    }

    public void displayAvailableSeats(int showNumber) {
        Show show = shows.get(showNumber);
        if (show != null) {
            show.displayAvailableSeats(); // Display available seats for the show
        } else {
            System.out.println("Show not found.");
        }
    }

    public void bookSeats(int showNumber, String phoneNo, String[] seatNumbers) {
        Show show = shows.get(showNumber);
        if (show != null) {
            if (phoneNo.length() != 8) {
                System.out.println("Please enter a valid phone number.");
            } else {
                show.bookSeats(showNumber, phoneNo, seatNumbers);
            }
        } else {
            System.out.println("Show not found.");
        }
    }

    public void cancelBooking(String ticketNumber, String phoneNumber) {
        String status = "";
        for (int key : shows.keySet()) {
            Show show = shows.get(key);
            if (show != null) {
                status = show.cancelBooking(ticketNumber, phoneNumber);
                if (status == "cancelled") {
                    System.out.println("Your booking has been cancelled.");
                    break;
                }
                if (status == "exceeded") {
                    System.out.println("Cancellation window exceeded.");
                    break;
                }
            }
        }
        if (status == "invalid") {
            System.out.println("Please enter a valid ticket number/phone number.");
        }
    }
}