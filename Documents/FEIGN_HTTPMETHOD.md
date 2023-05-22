# 페인 클라이언트에서 파라미터와 http method
* 일반적인 api도 마찬가지 이지만
* request body로 json을 보내는 경우는 command만 가능하다
* 즉 POST, PUT, DELTE 등만 가지고 있다.
* GET메서드는 pathvariable로 url에 데이터를 삽입하거나,
* request param처럼 쿼리스트링에 정보를 넣어서 전달한다.

## 지키지 않을경우
* 정상적으로 http method가 매핑되지 않는다.
* POST로 요청을 보내더라도 request param 혹은 pathvariable의 형태로 보내면 get으로 해석하여 에러를 발생시킨다.