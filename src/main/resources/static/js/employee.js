document.addEventListener("DOMContentLoaded", () => {
    console.log("Page loaded");

    loadDepartments();
    loadEmployees();

    document.getElementById("employeeForm").addEventListener("submit", saveEmployee);
});

async function loadDepartments() {
    try {
        const res = await fetch("/api/departments");
        const departments = await res.json();

        const select = document.getElementById("departmentSelect");
        select.innerHTML = "";

        departments.forEach(d => {
            const option = document.createElement("option");
            option.value = d.departmentId;
            option.textContent = d.departmentName;
            select.appendChild(option);
        });
    } catch (err) {
        console.error("Error loading departments:", err);
    }
}

async function loadEmployees() {
    try {
        const res = await fetch("/api/employees");
        const employees = await res.json();

        console.log("Employees:", employees);

        const tbody = document.getElementById("employeeTableBody");
        tbody.innerHTML = "";

        employees.forEach(emp => {
            const row = document.createElement("tr");

            row.innerHTML = `
        <td>${emp.employeeId}</td>
        <td>${emp.name}</td>
        <td>${emp.family}</td>
        <td>${emp.city}</td>
        <td>${emp.department ? emp.department.departmentName : ""}</td>
        <td>
          <button onclick="deleteEmployee(${emp.employeeId})">Delete</button>
        </td>
      `;

            tbody.appendChild(row);
        });
    } catch (err) {
        console.error("Error loading employees:", err);
    }
}

async function saveEmployee(event) {
    event.preventDefault();

    const id = document.getElementById("employeeId").value;
    const name = document.getElementById("name").value;
    const family = document.getElementById("family").value;
    const city = document.getElementById("city").value;
    const departmentId = document.getElementById("departmentSelect").value;

    const employee = {
        employeeId: id || null,
        name,
        family,
        city,
        department: { departmentId: parseInt(departmentId) }
    };

    try {
        const res = await fetch("/api/employees" + (id ? `/${id}` : ""), {
            method: id ? "PUT" : "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(employee)
        });

        if (res.ok) {
            loadEmployees();
            document.getElementById("employeeForm").reset();
        } else {
            console.error("Failed to save employee:", await res.text());
        }
    } catch (err) {
        console.error("Error saving employee:", err);
    }
}
async function deleteEmployee(id) {
    if (!confirm("Are you sure you want to delete this employee?")) return;

    try {
        const res = await fetch(`/api/employees/${id}`, {
            method: "DELETE"
        });

        if (res.ok) {
            loadEmployees(); // refresh table
        } else {
            console.error("Failed to delete employee:", await res.text());
        }
    } catch (err) {
        console.error("Error deleting employee:", err);
    }
}
