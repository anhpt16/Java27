import { getData, postData, putData, deleteData} from "./baseApi.js";

export const favoriteService = {
    // Lấy danh sách phim yêu thích
    getFavoriteMovies: async (page = null) => {
        let queryString = "?";
        if (page != null) {
            queryString += `page=${page}`;
        }
        return await getData(`/favorites${queryString}`);
    },

    // Thêm phim vào danh sách yêu thích
    addFavoriteMovie: async (movieId) => {
        return await postData(`/favorites/${movieId}`);
    },

    // Xóa phim khỏi danh sách yêu thích
    removeFavoriteMovie: async (movieId) => {
        return await deleteData(`/favorites/${movieId}`);
    },

    // Xóa tất cả phim khỏi danh sách yêu thích
    removeAllFavoriteMovie: async () => {
        return await deleteData(`/favorites`);
    }
}
