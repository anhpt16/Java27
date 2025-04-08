const BASE_URL = "http://localhost:8081/api";

// Get Data
export const getData = async (url) => {
    try {
        const response = await axios.get(`${BASE_URL}${url}`);
        return response.data;
    } catch (error) {
        console.log("Error GET api: ");
        throw error;
    }
}

// Post Data
export const postData = async (url, data) => {
    try {
        const response = await axios.post(`${BASE_URL}${url}`, data || {});
        return response.data;
    } catch (error) {
        console.error("Error POST api: ");
        throw error;
    }
};

// Put Data
export const putData = async (url, data) => {
    try {
        const response = await axios.put(`${BASE_URL}${url}`, data);
        return response.data;
    } catch (error) {
        console.error('Error PUT api:');
        throw error;
    }
};

// Delete Data
export const deleteData = async (url) => {
    try {
        const response = await axios.delete(`${BASE_URL}${url}`);
        return response;
    } catch (error) {
        console.error('Error DELETE api:');
        throw error;
    }
};