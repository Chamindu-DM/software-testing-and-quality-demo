Bug Report: Task Creation Fails on Empty Title
Reported By: QA Team

Date Reported: 2025-09-19

1. Bug Summary
   Attempting to create a new task with an empty title results in a 500 Internal Server Error instead of a 400 Bad Request.

2. Environment Details
   Application Version: 1.0.0-SNAPSHOT

Operating System: Windows 11

Browser: Chrome v108

API Endpoint: POST /api/tasks

3. Steps to Reproduce
   Open the application UI.

Navigate to the Task Creation form.

Leave the "Title" field blank.

Enter a valid "Description" (e.g., "This is a test description").

Click the "Add Task" button.

4. Expected Behavior
   The API should return a 400 Bad Request status code, and the UI should display a user-friendly error message, such as "Title cannot be empty."

5. Actual Behavior
   The API returns a 500 Internal Server Error status code. The server logs show a NullPointerException because the task.getTitle() is not handled correctly before being used in the validation logic.

6. Screenshots/Logs
   API Response: HTTP/1.1 500 Internal Server Error

Stack Trace:

java.lang.NullPointerException: Cannot invoke "String.isEmpty()" because the return value of "com.testing.taskmanager.entity.Task.getTitle()" is null
at com.testing.taskmanager.service.TaskService.createTask(TaskService.java:42)
...

7. Severity
   Severity: High (P1)
   Reasoning: This bug breaks a core functionality and exposes a server-side error to the client, which is a security risk and poor user experience.

8. Root Cause Analysis (Initial Guess)
   The TaskService.createTask method does not handle null or empty task titles gracefully.

The validateTaskInput method is not properly invoked or lacks robust checks for null/empty values.

Status: Open
Assigned To: John Doe
Comments: This needs to be fixed urgently to prevent server crashes and improve user experience.