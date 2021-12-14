import './App.css';
import {BrowserRouter, Route, Routes} from 'react-router-dom'
import HeaderComponent from "./components/HeaderComponent";
import ListBookComponent from "./components/book/ListBookComponent";
import BookComponent from "./components/book/BookComponent";
import ListAuthorComponent from "./components/author/ListAuthorComponent";
import AuthorComponent from "./components/author/AuthorComponent";
import ListGenreComponent from "./components/genre/ListGenreComponent";
import GenreComponent from "./components/genre/GenreComponent";

function App() {
    return (
        <div>
            <BrowserRouter>
                <HeaderComponent/>
                <div className="container">
                    <Routes>
                        <Route path="/" exact element={<ListBookComponent/>}/>
                        <Route path="/books" element={<ListBookComponent/>}/>
                        <Route path="/books/:id" element={<BookComponent/>}/>
                        <Route path="/authors" element={<ListAuthorComponent/>}/>
                        <Route path="/authors/:id" element={<AuthorComponent/>}/>
                        <Route path="/genres" element={<ListGenreComponent/>}/>
                        <Route path="/genres/:id" element={<GenreComponent/>}/>
                    </Routes>
                </div>
            </BrowserRouter>
        </div>
    );
}

export default App;
