# Ticket-Booking-System
Console project in Java.

### There are two panels
1. Admin Panel
2. Customer Panel 

### 1. Admin Panel
* **`Setup`-** Set up number of seats per show
* **`View`-** To display Show number, ticket number, buyer phone number, seat numbers allocated to the buyer

 ### 2. Customer Panel
* **`Availability`-** To list all available seat numbers for a show.

* **`Book`-** To book a ticket. A unique ticket number is generated and displayed.

* **`Cancel`-**  To cancel a ticket, must be before the cancellation window

### Constraints
Constraints:
- Assume max seats per row is 10 and max rows are 26. Example seat number A1,  H5 etc. The “Add” command for admin must ensure rows cannot be added beyond the upper limit of 26.
- After booking, User can cancel the seats within a time window of 2 minutes (configurable).  
Cancellation after that is not allowed.
- Only one booking per phone# is allowed per show.

### Assumptions
1. Assume that there is a login function for each users.