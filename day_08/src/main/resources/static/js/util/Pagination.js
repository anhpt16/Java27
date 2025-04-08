import {getData} from "../service/baseApi.js";




export const getDataPagination = async (baseUrl, queryString) => {
    let api = baseUrl + queryString;
    try {
        const data = await getData(api);
        return data;
    } catch (error) {
        throw error;
    }
}

