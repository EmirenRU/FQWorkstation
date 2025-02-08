import  { useState, useEffect} from 'react';
import { saveInputs } from './Save';

import { useFormContext } from '../context';


function jsonToData(data: string): object {
    return JSON.parse(data);
}

function getLocalData(name: string) {
    const data = localStorage.getItem(name);
    return data ? jsonToData(data) : [];
    
}



export const  LoadSaved = () =>  {
    const [orientationData, setOrientationData] = useState<string[]>([]);
    const [themesData, setThemesData] = useState<string[]>([]);
    const [departmentData, setDepartmentData] = useState<string[]>([]);
    const [lecturerData, setLecturerData] = useState<string[]>([]);
    const [fromData, setFromData] = useState('');
    const [tillData, setTillData] = useState('');
    const { setFormData } = useFormContext();
    


    interface DataItem {
        name: string;
        value: string[];
    }

    interface DataObject {
        [key: string]: DataItem;
    }



    useEffect(() => {
        const data = getLocalData('Restore data') as DataObject;
        let yearEntry;
        const filteredData = Object.entries(data);
        console.log("here", filteredData);
        for (const entry of filteredData) {
            const entryVal: string[] = entry[1].value;
            const entryName = entry[1].name;
            console.log("To save", entryVal)
            switch (entryName) {
                case 'orientation':
                    setOrientationData(entryVal);
                    saveInputs(entryName, entryVal,"restore");
                    break;
                case 'department':
                    setDepartmentData(entryVal);
                    saveInputs(entryName, entryVal,"restore");
                    break;
                case 'themes':
                    setThemesData(entryVal);
                    saveInputs(entryName, entryVal,"restore");
                    break;
                case 'lecturer':
                    setLecturerData(entryVal);
                    saveInputs(entryName, entryVal,"restore");
                    break;
                case "from":
                    yearEntry = Object.entries(entryVal);
                    console.log("Year entry",yearEntry[0][1])
                    setFromData(yearEntry[0][1]);
                    saveInputs(entryName, entryVal, "restore");
                break;
                case "to":
                    yearEntry = Object.entries(entryVal);
                    setTillData(yearEntry[0][1]);
                    saveInputs(entryName, entryVal, "restore");
                break;
            }
        }
    }, []);




    const handleYearsChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const value = event.target.value;
        const  {name}  = event.target;
        switch (name) {
            case 'from':
                setFromData(value);
                saveInputs(name, {value});
                break;
            case 'to':
                setTillData(value);
                saveInputs(name, {value});
                break;
        }

    }




    const handleInputChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        const options = event.target.options;
        const { name } = event.target;
        const selectedValues = Array.from(options)
            .filter(option => option.selected)
            .map(option => option.value);
        console.log("Selected values", selectedValues);

        switch (name) {
            case 'orientation':
                setOrientationData(selectedValues);
                saveInputs(name, selectedValues);

                break;
            case 'department':
                setDepartmentData(selectedValues);
                saveInputs(name, selectedValues);
                console.log(typeof setFormData)

                break;
            case 'themes':
                setThemesData(selectedValues);
                saveInputs(name, selectedValues);

                break;
            case 'lecturer':
                setLecturerData(selectedValues);
                saveInputs(name, selectedValues);

                break;
        }
    };

    const handleClick = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        e.preventDefault;
        const objectToParse = {
            orientation: orientationData,
            department: departmentData,
            from: fromData,
            till: tillData, 
            themes: themesData,
            lecturer: lecturerData
        }
        setFormData((prev) => ({ ...prev, ...objectToParse }));
    }

    return(<>

        <div className="selector-patch">
                           <label className="selection-param">Направление
                           <select   name="orientation"  className="selectpicker"  onChange={handleInputChange}  id="orientation" multiple data-live-search="true" value={orientationData}>
                                   <option value="-1"> Выберите Направление</option>
                                   <option value="02.03.02">02.03.02 Фундаментальная информатика и информационные технологии</option>
                                   <option value="02.04.02">02.04.02 Фундаментальная информатика и информационные технологии</option>
                                   <option value="09.03.03">09.03.03 Прикладная информатика</option>
                                   <option value="38.03.05">38.03.05 Бизнес-информатика</option>
                                   <option value="01.03.02">01.03.02 Прикладная математика и информатика</option>
                                   <option value="02.03.01">02.03.01 Математика и компьютерные науки</option>
                                   <option value="01.04.02">01.04.02 Прикладная математика и информатика</option>
                               </select>
                           </label>
                       </div>
                       <div className="selector-patch">
                                <label className="selection-param">Кафедра
                                    <select name="department" className="selectpicker" multiple  onChange={handleInputChange} id="department" value={departmentData}>
                                        <option value="-1">
                                            Выберите кафедру
                                        </option>
                                        <option value="1">Математического моделирования и искусственного интеллекта</option>
                                    </select>
                                </label>
                            </div>
                        <div className="selector-patch-2">
                            <label className="selection-param">Годы работ
                                <div className="selection-param__years flex">
                                    <span className="selection-param__years__text" >с</span>
                                    <input type="text" name="from" id = "from" className="val" value={fromData} onChange={handleYearsChange}/>
                                    <span className="selection-param__years__text" > до</span>
                                    <input type="text" name="to" id = "to" className="val" value={tillData} onChange={handleYearsChange}/>
                                </div>
                            </label>
                        </div>
                        <div className="selector-patch-2">
                                <label className="selection-param">Тема
                                    <select className="selectpicker" name="themes" data-placeholder="Select or type to search" onChange={handleInputChange}  id="themes" multiple data-live-search="true" value={themesData}>
                                    <option value="-1" disabled>Выберите тему: </option>
                                <option value="1">Обучение лингвистической модели путем задания контекста</option>
                                <option value="2">Обучение нейронных сетей для аппроксимации решений краевых задач с применением неклассических вариационных формулировок</option>
                                <option value="3">Приложения метода коллокаций</option>
                                <option value="4">Анализ экономической динамики стран Центральной Африки с применением методов машинного обучения</option>
                                <option value="5">Разработка методов анализа слабо структурированных данных с применением методов машинного обучения</option>
                                <option value="6">Стратегия управления полетом квадрокоптера для выполнения заданных маневров</option>
                                <option value="7">Разложение по многочленам Чебышева</option>
                                <option value="8">Анализ и прогнозирование временных рядов методом SSA</option>
                                <option value="9">Сравнительный анализ методов прогнозирования временных рядов</option>
                                <option value="10">Оценки риска использование языковых моделей для формирования вредоносный запросов</option>
                                <option value="11">Методы анализа временных рядов</option>
                                <option value="12">Нейросетевая аппроксимация движения непотенциальных динамических систем</option>
                                <option value="13">Построение интегрированной системы, объединяющей базы данных складских терминалов разных собственников</option>
                                <option value="14">Предиктивная аналитика данных в задачах утилизации вычислительных ресурсов</option>
                                <option value="15">Выделение псевдооснов слов</option>
                                <option value="16">Разработка web-приложения для работы с базами данных кафедры</option>
                                <option value="17">Анализ корпусов литературных текстов</option>
                                <option value="18">Разработка приложения для сбора и анализа информации по обучающимся для выбора стиля обучения</option>
                                <option value="19">Работа с изображениями с помощью сверточных нейронных сетей</option>
                                <option value="20">Исследование методов определения опухоли мозга на МРТ снимках</option>
                                <option value="21">Задача анализа эффективности сотрудников предприятия</option>
                                <option value="22">Применение технологий машинного обучения в задачах EdTech</option>
                                <option value="23">Методы определений активности человека по данным из мобильных устройств</option>
                                <option value="24">Математическое моделирование экономической динамики с применением методов машинного обучения</option>
                                <option value="25">Сбор статистически значимой информации о большой лингвистической модели</option>
                                <option value="26">Сравнение иволютивных и классических алгоритмов вычисления базисов Грёбнера полиномиальных идеалов.</option>
                                <option value="27">Разработка многомерной информационной системы для анализа статистики правонарушений</option>
                                <option value="28">Развитие интеллектуальных систем управления транспортными потоками на основе данных IoT</option>
                                <option value="29">Анализ ЕЯ-текстов с применением нейронных сетей</option>
                                <option value="30">Построение электронной автоматизированной системы учёта успеваемости студентов в вузе</option>
                                <option value="31">Разработка многомерной информационной системы для анализа работы службы экстренных вызовов</option>
                                <option value="32">Использование многомерной информационной системы для анализа деятельности логистической компании</option>
                                <option value="33">Разработка многомерной информационной системы для анализа деятельности компании по продаже электроники</option>
                                <option value="34">Разработка методики анализа финансового состояния компаний с использованием методов машинного обучения</option>
                                <option value="35">Проектирование оптических покрытий</option>
                                <option value="36">Применение методов машинного обучения к медицинским данным</option>
                                <option value="37">Разработка системы управления релизами программного обеспечения</option>
                                <option value="38">Разработка многомерной информационной системы для анализа деятельности мотоклуба</option>
                                <option value="39">Разработка многомерной информационной системы для анализа ИТ- инфраструктуры компании</option>
                                <option value="40">Исследование методов и разработка алгоритмов для прогнозирования изменения цен и объемов продаж на бирже</option>
                                <option value="41">Анализ медицинских данных</option>
                                <option value="42">Разработка распределенной системы совершения торговых операций на фондовом рынке с применением нейросетевых моделей</option>
                                <option value="43">Проектирование и разработка информационной системы для обеспечения учебно-методической работы кафедры</option>
                                <option value="44">Нейронные сети на основе биномиального дерева для прогнозирования стоимости нелинейных деривативов</option>
                                <option value="45">Исследование методов и алгоритмов поиска аномалий в загрузке телекоммуникационных сетях</option>
                                <option value="46">Построение аналитической системы многокритериального контроля работы преподавателей в высших учебных заведениях</option>
                                <option value="47">Разработка и реализация электронной системы управления потоком задач</option>
                                <option value="48">Разработка программного комплекса для подготовки документов в патентный отдел</option>
                                <option value="49">Построение кросс-языковых эмбедингов для извлечения химических структур из текстов на русском и английском языках</option>
                                <option value="50">Проектирование и реализация прикладной системы, сопровождающей учебный процесс в школах Экваториальной Гвинеи</option>
                                <option value="51">Особенности баз знаний для динамических интеллектуальных систем</option>
                                <option value="52">Разработка и исследование подходов к решению задачи траекторного движения робототехнических систем в динамической среде</option>
                                <option value="53">Математическое моделирование процессов управления при создании и сопровождении цифровых продуктов при наличии рисков</option>
                                <option value="54">Нейросетевое распознавание музыкальных фрагментов</option>
                                <option value="55">Моделирование распространения волноводных мод в нерегулярных волноводах</option>
                                <option value="56">Использование технологии баз данных для моделирования потребности в рабочем персонале розничной торговой сети</option>
                                <option value="57">Исследование способов формального представления бизнес-процессов и методов оценки качества алгоритмов их автоматического синтеза</option>
                                <option value="58">Проектирование и реализация электронного приложения, сопровождающего работу Министерства по чрезвычайным ситуациям (МЧС)</option>
                                <option value="59">Использование многомерной информационной системы для анализа рынка банковского кредитования</option>
                                <option value="60">Исследование методов и алгоритмов автоматического перевода текстов</option>
                                <option value="61">Стратегии управления финансовыми рисками на основе генеративно-состязательных сетей</option>
                                <option value="62">Методы и подходы к исследованию изменения курса ценных бумаг</option>
                                <option value="63">Методы кластеризации в Machine Learning</option>
                                <option value="64">Исследование и применение современных методов решения задачи планирования траекторного движения беспилотных летательных аппаратов</option>
                                <option value="65">Исследование методов и разработка алгоритмов поиска аномалий (фродов) в банковских операциях</option>
                                <option value="66">Исследование, реализация и сравнение моделей популяции паразитических организмов</option>
                                <option value="67">Разработка механизма распознавания заболеваний опорно-двигательного аппарата с помощью методов глубокого обучения</option>
                                <option value="68">Разработка интерфейсного модуля интерактивного создания правил</option>
                                <option value="69">Использование генеративно- состязательной нейронной сети для генерации субъективно привлекательных лиц людей</option>
                                <option value="70">Разработка модуля получения и анализа данных с носимых гаджетов, записываемых в облачные решения (на примере Google fit)</option>
                                <option value="71">Реализация и исследование моделей поведения кооперирующихся агентов</option>
                                <option value="72">Применение методов активного обучения для разметки текстов, содержащих негативные новости о контрагентах</option>
                                <option value="73">Определение болезни Альцгеймера по МРТ снимкам</option>
                                <option value="74">Обучение с подкреплением в анализе медицинских изображений</option>
                                <option value="75">Предиктивная аналитика данных</option>
                                <option value="76">Интерактивная маска - распознавание и моделирование мимики</option>
                                <option value="77">Исследование методов обработки снимков магнитно-резонансной томографии для анализа зон интереса</option>
                                <option value="78">Анализ профилей пользователей</option>
                                <option value="79">Исследование современных подходов к решению задачи распознавания жестов</option>
                                <option value="80">Разработка интерфейса системы сбора информации о состоянии здоровья людей и отображения результатов ее обработки с применением Web-технологий</option>
                                <option value="81">Построение электронной автоматизированной системы контроля банковских операций и управления персоналом банка</option>
                                <option value="82">Прогнозирование цен на рынке стального проката с помощью методов машинного обучения</option>
                                <option value="83">Разработка интерфейсного модуля тестирования разрабатываемых баз знаний на основе неоднородных семантических сетей</option>
                                <option value="84">Применение методов машинного обучения в управлении портфелем ценных бумаг</option>
                                <option value="85">Исследование методов анализа графического интернет-контента пользователей с целью выявления личностных черт</option>
                                <option value="86">Проектирование и разработка информационной системы для обеспечения практики студентов</option>
                                <option value="87">Анализ ассоциативных правил в задачах ритейла</option>
                                <option value="88">Обучение с подкреплением для управления портфелем ценных бумаг</option>
                                <option value="89">Исследование методов планирования маршрута движения робототехнических систем в среде с препятствиями</option>   
                                    </select>
                                </label>
                            </div>

                            <div className="selector-patch-2 margin-fix">
                                <label className="selection-param">Науч рук
                                    <select name="lecturer" className="selectpicker"  onChange={handleInputChange} value={lecturerData} id="lecturer" multiple data-live-search="true">
                                        <option value="-1">
                                            Выберите преподавателя
                                        </option>
                                        <option value="1">Малых Михаил Дмитриевич</option>
                                        <option value="2">Александрова Лариса Валерьевна</option>
                                        <option value="3">Виноградов Андрей Николаевич</option>
                                        <option value="4">Молодченков Алексей Игоревич</option>
                                        <option value="5">Панкратов Александр Серафимович</option>
                                        <option value="6">Салпагаров Солтан Исмаилович</option>
                                        <option value="7">Стефанюк Вадим Львович</option>
                                        <option value="8">Фомин Максим Борисович</option>
                                        <option value="9">Хачумов Вячеслав Михайлович</option>
                                        <option value="10">Хачумов Михаил Вячеславович</option>
                                        <option value="11">Чеповский Андрей Михайлович</option>
                                        <option value="12">Шорохов Сергей Геннадьевич</option>
                                        <option value="13">Андрейчук Антон Андреевич</option>
                                        <option value="14">Киселёв Глеб Андреевич</option>
                                        <option value="15">Кройтор Олег Константинович</option>
                                        <option value="16">Диваков Дмитрий Валентинович</option>
                                        <option value="17">Тютюнник Анастасия Александровна</option>
                                        <option value="18">Севастьянов Леонид Антонович</option>
                                        <option value="19">Ловецкий Константин Петрович</option>
                                        <option value="20">Виницкий Сергей Ильич</option>
                                        <option value="21">Белов Александр Александрович</option>
                                        <option value="22">Хохлов Алексей Анатольевич</option>
                                        <option value="23">Васильев Сергей Анатольевич</option>
                                        <option value="24">Мамонов Антон Алексеевич</option>
                                    </select>
                                </label>
                            </div>
                        
            <button className="btn-reset form-button list-button" onClick={(e) => {handleClick(e)} } type='submit'> Получить</button>


   </>)

}


