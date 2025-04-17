import { getData, postData, putData, deleteData} from "./baseApi.js";

export const reviewService = {

    // Thêm bình luận cho phim
    addReview: async (data) => {
        return await postData(`/reviews`, data);
    },

    // Sửa bình luận
    updateReview: async (data) => {
        return await putData(`/reviews`, data);
    },

    // Xóa review
    deleteReview: async (reviewId) => {
        return await deleteData(`/reviews/${reviewId}`);
    }
}
