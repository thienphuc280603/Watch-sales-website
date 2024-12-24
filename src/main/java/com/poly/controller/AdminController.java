package com.poly.controller;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.dao.CategorizesDAO;
import com.poly.dao.ProductsDAO;
import com.poly.entity.Brands;
import com.poly.entity.Categorizes;
import com.poly.entity.Colors;
import com.poly.entity.DiscountsProducts;
import com.poly.entity.Genders;
import com.poly.entity.Images;
import com.poly.entity.Item;
import com.poly.entity.Order;
import com.poly.entity.Products;
import com.poly.entity.Sizes;
import com.poly.entity.User;
import com.poly.serviceimpl.AuthorityServiceImpl;
import com.poly.serviceimpl.BrandServiceImpl;
import com.poly.serviceimpl.CategoryServiceImpl;
import com.poly.serviceimpl.ColorServiceImpl;
import com.poly.serviceimpl.DiscountsProductsServiceImpl;
import com.poly.serviceimpl.GenderServiceImpl;
import com.poly.serviceimpl.ImageServiceImpl;
import com.poly.serviceimpl.ItemServiceImpl;
import com.poly.serviceimpl.OrderServiceImpl;
import com.poly.serviceimpl.ProductServiceImpl;
import com.poly.serviceimpl.SizeServiceImpl;
import com.poly.serviceimpl.UserServiceImpl;

@Controller
public class AdminController {
	@Autowired
	UserServiceImpl userService;
	@Autowired
	OrderServiceImpl orderService;
	@Autowired
	ItemServiceImpl itemService;
	@Autowired
	DiscountsProductsServiceImpl discountsProductsService;
	@Autowired
	AuthorityServiceImpl authorityService;

	@Autowired
	ProductServiceImpl productServiceImpl;

	@Autowired
	GenderServiceImpl genderServiceImpl;

	@Autowired
	CategoryServiceImpl categoryServiceImpl;

	@Autowired
	BrandServiceImpl brandServiceImpl;

	@Autowired
	SizeServiceImpl sizeServiceImpl;

	@Autowired
	ImageServiceImpl imageServiceImpl;

	@Autowired
	ColorServiceImpl colorServiceImpl;

	@ModelAttribute
	public void checkUser(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		model.addAttribute("userInfo", user);
	}
	//Role
	@RequestMapping("/role")
	public String role() {
		return "main/exception/ExceptionRole";
	}

	// AccountInfo
	@RequestMapping("/adm/useraccount")
	public String UserAccount() {
		return "main/admin/accountInfo";
	}

	// UPDATE AccountInfo
	@RequestMapping("/adm/action-update/info-admin")
	public String getActionUpdateInfoAdmin(Model model, HttpServletRequest request,
			@ModelAttribute("userInfo") User user) {
		User userUpdate = userService.updateUser(user);
		HttpSession oldSession = request.getSession(false);
		if (oldSession != null) {
			oldSession.invalidate();

			HttpSession newSession = request.getSession(true);
			newSession.setAttribute("user", userUpdate);
		}

		return "redirect:/adm/useraccount";
	}

	// AdminMember
	@RequestMapping("/adm/adminmember")
	public String AdminMember(Model model) {
		model.addAttribute("adminsiterList", userService.findListByRole("administer"));
		model.addAttribute("admindeployList", userService.findListByRole("staff"));
		return "main/admin/adminmember";
	}

	// Bill
	@RequestMapping("/adm/bill")
	public String Bill(Model model) {
		model.addAttribute("orderListNew", orderService.findByActiveAndStatus(true, false));
		model.addAttribute("orderListConfirm", orderService.findByActiveAndStatus(true, true));
		model.addAttribute("orderListCancel", orderService.findByActiveAndStatus(false, false));
		return "main/admin/bill";
	}

	// Bill Action
	@RequestMapping("/adm/orderAction/change-order/{change}/{orderId}")
	public String BillActionConfirm(Model model, HttpServletRequest request,
			@PathVariable("orderId") int orderId, @PathVariable("change") String change) {

		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("user");
		if(userLogin.getRole().equals("user") || userLogin == null) {
			return "redirect:/role";
		}else {
			Order orderChange = orderService.findByOrderId(orderId);
			if (change.equals("confirm")) {
				orderChange.setStatus(true);
			} else {
				orderChange.setActive(false);
			}
			orderService.saveOrder(orderChange);
		}
		
		model.addAttribute("orderListNew", orderService.findByActiveAndStatus(true, false));
		model.addAttribute("orderListConfirm", orderService.findByActiveAndStatus(true, true));
		model.addAttribute("orderListCancel", orderService.findByActiveAndStatus(false, false));
		return "redirect:/adm/bill";
	}

	// UserList
	@RequestMapping("/adm/userlist")
	public String UserList(Model model, @RequestParam(defaultValue = "0") int page) {
		int pageSize = 20;
		Page<User> userPage = userService.getUsersByRole("user", page, pageSize);
		model.addAttribute("users", userPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", userPage.getTotalPages());
		return "main/admin/userAccount";
	}

	// UserList
	@RequestMapping("/adm/userlist/change-role/{userId}/{role}")
	public String UserChangeRole(Model model, HttpServletRequest request,
			@PathVariable("userId") String userId,
			@PathVariable("role") String role) {

		//Change role

		HttpSession session = request.getSession();
		User userLogin = (User) session.getAttribute("user");
		if(userLogin.getRole().equals("administer") && userLogin.getUserId() != userId) {

			User userChangeRole = userService.findByUserId(userId);
			if(role.equals("administer")) {
				userChangeRole.setRole("administer");
			}else if(role.equals("staff")) {
				userChangeRole.setRole("staff");
			}else if(role.equals("user")) {
				userChangeRole.setRole("user");
			}
			userService.saveUser(userChangeRole);
			return "redirect:/adm/adminmember";
		}else {
			return "redirect:/role";
		}
	}

	// BillDetail
	@RequestMapping("/adm/detail-order/{orderId}")
	public String getDetailOrder(Model model, @PathVariable("orderId") int orderId) {
		List<Item> itemList = itemService.findAllItemByOrder(orderId);
		model.addAttribute("itemList", itemList);
		model.addAttribute("order", orderService.findByOrderId(orderId));
		return "main/admin/admin-detail-order";
	}

	// DiscountProducts
	@RequestMapping("/adm/discountproduct")
	public String DiscountProduct(Model model) {
		model.addAttribute("discountsProducts", new DiscountsProducts());
		model.addAttribute("discountsProductsList", discountsProductsService.getAllDiscountsProducts());
		return "main/admin/discountProduct";
	}

	// DISCOUNTS PRODUCTS ACTION
	@RequestMapping("/adm/action-add/discount-product")
	public String getActionAddDiscountsProducts(Model model,
	        @ModelAttribute("discountsProducts") @Valid DiscountsProducts discountsProducts, BindingResult bindingResult) {
	    
	    if (bindingResult.hasErrors()) {
			model.addAttribute("discountsProductsList", discountsProductsService.getAllDiscountsProducts());
	        return "main/admin/discountproduct";
	    }
	    discountsProductsService.createDiscountsProducts(discountsProducts);
	    return "redirect:/adm/discountproduct";
	}

	@RequestMapping("/adm/action-update/discount-product")
	public String getActionUpdateDiscountsProducts(Model
			model,
	        @ModelAttribute("discountsProducts") @Valid DiscountsProducts discountsProducts, BindingResult bindingResult) {
	    
	    if (bindingResult.hasErrors()) {
			model.addAttribute("discountsProductsList", discountsProductsService.getAllDiscountsProducts());
	        return "main/admin/discountproduct";
	    }

	    discountsProductsService.updateDiscountsProducts(discountsProducts, discountsProducts.getDiscountsProductsId());
	    return "redirect:/adm/discountproduct";
	}
	@RequestMapping("/adm/action-delete/discount-product")
	public String getActionDeleteDiscountsProducts(Model model,
			@ModelAttribute("discountsProducts") DiscountsProducts discountsProducts) {
		discountsProductsService.deleteDiscountsProducts(discountsProducts.getDiscountsProductsId());
		return "redirect:/adm/discountproduct";
	}

	@Autowired
	ProductsDAO pdao;

	@GetMapping("/adm/product")
	public String AdminProduct(
	    Model model,
	    @RequestParam(name = "page", required = false, defaultValue = "0") int page,
	    @RequestParam(name = "keyword", required = false) String keyword) {
	    
	    int pageSize = 6;
	    Page<Products> productPage;

	    if (keyword != null && !keyword.isEmpty()) {
	        // Xử lý tìm kiếm nếu có từ khóa
	        productPage = pdao.findByNameContainingIgnoreCase(keyword, PageRequest.of(page, pageSize));
	    } else {
	        // Lấy toàn bộ nếu không có từ khóa
	        productPage = pdao.findAll(PageRequest.of(page, pageSize));
	    }

	    List<Products> listproduct = productPage.getContent();
	    model.addAttribute("listProductAdmin", listproduct);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", productPage.getTotalPages());
	    model.addAttribute("productPage", productPage);
	    return "main/admin/admin-product";
	}

	@GetMapping("/adm/editproduct/{id}")
	public String Product(@PathVariable("id") Integer id, Model model) {
		Products product = productServiceImpl.getProductByID(id);
		model.addAttribute("editProductAdmin", product);
		List<Genders> gender = genderServiceImpl.getAllGenders();
		model.addAttribute("listgender", gender);
		List<Categorizes> cate = categoryServiceImpl.getAllCategory();
		model.addAttribute("listcate", cate);
		List<Brands> brand = brandServiceImpl.getAllBrand();
		model.addAttribute("listbrand", brand);
		List<DiscountsProducts> discountsProducts = discountsProductsService.getAllDiscountsProducts();
		model.addAttribute("listDiscoutProduct", discountsProducts);
		return "main/admin/detail-productadmin";
	}
	
	@GetMapping("/adm/editListImage/{id}")
	public String ProductImage(@PathVariable("id") Integer id,Model model, HttpSession session) {
		List<Images> listImage = imageServiceImpl.getImageByProductId(id);
		model.addAttribute("listImageProduct", listImage);
		session.setAttribute("productId", id);
		return "main/admin/imageProduct";
	}

	@GetMapping("/adm/gender")
	public String cate(Model model) {
		List<Genders> cate = genderServiceImpl.getAllGenders();
		model.addAttribute("listgender", cate);
		return "main/admin/gender";
	}

	@PostMapping("/admin/products/add")
	public String addProduct(@ModelAttribute("product") Products newProduct, Model model) {
		List<Products> listproduct = productServiceImpl.getAllProduct();
		model.addAttribute("listProductAdmin", listproduct);
		productServiceImpl.addProduct(newProduct);
		return "main/admin/admin-product";
	}

	@RequestMapping("/adm/product/update")
	public String updateProduct(@ModelAttribute Products editProductAdmin, Model model) {
		productServiceImpl.updateProduct(editProductAdmin);
		List<Products> listproduct = productServiceImpl.getAllProduct();
		model.addAttribute("listProductAdmin", listproduct);
		return "redirect:/adm/product";
	}

	@RequestMapping("/adm/product/clear")
	public String clearProductForm(Model model) {
		Products newProduct = new Products();
		model.addAttribute("editProductAdmin", newProduct);
		List<Genders> gender = genderServiceImpl.getAllGenders();
		model.addAttribute("listgender", gender);
		List<Categorizes> cate = categoryServiceImpl.getAllCategory();
		model.addAttribute("listcate", cate);
		List<Brands> brand = brandServiceImpl.getAllBrand();
		model.addAttribute("listbrand", brand);
		List<DiscountsProducts> discountsProducts = discountsProductsService.getAllDiscountsProducts();
		model.addAttribute("listDiscoutProduct", discountsProducts);
		return "main/admin/detail-productadmin";
	}

	@RequestMapping("/adm/product/create")
	public String createProduct(@ModelAttribute Products newProduct, Model model) {
		productServiceImpl.addProduct(newProduct);
		List<Products> listProduct = productServiceImpl.getAllProduct();
		model.addAttribute("listProductAdmin", listProduct);
		return "redirect:/adm/product";
	}

	@GetMapping("/adm/size")
	public String getPageSize(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {

		int pageSize = 10;
		Page<Sizes> sizePage = sizeServiceImpl.findAll(PageRequest.of(page, pageSize));
		List<Sizes> listSize = sizePage.getContent();
		model.addAttribute("listSize", listSize);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", sizePage.getTotalPages());
		model.addAttribute("sizePage", sizePage);
		return "main/admin/sizeProducts";
	}

	@GetMapping("/adm/editSize/{id}")
	public String editSize(@PathVariable("id") Integer id, Model model) {
		Sizes size = sizeServiceImpl.getSizeById(id);
		model.addAttribute("size", size);
		return "main/admin/sizeDetails";
	}

	@RequestMapping("/adm/size/update")
	public String updateSize(@ModelAttribute Sizes editSize, Model model) {
		sizeServiceImpl.updateSize(editSize);
		List<Sizes> listSize = sizeServiceImpl.getAllSizes();
		model.addAttribute("listSize", listSize);
		return "redirect:/adm/size";
	}

	@PostMapping("/adm/size/create")
	public String createSize(@ModelAttribute Sizes size, Model model) {
		sizeServiceImpl.addSize(size);
		Sizes sizes = new Sizes();
		model.addAttribute("size", sizes);
		return "redirect:/adm/size";
	}

	@RequestMapping("/adm/size/clear")
	public String clearSizeForm(Model model) {
		Sizes size = new Sizes();
		model.addAttribute("size", size);
		return "main/admin/sizeDetails";
	}

	@GetMapping("/adm/deleteSize/{id}")
	public String deleteSize(@PathVariable("id") Integer id, Model model) {
		sizeServiceImpl.deleteSize(id);
		List<Sizes> listSize = sizeServiceImpl.getAllSizes();
		model.addAttribute("listSize", listSize);
		return "main/admin/sizeProducts";
	}

	@GetMapping("/adm/image")
	public String AdminImage(Model model, @RequestParam(name = "page", required = false, defaultValue = "0") int page) {

		int pageSize = 7;
		Page<Images> imagesPage = imageServiceImpl.getAllImages(PageRequest.of(page, pageSize));
		List<Images> listimage = imagesPage.getContent();
		model.addAttribute("listImage", listimage);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", imagesPage.getTotalPages());
		model.addAttribute("imagesPage", imagesPage);
		return "main/admin/image";
	}

	@GetMapping("/adm/editImage/{id}")
	public String editImage(@PathVariable("id") Integer id, Model model) {
		Images image = imageServiceImpl.getImageById(id);
		model.addAttribute("image", image);
		List<Colors> listColorsImage = colorServiceImpl.getAllColorImage();
		model.addAttribute("listColorImage", listColorsImage);
		return "main/admin/imageDetails";
	}

	@RequestMapping("/adm/image/update")
	public String updateImage(@ModelAttribute Images image, Model model) {
		imageServiceImpl.updateImage(image);
		List<Images> listimage = imageServiceImpl.getAllImage();
		model.addAttribute("listImage", listimage);
		return "redirect:/adm/product";
	}

	@PostMapping("/adm/image/create")
	public String createImage(@ModelAttribute Images images, Model model) {
		imageServiceImpl.addImage(images);
		Images image = new Images();
		model.addAttribute("image", image);
		List<Colors> listColorsImage = colorServiceImpl.getAllColorImage();
		model.addAttribute("listColorImage", listColorsImage);
		return "redirect:/adm/product";
	}

	@RequestMapping("/adm/image/clear")
	public String clearImageForm(Model model, HttpSession session) {
		Integer productId = (Integer) session.getAttribute("productId");
		Products product = productServiceImpl.getProductByID(productId);
		Images image = new Images();
		image.setProducts(product);
		model.addAttribute("image", image);
		List<Colors> listColorsImage = colorServiceImpl.getAllColorImage();
		model.addAttribute("listColorImage", listColorsImage);
		return "main/admin/imageDetails";
	}

	@RequestMapping("/adm/image/delete")
	public String deleteImage(@ModelAttribute Images images, Model model) {
		imageServiceImpl.deleteImage(images);
		return "redirect:/adm/product";
	}

	@GetMapping("/adm/thongke")
	public String thongke(@RequestParam(required = false) String startDate,
	                       @RequestParam(required = false) String endDate,
	                       @RequestParam(required = false) Integer selectedYear, // Thêm tham số selectedYear
	                       Model model) {
	    
	    List<Object[]> getthongke;

	    if (startDate == null || endDate == null) {
	        getthongke = orderService.getAllThongke();
	    } else {
	        getthongke = orderService.getThongke(startDate, endDate);
	    }

	    model.addAttribute("listOrders", getthongke);
	    model.addAttribute("startDate", startDate);
	    model.addAttribute("endDate", endDate);

	    // Sử dụng giá trị năm từ tham số truyền vào
	    int currentYear = Year.now().getValue(); // Lấy giá trị năm hiện tại
	    int year = (selectedYear != null) ? selectedYear : currentYear; // Nếu selectedYear có giá trị thì sử dụng, ngược lại sử dụng năm hiện tại

	    // Truyền giá trị năm vào hàm
	    model.addAttribute("thang1", orderService.getTotalPriceForMonthAndYear(1, year));
	    model.addAttribute("thang2", orderService.getTotalPriceForMonthAndYear(2, year));
	    model.addAttribute("thang3", orderService.getTotalPriceForMonthAndYear(3, year));
	    model.addAttribute("thang4", orderService.getTotalPriceForMonthAndYear(4, year));
	    model.addAttribute("thang5", orderService.getTotalPriceForMonthAndYear(5, year));
	    model.addAttribute("thang6", orderService.getTotalPriceForMonthAndYear(6, year));
	    model.addAttribute("thang7", orderService.getTotalPriceForMonthAndYear(7, year));
	    model.addAttribute("thang8", orderService.getTotalPriceForMonthAndYear(8, year));
	    model.addAttribute("thang9", orderService.getTotalPriceForMonthAndYear(9, year));
	    model.addAttribute("thang10", orderService.getTotalPriceForMonthAndYear(10, year));
	    model.addAttribute("thang11", orderService.getTotalPriceForMonthAndYear(11, year));
	    model.addAttribute("thang12", orderService.getTotalPriceForMonthAndYear(12, year));
	    model.addAttribute("year", currentYear);

	    return "main/admin/statistical";
	}


	@GetMapping("/adm/thongkeuser")
	public String thongkeuser(@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, Model model) {
		List<Object[]> getthongke = orderService.getThongkeTheoUser(startDate, endDate);
		model.addAttribute("listOrders", getthongke);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		return "main/admin/StatisticsByUser";
	}

}
