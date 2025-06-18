# 📒 Address Book API

A modular, in-memory contact management REST API built with **Spring Boot**. This system supports creating, updating, deleting, and searching contacts efficiently, following scalable design and backend best practices.

---

## 🚀 Features

- ✅ Create multiple contacts at once
- ✏️ Update partial or full contact data by ID
- 🔍 Search contacts by name with keyword matching
- ❌ Delete multiple contacts by their UUID
- 📦 In-memory storage using `ConcurrentHashMap`
- 🧩 Clean modular architecture (Controller, Service, Repository, DTO, Exception)
- 🛡️ Global exception handling with custom exceptions
- 📄 API validation with `jakarta.validation`
- 📝 Logging with `Slf4j`
- 🌐 OpenAPI (Swagger) documentation

---

## 🧱 Project Structure

com.book.address    
├── controller # REST endpoints     
├── service # Business logic     
├── dto # Request and Response DTOs      
│ ├── request  
│ └── response   
├── model # Data model    
├── storage # In-memory DB (repository)    
├── exception # Custom exceptions and global handler    
└── AddressApplication.java


---

## 📦 Tech Stack

| Tool            | Version  |
|-----------------|----------|
| Java            | 17+      |
| Spring Boot     | 3.x+     |
| Lombok          | ✅       |
| Jakarta Validation | ✅     |
| Swagger/OpenAPI | ✅       |

---

## 🛠️ Getting Started

### 🔗 Prerequisites

- Java 17+
- Maven

---

### 🧪 Clone and Build

```bash
git clone https://github.com/Rajveer98singh/contactbook
cd address-book-api
mvn clean install
```

### Run the Application

```bash
mvn spring-boot:run
```



## swagger UI

📘 Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)



### Test the API

Make sure Python 3 and requests library are installed.

Run the tests:

```
python3 test_address_book_api.py
```

You should see:

```
Testing /create... 
Testing /update...
Testing /search...
Testing /delete...
✅ All tests passed successfully.
```