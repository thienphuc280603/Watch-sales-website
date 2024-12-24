//DisCountsProducts
var editButtonsDiscountProduct = document.querySelectorAll('.edit-button-DiscountProduct');

editButtonsDiscountProduct.forEach(function(button) {
	button.addEventListener('click', function(event) {

		event.preventDefault();

		var idDiscountProduct = button.getAttribute('data-idDiscountProduct');

		var xhr = new XMLHttpRequest();
		xhr.open('GET', '/api/discount/product/' + idDiscountProduct, true);
		xhr.onreadystatechange = function() {
			if (xhr.readyState === 4) {
				if (xhr.status === 200) {
					var discountProduct = JSON.parse(xhr.responseText);

					document.querySelector('input[name="discountsProductsId"]').value = discountProduct.discountsProductsId;
					document.querySelector('input[name="discountAmount"]').value = discountProduct.discountAmount;
					var discountDateS = new Date(discountProduct.dateStart);
					var formattedDateS = discountDateS.getFullYear() + '-' +
						('0' + (discountDateS.getMonth() + 1)).slice(-2) + '-' +
						('0' + discountDateS.getDate()).slice(-2);
					document.getElementById('dateStartInput').value = formattedDateS;

					var discountDateE = new Date(discountProduct.dateEnd);
					var formattedDateE = discountDateE.getFullYear() + '-' +
						('0' + (discountDateE.getMonth() + 1)).slice(-2) + '-' +
						('0' + discountDateE.getDate()).slice(-2);
					document.getElementById('dateEndInput').value = formattedDateE;

				} else if (xhr.status === 404) {
					// Xử lý trường hợp không tìm thấy dữ liệu
				} else {
					// Xử lý các trường hợp lỗi khác
				}
			}
		};
		xhr.send();
		document.getElementById('BtnAddDiscountProduct').style.display = 'none';
		document.getElementById('BtnUpdateDiscountProduct').style.display = 'inline-block';
		document.getElementById('BtnDeleteDiscountProduct').style.display = 'inline-block';
	});
});


function scrollToBottom() {
	window.scrollTo(0, document.body.scrollHeight);
}

//Info 
    function clearForm(event) {
        document.querySelector('input[name="fullName"]').value = '';
        document.querySelector('input[name="email"]').value = '';
        document.querySelector('input[name="address"]').value = '';
        document.querySelector('input[name="phone"]').value = '';

        document.querySelector('input[name="gender"][value="true"]').checked = true;
        document.querySelector('input[name="gender"][value="false"]').checked = false;
        
        event.preventDefault();
    }



