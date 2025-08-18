<p align="center">
  <img src="https://i.ibb.co/08rtXCB/logo.png" alt="logo" width="65">
</p>
<h1 align="center">Singularities AI - Ollama Web UI</h1>

Singularities AI is a web platform designed to leverage text-based AI models powered by Ollama. Feature-rich, it has
been built to deliver a simple yet high-performance experience, even for users new to LLMs. With its customization
options, role configuration, and access management, Singularities AI is an ideal solution for professional use, ensuring
data security and eliminating reliance on proprietary APIs.

**Need an enterprise plan or custom features?** – **[Contact us](mailto:contact@singularities-ai.com)**

## Installation 🚀

### Ollama

You need to have Ollama installed on a machine with a URL accessible by the API.

Please follow the [Ollama documentation](https://github.com/ollama/ollama) for installation instructions.

### Running the API

You need to configure the following properties:

- `singularities.app.jwtSecret` = Your JWT secret (e.g., generated with a JWT secret generator)
- `spring.mail.host` = Your SMTP provider host
- `spring.mail.username` = Your SMTP provider username
- `spring.mail.password` = Your SMTP provider password
- `app.name` = Your app name (e.g., Singularities AI)
- `app.url` = Your frontend URL (e.g., http://127.0.0.1:7000)
- `app.url.client` = Client URL (e.g., http://127.0.0.1:7000)
- `spring.ai.ollama.base-url` = Your Ollama server URL (e.g., http://localhost:11434)

Then, start the application using Maven:

```bash
mvn spring-boot:run
````

### Running the Frontend

1. Update the `.env` file:

```env
API_URL=Your API URL (e.g., http://localhost:8080)
```

2. Install dependencies:

```bash
pnpm install
```

3. Start the application using the scripts available in `package.json`.

> ⚠
> The `:dev` branch contains the latest unstable features and changes. Use it at your own risk as it may have bugs or
> incomplete features.

### Containerisation 🐳

Run the API and frontend using our prebuilt Docker images or build them yourself.

---

#### 1. Using prebuilt images

```bash
# Backend
docker pull singularitiesai/singularities-ai-api:latest

# Frontend
docker pull singularitiesai/singularities-ai-frontend:latest
```

#### 2. Using Docker Compose

A [docker-compose.yml](./docker-compose.yml) example is included in the repository.
Run the full stack (API + frontend + Ollama + database) with:

```bash
docker compose up -d
```

#### 3. Building locally

```bash
# Backend
docker build -t singularities-ai-api ./backend

# Frontend
docker build -t singularities-ai-frontend ./frontend
````

## Features ⚡️

- **Secure and Local-First**: All LLMs are stored and run on your machine without any external internet interaction.
  This ensures that no data can be leaked.

- **Multi-Model Conversations**: Singularities supports multiple LLMs and allows you to switch between them within the
  same conversation with just a few clicks.

- **Chat and User Context**: You can define a custom context for a single conversation or across all your conversations
  to personalize your interactions with the LLMs.

- **Responsive Design**: The application works seamlessly on PC, tablet, or mobile, regardless of screen size.

- **Easy Customization**: The public source code allows anyone to modify, remove, or add new features according to their
  needs.

- **Continuous Updates**: Singularities AI is actively developed, with new features being added regularly.

## ⚙️ Built with

[![TypeScript](https://img.shields.io/badge/TypeScript-3178C6?logo=typescript&logoColor=fff)](#)
[![Nuxt](https://img.shields.io/badge/Nuxt-002E3B?logo=nuxt&logoColor=#00DC82)](#)
[![shadcn/ui](https://img.shields.io/badge/shadcn%2Fui-000?logo=shadcnui&logoColor=fff)](#)
[![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)](#)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff)](#)
[![Postgres](https://img.shields.io/badge/Postgres-%23316192.svg?logo=postgresql&logoColor=white)](#)
[![Ollama](https://img.shields.io/badge/Ollama-fff?logo=ollama&logoColor=000)](#)

## Screenshot 📷

<img src="https://i.ibb.co/v4rVTLcJ/image.png" alt="Login page" border="0">
<img src="https://i.ibb.co/8gfx4wpp/image.png" alt="Chat page" border="0">
<img src="https://i.ibb.co/Z1crttjh/image.png" alt="Account page" border="0">
<img src="https://i.ibb.co/35nwG9WF/image.png" alt="Admin models page" border="0">

## License 📜

This project is licensed under a Source-Available Non-Commercial License.
Free for personal, educational, or non-commercial use.
The free version is provided “as-is” without warranty; commercial licenses may include warranties.
For full license text, see LICENSE.txt.

## Support 💬

If you have any questions, suggestions, or need assistance, please open an issue or contact us at
contact@singularities-ai.com