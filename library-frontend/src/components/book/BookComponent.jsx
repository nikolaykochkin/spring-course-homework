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
            authorId: null,
            genreId: null,
            authors: [],
            genres: [],
            wasValidated: false
        };

        this.changeTitleHandler = this.changeTitleHandler.bind(this);
        this.changeAuthorHandler = this.changeAuthorHandler.bind(this);
        this.changeGenreHandler = this.changeGenreHandler.bind(this);
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
                this.setState({title: book.title, authorId: book.author.id, genreId: book.genre.id});
            });
        }
    }

    saveBook = (event) => {
        event.preventDefault();
        if (this.state.authorId === null
            || this.state.genreId === null
            || this.state.title === '') {
            this.setState({wasValidated: true})
            return;
        }
        let author = this.state.authors.find(a => a.id === this.state.authorId);
        let genre = this.state.genres.find(g => g.id === this.state.genreId);
        let book = {
            id: this.state.id,
            title: this.state.title,
            author: author,
            genre: genre
        };
        console.log("book => " + JSON.stringify(book));
        if (this.state.id === "new") {
            book.id = null;
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
        this.setState({title: event.target.value});
    }

    changeAuthorHandler = (event) => {
        this.setState({authorId: event.target.value || null});
    }

    changeGenreHandler = (event) => {
        this.setState({genreId: event.target.value || null});
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
                                <form className={this.state.wasValidated && "was-validated"} noValidate>
                                    <div className="mb-3">
                                        <label htmlFor="bookInputTitle" className="form-label">Title</label>
                                        <input type="text" className="form-control" id="bookInputTitle"
                                               placeholder="Title" value={this.state.title}
                                               onChange={this.changeTitleHandler} required/>
                                        <div className="invalid-feedback">
                                            Please provide a title.
                                        </div>
                                    </div>
                                    <div className="mb-3">
                                        <label htmlFor="bookSelectAuthor" className="form-label">Author</label>
                                        <select className="form-select" aria-label="Book select Author"
                                                id="bookSelectAuthor" onChange={this.changeAuthorHandler}
                                                value={this.state.authorId || ''} required>
                                            <option value={''} disabled>Choose author...</option>
                                            {this.state.authors.map(a =>
                                                <option value={a.id}>{a.name}</option>)}
                                        </select>
                                        <div className="invalid-feedback">
                                            Please select an author.
                                        </div>
                                    </div>
                                    <div className="mb-3">
                                        <label htmlFor="bookSelectGenre" className="form-label">Genre</label>
                                        <select className="form-select" aria-label="Book select Genre"
                                                id="bookSelectGenre" onChange={this.changeGenreHandler}
                                                value={this.state.genreId || ''} required>
                                            <option value={''} disabled>Choose genre...</option>
                                            {this.state.genres.map(g =>
                                                <option value={g.id}>{g.name}</option>)}
                                        </select>
                                        <div className="invalid-feedback">
                                            Please select a genre.
                                        </div>
                                    </div>
                                    <button className="btn btn-success" onClick={this.saveBook} type="submit">Save
                                    </button>
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