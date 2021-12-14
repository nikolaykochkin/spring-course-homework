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
            <div>
                <h2 className="text-center"> Genres List </h2>
                <div className="row">
                    <button className="btn btn-primary" onClick={this.addGenre}>Add genre</button>
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
                            this.state.genres.map(
                                genre =>
                                    <tr key={genre.id}>
                                        <td>{genre.name}</td>
                                        <td>
                                            <button onClick={() => this.editGenre(genre.id)}
                                                    className="btn btn-info"> Edit
                                            </button>
                                            <button style={{marginLeft: "10px"}}
                                                    onClick={() => this.deleteGenre(genre.id)}
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
    return <ListGenreComponent {...props} navigate={navigate}/>
}

export default WithNavigate;