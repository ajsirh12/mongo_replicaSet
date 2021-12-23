# mongoDB ReplicaSet 구성
## 1. mongoDB 설치 (centOS 7.0)
### yum 설치파일 생성
```
vi /etc/yum.repo.d/mongodb-org-4.4.repo
```
```
[mongodb-org-4.4]
name=MongoDB Repository
baseurl=https://repo.mongodb.org/yum/redhat/$releasever/mongodb-org/4.4/x86_64/
gpgcheck=1
enabled=1
gpgkey=https://www.mongodb.org/static/pgp/server-4.4.asc
```
### mongodb-org-4.4 설치
```
sudo yum install -y mongodb-org
```
### mongod.conf 설정
```
vi /etc/mongod.conf
```
```
# mongod.conf

# for documentation of all options, see:
#   http://docs.mongodb.org/manual/reference/configuration-options/

# where to write logging data.
systemLog:
  destination: file
  logAppend: true
  path: /var/log/mongodb/mongod.log

# Where and how to store data.
storage:
  dbPath: /var/lib/mongo
  journal:
    enabled: true
#  engine:
#  wiredTiger:

# how the process runs
processManagement:
  fork: true  # fork and run in background
  pidFilePath: /var/run/mongodb/mongod.pid  # location of pidfile
  timeZoneInfo: /usr/share/zoneinfo

# network interfaces
net:
  port: 27017
  bindIp: 0.0.0.0  # Enter 0.0.0.0,:: to bind to all IPv4 and IPv6 addresses or, alternatively, use the net.bindIpAll setting.


#security:

#operationProfiling:

replication:
  replSetName: replTest

#sharding:

## Enterprise-Only Options

#auditLog:

#snmp:
```
- systemLog:
  - path: /var/log/mongodb/mongod.log
    - 로그 저장 장소로 사용할 위치에 미리 디렉토리 생성 (여기서는 기본값 사용)

- storage:
  - dbPath: /var/lib/mongo
    - DB 저장 장소로 사용할 위치에 미리 디렉토리 생성 (여기서는 기본값 사용)

- net:
  - port: 27017
    - 사용할 DB port번호

  - bindIp: 0.0.0.0
    - 접근 허용할 ip 설정 (0.0.0.0 모든 ip 허용)

- replication:
  - replSetName: replTest
    - DB에서 Replica Set을 구성할 이름 설정


## 2. mongoDB 
### mongoDB 실행
```
mongod --port <port 번호> --config <mongod.conf 경로>
```
mongod.conf에서 설정한 port와 mongod.conf 경로 입력
### mongoDB 접속
#### 로컬 접속
```
mongo --port <port 번호>
```
mongod.conf에서 설정한 port 입력
#### ip주소 접속
```
mongo <ipaddress:port>
```
접속할 ip주소와 port 입력

## 3. replicaSet 구성
### replicaSet 설정
```
rs.conf = {
  _id: <replSetName>,
  members:[
    {_id:0, host:<ipaddress:port>, priority:2},
    {_id:1, host:<ipaddress:port>, priority:1},
    {_id:2, host:<ipaddress:port>, priority:1},
  ]
}
```
- mongod.conf에서 설정한 replSetName 입력
- 연결할 mongoDB 주소, 포트번호 입력
- priority:2 => PRIMARY 서버
  - 해당 서버가 활성화시 자동으로 PRIMARY 복구
- priority:1 => SECOND 
  - PRIMARY 서버 비활성화시 투표를 통해 PRIMARY서버 역할
- priority:0 => 아비터 설정

### replicaSet 실행
```
rs.initiate(rsconf)
```
한 번만 실행해야 함

### replicaSet 서버 추가
```
rs.add(<ipaddress:port>)
```

### replicaSet 아비터 추가
```
rs.addArb(<ipaddress:port>)
```

### replicaSet 서버 삭제
```
rs.remove(<ipaddress:port>)
```
