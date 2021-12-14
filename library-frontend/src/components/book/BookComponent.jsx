import React, {Component} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import BookService from "../../services/BookService";
import AuthorService from "../../services/AuthorService";
import GenreService from "../../services/GenreService";

class BookComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            id: this.props.params.id,
            title: '',
            author: {},
            genre: {},
            authors: [],
            genres: []
        };

        this.changeTitleHandler = this.changeTitleHandler.bind(this);
        this.saveBook = this.saveBook.bind(this);
    }

    componentDidMount() {
        AuthorService.getAuthors().then(res => {
            this.setState({authors: res.data})
        });
        GenreService.getGenres().then(res => {
            this.setState({genres: res.data})
        });
        if (this.state.id !== "new") {
            BookService.getBookById(this.state.id).then((res) => {
                let book = res.data;
                this.setState({title: book.title, author: book.author, genre: book.genre});
            });
        }
    }

    saveBook = (event) => {
        event.preventDefault();
        let book = {
            name: this.state.name,
            author: this.state.author,
            genre: this.state.genre
        };
        console.log("book => " + JSON.stringify(book));
        if (this.state.id === "new") {
            BookService.createBook(book).then(res => {
                this.props.navigate("/books");
            });
        } else {
            BookService.updateBook(book).then(res => {
                this.props.navigate("/books");
            });
        }
    }

    changeTitleHandler = (event) => {
        this.setState({name: event.target.value});
    }

    cancel() {
        this.props.navigate("/books");
    }

    getTitle() {
        if (this.state.id === "new") {
            return "Create Book";
        } else {
            return "Edit Book";
        }
    }

    render() {
        return (
            <div>
                <div className="container">
                    <div className="row">
                        <div className="card col-md-6 offset-md-3 offset-md-3">
                            <h3 className="text-center">{this.getTitle()}</h3>
                            <div className="card-body">
                                <form>
                                    <div className="mb-3">
                                        <label htmlFor="bookInputTitle" className="form-label">Title</label>
                                        <input type="email" className="form-control" id="bookInputTitle"
                                               placeholder="Title" value={this.state.title}
                                               onChange={this.changeTitleHandler}/>
                                    </div>
                                    <div className="mb-3">
                                        <label htmlFor="bookSelectAuthor" className="form-label">Author</label>
                                        <select className="form-select" aria-label="Book select Author"
                                                id="bookSelectAuthor">
                                            {this.state.authors.map(a => a.id === this.state.author.id ?
                                                <option selected>{a.name}</option> :
                                                <option value={a}>{a.name}</option>)}
                                        </select>
                                    </div>
                                    <button className="btn btn-success" onClick={this.saveBook}>Save</button>
                                    <button className="btn btn-danger" onClick={this.cancel.bind(this)}
                                            style={{marginLeft: "10px"}}>Cancel
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

function WithNavigateAndParams(props) {
    let navigate = useNavigate();
    let params = useParams();
    return <BookComponent {...props} navigate={navigate} params={params}/>
}

export default WithNavigateAndParams;