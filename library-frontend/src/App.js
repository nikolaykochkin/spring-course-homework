import logo from './logo.svg';
import './App.css';
import ListAuthorComponent from "./components/ListAuthorComponent";
import HeaderComponent from "./components/HeaderComponent";
import FooterComponent from "./components/FooterComponent";

function App() {
    return (
        <div>
            <HeaderComponent/>
            <div className="container">
                <ListAuthorComponent/>
            </div>
            <FooterComponent/>
        </div>
    );
}

export default App;
