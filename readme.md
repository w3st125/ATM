# ATM
Учебный проект, имитирующий работу банковского сервиса

## Технологии
- Maven
- Spring Boot (Security, Data)
- PostgreSQL
- Lombok
- JUnit
- JWT
- Testcontainers

## Запуск

Для запуска приложения необходимо поднять через docker-compose,
путём ввода команды в консоли:

```bash
docker-compose up
```

## Использование

Примеры использования сервиса указаны в
[example-requests.http](example-requests.http)

## Тестирование

Для тестирования, как и для запуска приложения, необходим запущенный docker daemon