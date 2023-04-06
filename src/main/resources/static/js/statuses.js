const host = "http://localhost:8080"
const statusSection = document.querySelector("#status-section")
const usernameInput = document.querySelector("#username");
let statuses = []

function fetchStatuses() {
    fetch(`${host}/api/statuses`)
        .then((response) => response.json())
        .then((body) => {
            statuses = body
            updateTable(statuses)
        })
}

function updateTable(filteredStatuses) {
    statusSection.innerHTML = "" // Clear the table before updating
    for (statusLog of filteredStatuses) {
        const date = new Date(statusLog.dateTime);
        let dateTime = date.getDay() + "/" + date.getMonth() + "/" + date.getFullYear() + ", " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds()
        let statusLogHtml = "<tr>"
        statusLogHtml += "<td>" + dateTime + "</td>"
        statusLogHtml += "<td>" + statusLog.user + "</td>"
        statusLogHtml += "<td>" + statusLog.event + "</td>"
        statusLogHtml += "<td>" + statusLog.previousStatus + "</td>"
        statusLogHtml += "<td>" + statusLog.newStatus + "</td>"
        statusLogHtml += "<td>" + statusLog.objectId + "</td>"
        statusLogHtml += "</tr>"

        statusSection.innerHTML += statusLogHtml
    }
}

function filterStatuses() {
    const username = usernameInput.value
    const filteredStatuses = statuses.filter(status => status.user.toLowerCase().includes(username.toLowerCase()))
    updateTable(filteredStatuses)
}

usernameInput.addEventListener("keyup", filterStatuses)

fetchStatuses()