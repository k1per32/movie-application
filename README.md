# Movie Application

Movie Application — это RESTful API, предоставляющий интерфейс для работы с кинопоиск API и реализует функциональность поиска фильмов, работы с избранным списком фильмов и пр.

## Начало работы

Эти инструкции позволят вам запустить копию проекта на вашем локальном компьютере для разработки и тестирования. Ознакомьтесь с примечаниями по развертыванию проекта на системе в качестве готового к работе продукта.

### Предварительные требования

Перед началом работы убедитесь, что у вас установлены следующие инструменты:

- [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html) версии 1.8 или выше
- [Maven](https://maven.apache.org/) для сборки и управления зависимостями проекта
- [Git](https://git-scm.com/) для клонирования репозитория
- [Docker](https://www.docker.com/) для создания и управления контейнерами (если вы планируете использовать Docker)

### Установка

Для начала необходимо клонировать репозиторий на ваш локальный компьютер:
git clone https://github.com/k1per32/movie-application.git

Перейдите в каталог проекта и запустите сборку при помощи Maven:

-cd movie-application

-mvn clean install

Для запуска приложения выполните команду:

-mvn spring-boot:run

### Запуск в Docker

Запустить приложение в docker compose можно при помощи compose.yaml

### Приложение поддерживает работу в swagger-ui

Для того, чтобы открыть swagger перейдите по url: /swagger-ui.html


