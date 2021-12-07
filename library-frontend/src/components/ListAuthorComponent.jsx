import React, {Component} from 'react';
import AuthorService from "../services/AuthorService";

class ListAuthorComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            authors: []
        };
    }

    componentDidMount() {
        AuthorService.getAuthors().then((res) => {
            this.setState({authors: res.data});
        })
    }

    render() {
        return (
            <div>
                <h2 className="text-center"> Authors List </h2>
                <div className="row">
                    <table className="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <th> Name</th>
                            <th> Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.authors.map(
                                author =>
                                    <tr key={author.id}>
                                        <td>{author.name}</td>
                                    </tr>
                            )
                        }
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}

export default ListAuthorComponent;