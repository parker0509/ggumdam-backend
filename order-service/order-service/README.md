# 🧾 Order-Service (마이크로서비스 구조)

이 프로젝트는 Spring Cloud 기반의 마이크로서비스 아키텍처로 구성된 주문(Order) 처리 시스템의 일부입니다.  
사용자 인증, 주문 요청, 상태 조회 등을 담당하는 `order-service`를 중심으로 개발 진행 중입니다.

---

## 📦 구성 서비스

- **user-service**: 사용자 정보 관리
- **order-service**: 주문 생성 및 상태 관리
- **gateway-service**: 공통 진입점 (Spring Cloud Gateway)
- **eureka-server**: 서비스 디스커버리

---

## ✅ 현재까지 진행사항

### 🔹 1. 주문 생성 기능
- `POST /api/orders`
- FeignClient를 통해 `user-service`로부터 유저 정보 검증
- 주문 저장 시 사용자 ID, 프로젝트 ID, 금액, 수량 등 저장
- `OrderStatus` Enum을 활용한 상태 관리

### 🔹 2. 주문 단건 조회
- `GET /api/orders/{id}`
- 주문 ID로 단건 조회 후 응답

### 🔹 3. 유저별 주문 목록 조회
- `GET /api/orders/status/{userId}`
- 특정 유저가 가진 주문 목록 조회
- 추후 `OrderStatus`를 추가하여 상태별 필터링 가능

---

## 🧩 사용 기술

| 기술        | 설명                           |
|-------------|--------------------------------|
| Spring Boot | 마이크로서비스별 핵심 프레임워크 |
| Spring Cloud OpenFeign | 마이크로서비스 간 통신 (FeignClient) |
| Spring Data JPA | DB 연동 및 CRUD |
| MySQL       | 주문 데이터 저장소              |
| Eureka      | 서비스 등록 및 디스커버리       |
| Gateway     | 통합 API 게이트웨이            |

---

## 🛠️ 주요 클래스

- `OrderService`: 비즈니스 로직 (주문 생성, 조회)
- `OrderController`: REST API 컨트롤러
- `UserClient`: FeignClient로 사용자 서비스 호출
- `OrderRepository`: JPA Repository
- `OrderStatus`: 주문 상태 Enum (`PENDING`, `CONFIRMED`, `CANCELLED`, `COMPLETED` 등)

---

## 🔄 예정 기능 (To-do)

- [ ] 주문 상태 변경 API
- [ ] 사용자 주문 히스토리 페이지
- [ ] 관리자용 전체 주문 목록 및 필터링
- [ ] Kafka 연동 또는 메시징 큐로 비동기 주문 처리
- [ ] 유닛 테스트 및 통합 테스트 추가

---

## 🚀 실행 방법

1. `user-service` 먼저 실행 (`localhost:8005`)
2. `eureka-server` 실행
3. `gateway-service` 실행
4. 마지막으로 `order-service` 실행
5. Postman 또는 Swagger를 통해 API 테스트

---

## 📁 프로젝트 구조 (일부)
1. 8000 -> auth-service -> DB 33010
2. 8005 -> user-service -> DB 3307
3. 8006 -> project-service -> DB 3308
4. 8010 -> order-service -> DB 3309
5. 8015 -> payment-service
6. 10000-> eureka-service