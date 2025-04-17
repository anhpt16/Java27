import {favoriteService} from "./service/FavoriteService.js";
import {reviewService} from "./service/ReviewService.js";


let reviewModal = bootstrap.Modal.getOrCreateInstance(document.getElementById("review-modal"));

// Xử lý khi nhấn vào nút yêu thích phim
document.querySelector(".btn-favorite").addEventListener("click", () => {
    const movieId = document.querySelector(".btn-favorite").getAttribute("data-id");
    let isFavorite = document.querySelector(".btn-favorite").getAttribute("data-favorite") === "true";
    if (!movieId && !isFavorite) {
        window.location.href = "/login";
        return;
    }
    console.log(isFavorite)
    console.log(movieId)
    if (isFavorite === false) {
        addFavorite(movieId);
    }
    if (isFavorite === true) {
        removeFavorite(movieId);
    }
})

// Xử lý khi hover vào rating
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

// Thêm-Sửa bình luận cho phim
document.getElementById("save-review").addEventListener("click", async function () {
    let rating = document.getElementById("detail-rating").getAttribute("data-rate");
    let content = document.getElementById("detail-content").value.trim();
    const movieId = document.querySelector(".title-movie-detail").getAttribute("data-id");
    console.log(rating);
    console.log(content);
    console.log(movieId);

    // Lấy action
    let action = this.dataset.action;
    let reviewid = this.dataset.reviewid;

    if (rating != null && content !== "" && movieId != null) {
        if (action === "add") {
            try {
                let data = {
                    movieId: movieId,
                    content: content,
                    rating: rating
                }
                console.log(data);
                const result = reviewService.addReview(data);
                reviewModal.hide();
            } catch (error) {
                console.log(error);
            }
        }
        if (action === "update" && reviewid !== null) {
            try {
                let data = {
                    reviewId: reviewid,
                    content: content,
                    rating: rating
                }
                console.log(data);
                const result = reviewService.updateReview(data);
                reviewModal.hide();
            } catch (error) {
                console.log(error);
            }
        }
    }
})

// Sửa-Xóa một bình luận
document.querySelectorAll(".card-review").forEach(review => {
    const reviewId = review.dataset.id;
    const userId = review.querySelector(".avatar-name").dataset.id;
    console.log(`reviewId = ${reviewId} \n userId = ${userId} \n crUserId = ${currentUser.id}`);

    if (reviewId == null || userId == null) return;

    if (currentUser !== null && currentUser.id == userId) {
        const editBtn = review.querySelector(".btn-warning");
        const deleteBtn = review.querySelector(".btn-danger");

        // Sửa một bình luận
        editBtn.addEventListener("click", () => {
            let content = review.querySelector(".review-content").textContent;
            let rating = review.querySelector(".rating").dataset.rate;
            console.log(content)
            document.querySelector("#review-modal #detail-rating").textContent = rating;
            document.querySelector("#review-modal #detail-rating").dataset.rate = rating;
            document.querySelector("#review-modal span.ms-3").classList.remove("d-none");
            document.querySelector("#review-modal span.ms-3").classList.add("d-block");
            document.querySelector("#review-modal #detail-content").value = content;
            // Chuyển modal sang trạng thái save
            document.getElementById("save-review").dataset.action = "update";
            document.getElementById("save-review").dataset.reviewid = reviewId;
            reviewModal.show();
        })

        // Xóa một bình luận
        deleteBtn.addEventListener("click", async () => {
            try {
                const result = await reviewService.deleteReview(reviewId);
            } catch (error) {
                console.log(error);
            }
        })
    }
})


// Hành động khi thực hiện đóng review-modal
document.getElementById("review-modal").addEventListener("hidden.bs.modal", function () {
    document.getElementById("save-review").dataset.action = "add";
    document.getElementById("save-review").dataset.reviewid = "";
    document.getElementById("detail-content").value = "";
    document.getElementById("detail-rating").textContent = "";
    document.getElementById("detail-rating").dataset.rate = "";
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