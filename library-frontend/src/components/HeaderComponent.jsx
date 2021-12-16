import React, {Component} from 'react';
import {useLocation} from "react-router-dom";

class HeaderComponent extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <nav className="navbar navbar-expand-lg navbar-light bg-light">
                <div className="container-fluid">
                    <a className="navbar-brand" href="/">Library</a>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false"
                            aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"/>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarNav">
                        <ul className="navbar-nav">
                            <li className="nav-item">
                                {this.props.location.pathname.startsWith("/books")
                                    ? <a className="nav-link active" aria-current="page" href="/books">Books</a>
                                    : <a className="nav-link" href="/books">Books</a>}
                            </li>
                            <li className="nav-item">
                                {this.props.location.pathname.startsWith("/authors")
                                    ? <a className="nav-link active" aria-current="page" href="/authors">Authors</a>
                                    : <a className="nav-link" href="/authors">Authors</a>}
                            </li>
                            <li className="nav-item">
                                {this.props.location.pathname.startsWith("/genres")
                                    ? <a className="nav-link active" aria-current="page" href="/genres">Genres</a>
                                    : <a className="nav-link" href="/genres">Genres</a>}
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        );
    }
}

function WithNavigateAndParams(props) {
    let location = useLocation();
    return <HeaderComponent {...props} location={location}/>
}

export default WithNavigateAndParams;