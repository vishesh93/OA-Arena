# 🚀 OA Arena – AI Powered Coding Assessment Platform

OA Arena is a scalable coding assessment platform that enables administrators to create and manage coding tests while allowing users to attempt problems with real-time code execution and evaluation. The system integrates AI capabilities to enhance feedback and simulate real-world interview environments.

---

## 🧠 Features

* 📝 **Admin Panel**

  * Create and manage coding tests
  * Add problems and test cases
  * Control test flow and access

* 💻 **User System**

  * Attempt coding tests in real-time
  * Submit solutions and get instant results

* ⚙️ **Code Execution Engine**

  * Secure multi-language execution using Judge0 API
  * Automated evaluation of submissions

* 🤖 **AI Integration**

  * Integrated Groq AI & Google Gemini APIs
  * Intelligent response generation and assistance

* 🔐 **Authentication & Security**

  * JWT-based authentication
  * Role-based access control (Admin/User)
  * Secure API endpoints using Spring Security

* 🚀 **Scalable Backend**

  * Designed modular REST APIs
  * Handles concurrent submissions efficiently

---

## 🛠️ Tech Stack

### Backend

* Java (Spring Boot, Spring MVC)
* Spring Security

### Authentication

* JWT (JSON Web Token)
* BCrypt

### APIs

* Judge0 (Code Execution)
* Groq AI
* Google Gemini

### Database

* Postgre Sql

### Concepts

* REST APIs
* System Design
* AI Integration
* Code Execution Systems

---

## ⚡ Architecture Overview

```
User → Frontend → Backend (Spring Boot)
                → Judge0 API (code execution)
                → AI APIs (Groq / Gemini)
                → Database (MySQL)
```

---

## 🔥 Key Highlights

* Built a **real-world coding platform** similar to online assessment systems
* Integrated **AI + code execution engine** in a single system
* Implemented **secure authentication and authorization**
* Designed for **scalability and modularity**

---

## 🧪 How It Works

1. Admin creates a test with problems and test cases
2. User attempts the test via UI
3. Code is sent to backend
4. Backend sends code to Judge0 for execution
5. Results are evaluated and returned
6. AI APIs enhance responses and interaction
7. theory answers are evaluated through groq and a suitable feedback is given



## 📌 Future Improvements

* AI-based code feedback and optimization suggestions
* Plagiarism detection system
* Advanced analytics for performance tracking
* Full interview simulation with AI


