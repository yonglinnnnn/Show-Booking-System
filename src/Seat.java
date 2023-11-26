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
        isBooked = true;
        this.ticketNumber = ticketNumber;
        this.phoneNumber = phoneNumber;
        this.dateTime = dateTime;
    }

    public void cancelBooking() {
        isBooked = false;
        this.ticketNumber = "";
        this.phoneNumber = "";
        this.dateTime = null;
    }
}