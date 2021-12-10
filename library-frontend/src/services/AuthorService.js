import axios from "axios";

const AUTHOR_API_BASE_URL = "http://localhost:8080/api/authors"

class AuthorService {

    getAuthors() {
        return axios.get(AUTHOR_API_BASE_URL);
    }

    createAuthor(author) {
        return axios.post(AUTHOR_API_BASE_URL, author);
    }

    getAuthorById(authorId) {
        return axios.get(AUTHOR_API_BASE_URL + "/" + authorId);
    }

    updateAuthor(author) {
        return axios.put(AUTHOR_API_BASE_URL + "/" + author.id, author);
    }
}

export default new AuthorService()