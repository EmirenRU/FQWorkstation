import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { Footer } from "./footer";
import { Header } from "./Header";
import { FAQ } from "../FAQ/FAQ";
import { Support } from "../Support/Support";
import { Lecturers } from "../Lecturers/Lecturers";
import { Protocol } from "../Protocol/Protocol";






export const PageLayout = () => {
    return (
        <Router>
            <Header />
            <Routes>
                <Route path="/lecturers" Component={Lecturers} />
                <Route path="/faq" Component={FAQ} />
                <Route path="/support"  element={<Support  />} />
                <Route path="/protocol" Component={Protocol} />
                <Route path="/" Component={Lecturers} /> {/* По умолчанию отображаем Lecturers */}
            </Routes>
            <Footer />
        </Router>
    );
};