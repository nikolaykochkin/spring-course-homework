import React, {Component} from 'react';

class FooterComponent extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <footer className="footer">
                    <span className="text-muted">All Rights Reserved 2021 @Nikolay</span>
                </footer>
            </div>
        );
    }
}

export default FooterComponent;