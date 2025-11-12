# 🔗 URL Shortener Application

A powerful and simple **URL Shortener Web Application** built with **Spring Boot**, **PostgreSQL**, and **H2**.  
This app converts long URLs into short, shareable links — with user authentication, admin management, pagination, and private URL features.

---

## 🚀 Features

✅ **Shorten Long URLs** — Convert any lengthy link into a short and clean URL.  
✅ **Private URLs** — Users can make URLs private (visible only to them).  
✅ **User Authentication** — Secure login and registration system.  
✅ **Admin Dashboard** — Manage users and track URLs from the admin side.  
✅ **Click Tracking** — Each shortened URL maintains a click count.  
✅ **Pagination Support** — Efficiently handle large datasets with pagination.  
✅ **H2 & PostgreSQL** — Supports both in-memory (for testing) and PostgreSQL (for production).  
✅ **Timestamps & Expiry** — Option to set expiry time for links.  
✅ **Clean REST APIs** — Built using Spring Boot best practices.

---

## 🏗️ Tech Stack

| Layer | Technology Used |
|-------|------------------|
| Backend Framework | Spring Boot |
| Database | PostgreSQL (Production), H2 (Development/Test) |
| ORM | Hibernate / JPA |
| Security | Spring Security |
| API Testing | Postman |
| Build Tool | Maven |
| Language | Java 17+ |

---

## ⚙️ Installation & Setup

###  Clone the Repository

git clone https://github.com/yourusername/url-shortener.git
cd url-shortener
### Configure Database
Update your application.properties (or application.yml) file:

properties
Copy code
# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/shortenerdb
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

# Optional H2 (for testing)
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true
 Run the Application
Using Maven:

bash
Copy code
mvn spring-boot:run
Or using your IDE (IntelliJ / Eclipse), run the main class:

Copy code
UrlShortenerApplication.java
Once started, the app runs on:
 http://localhost:8080

 How It Works
Users can register or log in.

Paste a long URL and get a shortened one (e.g., http://localhost:8080/s/abc123).

Each short link is stored with a unique key.

Clicking the short link redirects the user to the original URL.

Admins can view all URLs, users, and statistics.

 Roles and Permissions
Role	Permissions
User	Create, view, and manage their own URLs
Admin	Manage all users, view analytics, delete URLs

 Example API Endpoints
Method	Endpoint	Description
POST	/api/auth/login	User login
POST	/api/urls	Create new short URL
GET	/api/urls?page=0&size=10	Paginated list of URLs
GET	/api/urls/{shortKey}	Redirect to original URL
DELETE	/api/urls/{id}	Delete a URL (user or admin)


### Screenshots

  <img width="960" height="476" alt="Screenshot 2025-11-12 130251" src="https://github.com/user-attachments/assets/d39c1452-59e9-4a81-9769-2a5b01d886c8" />

  <img width="958" height="476" alt="Screenshot 2025-11-12 125806" src="https://github.com/user-attachments/assets/311ae32e-fcf4-42d4-bddd-270eee24ed12" />

  <img width="960" height="474" alt="Screenshot 2025-11-12 130456" src="https://github.com/user-attachments/assets/ac146505-93fd-429d-860d-73bcc611c471" />

  

  <img width="960" height="474" alt="Screenshot 2025-11-12 111449" src="https://github.com/user-attachments/assets/5c652ade-7c67-47d9-b5e1-06bb8ae8042b" />


<img width="960" height="480" alt="Screenshot 2025-11-12 111502" src="https://github.com/user-attachments/assets/2b3d1af0-c24d-4a5b-9d4a-33e491d17a4d" />


