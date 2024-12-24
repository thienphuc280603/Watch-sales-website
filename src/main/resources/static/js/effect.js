window.addEventListener("scroll", function() {
	var imgRight = document.querySelector(".img_right img");
	var scrollY = window.scrollY;

	if (scrollY >= 50) {
		imgRight.style.top = "100px";
	} else {
		imgRight.style.top = "200px";
	}

});

window.addEventListener("scroll", function() {
	var imgRight = document.querySelector(".img_left img");
	var scrollY = window.scrollY;

	if (scrollY >= 50) {
		imgRight.style.top = "100px";
	} else {
		imgRight.style.top = "200px";
	}
});


// Hiển thị lớp phủ và dòng chữ "Đã thêm vào giỏ hàng" khi ấn vào nút
$(document).ready(function() {
    $("button[ng-click^='cart.add']").click(function() {
        $(".overlay").fadeIn();
        $(".added-message").fadeIn();
        setTimeout(function() {
            $(".overlay").fadeOut();
            $(".added-message").fadeOut();
        }, 2000); // Ẩn lớp phủ và dòng chữ sau 2 giây
    });
});
