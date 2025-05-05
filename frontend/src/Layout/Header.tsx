import './header.css';
import logo from "../assets/rudn_logo.png";
import { Dropdown } from 'react-bootstrap';

export const Header = () => {
    return (
        <header className="header">
            <div className="header__container flex">
                <div className="container-fluid flex">
                    <a href="/" className="header__logo">
                        <div className="header__logo-inside flex">
                            <img src={logo} alt="РУДН" className="header__logo__picture" />
                        </div>
                    </a>
                    <nav className="nav flex col-lg-10">
                        <ul className="list-reset nav__list flex">
                            <li className="nav__item">
                                <a href="/lecturers" className="nav__links">Преподавателям</a>
                            </li>
                            <li className="nav__item">
                                <a href="/faq" className="nav__links">Вопросы</a>
                            </li>
                            <li className="nav__item">
                                <a href="/support" className="nav__links">Поддержка</a>
                            </li>
                            <li className="nav__item">
                            <a href="/sendFile" className="nav__links">Протокол</a>
                            </li>
                            <Dropdown>
                    <Dropdown.Toggle variant="primary" id="dropdown-basic" style={{textTransform: "none"}}>
                        Дополнительно
                    </Dropdown.Toggle>

                    <Dropdown.Menu>
                        <Dropdown.Item  href="/protocol">Добавить</Dropdown.Item>
                        <Dropdown.Item href="/project">Новая форма</Dropdown.Item>
                    </Dropdown.Menu>
                    </Dropdown>
                        </ul>
                        
                    </nav>
                </div>
            </div>
        </header>
    );
}