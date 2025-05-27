# Journal App API Endpoints
# Short start
## User Endpoints
- **POST** `/user/register` – Register a new user
- **POST** `/user/login` – User login, returns JWT token
- **GET** `/user/me` – Get authenticated user info
- **PUT** `/user` – Update authenticated user info
- **DELETE** `/user` – Delete authenticated user account

## Journal Endpoints
- **GET** `/journal` – Get all journal entries for user
- **GET** `/journal/{id}` – Get journal entry by ID
- **POST** `/journal` – Create new journal entry
- **PUT** `/journal/{id}` – Update journal entry by ID
- **DELETE** `/journal/{id}` – Delete journal entry by ID

## Health Check
- **GET** `/` – Health check endpoint
## User Endpoints

### POST /user/register
- **Description**: Register a new user.
- **Authentication**: Not required.
- **Body**:
  ```json
  {
    "username": "example",
    "email": "user@example.com",
    "password": "securepassword"
  }
  ```

### POST /user/login
- **Description**: Authenticate a user and return a JWT token.
- **Authentication**: Not required.
- **Body**:
  ```json
  {
    "username": "example",
    "password": "securepassword"
  }
  ```

### GET /user/me
- **Description**: Get details of the authenticated user.
- **Authentication**: Required (Bearer Token).

### PUT /user
- **Description**: Update authenticated user's information.
- **Authentication**: Required (Bearer Token).
- **Body**:
  ```json
  {
    "email": "new@example.com",
    "password": "newpassword"
  }
  ```

### DELETE /user
- **Description**: Delete the authenticated user account.
- **Authentication**: Required (Bearer Token).

## Journal Endpoints

### GET /journal
- **Description**: Retrieve all journal entries of the authenticated user.
- **Authentication**: Required (Bearer Token).

### GET /journal/{id}
- **Description**: Retrieve a specific journal entry by its ID.
- **Authentication**: Required (Bearer Token).
- **Path Parameter**:
  - `id`: ObjectId of the journal entry.

### POST /journal
- **Description**: Create a new journal entry.
- **Authentication**: Required (Bearer Token).
- **Body**:
  ```json
  {
    "title": "My Journal Title",
    "content": "Journal content here."
  }
  ```

### PUT /journal/{id}
- **Description**: Update an existing journal entry by ID.
- **Authentication**: Required (Bearer Token).
- **Path Parameter**:
  - `id`: ObjectId of the journal entry.
- **Body**:
  ```json
  {
    "title": "Updated Title",
    "content": "Updated content."
  }
  ```

### DELETE /journal/{id}
- **Description**: Delete a journal entry by ID.
- **Authentication**: Required (Bearer Token).
- **Path Parameter**:
  - `id`: ObjectId of the journal entry.

## Health Check

### GET /
- **Description**: Returns "ok" if the server is running.
- **Authentication**: Not required.
