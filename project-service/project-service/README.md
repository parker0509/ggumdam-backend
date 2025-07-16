##
## 06.23 ##
TODO:
1. 프론트에 프로젝트 디테일페이지 추가 + // 완료
2. 펀딩 값 
3. 직접 결제 
4. 검색
5. oAuth 
6. 회원가입 css
7. order 주문시 값 넘기기 테스트 // 완료
8. 로그아웃 시간 지정 // 완료
9. 쿠폰
10. 배송지
11. 휴대폰 인증하기 // 보류



elasticsearch:
image: docker.elastic.co/elasticsearch/elasticsearch:8.13.0
container_name: elasticsearch
environment:
- discovery.type=single-node
- xpack.security.enabled=false
- TZ=Asia/Seoul
ports:
- "9200:9200"
volumes:
- esdata:/usr/share/elasticsearch/data
networks:
- project-network

kibana:
image: docker.elastic.co/kibana/kibana:8.13.0
container_name: kibana
environment:
- ELASTICSEARCH_HOSTS=http://elasticsearch:9200
- TZ=Asia/Seoul
ports:
- "5601:5601"
depends_on:
- elasticsearch
networks:
- project-network

logstash:
image: docker.elastic.co/logstash/logstash:8.13.0
container_name: logstash
volumes:
- ./logstash/pipeline:/usr/share/logstash/pipeline
- ./logs:/app/logs
ports:
- "5044:5044"
- "9600:9600"
depends_on:
- elasticsearch
networks:
- project-network
