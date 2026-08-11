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

## Module 3 - Basic REST API

- `@RestController`, `@RequestMapping`, `@GetMapping`, `@PostMapping`.
- Nhan du lieu bang `@RequestBody`, `@PathVariable`, `@RequestParam`.
- Dung `ResponseEntity` de chu dong HTTP status.
- Du lieu nhan vien dang luu in-memory de tap trung vao REST truoc khi hoc database.

## Module 4 - Spring Boot + Database (Spring Data JPA)

- Them Spring Data JPA, H2 cho moi truong hoc nhanh va MySQL driver de trien khai that.
- Entity `Employee`, `Department` voi quan he nhieu nhan vien - mot phong ban.
- Repository ke thua `JpaRepository`.
- CRUD nhan vien voi database.
- Tim nhan vien theo ten hoac phong ban bang derived query.

## Module 5 - Validation & Exception Handling

- Bean Validation voi `@NotBlank`, `@Size`, `@Email`.
- Controller dung `@Valid` de kiem tra request truoc khi xu ly.
- `EmployeeNotFoundException` cho truong hop khong tim thay nhan vien.
- `@RestControllerAdvice` xu ly loi tap trung va tra JSON loi ro rang.
- DTO request/response giup tach REST API khoi JPA entity.

## Module 6 - Spring Boot Web (MVC + Thymeleaf)

- Them `spring-boot-starter-thymeleaf`.
- `EmployeeMvcController` minh hoa Controller + Model + View.
- Trang `/employees/list`: xem danh sach va tim kiem theo ten/phong ban.
- Trang `/employees/add`: them nhan vien bang HTML form + data binding.
- Bo sung edit/delete de hoan thien CRUD tren giao dien web.

## Module 7 - Logging & Profiles

- Logging bang SLF4J/Logback khi them, sua, xoa nhan vien.
- Profile `dev`: H2 in-memory de chay ngay khi hoc.
- Profile `prod`: MySQL, lay URL/user/password tu bien moi truong.
- Chon profile bang `SPRING_PROFILES_ACTIVE=dev|prod`.
