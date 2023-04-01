const host = "http://localhost:8080"
let statusSection = document.getElementById("status-section")

fetch(`${host}/api/statuses`)
    .then((response) => response.json())
    .then((body) => {
        for (statusLog of body) {
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
    })