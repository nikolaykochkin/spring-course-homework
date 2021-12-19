import React, {Component} from 'react';
import {useNavigate} from 'react-router-dom'
import BookService from "../../services/BookService";

class ListBookComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            books: []
        };

        this.addBook = this.addBook.bind(this);
        this.editBook = this.editBook.bind(this);
        this.deleteBook = this.deleteBook.bind(this);
    }

    componentDidMount() {
        BookService.getBooks().then((res) => {
            this.setState({books: res.data});
        })
    }

    addBook() {
        this.props.navigate("/books/new");
    }

    editBook(id) {
        this.props.navigate(`/books/${id}`);
    }

    deleteBook(id) {
        BookService.deleteBook(id).then(res => {
            this.setState({books: this.state.books.filter(book => book.id !== id)})
        });
    }

    render() {
        return (
            <div className="container p-2">
                <div className="row p-1">
                    <div className="col">
                        <h2 className="text-center"> Books List </h2>
                    </div>
                </div>
                <div className="row p-1">
                    <div className="col-3">
                        <button className="btn btn-primary" onClick={this.addBook}>Add book</button>
                    </div>
                </div>
                <div className="row p-1">
                    <div className="col">
                        <table className="table table-striped table-bordered">
                            <thead>
                            <tr>
                                <th> Title</th>
                                <th> Author</th>
                                <th> Genre</th>
                                <th> Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                this.state.books.map(
                                    book =>
                                        <tr key={book.id}>
                                            <td>{book.title}</td>
                                            <td>{book.author.name}</td>
                                            <td>{book.genre.name}</td>
                                            <td>
                                                <div className="d-grid gap-2 d-md-flex justify-content-evenly">
                                                    <button onClick={() => this.editBook(book.id)}
                                                            className="btn btn-info"
                                                            type="button"> Edit
                                                    </button>
                                                    <button onClick={() => this.deleteBook(book.id)}
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
    return <ListBookComponent {...props} navigate={navigate}/>
}

export default WithNavigate;