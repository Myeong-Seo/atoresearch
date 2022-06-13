# atoresearch

1. REST API 설명
모든 기능들은 main.html에서 시작한다.

CRUD기능 완성(예외처리 및 보완사항 존재)
등록(C) - insert.html host명과 ip주소를 입력하여 등록한다.
검색(R) - search.html, allsearch.html 하나의 호스트 조회, 전체 호스트 조회 2가지를 구현
수정(U) - searchresult.html, update.html host명으로 검색하여 정보를 수정
삭제(D) - searchresult.html, delete.html host명으로 검색하여 정보를 삭제

2. 특정 호스트의 현재 Alive 상태 조회
호스트명이 아닌 ip로 Alive를 확인할 수 있도록 구현.
검색(R)에서 Alive 또는 Unreachable을 확인할 수 있다.
Alive 상태면 last_Alive상태시간이 저장이 되어 보여지며, Unreachable상태라면 last_Alive상태시간은 저장이 되어있지 않다.

3. 호스트들의 Alive 모니터링 결과 조회
검색(R)부분의 searchresult.html, allsearch.html에 표시가 된다.
모든 host들의 Alive 또는 Unreachable상태, last_Alive상태시간이 보여진다.
만약 last_Alive상태시간이 없었다면 allsearch.html로 이동할때 현재시간으로 저장이 된다.

제약조건
Database의 Table hoststatus를 생성 후 max_rows를 100으로 수정하여 저장하였다.(호스트 등록을 100개로 제한)
또한 hostname과 ip가 중복이 되면 안되므로 UNIQUE로 중복을 못하게 설정.
등록(C)을 진행할 ip주소는 정규표현식으로 양식이 존재하므로 지켜줘야한다. (xxx.xxx.xxx.xxx)


시험 테스트(x의 부분은 실제 IP주소이므로 수정)
호스트명	IP주소	        등록/수정시간	    상태확인	    마지막Alive시간
aaa	     1.1.1.1	       2022-06-14 02:19	Alive	       2022-06-14 05:54
bbbb	   2.2.2.2	       2022-06-14 04:07	Unreachable	
ccc	     3.3.3.3	       2022-06-14 04:45	Alive	       2022-06-14 05:54
ddd	     4.4.4.4	       2022-06-14 04:48	Alive	       2022-06-14 05:54
google	 8.8.8.8	       2022-06-14 05:51	Alive	       2022-06-14 05:51
eee	     1.2.3.4	       2022-06-14 04:07	Unreachable	
zxc	     192.168.1.1     2022-06-14 04:13	Unreachable	
naver    223.130.200.107 2022-06-14 04:36	Unreachable	
ms	     1xx.1xx.6x.1xx	 2022-06-14 04:43	Alive	       2022-06-14 05:54
asdasd	 7.8.9.10	       2022-06-14 05:23	Unreachable
