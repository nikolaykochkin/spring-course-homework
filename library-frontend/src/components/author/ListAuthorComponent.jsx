import React, {Component} from 'react';
import {useNavigate} from 'react-router-dom'
import AuthorService from "../../services/AuthorService";

class ListAuthorComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            authors: []
        };

        this.addAuthor = this.addAuthor.bind(this);
        this.editAuthor = this.editAuthor.bind(this);
        this.deleteAuthor = this.deleteAuthor.bind(this);
    }

    componentDidMount() {
        AuthorService.getAuthors().then((res) => {
            this.setState({authors: res.data});
        })
    }

    addAuthor() {
        this.props.navigate("/authors/new");
    }

    editAuthor(id) {
        this.props.navigate(`/authors/${id}`);
    }

    deleteAuthor(id) {
        AuthorService.deleteAuthor(id).then(res => {
            this.setState({authors: this.state.authors.filter(author => author.id !== id)})
        });
    }

    render() {
        return (
            <div className="container p-2">
                <div className="row p-1">
                    <div className="col">
                        <h2 className="text-center"> Authors List </h2>
                    </div>
                </div>
                <div className="row p-1">
                    <div className="col-3">
                        <button className="btn btn-primary" onClick={this.addAuthor}>Add author</button>
                    </div>
                </div>
                <div className="row p-1">
                    <div className="col">
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
                                            <td>
                                                <div className="d-grid gap-2 d-md-flex justify-content-evenly">
                                                    <button onClick={() => this.editAuthor(author.id)}
                                                            className="btn btn-info"
                                                            type="button"> Edit
                                                    </button>
                                                    <button onClick={() => this.deleteAuthor(author.id)}
                                                            className="btn btn-danger"
                                                            type="button">
                                                        Delete
                                                    </button>
                                                </div>
                                            </td>
                                        </tr>
                                )
                            }
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        );
    }
}

function WithNavigate(props) {
    let navigate = useNavigate();
    return <ListAuthorComponent {...props} navigate={navigate}/>
}

export default WithNavigate;