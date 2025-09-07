# 📄 AskDocs

AskDocs is an intelligent document assistant built with **Spring AI** and **Retrieval Augmented Generation**.  
It allows you to upload documents, automatically ingests them, and lets you ask contextual questions based on their content.  

---

## 📹 Demo

https://github.com/user-attachments/assets/849d3e13-c38a-4db3-ba8d-7398d66cb4c1 


---

## ✨ Features

- 📂 **Automatic Ingestion**  
  - On startup, AskDocs scans a folder for documents.  
  - If a document hasn’t been ingested yet, it is processed and a `.markup` file is created.  
  - Prevents re-ingesting the same file multiple times.  

- ⬆️ **File Upload**  
  - Upload one document at a time via REST endpoint.  
  - Uploaded files are automatically ingested.  

- ❓ **Ask Questions**  
  - Query across ingested documents.  
  - AI retrieves relevant context and returns precise answers.  

---

## 🚀 Endpoints
### Home Page
| Method | Endpoint             | Description              |
|--------|----------------------|--------------------------|
| GET    |   `/`                | Takes to home page       |

### 📂 Upload a File
| Method | Endpoint             | Description              |
|--------|----------------------|--------------------------|
| POST   | `/upload`            | Upload and ingest a file |

**Request (multipart/form-data)**  
```http
POST /upload
Content-Type: multipart/form-data
```

## ❓ Ask a Question
| Method | Endpoint            | Description              |
|--------|---------------------|--------------------------|
| GET   |  `/chat`             | Ask a query             |

**Request (JSON)**
```
{
  "query": "What is covered in the contract?"
}
```


**Response (JSON)**
```
{
  "answer": "The contract covers service obligations, timelines, and penalties."
}
```

## ⚙️ Running Locally
### 1. Clone the Repository
git clone https://github.com/ANIKET150104/AskDocs.git
cd AskDocs

### 2. Configure Database

**Update src/main/resources/application.properties with your DB credentials:**
```
# Database config
spring.datasource.url=jdbc:postgresql://localhost:5432/Your_Database_Name
spring.datasource.username= Your_Database_Username
spring.datasource.password= Your_Database_Password

# PgVectorStore config
spring.ai.vectorstore.type=pgvector
spring.ai.vectorstore.pgvector.enabled=true
spring.ai.vectorstore.pgvector.initialize-schema=true
spring.ai.vectorstore.pgvector.dimension=512
spring.ai.vectorstore.pgvector.index-type=hnsw
spring.ai.vectorstore.pgvector.index-distance-type=cosine
```

(Skip database config part if using an in-memory setup.)

### 3. Run the Application

**Using Maven:**
```
mvn spring-boot:run
```

Or build and run:

mvn clean package
java -jar target/askdocs-0.0.1-SNAPSHOT.jar

### 4. Access APIs

Home Page → GET http://localhost:8080/

Upload File → POST http://localhost:8080/upload

Ask Question → GET http://localhost:8080/cht

## 🛠 Tech Stack

**Backend: Spring Boot 3.5+**

**AI Layer: Spring AI**

**Frontend: HTML, CSS, HTMX(dynamic reloading), JTE(Server-side rendering)**

**LLM: Any of Your liking.**

**Embedding Model: Any of Your liking.**

**Database: PgVector**

**Build Tool: Maven**

## 📌 Roadmap

 **Add support for multiple file upload**

 **Enhance query responses with summarization**

 **Deploying the backend**

## 🤝 Contributing

Contributions are welcome! Please fork the repo and submit a PR.

## 📜 License

This project is licensed under the MIT License.
