# Книжный Магазин

## Оглавление

1. [Информация](#Информация)
2. [Задачи](#Задачи)
3. [Инструкция](#Инструкция)

## Информация

[:arrow_up:Оглавление](#Оглавление)

## Задачи

:white_check_mark: Это уже сделано    
:negative_squared_cross_mark: Я не буду это делать    
:black_square_button: делать или не делать, вот в чем вопрос? 

:white_check_mark: Модуль  «Лента новостей» 
:white_check_mark: Модуль «Обратная связь»  
:black_square_button: Модуль  «Список дел» (дополнительное)   
:white_check_mark: Модуль  «Регистрация/авторизация»    
:white_check_mark: Модуль  «Галерея»    
:white_check_mark:  Система  управления содержимым сайта          

[:arrow_up:Оглавление](#Оглавление)

## Инструкция

1. Установка java 8 или выше 

2. В проекте в  **src/main/resources** есть файл **application.properties** в нём изменяем следующее 

   ```properties
   #для MySQL 
   spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/(имя бд)
   spring.datasource.username=(имя пользователя)
   spring.datasource.password=(пароль пользователя)
   ...
   upload.path=(полный путь к папке для сохранения изоброжений)
   #при необходимости меням порт в
   server.port=(прот)
   ```

3. Стартовый файл **StoreApplication** находится по следующему адресу **src/main/java/com/store**     

  [:arrow_up:Оглавление](#Оглавление)