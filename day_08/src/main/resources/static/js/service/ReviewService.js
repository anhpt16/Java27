import { getData, postData, putData, deleteData} from "./baseApi.js";

export const reviewService = {

    // Thêm bình luận cho phim
    addReview: async (data) => {
        return await postData(`/review`, data);
    },

    // Sửa bình luận
    updateReview: async (data) => {
        return await putData(`/review`, data);
    }


}
