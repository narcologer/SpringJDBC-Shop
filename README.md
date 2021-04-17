Examples of curl requests:

curl localhost:8080/register?login=l&pass=p
Register user with login l and password p. He will also be given $8.

curl localhost:8080/login?login=l&pass=p
Login this user in system. (For simplicity, it is assumed that his login is also a token)

curl localhost:8080/payment?logout=l
Logout this user off system.

curl localhost:8080/payment?login=l
Decreases this user's equity by 1.1.

curl localhost:8080/viewreg
View all registered users's logins and their equities.
