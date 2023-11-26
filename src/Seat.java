import java.time.LocalDateTime;

class Seat {
    private String ticketNumber = "";
    private String seatNumber = "";
    private String phoneNumber = "";
    private LocalDateTime dateTime;
    private boolean isBooked;

    public Seat(String seatNumber) {
        this.seatNumber = seatNumber;
        this.isBooked = false;
    }

    // getters
    public String getTicketNumber() {
        return ticketNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void book(String ticketNumber, String phoneNumber, LocalDateTime dateTime) {
        // Set everything to the value when seat is booked
        isBooked = true;
        this.ticketNumber = ticketNumber;
        this.phoneNumber = phoneNumber;
        this.dateTime = dateTime;
    }

    public void cancelBooking() {
        // Set everything to default value when ticket is cancelled
        isBooked = false;
        this.ticketNumber = "";
        this.phoneNumber = "";
        this.dateTime = null;
    }
}