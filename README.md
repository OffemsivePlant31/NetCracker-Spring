Полезные ссылки
================
1. [Схема базы данных](http://ondras.zarovi.cz/sql/demo/?keyword=nc-project)
2. [Презентация](https://docs.google.com/presentation/d/141Vwb4QCbVjLcWrGPZUKNFRvfbvKO7Vn5zJi5rpHKps/edit#slide=id.g2bc0102b44_0_13)
3. [Диаграмма:Уведомления](http://www.plantuml.com/plantuml/uml/XPD1QyCm38Nl-XKYkso7mdOjfJHjO0nALnYxvUB6QYhcoi5HjkxVpqaBI-YQtSopzCbFBrdKQe8sHmVr1qjbWZdYqf-snbgM4fyU0KoXFk1xJG5Wvq-Oh1oJQ89tQouF-Xo810el6hp391KRFDjSqkrByYkVUt8nLTLJDrvvpuLHzfBB6uLFBYW14ejnqcLSArEuCMv8M9rb5yl16ZKxKnCcsUMKots_7BFqzQ4th7M8a_dmhJZ5RaCmLDkolnyV6q8ZuBolyEl0Zb13GmYtogz9FHCHLfAYzEvh62blOQDkSRfdiQbpo-ykq17MRF8lQysrpleNyvisMzgOpIjKfZxI1bzJX8midSg8Y0bk1ilgGerzGr0CbKinhWk_ZGeMyHJ_WXy0)
4. [Диаграмма:Создание уведомлений](http://www.plantuml.com/plantuml/uml/VOunJyD038Lt_mhhkj212DOEID1ANIfQ2TYwE8cRMhtw8f-9AfGVpmLOa2Y3fIVx-NildqHxCZhZKxhig6r5Exo_0pXAotX8oUjq0Q36as5uosnxlLb6ONMalfy702WE32zi0rl0sKQwew-DMQ59wfOYXgrrfF8nFI-_Tx5ueIMdcZ9ZMFse0dzEBWTfo2KfhXwt9OArnQyQy5S3l7sube0Bp4xcQpeNT7wlSsDI5pbGb9BEhvHFUurCGrbMLGNNG5F6loLnaGlars4EupXEyma0)
4. [Диаграмма:Создание уведомлений 2](http://www.plantuml.com/plantuml/uml/XP2zQWD138HxFONOD4bXY8iqmRWm0HCm9f0sZRoht0dlISUkpZXldxtyW241TuBXcnbDFOgEL6Z0ZPrVA2NH3fVt00SxOtZbSk8Iy6OdNU_Rmgo064szM6vox3bDcTERRfWqD2sHqu4gOtZwkGAm9cns0DbiH6wE7_Y0W-Za5U2F2oxM-0lZ8tRikAM1dxKwQUJw3x5QN18xgRwwHDO0bBqbPgvkoaYnbzOZNGsVnlOGprs_oVRXDQJIYM_tvl8kaLnCx-O1u6k5UO1F)



API
================
* GET <http://178.62.116.170:8080/demo/init> - заполнение базы тестовыми данными
* GET <http://178.62.116.170:8080/demo/instance> - отображение тестовых данных (список экземпляров игр)

### Организация игры

* GET <http://178.62.116.170:8080/game> - список всех шаблонов

### Сопровождение текущей игры

* GET <http://178.62.116.170:8080/organizer/game> - получение информации о текущей игре (описание, параметры)
* GET <http://178.62.116.170:8080/organizer/task> - список задач текущей игры
* GET <http://178.62.116.170:8080/organizer/task/next> - текущая задача команды


* POST <http://178.62.116.170:8080/organizer/start> - создать инстанс игры
* POST <http://178.62.116.170:8080/organizer/validate> - проверить данные игрока