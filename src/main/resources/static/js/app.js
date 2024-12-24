const findButton = document.getElementById("find-open");
const topFindContainer = document.getElementById("up");
const bottomFindContainer = document.getElementById("down");
const mainVideo = document.getElementById("video-banner");
const closeFind = document.getElementById("close");
const hidden_visible = document.getElementById("hidden");

let isCheck = true;
findButton.addEventListener("click", function() {
	if (isCheck == isCheck) {
		hidden_visible.style.visibility = "unset";
		topFindContainer.style.top = "0";
		bottomFindContainer.style.bottom = "0";
		document.body.style.overflowY = "hidden";
		isCheck = !isCheck;
	} else {
		hidden_visible.style.visibility = "hidden";
		topFindContainer.style.top = "-100%";
		bottomFindContainer.style.bottom = "-70%";
		isCheck = isCheck;
	}
});
closeFind.addEventListener("click", function() {
	hidden_visible.style.visibility = "hidden";
	document.body.style.overflowY = "auto";
	topFindContainer.style.top = "-100%";
	bottomFindContainer.style.bottom = "-70%";
	// mainVideo.style.transform = "none";
	isCheck = isCheck;
});
// cart
const cartButton = document.getElementById("cart-open");
const hidden_cart = document.getElementById("hidden-cart");
const cart_close = document.getElementById("cart-close");
cartButton.addEventListener("click", function() {
	document.body.style.overflowY = "hidden";
	hidden_cart.style.right = "0";
});
cart_close.addEventListener("click", function() {
	document.body.style.overflowY = "auto";

	hidden_cart.style.right = "-100%";
});
// login
function openNewTabAndNavigate() {
	var urlToNavigate = "/adm/useraccount"; // Đường dẫn bạn muốn truy cập
	var newTab = window.open(urlToNavigate, '_blank');
	newTab.focus(); // Tập trung vào tab mới
}

function searchOnEnter(event) {
            if (event.key === "Enter") {
                // Lấy giá trị từ trường input
                var searchText = document.getElementById("searchInput").value;
                
                // Tạo URL mà bạn muốn điều hướng đến
                var url = "/find/" + searchText;
                
                // Chuyển trang đến URL đã tạo
                window.location.href = url;
            }
        }
function resetCart(element) {
           window.location.reload();
         }


