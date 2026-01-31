# Financial Tracker API
Live API: https://financial-tracker-api-6yq1.onrender.com

This is the **Java Spring Boot API** for my Financial Tracker application.  
It handles file upload and retrieval of processed transaction records stored in AWS services (S3 and DynamoDB).  
It is used in conjunction with an Angular front end and a Python AWS Lambda function, and interacts with AWS services using the AWS SDK.

Front end repo: https://github.com/PhilTBatt/financial-tracker-app  
Lambda repo: https://github.com/PhilTBatt/financial-tracker-app-lambda-layer

---

## Tech Stack

- Java (Spring Boot)
- Docker
- AWS (S3 + DynamoDB)

---

## Development Setup

### Prerequisites
- Java 21+
- Docker

### Create a `.env` file
```env
AWS_ACCESS_KEY_ID=your_access_key
AWS_SECRET_ACCESS_KEY=your_secret_key
AWS_REGION=eu-north-1
```

### Run Locally (Docker)

Build the Docker image:
```./mvnw spring-boot:build-image```

Start the service:
```docker compose up```

The API will be available at:
```http://localhost:8080```

### Swagger UI
```http://localhost:8080/api/swagger```

#### Health Check
```GET /health```
