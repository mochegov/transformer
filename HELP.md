# Demo project for transforming a list of strings

Each row has a list of possible transformations, which are performed in the specified order.
It is necessary to save the transformation results in the database and retrieve them by ID.

Start Postgres before running the application: environment/docker-compose-local.yaml

### Types of transformation:
* FIND_AND_DELETE Finds substrings using a regular expression and removes them. One parameter is required: a regular expression.
* FIND_AND_REPLACE Finds substrings using a regular expression and replaces them with another string. It takes two parameters: the regular expression and the substring to replace.
* CYRILLIC Finds Cyrillic characters in a string and replaces them with the corresponding English equivalent. No parameters required.

Open API: http://localhost:8181/swagger-ui/index.html


# Демонстрационный проект для трансформации списка строк

Каждая строка содержит список возможных преобразований, которые выполняются в указанном порядке.
Необходимо сохранять результаты преобразования в базе данных и извлекать их по идентификатору.

Запустите Postgres перед запуском приложения: environment/docker-compose-local.yaml

### Типы трансформации:
* FIND_AND_DELETE Находит подстроки с помощью регулярного выражения и удаляет их. Требуется один параметр: регулярное выражение.
* FIND_AND_REPLACE Находит подстроки с помощью регулярного выражения и заменяет их другой строкой. Принимает два параметра: регулярное выражение и заменяемую подстроку.
* CYRILLIC Находит кириллические символы в строке и заменяет их соответствующим английским эквивалентом. Параметры не требуются.

Open API: http://localhost:8181/swagger-ui/index.html
