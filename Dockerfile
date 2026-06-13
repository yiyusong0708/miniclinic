# 第一階段：使用 Maven 進行專案編譯與打包
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# 複製專案原始碼與 pom.xml
COPY pom.xml .
COPY src ./src

# 執行編譯，跳過測試以加速部署
RUN mvn clean package -DskipTests

# 第二階段：只複製打包好的 JAR 檔進行輕量化執行
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 從第一階段的成品中複製 JAR 檔 (請確保 jar 名稱與你 pom.xml 設定一致，通常可以用預設的封裝路徑)
COPY --from=build /app/target/*.jar app.jar

# 暴露 Spring Boot 預設的 8080 埠號
EXPOSE 8080

# 啟動命令
ENTRYPOINT ["java", "-jar", "app.jar"]