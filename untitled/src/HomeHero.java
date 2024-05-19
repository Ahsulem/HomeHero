import java.util.*;
class User extends Person {
    private int userId;
    private double tokenBalance;
    private String address;
    private String contactNumber;
    private Date createdAt;
    private Date updatedAt;
    private boolean isActive;
    private Service selectedService;
    private List<Rating> ratings = new ArrayList<>();

    // Default constructor
    public User() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.isActive = true;
    }

    // Parameterized constructor
    public User(String name, String email, String password) {
        super(name, email, password);
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.isActive = true;
    }

    // Browse available services
    public List<Service> browseServices() {
        List<Service> availableServices = new ArrayList<>();
        List<Service> allServices = ServiceRepository.getAllServices();
        for (Service service : allServices) {
            if (service.isActive()) {
                availableServices.add(service);
            }
        }
        return availableServices;
    }

    // Select a service
    public void selectService(Service service) {
        if (isLoggedIn()) {
            this.setSelectedService(service);
            System.out.println("Service selected: " + service.getName());
        } else {
            System.out.println("No user currently logged in.");
        }
    }

    // Assign a task to a helper
    public void assignTask(Task task, Helper helper) {
        if (helper.isActive() && "Pending".equals(task.getStatus())) {
            task.setAssignedHelper(helper);
            task.setStatus("Assigned");
            System.out.println("Task assigned to helper: " + helper.getName());
        } else {
            System.out.println("Cannot assign task to inactive helper or task is already assigned.");
        }
    }

    // Purchase tokens
    public void purchaseTokens(double amount) {
        Token token = new Token();
        double tokenCost = token.calculateTokenCost(amount);
        if (tokenCost <= tokenBalance) {
            tokenBalance -= tokenCost;
            token.addTokens(amount);
            System.out.println("Tokens purchased successfully.");
        } else {
            System.out.println("Insufficient balance to purchase tokens.");
        }
    }

    // Rate a helper
    public void rateHelper(Helper helper, int ratingValue, String feedback) {
        Rating rating = new Rating(this, helper, ratingValue, feedback, new Date());
        ratings.add(rating);
        helper.getRatings().add(rating);
    }

    // View profile details
    public void viewProfile() {
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Address: " + getAddress());
        System.out.println("Contact Number: " + getContactNumber());
        System.out.println("Token Balance: " + getTokenBalance());
        System.out.println("Created At: " + getCreatedAt());
        System.out.println("Updated At: " + getUpdatedAt());
        System.out.println("Active: " + isActive());
    }

    // Update profile details
    public void updateProfile(String name, String email, String address, String contactNumber) {
        setName(name);
        setEmail(email);
        setAddress(address);
        setContactNumber(contactNumber);
        setUpdatedAt(new Date());
        System.out.println("Profile updated successfully.");
    }

    // Deactivate the account
    public void deactivateAccount() {
        setActive(false);
        System.out.println("Account deactivated successfully.");
    }

    // Check if user is logged in
    public boolean isLoggedIn() {
        return getCurrentUser() != null;
    }

    // Getter and Setter methods
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTokenBalance() {
        return tokenBalance;
    }

    public void setTokenBalance(double tokenBalance) {
        this.tokenBalance = tokenBalance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Service getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(Service selectedService) {
        this.selectedService = selectedService;
    }

    public List<Rating> getRatings() {
        return ratings;
    }
}
class UserRepository {
    private static List<User> users = new ArrayList<>();

    public static List<String> getAllEmails() {
        List<String> emails = new ArrayList<>();
        for (User user : users) {
            emails.add(user.getEmail());
        }
        return emails;
    }

    public static void saveUser(User user) {
        users.add(user);
        System.out.println("User saved: " + user.getName());
    }

    public static List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static void removeUser(User user) {
        users.remove(user);
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static void updateUser(User user) {
        int index = users.indexOf(user);
        if (index != -1) {
            users.set(index, user);
        }
    }
}

class ServiceRepository {
    private static List<Service> services = new ArrayList<>();

    public static List<Service> getAllServices() {
        return services;
    }

    public void addService(Service service) {
        services.add(service);
    }

    public void removeService(Service service) {
        services.remove(service);
    }

    public Service getServiceByName(String name) {
        for (Service service : services) {
            if (service.getName().equals(name)) {
                return service;
            }
        }
        return null;
    }

    public void updateService(Service service) {
        int index = services.indexOf(service);
        if (index != -1) {
            services.set(index, service);
        }
    }
}
class Notification {
    private String title;
    private String message;
    private Date timestamp;

    public Notification(String title, String message) {
        this.title = title;
        this.message = message;
        this.timestamp = new Date();
    }

    // Getter methods
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public Date getTimestamp() { return timestamp; }
}

class NotificationService {
    private List<Notification> notifications = new ArrayList<>();

    public void addNotification(Notification notification) {
        notifications.add(notification);
        System.out.println("Notification added: " + notification.getTitle());
    }

    public List<Notification> getNotifications() {
        return new ArrayList<>(notifications);
    }

    public void clearNotifications() {
        notifications.clear();
        System.out.println("All notifications cleared.");
    }
}

class EmailService {
    public static void sendEmail(String recipientEmail, String subject, String message) {
        // This is a placeholder for actual email sending logic
        System.out.println("Sending email to: " + recipientEmail);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);
    }
}

class Project {
    private int projectId;
    private String name;

    public Project(int projectId, String name) {
        this.projectId = projectId;
        this.name = name;
    }

    // Getter and Setter methods
    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

class ProjectRepository {
    private Map<Integer, Project> projectMap = new HashMap<>();

    public void addProject(Project project) {
        projectMap.put(project.getProjectId(), project);
        System.out.println("Project added: " + project.getName());
    }

    public Project getProjectById(int projectId) {
        return projectMap.get(projectId);
    }

    public List<Project> getAllProjects() {
        return new ArrayList<>(projectMap.values());
    }

    public void removeProject(int projectId) {
        projectMap.remove(projectId);
        System.out.println("Project removed: " + projectId);
    }
}

class TaskRepository {
    private Map<Integer, Task> taskMap = new HashMap<>();

    public void addTask(Task task) {
        taskMap.put(task.getTaskId(), task);
        System.out.println("Task added: " + task.getDescription());
    }

    public Task getTaskById(int taskId) {
        return taskMap.get(taskId);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(taskMap.values());
    }

    public void removeTask(int taskId) {
        taskMap.remove(taskId);
        System.out.println("Task removed: " + taskId);
    }
}

class TaskStatistics {
    private TaskRepository taskRepository;

    public TaskStatistics(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public int getTotalTasks() {
        return taskRepository.getAllTasks().size();
    }

    public int getCompletedTasks() {
        int count = 0;
        for (Task task : taskRepository.getAllTasks()) {
            if (task.getStatus() == TaskStatus.COMPLETED) {
                count++;
            }
        }
        return count;
    }

    public int getPendingTasks() {
        int count = 0;
        for (Task task : taskRepository.getAllTasks()) {
            if (task.getStatus() == TaskStatus.PENDING) {
                count++;
            }
        }
        return count;
    }

    public int getTasksByStatus(TaskStatus status) {
        int count = 0;
        for (Task task : taskRepository.getAllTasks()) {
            if (task.getStatus() == status) {
                count++;
            }
        }
        return count;
    }
}

class QualityAssuranceService {
    private TaskRepository taskRepository;

    public QualityAssuranceService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void reviewTask(Task task) {
        System.out.println("Reviewing task: " + task.getDescription());
    }

    public void checkForOverdueTasks() {
        Date currentDate = new Date();
        for (Task task : taskRepository.getAllTasks()) {
            if (task.getDueDate().before(currentDate) && task.getStatus() != TaskStatus.COMPLETED) {
                System.out.println("Overdue task found: " + task.getDescription());
            }
        }
    }

    public void provideFeedback(Task task, String feedback) {
        System.out.println("Providing feedback for task: " + task.getDescription());
    }
}

class Helper extends Person {
    private int helperId;
    private double rating;
    private List<Service> services = new ArrayList<>();
    private boolean isAvailable;
    private boolean isActive;

    public void acceptTask(Task task) {
        if (task.getStatus() == TaskStatus.ACCEPTED) {
            System.out.println("Task is already accepted.");
            return;
        }

        User assignedUser = UserRepository.getUserById(task.getAssignedUserId());
        if (assignedUser == null) {
            System.out.println("Invalid assigned user.");
            return;
        }

        if (assignedUser.getTaskLimit() > 0 && assignedUser.getTasks().size() >= assignedUser.getTaskLimit()) {
            System.out.println("User has reached the task limit.");
            return;
        }

        task.setStatus(TaskStatus.ACCEPTED);
        task.setAcceptedAt(new Date());
        assignedUser.addTask(task);
        TaskRepository.updateTask(task);
    }

    public void completeTask(Task task) {
        task.setCompleted(true);
        task.setCompletionDate(new Date());
        TaskRepository.updateTask(task);
        NotificationService.sendNotification(task.getOwner(), "Task '" + task.getName() + "' has been completed.");
        task.getOwner().incrementCompletedTasksCount();
        UserRepository.updateUser(task.getOwner());
        Project project = task.getProject();
        project.incrementCompletedTasksCount();
        ProjectRepository.updateProject(project);
        List<Task> dependentTasks = TaskRepository.getDependentTasks(task);
        for (Task dependentTask : dependentTasks) {
            dependentTask.setReadyForExecution(true);
            TaskRepository.updateTask(dependentTask);
        }
        TaskStatistics.updateStatistics(task);
        List<User> stakeholders = ProjectRepository.getProjectStakeholders(project);
        for (User stakeholder : stakeholders) {
            NotificationService.sendNotification(stakeholder, "Task '" + task.getName() + "' has been completed.");
        }
        project.updateProgress();
        ProjectRepository.updateProject(project);
        QualityAssuranceService.performChecks(task);
    }

    public void updateAvailability(boolean available) {
        isAvailable = available;
        System.out.println("Availability updated successfully: " + (isAvailable ? "Available" : "Not Available"));
    }

    public List<Task> viewTasks() {
        List<Task> assignedTasks = new ArrayList<>();
        List<Task> allTasks = TaskRepository.getAllTasks();
        for (Task task : allTasks) {
            if (task.getAssignedHelper() != null && task.getAssignedHelper().equals(this)) {
                assignedTasks.add(task);
            }
        }
        return assignedTasks;
    }

    public List<Rating> viewRatings() {
        List<Rating> ratings = new ArrayList<>();
        List<Rating> allRatings = RatingRepository.getAllRatings();
        for (Rating rating : allRatings) {
            if (rating.getHelper().equals(this)) {
                ratings.add(rating);
            }
        }
        return ratings;
    }

    public void updateProfile(String name) {
        setName(name);
        System.out.println("Profile updated successfully.");
    }

    // Getter and Setter methods
    public int getHelperId() {
        return helperId;
    }

    public void setHelperId(int helperId) {
        this.helperId = helperId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}

class Service {
    private int serviceId;
    private String name;
    private String description;
    private double price;
    private Category category;
    private Date createdAt;
    private Date updatedAt;
    private boolean isActive;
    private List<User> helpers;
    private List<User> activeHelpers;

    public Service(int serviceId, String name, String description, double price, Category category, boolean isActive) {
        this.serviceId = serviceId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.isActive = isActive;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.helpers = new ArrayList<>();
        this.activeHelpers = new ArrayList<>();
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void addHelper(User helper) {
        if (!helpers.contains(helper)) {
            helpers.add(helper);
            if (helper.isActive()) {
                activeHelpers.add(helper);
            }
            System.out.println("Helper added successfully.");
        } else {
            System.out.println("Helper is already assigned to this service.");
        }
    }

    public void removeHelper(Helper helper) {
        if (helpers.contains(helper)) {
            helpers.remove(helper);
            activeHelpers.remove(helper);
            System.out.println("Helper removed successfully.");
        } else {
            System.out.println("Helper is not assigned to this service.");
        }
    }

    public List<User> getActiveHelpers() {
        return this.activeHelpers;
    }

    public List<User> getHelpers() {
        return this.helpers;
    }
}

enum TaskStatus {
    PENDING, ASSIGNED, IN_PROGRESS, COMPLETED, CANCELLED
}

class Task {
    private int taskId;
    private String description;
    private TaskStatus status;
    private Date dueDate;
    private Date createdAt;
    private Date updatedAt;
    private Date completedAt;
    private User owner;
    private Helper assignedHelper;

    public Task(int taskId, String description, Date dueDate, User owner) {
        this.taskId = taskId;
        this.description = description;
        this.dueDate = dueDate;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.owner = owner;
        this.status = TaskStatus.PENDING;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Helper getAssignedHelper() {
        return assignedHelper;
    }

    public void setAssignedHelper(Helper assignedHelper) {
        this.assignedHelper = assignedHelper;
    }

    public void assignHelper(Helper helper) {
        if (helper.isAvailable() && status == TaskStatus.PENDING) {
            setAssignedHelper(helper);
            setStatus(TaskStatus.ASSIGNED);
            helper.acceptTask(this);
            System.out.println("Task assigned to helper: " + helper.getName());

            // Update the helper's task list
            helper.addTask(this);

            // Generate a notification for the helper
            Notification notification = new Notification("Task Assigned", "You have been assigned a new task: " + getDescription());
            helper.addNotification(notification);

            // Send an email notification to the helper
            EmailService.sendEmail(helper.getEmail(), "Task Assigned", "You have been assigned a new task: " + getDescription());
        } else {
            System.out.println("Cannot assign task to an unavailable helper or task is already assigned.");
        }
    }

    public void updateStatus(TaskStatus status) {
        if (isValidStatus(status)) {
            if (status == TaskStatus.COMPLETED) {
                setCompletedAt(new Date());
            }
            setStatus(status);
            setUpdatedAt(new Date());
            System.out.println("Task status updated to: " + status);
        } else {
            System.out.println("Invalid status. Please provide a valid status.");
        }
    }

    private boolean isValidStatus(TaskStatus status) {
        return Arrays.asList(TaskStatus.values()).contains(status);
    }

    public void extendDueDate(Date newDueDate) {
        if (status == TaskStatus.COMPLETED) {
            System.out.println("Cannot extend due date for a completed task.");
        } else if (status == TaskStatus.CANCELLED) {
            System.out.println("Cannot extend due date for a cancelled task.");
        } else {
            Date currentDate = new Date();

            if (newDueDate.after(currentDate)) {
                setDueDate(newDueDate);
                setUpdatedAt(currentDate);
                System.out.println("Due date extended to: " + newDueDate);
            } else {
                System.out.println("Invalid due date. Please provide a future date.");
            }
        }
    }

    public void cancelTask() {
        if (status != TaskStatus.COMPLETED) {
            setStatus(TaskStatus.CANCELLED);
            setUpdatedAt(new Date());
            System.out.println("Task cancelled successfully.");
        }
        else {
            System.out.println("Cannot cancel a completed task.");
        }
    }
}

class Rating {
    private int ratingId;
    private int rating;
    private String feedback;
    private Date createdAt;

    public Rating(User user, Helper helper, int rating, String feedback) {
        this.ratingId = generateRatingId();
        this.rating = rating;
        this.feedback = feedback;
        this.createdAt = new Date();
    }

    // Getter and Setter methods
    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    private int generateRatingId() {
        // Placeholder for rating ID generation logic
        return (int) (Math.random() * 10000);
    }
}

class Transaction {
    private int transactionId;
    private String type;
    private double amount;
    private Date timestamp;

    // Constructor
    public Transaction(String type, double amount) {
        this.transactionId = generateTransactionId();
        this.type = type;
        this.amount = amount;
        this.timestamp = new Date();
    }

    // Getter and Setter methods
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    private int generateTransactionId() {
        // Placeholder for transaction ID generation logic
        return (int) (Math.random() * 10000);
    }
}

class Category {
    private int categoryId;
    private String name;
    private String description;

    // Constructor
    public Category(int categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }

    // Getter and Setter methods
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


class Person {
    private String name;
    private String email;
    private String password;
    private Person currentUser;
    private Date lastLogin;

    // Default constructor
    public Person() {}

    // Parameterized constructor
    public Person(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Person getCurrentUser() { return currentUser; }
    public void setCurrentUser(Person currentUser) { this.currentUser = currentUser; }
    public Date getLastLogin() { return lastLogin; }
    public void setLastLogin(Date lastLogin) { this.lastLogin = lastLogin; }

    public void register() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        if (isEmailAvailable(email)) {
            setName(name);
            setEmail(email);
            setPassword(password);
            saveUserToDatabase();
            System.out.println("Registration successful!");
        } else {
            System.out.println("Email already registered. Registration failed.");
        }
    }

    // Check if email is available
    private boolean isEmailAvailable(String email) {
        List<String> registeredEmails = UserRepository.getAllEmails();
        return !registeredEmails.contains(email);
    }

    // Save user to database
    private void saveUserToDatabase() {
        UserRepository.saveUser(new User(getName(), getEmail(), getPassword()));
    }

    // Method for user login
    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (isValidCredentials(email, password)) {
            System.out.println("Login successful!");
            setLastLogin(new Date());
        } else {
            System.out.println("Invalid email or password. Login failed.");
        }
    }

    // Validate user credentials
    private boolean isValidCredentials(String email, String password) {
        List<User> registeredUsers = UserRepository.getAllUsers();
        for (User user : registeredUsers) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                setCurrentUser(user);
                return true;
            }
        }
        return false;
    }

    // Method for user logout
    public void logout() {
        if (isLoggedIn()) {
            setCurrentUser(null);
            setLastLogin(null);
            System.out.println("Logout successful.");
        } else {
            System.out.println("No user currently logged in.");
        }
    }

    // Check if a user is logged in
    private boolean isLoggedIn() {
        return currentUser != null;
    }

    // Method to change password
    public void changePassword(String oldPassword, String newPassword) {
        if (oldPassword.equals(getPassword())) {
            setPassword(newPassword);
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Invalid old password. Password change failed.");
        }
    }
}

class Admin {
    private String username;
    private String password;
    private Date createdAt;
    private Date lastLogin;

    public void login() {
        // Implementation of the login functionality for the Admin
        System.out.println("Admin login");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (isValidCredentials(username, password)) {
            System.out.println("Admin login successful.");
            setLastLogin(new Date());
        } else {
            System.out.println("Invalid username or password. Admin login failed.");
        }
    }

    private boolean isValidCredentials(String username, String password) {
        // Here, we will use a simple hardcoded check for demonstration purposes
        return username.equals("admin") && password.equals("admin123");
    }

    public void logout() {
        // Implementation of the logout functionality for the Admin
        if (isLoggedIn()) {
            setLastLogin(null);
            System.out.println("Admin logout successful.");
        } else {
            System.out.println("No admin currently logged in.");
        }
    }

    private boolean isLoggedIn() {
        return getLastLogin() != null;
    }

    public void manageUsers() {
        UserRepository userRepository = new UserRepository();
        List<User> users = userRepository.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("User Management");
            System.out.println("---------------");
            for (User user : users) {
                System.out.println("User ID: " + user.getUserId());
                System.out.println("Name: " + user.getName());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Address: " + user.getAddress());
                System.out.println("Contact Number: " + user.getContactNumber());
                System.out.println("Token Balance: " + user.getTokenBalance());
                System.out.println("Created At: " + user.getCreatedAt());
                System.out.println("Updated At: " + user.getUpdatedAt());
                System.out.println("Active: " + user.isActive());
                System.out.println("----------------");
            }
        }
    }

    public void manageServices() {
        ServiceRepository serviceRepository = new ServiceRepository();
        List<Service> services = ServiceRepository.getAllServices();
        if (services.isEmpty()) {
            System.out.println("No services found.");
        } else {
            System.out.println("Service Management");
            System.out.println("-----------------");
            for (Service service : services) {
                System.out.println("Service ID: " + service.getServiceId());
                System.out.println("Name: " + service.getName());
                System.out.println("Description: " + service.getDescription());
                System.out.println("Price: " + service.getPrice());
                System.out.println("Category: " + service.getCategory().getName());
                System.out.println("Created At: " + service.getCreatedAt());
                System.out.println("Updated At: " + service.getUpdatedAt());
                System.out.println("Active: " + service.isActive());
                System.out.println("-----------------");
            }
        }
    }

    public void addService(Service service) {
        if (service != null) {
            // Validate service details
            if (isValidService(service)) {
                // Assuming there is a ServiceRepository class to handle service-related logic
                ServiceRepository.addService(service);
                System.out.println("Service added successfully.");
            } else {
                System.out.println("Invalid service details.");
            }
        } else {
            System.out.println("Service cannot be null.");
        }
    }
    private boolean isValidService(Service service) {
        // Validate service details here, for example:
        if (service.getName() == null || service.getName().isEmpty()) {
            return false;
        }
        if (service.getDescription() == null || service.getDescription().isEmpty()) {
            return false;
        }
        if (service.getPrice() <= 0) {
            return false;
        }
        if (service.getCategory() == null) {
            return false;
        }
        if (service.getCreatedAt() == null) {
            return false;
        }
        if (service.getUpdatedAt() == null) {
            return false;
        }
        if (!service.isActive()) {
            return false;
        }
        if (service.getName().length() > 100) {
            return false;
        }
        if (service.getDescription().length() > 500) {
            return false;
        }
        if (service.getPrice() > 10000) {
            return false;
        }
        if (service.getCategory().getName() == null || service.getCategory().getName().isEmpty()) {
            return false;
        }
        if (service.getCategory().getDescription() == null || service.getCategory().getDescription().isEmpty()) {
            return false;
        }
        if (service.getCategory().getDescription().length() > 200) {
            return false;
        }
        return true;
    }

    public void removeService(Service service) {
        List<Service> allServices = ServiceRepository.getAllServices();

        if (allServices.contains(service)) {
            allServices.remove(service);
            System.out.println("Service removed successfully.");
        } else {
            System.out.println("Service not found.");
        }
    }

    public void resolveDispute(User user, Helper helper, Task task) {
        if (task.getStatus().equals("Pending")) {
            task.setStatus("Resolved");
            System.out.println("Dispute resolved for task: " + task.getDescription());
        } else {
            System.out.println("Cannot resolve dispute for a task that is not pending.");
        }
    }

    public Report generateReport(String reportType, Date startDate, Date endDate) {
        Report report = new Report();

        // Set the report type
        report.setReportType(reportType);

        // Retrieve data based on the report type and date range
        List<Task> tasks = TaskRepository.getTasksByDateRange(startDate, endDate);
        List<User> users = UserRepository.getUsersByDateRange(startDate, endDate);

        // Perform necessary calculations and analysis on the data
        int totalTasks = tasks.size();
        int totalUsers = users.size();
        double averageTasksPerUser = totalTasks / (double) totalUsers;

        // Add the data and analysis results to the report
        report.addData("Total Tasks", Integer.toString(totalTasks));
        report.addData("Total Users", Integer.toString(totalUsers));
        report.addData("Average Tasks per User", String.format("%.2f", averageTasksPerUser));

        // Set the generated report's timestamp
        report.setGeneratedAt(new Date());

        // Return the generated report
        return report;
    }
}

public class HomeHero {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User user = new User();
        Admin admin = new Admin();
        Helper helper = new Helper();

        boolean exit = false;
        while (!exit) {
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Register User");
            System.out.println("4. Browse Services");
            System.out.println("5. Manage Service");
            System.out.println("6. Logout");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (option) {
                case 1:
                    admin.login();
                    break;
                case 2:
                    user.login();
                    break;
                case 3:
                    user.register();
                    break;
                case 4:
                    List<Service> services = user.browseServices();
                    if (!services.isEmpty()) {
                        Service selectedService = services.get(0);
                        user.selectService(selectedService);
                    }
                    break;
                case 5:
                    // Example of managing services
                    Service newService = new Service();
                    newService.updateDetails("New Service", "This is a new service", 25.0, new Category(1, "Category1", "Description1"));
                    newService.addHelper(helper);
                    newService.activateService();
                    newService.removeHelper(helper);
                    newService.deactivateService();
                    break;
                case 6:
                    if (user.isLoggedIn()) {
                        user.logout();
                    } else if (admin.isLoggedIn()) {
                        admin.logout();
                    } else {
                        System.out.println("No user or admin currently logged in.");
                    }
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
}