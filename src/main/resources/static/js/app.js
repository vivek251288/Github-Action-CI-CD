const API_URL = "/api/tasks";


// ===============================
// Load Tasks
// ===============================

async function loadTasks() {

    try {

        const response = await fetch(API_URL);

        const tasks = await response.json();

        displayTasks(tasks);

        updateDashboard(tasks);

    } catch (error) {

        console.error("Error loading tasks:", error);

        alert("Unable to load tasks");

    }
}


// ===============================
// Display Tasks
// ===============================

function displayTasks(tasks) {

    const tableBody =
        document.getElementById("taskTableBody");

    tableBody.innerHTML = "";

    tasks.forEach(task => {

        const row = document.createElement("tr");

        row.innerHTML = `

            <td>${task.id}</td>

            <td>${task.title}</td>

            <td>${task.description || ""}</td>

            <td>${task.priority}</td>

            <td>
                <select
                    onchange="updateStatus(${task.id}, this.value)"
                >

                    <option
                        value="PENDING"
                        ${task.status === "PENDING" ? "selected" : ""}
                    >
                        PENDING
                    </option>

                    <option
                        value="IN_PROGRESS"
                        ${task.status === "IN_PROGRESS" ? "selected" : ""}
                    >
                        IN PROGRESS
                    </option>

                    <option
                        value="COMPLETED"
                        ${task.status === "COMPLETED" ? "selected" : ""}
                    >
                        COMPLETED
                    </option>

                </select>
            </td>

            <td>

                <button
                    class="delete-btn"
                    onclick="deleteTask(${task.id})"
                >
                    Delete
                </button>

            </td>
        `;

        tableBody.appendChild(row);

    });
}


// ===============================
// Create Task
// ===============================

document
    .getElementById("taskForm")
    .addEventListener("submit", async function(event) {

        event.preventDefault();

        const task = {

            title:
                document.getElementById("title").value,

            description:
                document.getElementById("description").value,

            priority:
                document.getElementById("priority").value,

            status: "PENDING"
        };


        try {

            const response = await fetch(API_URL, {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(task)

            });


            if (!response.ok) {

                throw new Error("Failed to create task");

            }


            this.reset();

            loadTasks();

        } catch (error) {

            console.error(error);

            alert("Unable to create task");

        }

    });


// ===============================
// Update Status
// ===============================

async function updateStatus(id, status) {

    try {

        const response =
            await fetch(`${API_URL}/${id}`);

        const task =
            await response.json();


        task.status = status;


        await fetch(`${API_URL}/${id}`, {

            method: "PUT",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(task)

        });


        loadTasks();

    } catch (error) {

        console.error(error);

        alert("Unable to update task");

    }
}


// ===============================
// Delete Task
// ===============================

async function deleteTask(id) {

    if (!confirm("Delete this task?")) {
        return;
    }


    try {

        await fetch(`${API_URL}/${id}`, {

            method: "DELETE"

        });

        loadTasks();

    } catch (error) {

        console.error(error);

        alert("Unable to delete task");

    }
}


// ===============================
// Search
// ===============================

async function searchTasks() {

    const title =
        document.getElementById("searchInput").value;


    if (!title) {

        loadTasks();

        return;
    }


    try {

        const response =
            await fetch(
                `${API_URL}/search?title=${encodeURIComponent(title)}`
            );


        const tasks =
            await response.json();


        displayTasks(tasks);

    } catch (error) {

        console.error(error);

    }
}


// ===============================
// Dashboard
// ===============================

function updateDashboard(tasks) {

    const total =
        tasks.length;

    const pending =
        tasks.filter(
            task => task.status === "PENDING"
        ).length;

    const completed =
        tasks.filter(
            task => task.status === "COMPLETED"
        ).length;


    document.getElementById("totalTasks")
        .innerText = total;

    document.getElementById("pendingTasks")
        .innerText = pending;

    document.getElementById("completedTasks")
        .innerText = completed;
}


// Initial load

loadTasks();