# MiniClinic 社區診所平台

這是本學期的期末整合部署專案，已透過 Docker 容器化並部署至 Render 雲端平台。

## 🌐 線上 Demo 網址
- GitHub Repository: https://github.com/yiyusong0708/miniclinic
- Render 部署網址: https://miniclinic-yiyusong0708.onrender.com

## 🛠️ 技術棧 (Tech Stack)
- 後端: Spring Boot 3.x, Spring Data JPA
- 前端: Thymeleaf, JavaScript (Fetch API)
- 資料庫: SQLite (Dev) / PostgreSQL (Prod)
- 部署工具: Git, Docker (Multi-stage build), Render

## 💻 本機執行步驟
1. 確保本機已安裝 Java 17 與 Maven。
2. 在根目錄執行指令：`mvn spring-boot:run`
3. 瀏覽器打開：`http://localhost:8080`

## 🔑 預設測試帳密
- 醫師帳號: D001
- 預設密碼: 123456