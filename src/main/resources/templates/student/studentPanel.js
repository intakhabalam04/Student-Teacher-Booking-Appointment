  // Function to toggle the sidebar menu
  function toggleSidebar() {
    const sidebar = document.getElementById('sidebar');
    const content = document.getElementById('content');
    if (sidebar.style.width === '250px') {
        sidebar.style.width = '0';
        content.style.marginLeft = '0';
    } else {
        sidebar.style.width = '250px';
        content.style.marginLeft = '250px';
    }
}
function bookAppointment(button) {
    button.classList.add("booked");
    button.innerText = "Booked";
    button.disabled = true;
}
document.getElementById("searchForm").addEventListener("submit", function(event) {
    event.preventDefault();

    // Fetch the values entered by the user
    const date = document.getElementById("date").value;
    const subject = document.getElementById("subject").value;
    const timeFrom = document.getElementById("timeFrom").value;
    const timeTo = document.getElementById("timeTo").value;

    // For demonstration purposes, let's just display the entered values as results.
    const results = `
        
        <div class="container">
        <h2>Appointment Details</h2>
        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Date</th>
                    <th>Subject</th>
                    <th>Time From</th>
                    <th>Time To</th>
                    <th>Get Appointment</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Ms. Aparajita Paul</td>
                    <td>${date}</td>
                    <td>${subject}</td>
                    <td>${timeFrom}</td>
                    <td> ${timeTo}</td>
                    <td><button class="get-appointment-button" onclick="bookAppointment(this)">Book</button></td> 
                </tr>
                <tr>
                    <td>Ms. Pushpita Roy</td>
                    <td>${date}</td>
                    <td>${subject}</td>
                    <td>${timeFrom}</td>
                    <td> ${timeTo}</td>
                    <td><button class="get-appointment-button" onclick="bookAppointment(this)">Book</button></td> 
                </tr>
            </tbody>
        </table>
    </div>
    `;

    document.getElementById("searchResults").innerHTML = results;
});