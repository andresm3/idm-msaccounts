# idm-msaccounts
ms que gestiona CRUD de accounts
Internamente para la creacion de Cuentas se comunica con idm-mscredits, validando 
que el cliente ya tiene un credito creado, ademas que el numero de cuenta no exista y otras reglas de negocio.


# Environment Variables
agregar environment variables a MscreditsApplication

```
MONGODB_DATABASE=db;MONGODB_URL=mongodb+srv://user:pwd@cluster/?retryWrites=true&w=majority;MS_CREDITS_URL=http://localhost/
```


# Endpoint creacion de Cuentas

```
curl --location 'localhost:8081/accounts/create' \
--header 'Content-Type: application/json' \
--data '{
    "debitCard": "4242212123232010",
    "number": "1017",
    "client": {
        "id": "64b8c582f621885376bddce5",
        "documentType": "DNI",
        "documentNumber": "45454656",
        "firstName": "Miguel",
        "lastName": "Grau",
        "type": 1,
        "profile": 2,
        "active": 1
    },
    "typeAccount": {
        "option": 1,
        "maxTransactions":3,
        "day":5
    },
    "balance": 10000,
    "status": true
}'
```