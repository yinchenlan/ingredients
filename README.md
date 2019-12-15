# Getting Started

## Create User

curl -H "Content-Type: application/json" -X POST -d '{"username": "chucklan","password": "password"}' https://boiling-plains-52145.herokuapp.com/users/sign-up

## Login User
curl -i -H "Content-Type: application/json" -X POST -d '{"username": "chucklan","password": "password"}' https://boiling-plains-52145.herokuapp.com/login

## Process Image
curl -i -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaHVja2xhbiIsImV4cCI6MTU2OTQ2OTMwM30.sbJPftgbgEXLu-ZNudAmW7XpVTmwvrv3ABvWNevrQlOryAk_VtBUgxqplfy2za3mG41he10TXaju7y_h28g9-w" -F file=@/Users/clan/Downloads/peanut.png https://boiling-plains-52145.herokuapp.com/ingredients/process/image
