
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class ShowBookingAppTest {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testValidAdminPanelCommands() {
        BookingSystem bookingSys = new BookingSystem();
        String inputLine = "setup 1 10 10 2";
        String[] getInputs = inputLine.split(" ");
        bookingSys.setupShow(Integer.parseInt(getInputs[1]), Integer.parseInt(getInputs[2]),
                Integer.parseInt(getInputs[3]), Integer.parseInt(getInputs[4]));
        // Get the printed content from the output
        String getSetupOutput = outContent.toString().trim();
        assertThat(getSetupOutput, containsString("Show setup completed for Show Number: 1"));
        outContent.reset();

        // Test View Command
        String viewCommand = "view 1";
        String[] getShowNum = viewCommand.split(" ");
        bookingSys.viewShow(Integer.parseInt(getShowNum[1]));
        String getViewOutput = outContent.toString().trim();
        assertEquals(getViewOutput, "--- Viewing details for Show Number: 1 ---\r\n" + //
                "--- Show #, Ticket #, Phone #, Seat # ---\r\n" + //
                "No buyer for show number 1 right now.");
        outContent.reset();
    }

    @Test
    public void testInvalidAdminPanelCommands() {
        BookingSystem bookingSys = new BookingSystem();
        // No. of seats per row is limit to 10 but I set to 15
        String inputLine = "setup 1 10 15 2";
        String[] getInputs = inputLine.split(" ");
        bookingSys.setupShow(Integer.parseInt(getInputs[1]), Integer.parseInt(getInputs[2]),
                Integer.parseInt(getInputs[3]), Integer.parseInt(getInputs[4]));
        // Get the printed content from the output
        String getSetupOutput = outContent.toString().trim();
        assertThat(getSetupOutput, containsString(
                "Invalid setup parameters for the show. Max no. of rows = 26, max no. of seats per row = 10, max cancellation window = 2."));
        outContent.reset();

        // No of row is limit to 26 but I set to 30
        inputLine = "setup 1 30 10 2";
        getInputs = inputLine.split(" ");
        bookingSys.setupShow(Integer.parseInt(getInputs[1]), Integer.parseInt(getInputs[2]),
                Integer.parseInt(getInputs[3]), Integer.parseInt(getInputs[4]));
        // Get the printed content from the output
        getSetupOutput = outContent.toString().trim();
        assertThat(getSetupOutput, containsString(
                "Invalid setup parameters for the show. Max no. of rows = 26, max no. of seats per row = 10, max cancellation window = 2."));
        outContent.reset();

        // Test View Command
        String viewCommand = "view 1";
        String[] getShowNum = viewCommand.split(" ");
        bookingSys.viewShow(Integer.parseInt(getShowNum[1]));
        String getViewOutput = outContent.toString().trim();
        assertEquals(getViewOutput, "Show not found.");
        outContent.reset();
    }

    @Test
    public void testValidCustomerPanelCommands() {
        BookingSystem bookingSys = new BookingSystem();
        // Add show 1 first
        String inputLine = "setup 1 5 5 1";
        String[] getInputs = inputLine.split(" ");
        bookingSys.setupShow(Integer.parseInt(getInputs[1]), Integer.parseInt(getInputs[2]),
                Integer.parseInt(getInputs[3]), Integer.parseInt(getInputs[4]));
        outContent.reset();

        // Check seats availability
        String seatAvailInput = "availability 1";
        String[] getShowNum = seatAvailInput.split(" ");
        bookingSys.displayAvailableSeats(Integer.parseInt(getShowNum[1]));
        String getAvailabilityOutput = outContent.toString().trim();
        assertEquals(getAvailabilityOutput, "[A1, A2, A3, A4, A5]\r\n" + //
                "[B1, B2, B3, B4, B5]\r\n" + //
                "[C1, C2, C3, C4, C5]\r\n" + //
                "[D1, D2, D3, D4, D5]\r\n" + //
                "[E1, E2, E3, E4, E5]");
        outContent.reset();

        // Try to book ticket
        String bookSeats = "book 1 99999999 A1,A2,A3";
        String[] getBookingDetails = bookSeats.split(" ");
        String seatNumbers = getBookingDetails[3];
        String[] seatToArr = seatNumbers.split(",");
        bookingSys.bookSeats(Integer.parseInt(getBookingDetails[1]),
                getBookingDetails[2],
                seatToArr);
        String getBookingOutput = outContent.toString().trim();
        String[] getTicketNo = getBookingOutput.split(" ");
        String ticketNo = getTicketNo[getTicketNo.length - 1];
        assertThat(getBookingOutput, containsString("Tickets successfully booked!"));
        outContent.reset();

        // Check seats availability again after booking
        String seatAvailInputAftBooking = "availability 1";
        String[] getShowNumber = seatAvailInputAftBooking.split(" ");
        bookingSys.displayAvailableSeats(Integer.parseInt(getShowNumber[1]));
        String getAvailabilityOutputAfterBooking = outContent.toString().trim();
        assertEquals(getAvailabilityOutputAfterBooking, "[A4, A5]\r\n" + //
                "[B1, B2, B3, B4, B5]\r\n" + //
                "[C1, C2, C3, C4, C5]\r\n" + //
                "[D1, D2, D3, D4, D5]\r\n" + //
                "[E1, E2, E3, E4, E5]");
        outContent.reset();

        // Cancelling the ticket with ticketNo and hpNo
        String cancelDetails = "cancel " + ticketNo + " 99999999";
        String[] getCancelDetails = cancelDetails.split(" ");
        bookingSys.cancelBooking(getCancelDetails[1], getCancelDetails[2]);
        String getCancelledOutput = outContent.toString().trim();
        assertEquals(getCancelledOutput, "Your booking has been cancelled.");
        outContent.reset();
    }

    @Test
    public void testInvalidCustomerPanelCommands() {
        BookingSystem bookingSys = new BookingSystem();
        // Add show 1 first
        String inputLine = "setup 1 10 10 2";
        String[] getInputs = inputLine.split(" ");
        bookingSys.setupShow(Integer.parseInt(getInputs[1]), Integer.parseInt(getInputs[2]),
                Integer.parseInt(getInputs[3]), Integer.parseInt(getInputs[4]));
        outContent.reset();

        // Invalid handphone number
        String bookSeats = "book 1 123 A1,A2,A3";
        String[] getBookingDetails = bookSeats.split(" ");
        String seatNumbers = getBookingDetails[3];
        String[] seatToArr = seatNumbers.split(",");
        bookingSys.bookSeats(Integer.parseInt(getBookingDetails[1]),
                getBookingDetails[2],
                seatToArr);
        String getBookingOutput = outContent.toString().trim();
        assertEquals(getBookingOutput, ("Please enter a valid phone number."));
        outContent.reset();

        // Cancelling the ticket
        String cancelDetails = "cancel " + "123-456-789" + " 99999999";
        String[] getCancelDetails = cancelDetails.split(" ");
        bookingSys.cancelBooking(getCancelDetails[1], getCancelDetails[2]);
        String getCancelledOutput = outContent.toString().trim();
        assertEquals(getCancelledOutput, "Please enter a valid ticket number/phone number.");
        outContent.reset();
    }

    @Test
    public void HandphoneNumContraintTest() {
        BookingSystem bookingSys = new BookingSystem();
        // Add show 1 first
        String inputLine = "setup 1 5 5 1";
        String[] getInputs = inputLine.split(" ");
        bookingSys.setupShow(Integer.parseInt(getInputs[1]), Integer.parseInt(getInputs[2]),
                Integer.parseInt(getInputs[3]), Integer.parseInt(getInputs[4]));
        outContent.reset();

        // Try to book ticket
        String bookSeats = "book 1 99999999 A1,A2,A3";
        String[] getBookingDetails = bookSeats.split(" ");
        String seatNumbers = getBookingDetails[3];
        String[] seatToArr = seatNumbers.split(",");
        bookingSys.bookSeats(Integer.parseInt(getBookingDetails[1]),
                getBookingDetails[2],
                seatToArr);
        String getBookingOutput = outContent.toString().trim();
        assertThat(getBookingOutput, containsString("Tickets successfully booked!"));
        outContent.reset();

        // Check seats availability again after booking
        String seatAvailInputAftBooking = "availability 1";
        String[] getShowNumber = seatAvailInputAftBooking.split(" ");
        bookingSys.displayAvailableSeats(Integer.parseInt(getShowNumber[1]));
        String getAvailabilityOutputAfterBooking = outContent.toString().trim();
        assertEquals(getAvailabilityOutputAfterBooking, "[A4, A5]\r\n" + //
                "[B1, B2, B3, B4, B5]\r\n" + //
                "[C1, C2, C3, C4, C5]\r\n" + //
                "[D1, D2, D3, D4, D5]\r\n" + //
                "[E1, E2, E3, E4, E5]");
        outContent.reset();

        // Try to book seats again with the same handphone number
        String bookSeatsAgain = "book 1 99999999 B4,B5";
        String[] getBookingDetailsAgain = bookSeatsAgain.split(" ");
        String seatNumbersToBook = getBookingDetailsAgain[3];
        String[] seatToArray = seatNumbersToBook.split(",");
        bookingSys.bookSeats(Integer.parseInt(getBookingDetailsAgain[1]),
                getBookingDetails[2],
                seatToArray);
        String getBookingOutputAgain = outContent.toString().trim();
        assertThat(getBookingOutputAgain, containsString("Only one booking per phone# is allowed per show."));
        outContent.reset();
    }
}
