import axios from "axios";

const GENRE_API_BASE_URL = "http://localhost:8080/api/genres"

class GenreService {

    getGenres() {
        return axios.get(GENRE_API_BASE_URL);
    }

    createGenre(genre) {
        return axios.post(GENRE_API_BASE_URL, genre);
    }

    getGenreById(genreId) {
        return axios.get(GENRE_API_BASE_URL + "/" + genreId);
    }

    updateGenre(genre) {
        return axios.put(GENRE_API_BASE_URL + "/" + genre.id, genre);
    }

    deleteGenre(genreId) {
        return axios.delete(GENRE_API_BASE_URL + "/" + genreId);
    }
}

export default new GenreService()