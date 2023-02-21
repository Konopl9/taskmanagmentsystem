<h2>Build a Task Management System API</h2>
<p>The API should allow users to create, read, update, and delete tasks. Tasks can be assigned to users and have a status (e.g. "in progress" or "completed"). Admin users should be able to view all tasks, while regular users should only be able to view and modify their own tasks. Here are some steps you can follow to get started:</p>
<ol>
  <li><strong>Define the API endpoints:</strong> Define the API endpoints that you want to build for your application. For example, you could have endpoints for creating, reading, updating, and deleting tasks, as well as endpoints for assigning tasks to users and changing the status of tasks.</li>
  <li><strong>Implement user authentication and authorization:</strong> Use Spring Security to implement user authentication and authorization. Regular users should only be able to view and modify their own tasks, while admin users should be able to view all tasks.</li>
  <li><strong>Set up the database:</strong> Use Spring Data JPA to set up the database schema for storing tasks and users. You can define entities for tasks and users and use JPA annotations to map them to database tables.</li>
  <li><strong>Implement CRUD operations for tasks:</strong> Use Spring Data JPA to implement CRUD operations for tasks. You can define a repository interface for tasks and use it to perform database operations.</li>
  <li><strong>Implement task assignment and status changes:</strong> Use Spring Data JPA to implement functionality for assigning tasks to users and changing the status of tasks.</li>
  <li><strong>Add unit tests:</strong> Write unit tests to ensure that your API endpoints and database operations are working correctly.</li>
</ol>
<p>By building this Task Management System API, you'll have the opportunity to practice many of the key features of Spring Boot, Spring Security, and Spring Data JPA, including user authentication and authorization, database setup and management, and implementing CRUD operations for entities. Good luck!</p>

<ol>
  <li><strong>Roles to implement:</strong>User, Admin</li>
  <li><strong>Add priority:</strong>Task priority to utilize the custom annotation validation</li>
</ol>
