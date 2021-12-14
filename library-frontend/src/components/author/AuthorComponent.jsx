import React, {Component} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import AuthorService from "../../services/AuthorService";

class AuthorComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            id: this.props.params.id,
            name: ''
        };

        this.changeNameHandler = this.changeNameHandler.bind(this);
        this.saveAuthor = this.saveAuthor.bind(this);
    }

    componentDidMount() {
        if (this.state.id !== "new") {
            AuthorService.getAuthorById(this.state.id).then((res) => {
                let author = res.data;
                this.setState({name: author.name});
            });
        }
    }

    saveAuthor = (event) => {
        event.preventDefault();
        let author = {name: this.state.name};
        console.log("author => " + JSON.stringify(author));
        if (this.state.id === "new") {
            AuthorService.createAuthor(author).then(res => {
                this.props.navigate("/authors");
            });
        } else {
            AuthorService.updateAuthor(author).then(res => {
                this.props.navigate("/authors");
            });
        }
    }

    changeNameHandler = (event) => {
        this.setState({name: event.target.value});
    }

    cancel() {
        this.props.navigate("/authors");
    }

    getTitle() {
        if (this.state.id === "new") {
            return "Create Author";
        } else {
            return "Edit Author";
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
                                    <div className="form-group">
                                        <label>Name:</label>
                                        <input placeholder="Name" name="name" className="form-control"
                                               value={this.state.name} onChange={this.changeNameHandler}/>
                                    </div>
                                    <button className="btn btn-success" onClick={this.saveAuthor}>Save</button>
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
    return <AuthorComponent {...props} navigate={navigate} params={params}/>
}

export default WithNavigateAndParams;