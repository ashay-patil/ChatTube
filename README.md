# ChatTube

Chat with YouTube videos. Paste one or more YouTube links into a chat session, ask questions in plain language, and get answers grounded in the video transcripts, with **timestamps** pointing to the exact video moments that support each answer.

ChatTube is a Retrieval-Augmented Generation (RAG) app built with a Spring Boot backend, MongoDB Atlas Vector Search, Google Gemini, and a React frontend.

---

## Features

- **Chat sessions**: group related videos into a session and chat across all of them.
- **Transcript RAG**: transcripts are chunked, embedded, and retrieved by semantic similarity for each question.
- **Timestamped answers**: every answer includes the video segments (`videoId`, `startTime`, `endTime`) it was based on.
- **Chat-history memory**: past questions and answers are also embedded and searched, so you can ask things like "What did I ask earlier?".
- **JWT authentication**: register/login, with stateless token-based access to all APIs.
- **Per-user isolation**: all vector searches are filtered by `userId` and `chatSessionId`.
- **Prompt-injection guard**: the prompt treats transcripts and chat history as reference material only.

---

## Tech Stack

| Layer       | Technology                                                         |
| ----------- | ------------------------------------------------------------------ |
| Frontend    | React 19, Vite, React Router, Tailwind CSS 4                       |
| Backend     | Java 21, Spring Boot, Spring Security, Spring Data MongoDB, Lombok |
| Auth        | JWT (jjwt), BCrypt password hashing                                |
| Database    | MongoDB Atlas (with Atlas Vector Search)                           |
| AI          | Google Gemini via`google-genai` (embeddings + answer generation)   |
| Transcripts | [Supadata](https://supadata.ai) API                                |

---

## How It Works

```
                 ───────────────────── Ingestion ─────────────────────
YouTube links ─► Supadata transcript ─► chunk (50 segments) ─► Gemini embedding ─► MongoDB (video_chunks)

                 ────────────────────── Question ──────────────────────
Question ─► Gemini embedding ─┬─► vector search: video_chunks   ─┐
                              └─► vector search: ChatHistory    ─┴─► prompt ─► Gemini ─► { response, timestamps }
                                                                                              │
                                                              saved to ChatHistory (with embeddings)
```

1. **Ingest**: for each link, the backend fetches the transcript from Supadata, groups it into chunks of 50 transcript segments, generates a 768-dimension embedding per chunk, and stores it with its start/end time.
2. **Retrieve**: a question is embedded and used to run `$vectorSearch` over the session's video chunks and over past chat history (both the stored questions and the stored answers).
3. **Generate**: the retrieved context is inserted into `prompt.txt`, and Gemini returns strict JSON with a `response` and a list of `timestamps`.
4. **Remember**: the question, answer, and both embeddings are saved so later questions can draw on them.

---

## Project Structure

```
ChatTube/
├── backend/                      # Spring Boot API
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/chat/backend/
│       │   ├── RAG/              # EmbeddingService, LLMService, vector searches
│       │   ├── controllers/      # Auth, chat sessions, videos, chat
│       │   ├── services/         # Business logic (ingestion, chat)
│       │   ├── entities/         # User, ChatSession, Video, VideoChunk, ChatHistory
│       │   ├── repositories/     # Spring Data Mongo repositories
│       │   ├── filters/          # JWT authentication + validation filters
│       │   ├── configs/          # Security and CORS config
│       │   ├── utils/            # JWT utilities and auth provider
│       │   └── DTO/
│       └── resources/
│           ├── application.properties
│           └── prompt.txt        # RAG prompt template
└── frontend/                     # React + Vite app
    └── src/
        ├── Pages/                # Home, Login, Register, ChatSessions, Chat
        ├── Components/           # Navbar, Videos, RequestResponse, etc.
        └── App.jsx
```

---

## Prerequisites

- **Java 21** (the Maven wrapper is included)
- **Node.js 18+** and npm
- A **MongoDB Atlas** cluster
- A **Google Gemini API key**
- A **Supadata API key**

---

## Setup

### 1. MongoDB Atlas vector indexes

Create these two Atlas Vector Search indexes on the `YoutubeVideoRAG` database. Embeddings are **768 dimensions**.

**`vector_index`** on the `video_chunks` collection:

```json
{
  "fields": [
    {
      "type": "vector",
      "path": "embedding",
      "numDimensions": 768,
      "similarity": "cosine"
    },
    { "type": "filter", "path": "userId" },
    { "type": "filter", "path": "chatSessionId" }
  ]
}
```

**`chat_vector_index`** on the `ChatHistory` collection:

```json
{
  "fields": [
    {
      "type": "vector",
      "path": "userQuestionEmbedding",
      "numDimensions": 768,
      "similarity": "cosine"
    },
    {
      "type": "vector",
      "path": "LLMResponseEmbedding",
      "numDimensions": 768,
      "similarity": "cosine"
    },
    { "type": "filter", "path": "userId" },
    { "type": "filter", "path": "chatSessionId" }
  ]
}
```

### 2. Backend

Set the following environment variables:

| Variable           | Description                                                                 |
| ------------------ | --------------------------------------------------------------------------- |
| `DB_USER`          | MongoDB Atlas username                                                      |
| `DB_USER_PASSWORD` | MongoDB Atlas password                                                      |
| `GEMINI_API_KEY`   | Google Gemini API key                                                       |
| `SUPADATA_API_KEY` | Supadata API key (transcript fetching)                                      |
| `SECRET_KEY`       | Secret used to sign JWTs (use a long random string, at least 32 characters) |

Then run:

```bash
cd backend
./mvnw spring-boot:run
```

The API starts on `http://localhost:8080`.

> The MongoDB connection string in `application.properties` points to a specific Atlas cluster. Update it to your own cluster before running.

### 3. Frontend

```bash
cd frontend
npm install
npm run dev
```

The app runs on `http://localhost:5173`, which is the origin allowed by the backend's CORS config.

---

## API Overview

All endpoints except `/auth/register` and `/generate-token` require an `Authorization: Bearer <token>` header.

| Method | Endpoint                                    | Description                                                       |
| ------ | ------------------------------------------- | ----------------------------------------------------------------- |
| `POST` | `/auth/register`                            | Register a new user                                               |
| `POST` | `/generate-token`                           | Log in; the JWT is returned in the`Authorization` response header |
| `GET`  | `/auth/get-profile`                         | Get the current user                                              |
| `POST` | `/chat-session/create-chat-session`         | Create a chat session                                             |
| `GET`  | `/chat-session/get-chat-sessions`           | List the user's chat sessions                                     |
| `POST` | `/api/upload-youtube-videos?chatSessionId=` | Add YouTube links to a session (transcribe, chunk, embed, store)  |
| `GET`  | `/api/get-videos?chatSessionId=`            | List videos in a session                                          |
| `GET`  | `/api/get-video?videoId=`                   | Get a single video                                                |
| `POST` | `/api/ask-question?chatSessionId=`          | Ask a question; returns`{ response, timestamps }`                 |
| `GET`  | `/api/get-all-chats?chatSessionId=`         | Get the full chat history of a session                            |

### Example answer payload

```json
{
  "response": "The speaker explains that ...",
  "timestamps": [
    { "videoId": "<video id>", "startTime": 12345, "endTime": 67890 }
  ]
}
```

---

## Challenges Faced

**1. Getting reliable, structured output from the LLM**
The frontend needs both an answer and clickable timestamps, so the model has to return strict JSON. Free-form replies, Markdown fences, or extra fields would break parsing.
_Solution:_ `prompt.txt` defines an exact output schema (`response` and `timestamps`) with explicit rules for each case, and the backend deserializes the result straight into an `LLMResponse` object.

**2. Preventing hallucinated or irrelevant timestamps**
A high similarity score doesn't mean a retrieved chunk actually answers the question, and chat history has no timestamps at all.
_Solution:_ the prompt tells the model to ignore chunks that don't help, to list only the chunks it actually used, and never to generate timestamps from chat history.

**3. Deciding where an answer should come from**
Some questions are about the video, some are about the conversation ("What did I ask earlier?"), and some need both.
_Solution:_ the chat history is embedded and searched alongside the transcript chunks, and the prompt includes source-selection rules so the model picks chat history, transcript, or both.

**4. Searching past answers as well as past questions**
Matching only on the user's earlier questions misses follow-ups that relate to what the assistant said.
_Solution:_ each chat turn stores two embeddings (question and answer), and `ChatHistorySimilaritySearch` runs a vector search on each and merges the results.

**5. Chunking transcripts sensibly**
Transcript segments are only a few words long, which is too little context for good retrieval.
_Solution:_ segments are grouped 50 at a time into chunks, each keeping its start and end time so answers can link back to the exact moment in the video.

**6. Keeping each user's data isolated**
Vector search runs across a shared collection, so one user's chunks or chats must never leak into another's results.
_Solution:_ every `$vectorSearch` pre-filters on `userId` and `chatSessionId`, which also requires those fields to be declared as filter fields in the Atlas indexes.

**7. Prompt injection through transcripts**
Video transcripts are untrusted text and could contain instructions aimed at the AI.
_Solution:_ the prompt has a security rule that treats transcripts and chat history as reference material only, so only the current user question is followed.

**8. Custom stateless JWT authentication**
Spring Security's defaults are session-based, but this API is stateless and used by a separate frontend.
_Solution:_ custom filters handle login (`/generate-token`) and token validation, sessions are disabled, passwords are hashed with BCrypt, and CORS is configured for the frontend origin.

**9. Slow, synchronous ingestion**
Adding videos means fetching a transcript and generating an embedding for every chunk, all inside a single request, so long videos take a while.
_Current state:_ this works but blocks the request, which is why asynchronous ingestion is listed under Future Improvements.

---

## Future Improvements

- Stream responses instead of waiting for the full answer
- Process video ingestion asynchronously, with progress feedback
- Move the API base URL and CORS origin into environment configuration
- Add tests for the ingestion and retrieval pipeline
- Dockerize the Application
- Add Unit Testing and Integration Testing
- Use slf4j logging
