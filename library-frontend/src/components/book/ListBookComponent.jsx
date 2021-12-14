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
            <div>
                <h2 className="text-center"> Books List </h2>
                <div className="row">
                    <button className="btn btn-primary" onClick={this.addBook}>Add book</button>
                </div>
                <div className="row">
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
                                            <button onClick={() => this.editBook(book.id)}
                                                    className="btn btn-info"> Edit
                                            </button>
                                            <button style={{marginLeft: "10px"}}
                                                    onClick={() => this.deleteBook(book.id)}
                                                    className="btn btn-danger">
                                                Delete
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
    return <ListBookComponent {...props} navigate={navigate}/>
}

export default WithNavigate;