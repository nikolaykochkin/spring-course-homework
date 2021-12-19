import React, {Component} from 'react';
import {useNavigate} from 'react-router-dom'
import GenreService from "../../services/GenreService";

class ListGenreComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            genres: []
        };

        this.addGenre = this.addGenre.bind(this);
        this.editGenre = this.editGenre.bind(this);
        this.deleteGenre = this.deleteGenre.bind(this);
    }

    componentDidMount() {
        GenreService.getGenres().then((res) => {
            this.setState({genres: res.data});
        })
    }

    addGenre() {
        this.props.navigate("/genres/new");
    }

    editGenre(id) {
        this.props.navigate(`/genres/${id}`);
    }

    deleteGenre(id) {
        GenreService.deleteGenre(id).then(res => {
            this.setState({genres: this.state.genres.filter(genre => genre.id !== id)})
        });
    }

    render() {
        return (
            <div className="container p-2">
                <div className="row p-1">
                    <div className="col">
                        <h2 className="text-center"> Genres List </h2>
                    </div>
                </div>
                <div className="row p-1">
                    <div className="col-3">
                        <button className="btn btn-primary" onClick={this.addGenre}>Add genre</button>
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
                                this.state.genres.map(
                                    genre =>
                                        <tr key={genre.id}>
                                            <td>{genre.name}</td>
                                            <td>
                                                <div className="d-grid gap-2 d-md-flex justify-content-evenly">
                                                    <button onClick={() => this.editGenre(genre.id)}
                                                            className="btn btn-info"
                                                            type="button"> Edit
                                                    </button>
                                                    <button onClick={() => this.deleteGenre(genre.id)}
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
    return <ListGenreComponent {...props} navigate={navigate}/>
}

export default WithNavigate;