import {favoriteService} from "./service/FavoriteService.js";
import {reviewService} from "./service/ReviewService.js";

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

const stars = document.querySelectorAll(".review-rating .fa-star");
stars.forEach(star => {
    star.addEventListener("mouseenter", function (event) {
        const rating = event.target.dataset.num;
        if (rating !== undefined) {
            for (let i = 0; i < stars.length; i++) {
                if (i < rating) {
                    stars[i].classList.add("active-star");
                } else {
                    stars[i].classList.remove("active-star");
                }
            }
        }
    });
});
stars.forEach(star => {
    star.addEventListener("click", function (event) {
        const rating = event.target.dataset.num;
        const detail = document.getElementById("detail-rating");
        if (rating !== undefined) {
            for (let i = 0; i < stars.length; i++) {
                if (i < rating) {
                    stars[i].classList.add("choose-star");
                } else {
                    stars[i].classList.remove("choose-star");
                }
            }
        }
        const parentSpan = detail.closest("span.ms-3");
        console.log(parentSpan)
        if (parentSpan && parentSpan.classList.contains("d-none")) {
            parentSpan.classList.remove("d-none");
            parentSpan.classList.add("d-block");
        }
        if (detail !== undefined) {
            detail.textContent = rating;
            detail.setAttribute("data-rate", rating);
        }
    });
});
stars.forEach(star => {
    star.addEventListener("mouseleave", function (event) {
        stars.forEach(star => {
            if (star.classList.contains("active-star")) {
                star.classList.remove("active-star");
            }
        })
    });
});

document.getElementById("save-review").addEventListener("click", async function () {
    const rating = document.getElementById("detail-rating").getAttribute("detail-rating");
    const content = document.getElementById("detail-content").value.trim();
    const movieId = document.querySelector(".title-movie-detail").getAttribute("data-id");
    if (rating != null && content !== "" && movieId != null) {
        try {
            let data = {
                movieId: movieId,
                content: content,
                rating: rating
            }
            const result = reviewService.addReview(data);
        } catch (error) {
            console.log(error);
        }
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