import {favoriteService} from "./service/FavoriteService.js";

// Click xóa một bộ phim yêu thích
document.getElementById("list-favorite-movies").addEventListener("click", async function (event) {
    if (event.target.classList.contains("remove-favorite")) {
        const movieId = event.target.dataset.id;
        if (movieId !== undefined) {
            const items = this.querySelectorAll(".card-item");
            try {
                const result = await favoriteService.removeFavoriteMovie(movieId);
                // Khi trang chỉ có duy nhất 1 phim
                if (items.length === 1 && currentPage > 1) {
                    currentPage--;
                    window.location.href = `/phim-yeu-thich?page=${currentPage}`;
                }
                await rebuildListFavoriteMovie(this);
            } catch(error) {
                console.log(error);
            }
        }
    }
})

// Click xóa tất cả phim yêu thích
document.addEventListener("click", async function (event) {
    if (event.target.id === "delete-all") {
        try {
            const result = await favoriteService.removeAllFavoriteMovie();
            window.location.href = "/phim-yeu-thich";
        } catch (error) {
            console.log(error)
        }
    }
})

// Xử lý sau khi xóa một movie trên trang
const rebuildListFavoriteMovie = async (listContainer) => {
    try {
        const results = await favoriteService.getFavoriteMovies(currentPage);
        console.log(results)
        buildList(results, listContainer, buildCardFavoriteMovie)
    } catch (error) {
        throw error;
    }
}

// Hàm xây dựng danh sách movie
const buildList = (data, containerEl, buildItemFunc) => {
    if (data.totalElements <= 0) {
        containerEl.innerHTML = "<p class='text-center text-dark-emphasis fs-18'>Chưa có bộ phim nào</p>";
        document.getElementById("delete-all").remove();
        document.querySelector(".pagination .page-item").remove();
        return;
    };
    const movies = data.content;
    containerEl.innerHTML = "";
    movies.forEach(movie => {
        let cardFavorite = buildItemFunc(movie);
        if (cardFavorite.length > 0) {
            containerEl.innerHTML += cardFavorite;
        }
    })
}

// Hàm xây dựng item favorite movie
const buildCardFavoriteMovie = (movie) => {
    return `
            <div class="col-2 card-item position-relative">
                <a href="/phim/${movie.slug}">
                    <img src="/api/images/thumbnail/${movie.thumbnail}" alt="Movie Thumbnail">
                </a>
                <h3 class="mt-3 mb-2">${movie.name + '/' + movie.type}</h3>
                <i data-id="${movie.id}" class="fa-solid fa-heart-circle-xmark fs-26 position-absolute m-0 p-0 top-10 end-20 remove-favorite" style="color: #ef255c;"></i>
            </div>
        `;
}


