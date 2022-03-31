import React, {Component} from 'react';
import CommentService from "../../services/CommentService";

class CommentsComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            bookId: this.props.bookId,
            comment: '',
            comments: [],
            wasValidated: false
        }

        this.changeCommentHandler = this.changeCommentHandler.bind(this);
        this.saveComment = this.saveComment.bind(this);
    }

    componentDidMount() {
        CommentService.getCommentByBookId(this.state.bookId).then(res => {
            this.setState({comments: res.data});
        });
    }

    changeCommentHandler = (event) => {
        this.setState({comment: event.target.value});
    }

    saveComment = (event) => {
        event.preventDefault();
        if (this.state.comment === '') {
            this.setState({wasValidated: true});
            return;
        }
        let comment = {
            bookId: this.state.bookId,
            text: this.state.comment
        };
        CommentService.createComment(comment).then(res => {
            this.setState({
                comments: this.state.comments.concat(res.data),
                comment: '',
                wasValidated: false
            });
        });
    }

    render() {
        return (
            <div>
                <p>
                    <button className="btn btn-primary" type="button" data-bs-toggle="collapse"
                            data-bs-target="#collapseExample" aria-expanded="false"
                            aria-controls="collapseExample">
                        Comments ({this.state.comments.length})
                    </button>
                </p>
                <div className="collapse" id="collapseExample">
                    {this.state.comments.map(comment =>
                        <div key={comment.id} className="card mb-3">
                            <div className="card-body">{comment.text}</div>
                            <div className="card-footer text-muted text-end">
                                Posted: {new Date(comment.createdAt).toLocaleString()}
                            </div>
                        </div>
                    )}
                    <form className={this.state.wasValidated ? "was-validated" : ""} noValidate>
                        <div className="input-group mb-3">
                            <input type="text" className="form-control" placeholder="Enter comment..."
                                   aria-label="Comment" aria-describedby="button-addon2"
                                   value={this.state.comment} onChange={this.changeCommentHandler} required/>
                            <button className="btn btn-outline-secondary" type="button"
                                    id="button-addon2" onClick={this.saveComment}>Post
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        );
    }
}

export default CommentsComponent;