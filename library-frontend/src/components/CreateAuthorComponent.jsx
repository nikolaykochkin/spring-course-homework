import React, {Component} from 'react';
import {useNavigate} from "react-router-dom";
import AuthorService from "../services/AuthorService";

class CreateAuthorComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            name: ''
        };

        this.changeNameHandler = this.changeNameHandler.bind(this);
        this.saveAuthor = this.saveAuthor.bind(this);
    }

    saveAuthor = (event) => {
        event.preventDefault();
        let author = {name: this.state.name};
        console.log("author => " + JSON.stringify(author));
        AuthorService.createAuthor(author).then(res => {
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
                            <h3 className="text-center">Add Author</h3>
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

function WithNavigate(props) {
    let navigate = useNavigate();
    return <CreateAuthorComponent {...props} navigate={navigate}/>
}

export default WithNavigate;