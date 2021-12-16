import React, {Component} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import GenreService from "../../services/GenreService";

class GenreComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            id: this.props.params.id,
            name: '',
            wasValidated: false
        };

        this.changeNameHandler = this.changeNameHandler.bind(this);
        this.saveGenre = this.saveGenre.bind(this);
    }

    componentDidMount() {
        if (this.state.id !== "new") {
            GenreService.getGenreById(this.state.id).then((res) => {
                let genre = res.data;
                this.setState({name: genre.name});
            });
        }
    }

    saveGenre = (event) => {
        event.preventDefault();
        if (this.state.name === '') {
            this.setState({wasValidated: true})
            return;
        }
        let genre = {id: this.state.id, name: this.state.name};
        console.log("genre => " + JSON.stringify(genre));
        if (this.state.id === "new") {
            genre.id = null;
            GenreService.createGenre(genre).then(res => {
                this.props.navigate("/genres");
            });
        } else {
            GenreService.updateGenre(genre).then(res => {
                this.props.navigate("/genres");
            });
        }
    }

    changeNameHandler = (event) => {
        this.setState({name: event.target.value});
    }

    cancel() {
        this.props.navigate("/genres");
    }

    getTitle() {
        if (this.state.id === "new") {
            return "Create Genre";
        } else {
            return "Edit Genre";
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
                                        <label htmlFor="authorInputName" className="form-label">Name</label>
                                        <input type="text" className="form-control" id="authorInputName"
                                               placeholder="Name" value={this.state.name}
                                               onChange={this.changeNameHandler} required/>
                                        <div className="invalid-feedback">
                                            Please provide a name.
                                        </div>
                                    </div>
                                    <button className="btn btn-success" onClick={this.saveGenre}>Save</button>
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
    return <GenreComponent {...props} navigate={navigate} params={params}/>
}

export default WithNavigateAndParams;