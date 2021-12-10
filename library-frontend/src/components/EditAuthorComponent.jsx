import React, {Component} from 'react';
import AuthorService from "../services/AuthorService";
import {useNavigate, useParams} from "react-router-dom";

class EditAuthorComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            id: this.props.params.id,
            name: ''
        };

        this.changeNameHandler = this.changeNameHandler.bind(this);
        this.updateAuthor = this.updateAuthor.bind(this);
    }

    componentDidMount() {
        AuthorService.getAuthorById(this.state.id).then((res) => {
            let author = res.data;
            this.setState({name: author.name});
        });
    }

    updateAuthor = (event) => {
        event.preventDefault();
        let author = {id: this.state.id, name: this.state.name};
        console.log("author => " + JSON.stringify(author));
        AuthorService.updateAuthor(author).then(res => {
            this.props.navigate("/authors");
        });
    }

    changeNameHandler = (event) => {
        this.setState({name: event.target.value});
    }

    cancel() {
        this.props.navigate("/authors");
    }

    render() {
        return (
            <div>
                <div className="container">
                    <div className="row">
                        <div className="card col-md-6 offset-md-3 offset-md-3">
                            <h3 className="text-center">Edit Author</h3>
                            <div className="card-body">
                                <form>
                                    <div className="form-group">
                                        <label>Name:</label>
                                        <input placeholder="Name" name="name" className="form-control"
                                               value={this.state.name} onChange={this.changeNameHandler}/>
                                    </div>
                                    <button className="btn btn-success" onClick={this.updateAuthor}>Save</button>
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
    return <EditAuthorComponent {...props} navigate={navigate} params={params}/>
}

export default WithNavigateAndParams;