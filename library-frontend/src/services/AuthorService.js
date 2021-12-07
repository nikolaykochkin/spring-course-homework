import axios from "axios";

const AUTHOR_API_BASE_URL = "http://localhost:8080/api/authors"

class AuthorService {

    getAuthors() {
        return axios.get(AUTHOR_API_BASE_URL);
    }

}

export default new AuthorService()