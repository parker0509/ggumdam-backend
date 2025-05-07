✅ [auth-service] 로그인 기능 개발 TODO 
사용자 로그인 요청을 받아 user-service에서 사용자 정보를 조회하고, 비밀번호 검증 후 JWT 토큰을 발급한다.

**1️⃣ 유저 정보 조회**
`FeignClient로 user-service의 /api/user/{email} API 호출`
`예외 발생 시 에러 처리 (FeignException, NullPointerException 등)`

**2️⃣ 비밀번호 검증**
`입력한 비밀번호와 저장된 비밀번호를 BCrypt로 비교`
`일치하지 않으면 인증 실패 처리 (401 Unauthorized)`

**3️⃣ JWT 발급**
`JwtTokenProvider를 사용해 AccessToken 생성
토큰에 이메일 또는 사용자 ID를 포함한 Claim 설정
생성된 토큰을 클라이언트에 응답`

**4️⃣ RefreshToken 설계 (선택)**
`AccessToken 만료 대비 RefreshToken 발급
Redis 또는 DB에 저장하고 유효기간 관리
RefreshToken을 통한 재발급 API 추가`

**5️⃣ 테스트**
`UserClient 연동 통합 테스트 작성
로그인 성공/실패 케이스 단위 테스트 작성`