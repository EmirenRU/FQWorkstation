SET client_encoding TO 'UTF8';

Insert into department(code, name ) 
Values ('01', 'Математического моделирования и искусственного интеллекта');

Insert into orientation(code, name) 
Values ('02.03.02', 'Фундаментальная информатика и информационные технологии');
Insert into orientation(code, name) 
Values ('02.04.02', 'Фундаментальная информатика и информационные технологии');
Insert into orientation(code, name) 
Values ('09.03.03', 'Прикладная информатика');
Insert into orientation(code, name) 
Values ('38.03.05', 'Бизнес-информатика');
Insert into orientation(code, name) 
Values ('01.03.02', 'Прикладная математика и информатика');
Insert into orientation(code, name) 
Values ('02.03.01', 'Математика и компьютерные науки');
Insert into orientation(code, name) 
Values ('01.04.02', 'Прикладная математика и информатика');



Insert into lecturer(id, fio, academic_degree, position) 
Values (1, 'Малых Михаил Дмитриевич', 'д.ф.-м.н.', 'доцент');
Insert into lecturer(id, fio, academic_degree, position) 
Values (2, 'Александрова Лариса Валерьевна', '', 'старший преподаватель');
Insert into lecturer(id, fio, academic_degree, position) 
Values (3, 'Виноградов Андрей Николаевич', 'к.ф.-м.н.', 'доцент');
Insert into lecturer(id, fio, academic_degree, position) 
Values (4, 'Молодченков Алексей Игоревич', 'к.т.н.', 'доцент');
Insert into lecturer(id, fio, academic_degree, position) 
Values (5, 'Панкратов Александр Серафимович', 'к.ф.-м.н.', 'доцент');
Insert into lecturer(id, fio, academic_degree, position) 
Values (6, 'Салпагаров Солтан Исмаилович', 'к.ф.-м.н.', 'доцент');
Insert into lecturer(id, fio, academic_degree, position) 
Values (7, 'Стефанюк Вадим Львович', '', 'профессор');
Insert into lecturer(id, fio, academic_degree, position) 
Values (8, 'Фомин Максим Борисович', 'к.ф.-м.н.', 'доцент');
Insert into lecturer(id, fio, academic_degree, position) 
Values (9, 'Хачумов Вячеслав Михайлович', 'д.т.н.', 'профессор');
Insert into lecturer(id, fio, academic_degree, position) 
Values (10, 'Хачумов Михаил Вячеславович', 'к.ф.-м.н.', 'доцент');
Insert into lecturer(id, fio, academic_degree, position) 
Values (11, 'Чеповский Андрей Михайлович', 'д.т.н.', 'профессор');
Insert into lecturer(id, fio, academic_degree, position) 
Values (12, 'Шорохов Сергей Геннадьевич', 'к.ф.-м.н.', 'доцент');
Insert into lecturer(id, fio, academic_degree, position) 
Values (13, 'Андрейчук Антон Андреевич', '', 'ассистент');
Insert into lecturer(id, fio, academic_degree, position) 
Values (14, 'Киселёв Глеб Андреевич', 'к.ф.-м.н.', 'старший преподаватель');
Insert into lecturer(id, fio, academic_degree, position) 
Values (15, 'Кройтор Олег Константинович', '', 'преподаватель');
Insert into lecturer(id, fio, academic_degree, position) 
Values (16, 'Диваков Дмитрий Валентинович', 'к.ф.-м.н.', 'доцент');
Insert into lecturer(id, fio, academic_degree, position) 
Values (17, 'Тютюнник Анастасия Александровна', 'к.ф.-м.н.', 'доцент');
Insert into lecturer(id, fio, academic_degree, position) 
Values (18, 'Севастьянов Леонид Антонович', 'д.ф.-м.н.', 'профессор');
Insert into lecturer(id, fio, academic_degree, position) 
Values (19, 'Ловецкий Константин Петрович', 'к.ф.-м.н.', 'доцент');
Insert into lecturer(id, fio, academic_degree, position) 
Values (20, 'Виницкий Сергей Ильич', 'д.ф.-м.н.', 'профессор');
Insert into lecturer(id, fio, academic_degree, position) 
Values (21, 'Белов Александр Александрович', 'д.ф.-м.н.', 'доцент');
Insert into lecturer(id, fio, academic_degree, position) 
Values (22, 'Хохлов Алексей Анатольевич', 'к.ф.-м.н.', 'старший преподаватель');
Insert into lecturer(id, fio, academic_degree, position) 
Values (23, 'Васильев Сергей Анатольевич', 'к.ф.-м.н', 'доцент');
Insert into lecturer(id, fio, academic_degree, position) 
Values (24, 'Мамонов Антон Алексеевич', 'аспирант', 'ассистент');

Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (1, 'Адебайо Ридвануллахи Айофе', 1032205020, 'НИГЕРИЯ', 'Обучение лингвистической модели путем задания контекста', '02.03.01', '01', 'Бакалавриат', 'ПИ+П' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (2, 'Габриэль Тьерри', 1032204249, 'ГАИТИ', 'Обучение нейронных сетей для аппроксимации решений краевых задач с применением неклассических вариационных формулировок', '02.03.01', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (3, 'Ду Нашсименту Висенте Феликс Жозе', 1032199092, 'САН-ТОМЕ И ПРИНСИПИ', 'Приложения метода коллокаций', '02.03.01', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (4, 'Еленга Невлора Люглеш', 1032205073, 'КОНГО', 'Анализ экономической динамики стран Центральной Африки с применением методов машинного обучения', '02.03.01', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (5, 'Каймакджыоглу Мерич Дорук', 1032204917, 'ТУРЦИЯ', 'Разработка методов анализа слабо структурированных данных с применением методов машинного обучения', '02.03.01', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (6, 'Мухамедияр Адиль', 1032205725, 'КАЗАХСТАН', 'Стратегия управления полетом квадрокоптера для выполнения заданных маневров', '02.03.01', '01', 'Бакалавриат', 'ПИ+ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (7, 'Тейшейра Боа Морте Селмилтон', 1032199094, 'САН-ТОМЕ И ПРИНСИПИ', 'Разложение по многочленам Чебышева', '02.03.01', '01', 'Бакалавриат', 'ФИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (8, 'Шестаков Дмитрий Сергеевич', 1032201675, 'РОССИЯ', 'Анализ и прогнозирование временных рядов методом SSA', '02.03.01', '01', 'Бакалавриат', 'ФИ+Р' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (9, 'Эттеев Сулейман Хаджимуратович', 1032201677, 'РОССИЯ', 'Сравнительный анализ методов прогнозирования временных рядов', '02.03.01', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (10, 'Яссин Мохамад Аламин', 1032205004, 'СИРИЯ', 'Оценки риска использование языковых моделей для формирования вредоносный запросов', '02.03.01', '01', 'Бакалавриат', 'ПИ+ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (12, 'Байрамгельдыев Довлетмурат', 1032207470, 'ТУРКМЕНИСТАН', 'Стратегии управления финансовыми рисками на основе генеративно-состязательных сетей', '02.03.02', '01', 'Бакалавриат', 'ПИ+ПP' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (13, 'Брезгулевский Иван Алексеевич', 1032203965, 'РОССИЯ', 'Методы и подходы к исследованию изменения курса ценных бумаг', '02.03.02', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (14, 'Ван И', 1032198069, 'КИТАЙ', 'Методы кластеризации в Machine Learning', '02.03.02', '01', 'Бакалавриат', 'ПИ+П' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (15, 'Го Чаопэн', 1032194919, 'КИТАЙ', 'Исследование и применение современных методов решения задачи планирования траекторного движения беспилотных летательных аппаратов', '02.03.02', '01', 'Бакалавриат', 'ПИ+П' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (16, 'Голощапова Ирина Борисовна', 1032201666, 'РОССИЯ', 'Исследование методов и разработка алгоритмов поиска аномалий (фродов) в банковских операциях', '02.03.02', '01', 'Бакалавриат', 'ПИ+ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (17, 'Егорова Диана Витальевна', 1032201662, 'РОССИЯ', 'Исследование, реализация и сравнение моделей популяции паразитических организмов', '02.03.02', '01', 'Бакалавриат', 'ФИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (18, 'Ильин Андрей Владимирович', 1032201656, 'РОССИЯ', 'Разработка механизма распознавания заболеваний опорно-двигательного аппарата с помощью методов глубокого обучения', '02.03.02', '01', 'Бакалавриат', 'ПИ+ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (19, 'Казакова Виктория Алексеевна', 1032201659, 'РОССИЯ', 'Разработка интерфейсного модуля интерактивного создания правил', '02.03.02', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (20, 'Ким Михаил Алексеевич', 1032201664, 'РОССИЯ', 'Использование генеративно- состязательной нейронной сети для генерации субъективно привлекательных лиц людей', '02.03.02', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (21, 'Кузнецов Юрий Владимирович', 1032200533, 'РОССИЯ', 'Разработка модуля получения и анализа данных с носимых гаджетов, записываемых в облачные решения (на примере Google fit)', '02.03.02', '01', 'Бакалавриат', 'ПИ+ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (22, 'Логинов Егор Игоревич', 1032201661, 'РОССИЯ', 'Реализация и исследование моделей поведения кооперирующихся агентов', '02.03.02', '01', 'Бакалавриат', 'ПИ+ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (23, 'Любимов Дмитрий Андреевич', 1032200538, 'РОССИЯ', 'Применение методов активного обучения для разметки текстов, содержащих негативные новости о контрагентах', '02.03.02', '01', 'Бакалавриат', 'ПИ+П' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (24, 'Ндри Ив Алла Ролан', 1032205400, 'КОТ ДИВУАР', 'Определение болезни Альцгеймера по МРТ снимкам', '02.03.02', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (25, 'Низамова Альфия Айдаровна', 1032201670, 'РОССИЯ', 'Обучение с подкреплением в анализе медицинских изображений', '02.03.02', '01', 'Бакалавриат', 'ПИ+П' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (26, 'Новосельцев Данила Сергеевич', 1032206559, 'РОССИЯ', 'Предиктивная аналитика данных', '02.03.02', '01', 'Бакалавриат', 'ФИ+Р' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (27, 'Носов Артём Алексеевич', 1032200537, 'РОССИЯ', 'Интерактивная маска - распознавание и моделирование мимики', '02.03.02', '01', 'Бакалавриат', 'ПИ+П' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (28, 'Пиняева Анна Андреевна', 1032202458, 'РОССИЯ', 'Исследование методов обработки снимков магнитно-резонансной томографии для анализа зон интереса', '02.03.02', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (29, 'Прокошев Никита Евгеньевич', 1032202460, 'РОССИЯ', 'Анализ профилей пользователей', '02.03.02', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (30, 'Саттарова Вита Викторовна', 1032201655, 'РОССИЯ', 'Исследование современных подходов к решению задачи распознавания жестов', '02.03.02', '01', 'Бакалавриат', 'ПИ+П' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (31, 'Севастьянов Дмитрий Вадимович', 1032200536, 'РОССИЯ', 'Разработка интерфейса системы сбора информации о состоянии здоровья людей и отображения результатов ее обработки с применением Web-технологий', '02.03.02', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (32, 'Сергеев Тимофей Сергеевич', 1032201669, 'РОССИЯ', 'Построение электронной автоматизированной системы контроля банковских операций и управления персоналом банка', '02.03.02', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (33, 'Серегин Денис Алексеевич', 1032201665, 'РОССИЯ', 'Прогнозирование цен на рынке стального проката с помощью методов машинного обучения', '02.03.02', '01', 'Бакалавриат', 'ПИ+ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (34, 'Соболевский Денис Андреевич', 1032201668, 'РОССИЯ', 'Разработка интерфейсного модуля тестирования разрабатываемых баз знаний на основе неоднородных семантических сетей', '02.03.02', '01', 'Бакалавриат', 'ПИ+П' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (35, 'Тарусов Артём Сергеевич', 1032201667, 'РОССИЯ', 'Применение методов машинного обучения в управлении портфелем ценных бумаг', '02.03.02', '01', 'Бакалавриат', 'ПИ+ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (36, 'Чекалова Лилия Руслановна', 1032201654, 'РОССИЯ', 'Исследование методов анализа графического интернет-контента пользователей с целью выявления личностных черт', '02.03.02', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (37, 'Чупрына Петр Петрович', 1032183377, 'РОССИЯ', 'Проектирование и разработка информационной системы для обеспечения практики студентов', '02.03.02', '01', 'Бакалавриат', 'ПИ+ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (38, 'Шалыгин Георгий Эдуардович', 1032202456, 'РОССИЯ', 'Анализ ассоциативных правил в задачах ритейла', '02.03.02', '01', 'Бакалавриат', 'ПГ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (39, 'Юрченко Артём Алексеевич', 1032201660, 'РОССИЯ', 'Обучение с подкреплением для управления портфелем ценных бумаг', '02.03.02', '01', 'Бакалавриат', 'ПИ+ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (11, 'Бабков Дмитрий Николаевич', 1032201726, 'РОССИЯ', 'Исследование методов планирования маршрута движения робототехнических систем в среде с препятствиями', '02.03.02', '01', 'Бакалавриат', 'ПИ+П' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (40, 'Алхатиб Осама', 1032209334, 'СИРИЯ', 'Методы анализа временных рядов', '09.03.03', '01', 'Бакалавриат', 'ПИ+ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (41, 'Аникин Константин Сергеевич', 1032201736, 'РОССИЯ', 'Нейросетевая аппроксимация движения непотенциальных динамических систем', '09.03.03', '01', 'Бакалавриат', 'ПИ+П' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (42, 'Афтаева Ксения Васильевна', 1032201739, 'РОССИЯ', 'Построение интегрированной системы, объединяющей базы данных складских терминалов разных собственников', '09.03.03', '01', 'Бакалавриат', 'ПИ+ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (43, 'Бакулин Никита Алексеевич', 1032201747, 'РОССИЯ', 'Предиктивная аналитика данных в задачах утилизации вычислительных ресурсов', '09.03.03', '01', 'Бакалавриат', 'ФИ+ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (44, 'Боровикова Карина Владимировна', 1032201748, 'РОССИЯ', 'Выделение псевдооснов слов', '09.03.03', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (45, 'Гаглоев Олег Мелорович', 1032201347, 'РОССИЯ', 'Разработка web-приложения для работы с базами данных кафедры', '09.03.03', '01', 'Бакалавриат', 'ПИ+ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (46, 'Губина Ольга Вячеславовна', 1032201737, 'РОССИЯ', 'Анализ корпусов литературных текстов', '09.03.03', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (47, 'Колосова Кристина Александровна', 1032201738, 'РОССИЯ', 'Разработка приложения для сбора и анализа информации по обучающимся для выбора стиля обучения', '09.03.03', '01', 'Бакалавриат', 'ПИ+ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (48, 'Краснова Диана Владимировна', 1032201743, 'РОССИЯ', 'Работа с изображениями с помощью сверточных нейронных сетей', '09.03.03', '01', 'Бакалавриат', 'ПИ+ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (49, 'Максимов Алексей Александрович', 1032201201, 'РОССИЯ', 'Исследование методов определения опухоли мозга на МРТ снимках', '09.03.03', '01', 'Бакалавриат', 'ПИ+П' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (50, 'Медведева Кристина Андреевна', 1032206562, 'РОССИЯ', 'Задача анализа эффективности сотрудников предприятия', '09.03.03', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (51, 'Мухтарова Камила Айратовна', 1032203686, 'РОССИЯ', 'Применение технологий машинного обучения в задачах EdTech', '09.03.03', '01', 'Бакалавриат', 'ПИ+ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (52, 'Никулин Максим Геннадьевич', 1032196222, 'РОССИЯ', 'Методы определений активности человека по данным из мобильных устройств', '09.03.03', '01', 'Бакалавриат', 'ПИ+ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (53, 'Оулед Салем Яссин', 1032204121, 'ТУНИС', 'Математическое моделирование экономической динамики с применением методов машинного обучения', '09.03.03', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (54, 'Юсупов Шухратджон Фирдавсович', 1032205329, 'ТАДЖИКИСТАН', 'Сбор статистически значимой информации о большой лингвистической модели', '09.03.03', '01', 'Бакалавриат', 'ФИ+ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (55, 'Акопян Изабелла Арменовна', 1032203961, 'РОССИЯ', 'Сравнение иволютивных и классических алгоритмов вычисления базисов Грёбнера полиномиальных идеалов.', '38.03.05', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (56, 'Джумаев Бегенч', 1032204350, 'ТУРКМЕНИСТАН', 'Разработка многомерной информационной системы для анализа статистики правонарушений', '38.03.05', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (57, 'Иванов Никита', 1032204218, 'КАЗАХСТАН', 'Развитие интеллектуальных систем управления транспортными потоками на основе данных IoT', '38.03.05', '01', 'Бакалавриат', 'ПИ+П' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (58, 'Кайкы Руслан', 1032205289, 'МОЛДОВА', 'Анализ ЕЯ-текстов с применением нейронных сетей', '38.03.05', '01', 'Бакалавриат', 'ПИ+П' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (59, 'Лапасов Зелимхан Абитханович', 1132210643, 'РОССИЯ', 'Построение электронной автоматизированной системы учёта успеваемости студентов в вузе', '38.03.05', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (60, 'Лисягин Евгений Алексеевич', 1032200527, 'РОССИЯ', 'Разработка многомерной информационной системы для анализа работы службы экстренных вызовов', '38.03.05', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (61, 'Лукашов Никита Александрович', 1032201195, 'РОССИЯ', 'Использование многомерной информационной системы для анализа деятельности логистической компании', '38.03.05', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (62, 'Танаков Артём Алексеевич', 1032200526, 'РОССИЯ', 'Разработка многомерной информационной системы для анализа деятельности компании по продаже электроники', '38.03.05', '01', 'Бакалавриат', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (63, 'Черкасов Алексей Сергеевич', 1032156578, 'РОССИЯ', 'Разработка методики анализа финансового состояния компаний с использованием методов машинного обучения', '38.03.05', '01', 'Бакалавриат', 'ПИ+Р' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (64, 'Кочетов Андрей Владимирович', 1132223503, 'РОССИЯ', 'Проектирование оптических покрытий', '01.04.02', '01', 'Магистратура', 'ПИ+П' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (65, 'Хохлачева Яна Дмитриевна', 1132223501, 'РОССИЯ', 'Применение методов машинного обучения к медицинским данным', '01.04.02', '01', 'Магистратура', 'ПИ+ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (66, 'Адьяту Ибрайма Коллаволе Топе', 1032239106, 'НИГЕРИЯ', 'Разработка системы управления релизами программного обеспечения', '02.04.02', '01', 'Магистратура', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (67, 'Архипов Данила Алексеевич', 1132223453, 'РОССИЯ', 'Разработка многомерной информационной системы для анализа деятельности мотоклуба', '02.04.02', '01', 'Магистратура', 'ПИ+ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (68, 'Виноградова Варвара Станиславовна', 1132223449, 'РОССИЯ', 'Разработка многомерной информационной системы для анализа ИТ- инфраструктуры компании', '02.04.02', '01', 'Магистратура', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (69, 'Гаджиев Нурсултан Тофик оглы', 1132223454, 'РОССИЯ', 'Исследование методов и разработка алгоритмов для прогнозирования изменения цен и объемов продаж на бирже', '02.04.02', '01', 'Магистратура', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (70, 'Гафиров Абдималик Абдифаридович', 1132223465, 'РОССИЯ', 'Анализ медицинских данных', '02.04.02', '01', 'Магистратура', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (71, 'Греков Максим Сергеевич', 1132223456, 'РОССИЯ', 'Разработка распределенной системы совершения торговых операций на фондовом рынке с применением нейросетевых моделей', '02.04.02', '01', 'Магистратура', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (72, 'Гурбангельдиев Мухаммет Гурбангельдиевич', 1132223468, 'РОССИЯ', 'Проектирование и разработка информационной системы для обеспечения учебно-методической работы кафедры', '02.04.02', '01', 'Магистратура', 'ПИ+П' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (73, 'Дяченко Злата Константиновн а', 1132223497, 'РОССИЯ', 'Нейронные сети на основе биномиального дерева для прогнозирования стоимости нелинейных деривативов', '02.04.02', '01', 'Магистратура', 'ПИ+Р' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (74, 'Исмит Шаманта', 1032215448, 'БАНГЛАДЕШ', 'Исследование методов и алгоритмов поиска аномалий в загрузке телекоммуникационных сетях', '02.04.02', '01', 'Магистратура', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (75, 'Каба Диафоде Диаси', 1032215785, 'ГВИНЕЯ', 'Построение аналитической системы многокритериального контроля работы преподавателей в высших учебных заведениях', '02.04.02', '01', 'Магистратура', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (76, 'Казанкин Никита Вячеславович', 1132223462, 'РОССИЯ', 'Разработка и реализация электронной системы управления потоком задач', '02.04.02', '01', 'Магистратура', 'ПИ+П' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (77, 'Каримов Зуфар Исматович', 1032185247, 'ТАДЖИКИСТАН', 'Разработка программного комплекса для подготовки документов в патентный отдел', '02.04.02', '01', 'Магистратура', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (78, 'Логинов Сергей Андреевич', 1132223455, 'РОССИЯ', 'Построение кросс-языковых эмбедингов для извлечения химических структур из текстов на русском и английском языках', '02.04.02', '01', 'Магистратура', 'ПИ+П' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (79, 'Ндемесого Ндонг Висенте Нкулу', 1032215715, 'ЭКВАТОРИАЛЬНАЯ ГВИНЕЯ', 'Проектирование и реализация прикладной системы, сопровождающей учебный процесс в школах Экваториальной Гвинеи', '02.04.02', '01', 'Магистратура', 'ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (80, 'Нилой Шоумитра Басак', 1032165446, 'БАНГЛАДЕШ', 'Особенности баз знаний для динамических интеллектуальных систем', '02.04.02', '01', 'Магистратура', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (81, 'Пологов Владислав Александрович', 1132223464, 'РОССИЯ', 'Разработка и исследование подходов к решению задачи траекторного движения робототехнических систем в динамической среде', '02.04.02', '01', 'Магистратура', 'ПИ+П' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (82, 'Сасин Ярослав Игоревич', 1132223471, 'РОССИЯ', 'Математическое моделирование процессов управления при создании и сопровождении цифровых продуктов при наличии рисков', '02.04.02', '01', 'Магистратура', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (83, 'Семёнов Юрий Анатольевич', 1132223457, 'РОССИЯ', 'Нейросетевое распознавание музыкальных фрагментов', '02.04.02', '01', 'Магистратура', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (84, 'Слейман Хади', 1032229137, 'ЛИВАН', 'Моделирование распространения волноводных мод в нерегулярных волноводах', '02.04.02', '01', 'Магистратура', 'ФИ+Р' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (85, 'Сурков Алексей Алексеевич', 1032212314, 'РОССИЯ', 'Использование технологии баз данных для моделирования потребности в рабочем персонале розничной торговой сети', '02.04.02', '01', 'Магистратура', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (86, 'Феррейра Эдуарду Эштевау Кауайя', 1032225107, 'АНГОЛА', 'Исследование способов формального представления бизнес-процессов и методов оценки качества алгоритмов их автоматического синтеза', '02.04.02', '01', 'Магистратура', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (87, 'Шувалов Николай Константинович', 1132223469, 'РОССИЯ', 'Проектирование и реализация электронного приложения, сопровождающего работу Министерства по чрезвычайным ситуациям (МЧС)', '02.04.02', '01', 'Магистратура', 'ПИ' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (88, 'Яковлев Артём Александрович', 1132223463, 'РОССИЯ', 'Использование многомерной информационной системы для анализа рынка банковского кредитования', '02.04.02', '01', 'Магистратура', 'ПИ+ПР' );
Insert into student(id, fio, stud_num, citizenship, theme, orientation_code, department_code, loe, classifier ) 
Values (89, 'Яхёев Азизжон Азим угли', 1132223472, 'УЗБЕКИСТАН', 'Исследование методов и алгоритмов автоматического перевода текстов', '02.04.02', '01', 'Магистратура', 'ПИ' );

INSERT INTO student_lecturers (student_id, lecturer_id, is_consultant, is_scientific_supervisor) VALUES
                                                                                                  (1, 6, FALSE, TRUE),
                                                                                                  (2, 12, FALSE, TRUE),
                                                                                                  (3, 19, FALSE, TRUE),
                                                                                                  (4, 23, FALSE, TRUE),
                                                                                                  (5, 23, FALSE, TRUE),
                                                                                                  (6, 12, FALSE, TRUE),
                                                                                                  (7, 19, FALSE, TRUE),
                                                                                                  (8, 16, FALSE, TRUE),
                                                                                                  (9, 17, FALSE, TRUE),
                                                                                                  (10, 6, FALSE, TRUE),
                                                                                                  (12, 12, FALSE, TRUE),
                                                                                                  (13, 4, FALSE, TRUE),
                                                                                                  (14, 22, FALSE, TRUE),
                                                                                                  (15, 10, FALSE, TRUE),
                                                                                                  (16, 4, FALSE, TRUE),
                                                                                                  (17, 6, FALSE, TRUE),
                                                                                                  (18, 14, FALSE, TRUE),
                                                                                                  (19, 4, FALSE, TRUE),
                                                                                                  (20, 10, FALSE, TRUE),
                                                                                                  (21, 4, FALSE, TRUE),
                                                                                                  (22, 6, FALSE, TRUE),
                                                                                                  (23, 4, FALSE, TRUE),
                                                                                                  (24, 4, FALSE, TRUE),
                                                                                                  (25, 14, FALSE, TRUE),
                                                                                                  (26, 16, FALSE, TRUE),
                                                                                                  (27, 3, FALSE, TRUE),
                                                                                                  (28, 10, FALSE, TRUE),
                                                                                                  (29, 11, FALSE, TRUE),
                                                                                                  (30, 10, FALSE, TRUE),
                                                                                                  (31, 4, FALSE, TRUE),
                                                                                                  (32, 5, FALSE, TRUE),
                                                                                                  (33, 12, FALSE, TRUE),
                                                                                                  (34, 4, FALSE, TRUE),
                                                                                                  (35, 12, FALSE, TRUE),
                                                                                                  (36, 10, FALSE, TRUE),
                                                                                                  (37, 6, FALSE, TRUE),
                                                                                                  (38, 16, FALSE, TRUE),
                                                                                                  (39, 12, FALSE, TRUE),
                                                                                                  (11, 10, FALSE, TRUE),
                                                                                                  (40, 22, FALSE, TRUE),
                                                                                                  (41, 12, FALSE, TRUE),
                                                                                                  (42, 5, FALSE, TRUE),
                                                                                                  (43, 16, FALSE, TRUE),
                                                                                                  (44, 11, FALSE, TRUE),
                                                                                                  (45, 6, FALSE, TRUE),
                                                                                                  (46, 11, FALSE, TRUE),
                                                                                                  (47, 3, FALSE, TRUE),
                                                                                                  (48, 22, FALSE, TRUE),
                                                                                                  (49, 4, FALSE, TRUE),
                                                                                                  (50, 3, FALSE, TRUE),
                                                                                                  (51, 3, FALSE, TRUE),
                                                                                                  (52, 4, FALSE, TRUE),
                                                                                                  (53, 23, FALSE, TRUE),
                                                                                                  (54, 6, FALSE, TRUE),
                                                                                                  (55, 6, FALSE, TRUE),
                                                                                                  (56, 8, FALSE, TRUE),
                                                                                                  (57, 3, FALSE, TRUE),
                                                                                                  (58, 3, FALSE, TRUE),
                                                                                                  (59, 5, FALSE, TRUE),
                                                                                                  (60, 8, FALSE, TRUE),
                                                                                                  (61, 8, FALSE, TRUE),
                                                                                                  (62, 8, FALSE, TRUE),
                                                                                                  (63, 23, FALSE, TRUE),
                                                                                                  (64, 22, FALSE, TRUE),
                                                                                                  (65, 22, FALSE, TRUE),
                                                                                                  (66, 3, FALSE, TRUE),
                                                                                                  (67, 8, FALSE, TRUE),
                                                                                                  (68, 8, FALSE, TRUE),
                                                                                                  (69, 4, FALSE, TRUE),
                                                                                                  (70, 16, FALSE, TRUE),
                                                                                                  (71, 10, FALSE, TRUE),
                                                                                                  (72, 6, FALSE, TRUE),
                                                                                                  (73, 12, FALSE, TRUE),
                                                                                                  (74, 4, FALSE, TRUE),
                                                                                                  (75, 5, FALSE, TRUE),
                                                                                                  (76, 5, FALSE, TRUE),
                                                                                                  (77, 6, FALSE, TRUE),
                                                                                                  (78, 4, FALSE, TRUE),
                                                                                                  (79, 5, FALSE, TRUE),
                                                                                                  (80, 4, FALSE, TRUE),
                                                                                                  (81, 10, FALSE, TRUE),
                                                                                                  (82, 23, FALSE, TRUE),
                                                                                                  (83, 3, FALSE, TRUE),
                                                                                                  (84, 17, FALSE, TRUE),
                                                                                                  (85, 5, FALSE, TRUE),
                                                                                                  (86, 4, FALSE, TRUE),
                                                                                                  (87, 5, FALSE, TRUE),
                                                                                                  (88, 8, FALSE, TRUE),
                                                                                                  (89, 4, FALSE, TRUE);