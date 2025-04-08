import {favoriteService} from "./service/FavoriteService.js";

document.querySelector(".btn-favorite").addEventListener("click", () => {
    const movieId = document.querySelector(".btn-favorite").getAttribute("data-id");
    let isFavorite = document.querySelector(".btn-favorite").getAttribute("data-favorite") === "true";
    console.log(isFavorite)
    console.log(movieId)
    if (isFavorite === false) {
        addFavorite(movieId);
    }
    if (isFavorite === true) {
        removeFavorite(movieId);
    }
})

const addFavorite = async (movieId) => {
    try {
        const result = await favoriteService.addFavoriteMovie(movieId);
        rebuildFavoriteBtn("addFavorite", movieId);
    } catch (error) {
        console.log(error);
    }

}

const removeFavorite = async (movieId) => {
    try {
        const result = await favoriteService.removeFavoriteMovie(movieId);
        rebuildFavoriteBtn("removeFavorite", movieId)
    } catch (error) {
        console.log(error);
    }
}

const rebuildFavoriteBtn = (status, movieId) => {
    const favoriteBtnEl = document.querySelector(".btn-favorite");
    const icon = favoriteBtnEl.querySelector("i");
    const text = favoriteBtnEl.querySelector("span");
    try {
        if (status === "addFavorite") {
            icon.classList.replace("fa-regular", "fa-solid");
            text.innerText = "Đã thích";
            favoriteBtnEl.setAttribute("data-favorite", "true");
        }
        if (status === "removeFavorite") {
            icon.classList.replace("fa-solid", "fa-regular");
            text.innerText = "Yêu thích";
            favoriteBtnEl.setAttribute("data-favorite", "false");
        }
    } catch(error) {
        console.log("Error in rebuildFavoriteBtn()");
        throw error;
    }
}