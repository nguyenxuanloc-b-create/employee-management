# Employee Management System - Spring Boot Mini Project

Project học Spring Boot theo 10 module. Mỗi module tương ứng **1 Git commit** để có thể xem lại tiến trình học bằng `git log`.

## Module 1 - Getting Started with Spring Boot

- Khởi tạo Maven project.
- Cấu trúc package cơ bản.
- REST endpoint đầu tiên: `GET /hello`.

### Chạy project

```bash
mvn spring-boot:run
```

Sau đó truy cập:

```text
http://localhost:8080/hello
```

## Module 2 - Custom Bean & IoC

- `UtilityService` duoc Spring quan ly bang `@Service`.
- `PasswordEncoder` la custom bean khai bao trong `@Configuration`.
- Dung constructor injection de inject cac bean vao controller.
- API demo: `/api/tools/sample-code`, `/api/tools/format-name`, `/api/tools/hash`.
