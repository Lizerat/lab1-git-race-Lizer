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
        console.error("Error cargando saludos:", error);
    }
}

document.addEventListener("DOMContentLoaded", loadGreetings);
