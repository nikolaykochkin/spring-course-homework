import React, {Component} from 'react';

class HeaderComponent extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <header>
                    <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                        <div><a href="" className="navbar-brand">Library application</a></div>
                    </nav>
                </header>
            </div>
        );
    }
}

export default HeaderComponent;