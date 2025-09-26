
/**
 * Asynchronously loads greeting records from the backend API and renders them into an HTML table.
 *
 * This function:
 *  1. Fetches greeting data from the endpoint `/api/history`.
 *  2. Parses the response as JSON.
 *  3. Clears existing rows from the greetings table body.
 *  4. Creates a new table row for each greeting, populating columns with:
 *     - Greeting message
 *     - Name of the sender
 *     - Timestamp formatted as a locale string
 *  5. Appends the rows to the table body element.
 *
 * If an error occurs during the fetch or processing, it logs the error to the console.
 */
async function loadGreetings() {
    try {
        const response = await fetch('/api/history');
        const data = await response.json();

        const tbody = document.querySelector("#greetingsTable tbody");
        tbody.innerHTML = "";

        data.forEach(greeting => {
            const row = document.createElement("tr");

            const greetingCell = document.createElement("td");
            greetingCell.textContent = greeting.greeting;

            const nameCell = document.createElement("td");
            nameCell.textContent = greeting.name;

            const timestampCell = document.createElement("td");
            timestampCell.textContent = new Date(greeting.timestamp).toLocaleString();

            row.appendChild(greetingCell);
            row.appendChild(nameCell);
            row.appendChild(timestampCell);

            tbody.appendChild(row);
        });

    } catch (error) {
        console.error("Error loading greetings:", error);
    }
}

/**
 * Registers the loadGreetings function to run once the DOM is fully loaded.
 *
 * Ensures that greeting data is loaded and displayed when the history page is opened.
 */
document.addEventListener("DOMContentLoaded", loadGreetings);
