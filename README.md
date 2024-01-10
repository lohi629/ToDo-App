**Installation of Android Studio**
1. Download the latest version of Android studio from [Download](https://developer.android.com/studio)
2. Set destination path and start building the project.
3. Choose  language(Kotlin/Java) as per requiremnets.
4. Make sure JDK has to be installed in system when working with Java.
   

**ToDo App**

**Key features of ToDo App**
* Task title and description
* Status of task (Ongoing/Completed)
* Assigning Priority(Low/Medium/High)
* Categorizing tasks based on the type of task(Home/Work/Office etc.,)
* Due Date
* Adding new task
* Local Storage(SQLite)

**Description**
* Splash Screen is displayed when the application initializes. It showcases Application name and the logo.
* The tasks within the **MainActivity** are individually presented within distinct CardViews, providing a dedicated display for each task.
* Each Task contains Title ,Description,Status,Priority,Category,Due Date.
* With help of Scrollbar,we can scroll the page.
* Beneath the task list, a floating action button is available, facilitating the addition of new tasks with ease.
* The TaskList is organized to prioritize new tasks, ensuring they appear at the forefront of the list.
* **Swiping actions** are seamlessly facilitated using the **RecyclerView**, allowing intuitive and efficient interaction with individual tasks through swipe gestures.
* When _swiping left_, users can seamlessly _edit tasks_, while _swiping right_ allows for _swift deletion_ of tasks.
* Utilizing the **DataBaseHelper**, a structured database is established with designated columns(ID,TASK,DESCRIPTION,STATUS,PRIORITY,CATEGORY,DUEDATE). This setup empowers us to seamlessly execute Create, Read, Update, and Delete (CRUD) operations, enabling efficient management of data within the database.
* Data retrieval is facilitated through the utilization of getter and setter methods encapsulated within the **ToDoModel**.
* Upon clicking the _checkbox_, the task's status undergoes manipulation, transitioning between 'ongoing' and 'completed', effectively reflecting the current state of the task.
* Upon selecting a _priority level_, the _background color_ of the task dynamically adjusts to visually represent the chosen priority, enhancing task visibility and organization based on importance.
* Upon tapping the date displayed in the CardView, a calendar interface is triggered, allowing users to select a date conveniently using a picker.
* Category is selected using Spinner.
* Save button is provided to save the new task and update the database.

**Running App**
* For running an app, Emulator is selected.
* Open device Manager in Android Studio.
* Click on create Virtual device and select the model of device.
* Choose the layout and click on finish.
* Click on run button and wait until the gradle is build.

Upon running application, app looks like below:
![spalsh](https://github.com/lohi629/ToDo-App/assets/80086556/8c5a1233-22f6-4980-be5f-2cb98a7a2950)
![tasklist](https://github.com/lohi629/ToDo-App/assets/80086556/cc8360b3-bfe8-40fe-911d-ba470553330c)
![edit](https://github.com/lohi629/ToDo-App/assets/80086556/115417f2-9978-4a2f-96c6-885701a3e09a)
![category](https://github.com/lohi629/ToDo-App/assets/80086556/84778229-297e-4e84-ad78-23050e349151)
![calender](https://github.com/lohi629/ToDo-App/assets/80086556/b2f915e4-5363-4ffb-93c8-982fc24fc0d3)




