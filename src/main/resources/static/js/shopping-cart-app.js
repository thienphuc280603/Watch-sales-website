
const app = angular.module("app", []);
app.controller("myctrl", function($scope, $http) {
	$scope.cart = {
		items: [],
		add(id) {
		    var item = this.items.find(item => item[0][7] == id);
		
		    if (item) {
		        item[0][10]++;
		        this.saveToLocalStorage();
		        $scope.cart.loadFromLocalStorage();
		    } else {
		        $http.get(`/rest/product/${id}`).then(resp => {		
		            resp.data[0][10] = 1;
		            this.items.push(resp.data);
		            this.saveToLocalStorage();
		             $scope.$apply();
		        }).catch(error => {
		            console.error('Lỗi khi lấy thông tin sản phẩm', error);
		        });
		    }
	},



		remove(id) {
		    var index = this.items.findIndex(item => item[0][7] == id);
		    this.items.splice(index, 1);
		    this.saveToLocalStorage();
		    this.loadFromLocalStorage();
		},

		clear() {
			this.items = []
			this.saveToLocalStorage();
		},
		get count() {
			return this.items
				.map(item => item[0][10])
				.reduce((total, qty) => total += qty, 0);
		},
		get amount() {
			return this.items
				.map(item => item[0][10] * item[0][3])
				.reduce((total, qty) => total += qty, 0);
		},
		saveToLocalStorage() {
			var json = JSON.stringify(angular.copy(this.items));
			localStorage.setItem("cart", json);
		},
		loadFromLocalStorage() {
			var json = localStorage.getItem("cart");
			this.items = json ? JSON.parse(json) : [];
		},
	}
	$scope.cart.loadFromLocalStorage();
	
	Object.defineProperty($scope.cart, 'amounts', {
	    get: function() {
	        return this.items
	            .map(item => item[0][10] * item[0][3])
	            .reduce((total, qty) => total += qty, 0);
	    }
	    
	});
	$scope.totalPrice
	$scope.$watch('cart.amounts', function (newAmounts, oldAmounts) {
	    if (newAmounts !== oldAmounts) {
	        $scope.totalPrice = newAmounts;
	        return;
	    }
	    $scope.totalPrice = oldAmounts;
	});

	
	 $scope.discount =0;
	 $scope.discountUserId="";
	 $scope.payment ="";
	 $scope.active="";
	 $scope.checkQuantity = true;
	$scope.message = {
	    messAddress: "",
	    messCartCount: "",
	    messDiscount:"",
	};

	$scope.order = {
	 	 address: "",
	 	 
	    purchase: function() {
			var url = "/";
			$scope.payment ="COD";
			$scope.active= true;
			if ($scope.cart.count === 0) { 
	             $scope.message.messCartCount = "Giỏ hàng của bạn đang trống.";	              
	            return;
	         }
	        if ($scope.order.address === "") {
	           $scope.message.messAddres = "Vui lòng nhập địa chỉ trước khi đặt hàng.";
	            return;
	        }
	       	$scope.message.messCartCount = "";
	       	$scope.message.messAddress = "";
	       	angular.forEach($scope.cart.items, function (item) {
			    var quantitys = item[0][9] - item[0][10];
			    if (quantitys < 0) {
			        $scope.message.messCartCount = "Số lượng hàng trong kho không đủ";
			         $scope.checkQuantity = false;
			        return;
			    }
			});
			if( $scope.checkQuantity === false){
				$scope.checkQuantity = true;
				return;
			};
			setQuantity();
	        checkout();
	         setTimeout(function() {					
				location.href = url;
			}, 2500);
	       
	    },
	    
	};
	
	$scope.clickClose = function () {
	    $scope.message.messCartCount = "";
	   	$scope.message.messAddres ="";
	    $scope.message.messDiscount="";
	    $scope.order.address="";
	};
	

	$scope.vnPay = {
	    clickVNPay: function () {		
			$scope.payment = "VNPay";
			$scope.active= false;
	        // Gọi hàm calculateTotalPrice() và nhận giá trị
	      if ($scope.cart.count === 0) { 
		        $scope.message.messCartCount = "Giỏ hàng của bạn đang trống.";	              
		        return;
		    }
		
		    if ($scope.order.address === "") {
		        $scope.message.messAddress = "Vui lòng nhập địa chỉ trước khi đặt hàng.";
		        return;
		    }
		
		    $scope.message.messCartCount = "";
		    $scope.message.messAddress = "";
		
		    angular.forEach($scope.cart.items, function (item) {
		        var quantitys = item[0][9] - item[0][10];
		        if (quantitys < 0) {
		            $scope.message.messCartCount = "Số lượng hàng trong kho không đủ";
		            $scope.checkQuantity = false;
		            return;
		        }
		    });
		
		    if ($scope.checkQuantity === false) {
		        $scope.checkQuantity = true;
		        return;
		    }
	        
	        alert("Xác nhận đặt hàng")
	       
	         $http.get("/apipayment/calculateTotalPrice/" + $scope.totalPrice)
            .then(function(response) {
                 $http.get("/apipayment/createPayment")
			        .then(function (response) {
						setQuantity();
						checkout();
			            var paymentUrl = response.data.url; // Lấy giá trị từ trường 'url'
			            location.href = paymentUrl;
			             
			        })
			        .catch(function (error) {
			            console.error(error);
			        });
            })
            .catch(function(error) {
                // Xử lý lỗi nếu cần
                console.error(error);
            });
	   	},
	};
	$scope.discountUser = {
	    discount: "",
	    clickDiscountUser: function () {
	        var self = this; // Lưu trữ ngữ cảnh của $scope
	
	        $http.get("/api/discountUser")
	            .then(function (response) {
	                var discountUser = response.data;
	                var discountId = discountUser.id;
	                var discountAmount = discountUser.discountAmount;
					$scope.discountUserId = discountId;
	                if (discountId === self.discount) {
						$scope.discount = discountAmount * 100;
	                    $scope.totalPrice = $scope.cart.amounts - ($scope.cart.amounts * discountAmount);	                    
	                    self.discount = "";
	                    $scope.message.messDiscount="";
	                    

	                } else{
						$scope.message. messDiscount="Mã khuyến mãi không chính xác hoặc không tồn tại";
						self.discount = ""
					}
	            })
	            .catch(function (error) {
					$scope.message. messDiscount="Mã khuyến mãi không chính xác hoặc không tồn tại";
					self.discount = ""
	                console.error(error);
	            });
	           
	    }
	};
	function checkout(){
		$http.get("/api/user")
	    .then(function(resp) {	
			if (resp.data) {
			    var order = {
			        date: new Date(),
			        user: resp.data.userId,
			        address: $scope.order.address,
			        phone: resp.data.phone,
			        price:  $scope.totalPrice,
			        payment: $scope.payment,
			        active: $scope.active,
			        get items() {
			            return $scope.cart.items.map(item => {
			                return {
			                    product: { productsId: item[0][7] },
			                    quantity: item[0][10],
			                    price: item[0][3] * item[0][10],
			                };
			            });
			        },
			    };
			} else {
			    console.log("resp.data is null or undefined");
			}

	                // Gọi API để đặt hàng
	        $http.post("/api/orders", order)
	        .then(function(resp) {                    
		        $scope.cart.clear();
		        $scope.order.address="";               
		        $scope.message.messCartCount="Đơn hàng của bạn đã được tiếp nhận";     
	         }).catch(function(error) {                        
	            	console.log(error);
	         });
	         if($scope.discountUserId != ""){
				$http.delete(`/api/deleteDiscountUser/${$scope.discountUserId}`)
				.then(function (response) {
						     
				})
				.catch(function (error) {
					console.error("Error deleting product:", error);
				});		 
			 }
	       		
	    }).catch(function(error) {
			console.log("Lỗi khi lấy thông tin người dùng:", error);
	        alert("Lỗi khi lấy thông tin người dùng");
	        console.log(error);
	    });
	}
	
	function setQuantity(){
		angular.forEach($scope.cart.items, function (item) {
	        var quantitys = item[0][9] - item[0][10];
	        $http.get(`/getProductId/${item[0][7]}` )
	            .then(function (response) {
	             
		             var quantityData = {
						 productsId:response.data[0][0],
					    name: response.data[0][1],
					    price: response.data[0][2],
					    categorizes: {
					        categorizesId: response.data[0][3],
					        // Thêm các trường khác của Categorizes nếu cần
					    },
					    discountsProducts: {
					        discountsProductsId: response.data[0][4],
					        // Thêm các trường khác của DiscountsProducts nếu cần
					    },
					    brand: {
					        brandId: response.data[0][5],
					        // Thêm các trường khác của Brands nếu cần
					    },
					    describe: response.data[0][6],
					    gender: {
					        genderId: response.data[0][7],
					        // Thêm các trường khác của Genders nếu cần
					    },
					    active: response.data[0][8],
					    image: response.data[0][9],
					    quantity: quantitys,
					};

	 				$http.put("/api/putProduct" , quantityData)
	                .then(function(resp) {
	                
	                })
	                .catch(function(error) {
	                    // Xử lý khi có lỗi
	                    console.error(error);
	                });
	                // Bây giờ bạn có thể sử dụng quantityData như cần thiết
	            })
	            .catch(function (error) {
	                console.error(error);
	            });
	    });
	}
 
	// Hàm tính toán tổng giá trị từ giỏ hàng
	
});

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
