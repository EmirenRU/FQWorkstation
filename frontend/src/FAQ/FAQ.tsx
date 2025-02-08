import "./faq.css"
import Accordion from 'react-bootstrap/Accordion'
import img1 from "./Images/1.jpg"
import img2 from "./Images/2.jpg"
import img3 from "./Images/3.jpg"
import img4 from "./Images/4.jpg"
import img5 from "./Images/5.jpg"
import img6 from "./Images/6.jpg"
import img7 from "./Images/7.jpg"
import img8 from "./Images/8.jpg"
import img9 from "./Images/9.jpg"
export const FAQ = () => {
    return(
        <main>
        <div className="container-fluid instruction-container ">




    <div className=" container-fluid  instruction">
        <div className="container show__container">

        <h1 className="manual__header-main"> Инструкция по использованию сервиса</h1>

        <div className="text-space">
        <h3 className="manual__header">
            Добро пожаловать на страницу с инструкцией к сервису FQW Workstation.
            </h3>

            
            <p className="" >На данной странице будут подробно объяснен весь Функционал сервиса и все возможные с ним взаисодействия.<br>

            </br>
             На данный момент Функционал сервиса включает в себя: просмотр таблиц с ВКР за определенный год и экспорт таблиц с ВКР за определенный год.
            

             </p>
             </div>
             </div>

             <h3 className="manual__header" id="show"> Просмотр данных ВКР</h3>
        <div className="container show__container">
             <p className="instruction-text"><strong>1. перейти на главную страницу сервиса</strong></p>
             <Accordion>
                <Accordion.Item eventKey="0">
                    <Accordion.Header>Вид главной страницы</Accordion.Header>
                        <Accordion.Body>


             <picture className="instruction-picture">   

                <img src={img1} alt="Пример выбора параметров" className="instruction-img"></img>
                <figcaption>(Пример выбора параметров)</figcaption>
             </picture>

             </Accordion.Body>
                </Accordion.Item>
                </Accordion>


             <p className="instruction-text">
            <strong>2. Задать параметры поиска нужных данных о ВКР и нажать на кнопку "Получить":</strong> 
             </p>

             <Accordion>
                <Accordion.Item eventKey="0">
                    <Accordion.Header>Просмотр данных ВКР</Accordion.Header>
                        <Accordion.Body>

             <picture className="instruction-picture">   
                <img src={img2} alt="Пример выбора параметров" className="instruction-img"></img>
                <figcaption>(Пример выбора параметров)</figcaption>
             </picture>
             </Accordion.Body>
                </Accordion.Item>
                </Accordion>


             <p className="instruction-text">
             <strong>3. В резульате сервис предоставит таблицу следующего формата, которую можно будет <a href="#export">экспортировать</a>:</strong> </p>


             <Accordion>
                <Accordion.Item eventKey="0">
                    <Accordion.Header>Пример экспортируемой таблицы</Accordion.Header>
                        <Accordion.Body>
                        
            <picture className="instruction-picture-special">   
                <img src={img3} alt="Пример выбора параметров" className="instruction-img"></img>
                <figcaption>(Пример полученной таблицы)</figcaption>
            </picture>

            
             </Accordion.Body>
                </Accordion.Item>
                </Accordion>

                </div>

                <h2 id="export" className="manual__header">Экспорт данных </h2>
                <div className="container show__container">
                
                <p className="instruction-text">
                    <strong>1. Для экспорта данных из сервиса необходимо переместиться вниз страницы и нажать на кнопку "Скачать таблицу":</strong>
                </p>
                
                <Accordion>
                <Accordion.Item eventKey="1">
                    <Accordion.Header>Кнопка скачивания</Accordion.Header>
                        <Accordion.Body>




                <picture className="instruction-picture">   
                    <img src={img4} alt="Пример выбора параметров" className="instruction-img"></img>
                    <figcaption>(Кнопка загрузки)</figcaption>
                </picture>

            </Accordion.Body>
                </Accordion.Item>
                </Accordion>

                <p className="instruction-text">
                    2. Загруженный файл будет находиться в папке с загрузками:
                </p>
                
                <Accordion>
                <Accordion.Item eventKey="1">
                    <Accordion.Header>Загруженный файл</Accordion.Header>
                        <Accordion.Body>

                <picture className="instruction-picture">   
                    <img src={img5} alt="Пример выбора параметров" className="instruction-img"></img>
                    <figcaption>(Загруженный файл)</figcaption>
                </picture>

            </Accordion.Body>
                </Accordion.Item>
                </Accordion>

                <p className="instruction-text">
                    3. Откройте приложение Microsoft Excel и во вкладке данных выбирите "получить данные из CSV-файла":
                </p>

                <Accordion>
                <Accordion.Item eventKey="1">
                    <Accordion.Header>Получение данных из CSV-файла</Accordion.Header>
                        <Accordion.Body>
                
                <picture className="instruction-picture">   
                    <img src={img6} alt="Пример выбора параметров" className="instruction-img"></img>
                    <figcaption>("получить данные из CSV-файла")</figcaption>
                </picture>
        
                </Accordion.Body>
                </Accordion.Item>
                </Accordion>

                <p className="instruction-text">
                    4. Выберите файл в всплывающем окне и нажмите на импорт:
                </p>
                <Accordion>
                <Accordion.Item eventKey="1">
                    <Accordion.Header>Импорт данных</Accordion.Header>
                        <Accordion.Body>

                <picture className="instruction-picture-special">   
                    <img src={img7} alt="Пример выбора параметров" className="instruction-img" width = "700px" height="400px"></img>
                    <figcaption>("Импорт данных")</figcaption>
                </picture>
                </Accordion.Body>
                </Accordion.Item>
                </Accordion>


            <p className="instruction-text">
                5. В всплывающем окне нажмите на загрузить и при необходимости отредактируйте импортированные данные вручную
            </p>

            <Accordion>
                <Accordion.Item eventKey="1">
                    <Accordion.Header>Предпросмотр итоговой таблицы</Accordion.Header>
                        <Accordion.Body>
            
            <picture className="instruction-picture">   
                <img src={img8} alt="Пример выбора параметров" className="instruction-img" width = "700px" height="400px"></img>
                <figcaption>("Предпросмотр импортированных данных")</figcaption>
            </picture>

            <picture className="instruction-picture">   
                <img src={img9} alt="Пример выбора параметров" className="instruction-img" width = "700px" height="400px"></img>
                <figcaption>("Пример необходимых корректировок после импорта")</figcaption>
            </picture>



            </Accordion.Body>
                </Accordion.Item>
                </Accordion>

                </div>
    </div>
    <aside className="aside__menu">
            <div className="aside__menu__container">
                <h3>Navigation</h3>
            <ul className="aside__link__list">

            <a href="#show">Просмотр</a>
            <a href="#export">Экспорт данных</a>

            </ul>

            </div>
        </aside>
    </div>
    </main>
    )
}