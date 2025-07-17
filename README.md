# 🛠️ Microservices Architecture Overview

## 📦 기술 스택

| 계층        | 기술                               |
|-------------|------------------------------------|
| Frontend    | React                              |
| Backend     | Spring Boot (Java 17)              |
| DB          | MySQL                              |
| API Gateway | Spring Cloud Gateway               |
| Service Reg.| Eureka Discovery Service           |
| Messaging   | Apache Kafka (주로 Auth Service)   |
| Log Stack   | ELK Stack (Elasticsearch, Logstash, Kibana) |
| Build/Run   | Docker, Docker Compose             |

---

## 🌐 서비스 구성 및 포트 매핑

| Port   | 서비스 이름          | 설명                                | 연결 DB Port |
|--------|-----------------------|-------------------------------------|--------------|
| 3000   | React Frontend        | 사용자 웹 프론트엔드                | -            |
| 9000   | Gateway Service       | API 게이트웨이                      | -            |
| 10000  | Eureka Service        | 마이크로서비스 등록 및 디스커버리   | -            |

---

## 🧩 Backend Microservices

| Port  | 서비스 이름         | 설명                        | DB Port | DB 이름     |
|-------|----------------------|-----------------------------|---------|-------------|
| 8000  | Auth Service         | 로그인, JWT, OAuth, Kafka   | 3008    | auth-db     |
| 8005  | User Service         | 사용자 프로필, 정보         | 3307    | user-db     |
| 8006  | Project Service      | 프로젝트/펀딩 등록 및 조회 | 3310    | project-db  |
| 8010  | Order Service        | 주문 및 예약 기능           | 3309    | order-db    |
| 8015  | Payment Service      | 결제 처리 (Iamport 등)     | 3400    | payment-db  |

---

## 🔄 통신 구조 요약

```plaintext
React (3000)
   ↓ (Proxy)
Gateway Service (9000)
   ↓
+--------------------------+
| Eureka Discovery (10000)|
+--------------------------+
   ↓                       ↓
Auth (8000, Kafka 9092)  User (8005) ...
Project (8006)           Order (8010)
Payment (8015)

각 서비스는 개별 MySQL DB와 연결됨.
🔍 ELK 로그 수집 구성
Port	서비스 이름	설명
9200	Elasticsearch	로그 인덱싱 및 검색
5044	Logstash (input)	Filebeat → Logstash 수신 포트
9600	Logstash (monitor)	Logstash 상태 모니터링
5601	Kibana	시각화 대시보드 및 로그 탐색

✅ ELK는 Filebeat → Logstash → Elasticsearch → Kibana 구조로 연동됩니다.
✅ Spring Boot 로그는 JSON 포맷으로 logback-spring.xml에 정의 후 Filebeat로 수집됩니다.

🛰️ Kafka 구성
Port	구성 요소	설명
9092	Apache Kafka	인증 관련 이벤트 처리 (auth-service)

Kafka는 인증 성공/실패, 로그인 로그, 알림 등 이벤트 전송용으로 활용됩니다.
추후 주문/결제 이벤트로 확장 예정입니다.

📁 Git Repository 구조 (예시)
sql
복사
편집
root/
├── gateway-service/
├── eureka-service/
├── auth-service/
├── user-service/
├── project-service/
├── order-service/
├── payment-service/
├── frontend/ (React)
├── elk/
│   ├── elasticsearch/
│   ├── logstash/
│   ├── kibana/
├── kafka/
│   ├── zookeeper/
│   ├── kafka/
├── docker-compose.yml
└── README.md
