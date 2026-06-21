# Генератор паролей

Android-приложение для генерации одного или нескольких паролей по выбранным настройкам.

## Возможности

- выбор длины пароля от 6 до 20 символов, значение по умолчанию — 12;
- выбор количества паролей от 1 до 15;
- включение строчных букв, заглавных букв, цифр и специальных символов `!@#$%^&*`;
- генерация списка паролей по нажатию кнопки;
- сообщение об ошибке, если не выбран ни один тип символов.

## Структура

- `app/src/main/java/com/example/generatorparoley/MainActivity.java` — программно созданный Android UI;
- `app/src/main/java/com/example/generatorparoley/PasswordGenerator.java` — логика генерации и валидации;
- `app/src/test/java/com/example/generatorparoley/PasswordGeneratorSmokeTest.java` — простой smoke-тест ядра генератора.
