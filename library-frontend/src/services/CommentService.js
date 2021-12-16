import axios from "axios";

const COMMENT_API_BASE_URL = "http://localhost:8080/api/comments"

class CommentService {

    getComments() {
        return axios.get(COMMENT_API_BASE_URL);
    }

    createComment(comment) {
        return axios.post(COMMENT_API_BASE_URL, comment);
    }

    getCommentByBookId(bookId) {
        return axios.get(COMMENT_API_BASE_URL, {params: {bookId: bookId}})
    }

    getCommentById(commentId) {
        return axios.get(COMMENT_API_BASE_URL + "/" + commentId);
    }

    updateComment(comment) {
        return axios.put(COMMENT_API_BASE_URL + "/" + comment.id, comment);
    }

    deleteComment(commentId) {
        return axios.delete(COMMENT_API_BASE_URL + "/" + commentId);
    }
}

export default new CommentService()