import './App.css';
import {BrowserRouter, Route, Routes} from 'react-router-dom'
import ListAuthorComponent from "./components/ListAuthorComponent";
import HeaderComponent from "./components/HeaderComponent";
import FooterComponent from "./components/FooterComponent";
import AuthorComponent from "./components/AuthorComponent";

function App() {
    return (
        <div>
            <BrowserRouter>
                <HeaderComponent/>
                <div className="container">
                    <Routes>
                        <Route path="/" exact element={<ListAuthorComponent/>}/>
                        <Route path="/authors" element={<ListAuthorComponent/>}/>
                        <Route path="/authors/:id" element={<AuthorComponent/>}/>
                    </Routes>
                </div>
                <FooterComponent/>
            </BrowserRouter>
        </div>
    );
}

export default App;
