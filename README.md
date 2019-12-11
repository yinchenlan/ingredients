# Getting Started

## Create User

curl -H "Content-Type: application/json" -X POST -d '{"username": "chucklan","password": "password"}' http://localhost:8080/users/sign-up

## Login User
curl -i -H "Content-Type: application/json" -X POST -d '{"username": "chucklan","password": "password"}' http://localhost:8080/login

## Process Image
curl -i -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaHVja2xhbiIsImV4cCI6MTU2OTQ2OTMwM30.sbJPftgbgEXLu-ZNudAmW7XpVTmwvrv3ABvWNevrQlOryAk_VtBUgxqplfy2za3mG41he10TXaju7y_h28g9-w" -F file=@/Users/clan/Downloads/peanut.png http://localhost:8080/ingredients/process/image

https://boiling-plains-52145.herokuapp.com

curl -H "Content-Type: application/json" -X POST -d '{"username": "chucklan","password": "password"}' https://boiling-plains-52145.herokuapp.com/users/sign-up

curl -i -H "Content-Type: application/json" -X POST -d '{"username": "chucklan","password": "password"}' https://boiling-plains-52145.herokuapp.com/login

curl -i -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaHVja2xhbiIsImV4cCI6MTU2OTQ2OTc3OH0.r2ZmNgSS2HBOGFdqes-hLwC58dK_BhH-iRnssDQVQbk_BJzjQTnd2bCXhYi4BqvNPjC67pXJnXMBT1eVHyQchw" -F file=@/Users/clan/Downloads/nutritionlabel_02.jpg https://boiling-plains-52145.herokuapp.com/ingredients/process/image
