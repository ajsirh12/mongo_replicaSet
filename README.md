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
