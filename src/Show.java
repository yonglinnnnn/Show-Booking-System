import java.time.LocalDateTime;
import java.util.*;
import java.time.Duration;

class Show {
    private int showNumber;
    private int numRows;
    private int seatsPerRow;
    private int cancellationWindow;
    private List<List<Seat>> seats;
    private Map<String, List<Seat>> bookings;

    public Show(int showNumber, int numRows, int seatsPerRow, int cancellationWindow) {
        this.showNumber = showNumber;
        this.numRows = numRows;
        this.seatsPerRow = seatsPerRow;
        this.cancellationWindow = cancellationWindow;
        bookings = new HashMap<>();
        seats = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            List<Seat> row = new ArrayList<>();
            for (int j = 1; j <= seatsPerRow; j++) {
                char rowChar = (char) ('A' + i);
                String seatNumber = String.format("%c%d", rowChar, j);
                row.add(new Seat(seatNumber));
            }
            seats.add(row);
        }
    }

    // Display showNumber, ticketNumber, buyer phoneNumber, seat Numbers
    public void viewShow() {
        ArrayList<String> printTicketBuyers = new ArrayList<>();
        for (List<Seat> seat : seats) {
            for (Seat getIndividualSeat : seat) {
                if (getIndividualSeat.isBooked()) {
                    printTicketBuyers.add(showNumber + "," + getIndividualSeat.getTicketNumber() + ","
                            + getIndividualSeat.getPhoneNumber() + "," + getIndividualSeat.getSeatNumber());
                }
            }
        }
        if (printTicketBuyers.size() == 0) {
            System.out.println("No buyer for show number " + showNumber + " right now.");
        } else {
            for (String buyer : printTicketBuyers) {
                System.out.println(buyer);
            }
        }
    }

    // Display showNumber, ticketNumber, buyer phoneNumber, seat Numbers
    public ArrayList<String> getShowBuyersPhoneNumber() {
        ArrayList<String> buyerPhoneNumberList = new ArrayList<>();
        for (List<Seat> seat : seats) {
            for (Seat getIndividualSeat : seat) {
                if (getIndividualSeat.isBooked()) {
                    buyerPhoneNumberList.add(getIndividualSeat.getPhoneNumber());
                }
            }
        }
        return buyerPhoneNumberList;
    }

    public void bookSeats(int showNumber, String phoneNo, String[] seatNumbers) {
        Boolean checkAvailability = true;
        for (String seatNumber : seatNumbers) {
            ArrayList<Seat> seats = getAvailableSeats();
            for (Seat seat : seats) {
                if (seat.getSeatNumber().equals(seatNumber) && !seat.isBooked()) {
                    checkAvailability = true;
                    break;
                } else {
                    checkAvailability = false;
                }
            }
        }
        if (checkAvailability == true) {
            // check here if buyer has bought the tickets for same show
            ArrayList<String> getBuyersPhoneNumber = getShowBuyersPhoneNumber();
            if (!getBuyersPhoneNumber.contains(phoneNo)) {
                String ticketNumber = generateTicketNumber();
                ArrayList<Seat> seats = getAvailableSeats();
                for (String seatNumber : seatNumbers) {
                    for (Seat seat : seats) {
                        if (seat.getSeatNumber().equals(seatNumber) && !seat.isBooked()) {
                            LocalDateTime currentDateTime = LocalDateTime.now();
                            seat.book(ticketNumber, phoneNo, currentDateTime);
                        }
                    }
                }
                bookings.put(ticketNumber, seats);
                System.out.println("Tickets successfully booked! Ticket no: " + ticketNumber);
            } else {
                System.out.println("Only one booking per phone# is allowed per show.");
            }
        } else {
            System.out.println("Some errors have been encountered. May be due to seats chosen are not available.");
        }
    }

    private String generateTicketNumber() {
        // Implement logic to generate a unique ticket number
        // This could be a random string, a combination of numbers and letters, or using
        // a specific format
        // For example, you can use UUID.randomUUID() to generate a random UUID as a
        // ticket number
        return UUID.randomUUID().toString();
    }

    public String cancelBooking(String ticketNumber, String phoneNumber) {
        List<Seat> getSeats = bookings.get(ticketNumber);
        if (getSeats != null) {
            for (Seat getIndividualSeat : getSeats) {
                if (getIndividualSeat.getPhoneNumber().equals(phoneNumber)) {
                    LocalDateTime currentTime = LocalDateTime.now();
                    Duration duration = Duration.between(getIndividualSeat.getDateTime(), currentTime);
                    long minutesDifference = duration.toMinutes();
                    if (minutesDifference < cancellationWindow) {
                        getIndividualSeat.cancelBooking();
                    } else {
                        return "exceeded";
                    }
                }
            }
            bookings.remove(ticketNumber);
            return "cancelled";

        }
        return "invalid";
    }

    public void getAllSeats() {
        ArrayList<String> printSeats = new ArrayList<>();
        for (List<Seat> seat : seats) {
            for (Seat getIndividualSeat : seat) {
                printSeats.add(getIndividualSeat.getSeatNumber());
            }
            System.out.println(printSeats);
            printSeats.clear();
        }
    }

    public ArrayList<Seat> getAvailableSeats() {
        ArrayList<Seat> availableSeats = new ArrayList<>();
        for (List<Seat> seat : seats) {
            for (Seat getIndividualSeat : seat) {
                if (!getIndividualSeat.isBooked()) {
                    availableSeats.add(getIndividualSeat);
                }
            }
        }
        return availableSeats;
    }

    public void displayAvailableSeats() {
        ArrayList<String> availableSeats = new ArrayList<>();
        for (List<Seat> seat : seats) {
            for (Seat getIndividualSeat : seat) {
                if (!getIndividualSeat.isBooked()) {
                    availableSeats.add(getIndividualSeat.getSeatNumber());
                }
            }
            System.out.println(availableSeats);
            availableSeats.clear();
        }
    }
}