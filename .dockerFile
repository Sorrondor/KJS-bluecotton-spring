# 1️⃣ JDK 17 기반 이미지 (경량 alpine 대신 안정적인 Debian 사용)
FROM eclipse-temurin:17-jdk-jammy

# 2️⃣ 컨테이너 내부 작업 디렉토리
WORKDIR /app

# 3️⃣ Gradle Wrapper / 의존성 캐시 복사를 위한 단계
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle settings.gradle ./

# 4️⃣ Gradle 의존성 미리 다운 (캐시 용도)
RUN ./gradlew dependencies --no-daemon || true

# 5️⃣ 나머지 소스코드 복사
COPY src ./src

# 6️⃣ 빌드 (Spring Boot fat jar 생성)
RUN ./gradlew clean bootJar --no-daemon

# 포트 노출
EXPOSE 10000

# ✅ 자동으로 build/libs 안의 JAR 파일 탐색 후 실행
CMD sh -c 'JAR_FILE=$(find build/libs -name "*.jar" | head -n 1) && echo "▶ Running $JAR_FILE" && java -jar "$JAR_FILE"'