import React, {Component} from 'react';
import {useNavigate} from 'react-router-dom'
import AuthorService from "../services/AuthorService";

class ListAuthorComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            authors: []
        };
        this.addAuthor = this.addAuthor.bind(this);
        this.editAuthor = this.editAuthor.bind(this);
    }

    componentDidMount() {
        AuthorService.getAuthors().then((res) => {
            this.setState({authors: res.data});
        })
    }

    addAuthor() {
        this.props.navigate("/add-author");
    }

    editAuthor(id) {
        this.props.navigate(`/edit-author/${id}`);
    }

    render() {
        return (
            <div>
                <h2 className="text-center"> Authors List </h2>
                <div className="row">
                    <button className="btn btn-primary" onClick={this.addAuthor}>Add author</button>
                </div>
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
                                        <td>
                                            <button onClick={() => this.editAuthor(author.id)} className="btn btn-info">
                                                Edit
                                            </button>
                                        </td>
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

function WithNavigate(props) {
    let navigate = useNavigate();
    return <ListAuthorComponent {...props} navigate={navigate}/>
}

export default WithNavigate;