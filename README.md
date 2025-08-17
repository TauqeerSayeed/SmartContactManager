# SmartContactManager

A web application that allows users to efficiently manage, store, and organize personal and professional contacts with a user-friendly interface. The project leverages modern web technologies and robust backend architecture to provide secure and responsive contact management.

## Features

- User registration and authentication
- Add, edit, view, and delete contacts
- Search and filter contacts
- Responsive design for desktop and mobile
- Secure password management
- Contact grouping and notes
- Intuitive dashboard

## Technologies Used

| Layer      | Technologies                       |
|------------|------------------------------------|
| Backend    | Java, Spring Boot, Maven           |
| Frontend   | HTML, CSS, JavaScript, Tailwind CSS|
| Build Tool | Maven                              |
| Database   | (Add your DB here, e.g., MySQL/PostgreSQL) |
| Others     | Spring Security, Thymeleaf         |

## Project Structure

```
SmartContactManager/
├── .mvn/                 # Maven Wrapper files
├── src/                  # Source code (Java classes, resources)
├── package.json          # (If using frontend JS dependencies)
├── package-lock.json     # (If using frontend JS dependencies)
├── pom.xml               # Maven project descriptor
├── tailwind.config.js    # Tailwind CSS configuration
├── .gitignore
├── .gitattributes
├── README.md
```

## Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/TauqeerSayeed/SmartContactManager.git
   cd SmartContactManager
   ```

2. **Configure the database**
   - Update database credentials in `src/main/resources/application.properties`.

3. **Install dependencies**
   ```bash
   ./mvnw clean install
   # Or if using Node.js for frontend:
   npm install
   ```

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```
   - The app will typically run at `http://localhost:8080`.

5. **Access from the browser**
   - Register a new user and start managing your contacts.

## Contribution

Pull requests are welcome! Please make sure to open issues for bugs or feature requests. All contributions should be well tested and documented.
