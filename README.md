# Notifications-Module
This is a notifications module to send Emails and SMSs that's built in Java as a project throughout the advanced software engineering course in college.
It's not an entire software system but rather a component within a system, a module.
The project is divided into three major steps:

## 1- Notification Template Management:
A notification template is a template with a specified subject and contents, with placeholders inside the content that will then be changed to some actual values when sending the notifications.

## 2- Notification Queueing:
When the notifications module is invoked to send a notification such as an Email, it's a good design decision not to actually send this notification immediately, it's better to add this notification into some queue that is then dequeued when we want to actually send those notifications.

## 3- Notification sending "Dequeueing":
That's when we actually want to send the notifications that are currently in the queue to the appropriate Email addresses and Phone numbers.

The project is built in three Sprints:
## Sprint 1:
In Sprint 1 we develop the CRUD operations for the Notification Templates and its associated entities, we used H2's embedded database and Hibernate, following Dependency inversion principle using an interface for the CRUD operations.

## Sprint 2:
In Sprint 2 we develop a web service (web API) to expose those CRUD operations for the Notification Templates, we used Spring Boot for this purpose, we also develop a web service to send the Emails and SMSs as specified above (queueing the notifications not actually sending them).

## Sprint 3:
In Sprint 3 we develop a console application that is responsible for actually sending the notifications that are inside the notification queues.

The project was built in collaboration with: [Amr Bumadian](https://github.com/AmrBumadian) .
