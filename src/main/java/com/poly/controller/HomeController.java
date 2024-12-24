package com.poly.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.VNPay.TransactionDTO;
import com.poly.dao.BrandsDAO;
import com.poly.dao.CategorizesDAO;
import com.poly.dao.ColorsDAO;
import com.poly.dao.GendersDAO;
import com.poly.dao.ImageDAO;
import com.poly.dao.ProductsDAO;
import com.poly.dao.UserDAO;
import com.poly.entity.DiscountUser;
import com.poly.entity.Genders;
import com.poly.entity.Order;
import com.poly.entity.Root;
import com.poly.entity.User;
import com.poly.serviceimpl.BrandServiceImpl;
import com.poly.serviceimpl.CategoryServiceImpl;
import com.poly.serviceimpl.ColorServiceImpl;
import com.poly.serviceimpl.CookieService;
import com.poly.serviceimpl.DiscountUserImpl;
import com.poly.serviceimpl.EmailService;
import com.poly.serviceimpl.GenderServiceImpl;
import com.poly.serviceimpl.ImageServiceImpl;
import com.poly.serviceimpl.ItemServiceImpl;
import com.poly.serviceimpl.OrderServiceImpl;
import com.poly.serviceimpl.ProductServiceImpl;
import com.poly.serviceimpl.SizeServiceImpl;
import com.poly.serviceimpl.UserServiceImpl;

@Controller
public class HomeController {
	int loai;
	@Autowired
	ProductServiceImpl product;
	@Autowired
	GenderServiceImpl gen;
	@Autowired
	BrandServiceImpl brand;
	@Autowired
	CategoryServiceImpl cate;
	@Autowired
	ColorServiceImpl color;
	@Autowired
	ImageServiceImpl image;
	@Autowired
	SizeServiceImpl size;
	@Autowired
	OrderServiceImpl orderImpl;
	@Autowired
	ItemServiceImpl itemImpl;
	@Autowired
	UserServiceImpl userImpl;
	@RequestMapping("/")
	public String home(Model model) {
		List<Object[]> listsp = product.listProduct();
		model.addAttribute("listsp", listsp);
		List<Object[]> listTrending =product.listTrending();
		model.addAttribute("listTrending", listTrending);
		List<Object[]> list = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			list.add(listsp.get(i));
		}
		model.addAttribute("listBRACELETS", list);
		List<Object[]> listgg = product.listGG();
		List<Object[]> listTop10GG = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			listTop10GG.add(listgg.get(i));
		}
		model.addAttribute("listTop10GG", listTop10GG);
		
				footer(model);
		return "main/user/index";
	}
	@RequestMapping("/Products-discounts")
	public String listGG(Model model) {
		List<Object[]> listgg = product.listGG();
		model.addAttribute("listvt", listgg);
		fillthongtin(model);
		return "main/user/more";
	}
	public void footer(Model model) {
		List<Object[]> listspbyCasio = product.listProductByType("Casio");
		model.addAttribute("listcasio", listspbyCasio);
		List<Object[]> listspbyBabyG = product.listProductByType("Baby-G");
		model.addAttribute("listBabyG", listspbyBabyG);
		List<Object[]> listspbyGShock = product.listProductByType("Casio G-Shock");
		model.addAttribute("listGShock", listspbyGShock);
		List<Object[]> listspbyCitizen = product.listProductByType("Citizen");
		model.addAttribute("listCitizen", listspbyCitizen);
		List<Object[]> listspbyElio = product.listProductByType("Elio");
		model.addAttribute("listElio", listspbyElio);
		List<Object[]> listspbyJulius = product.listProductByType("Julius");
		model.addAttribute("listJulius", listspbyJulius);
		List<Object[]> listspbyEsprit = product.listProductByType("Esprit");
		model.addAttribute("listEsprit", listspbyEsprit);
		List<Object[]> listspbyOrient = product.listProductByType("Orient");
		model.addAttribute("listOrient", listspbyOrient);
		List<Object[]> listspbyQQ = product.listProductByType("Q&Q");
		model.addAttribute("listQQ", listspbyQQ);
		List<Object[]> listspbyRobertoCavalli = product.listProductByType("Roberto Cavalli");
		model.addAttribute("listRobertoCavalli", listspbyRobertoCavalli);
	}

	@RequestMapping("/login")
	public String login() {
		return "main/user/login";
	}

	List<Object[]> listfilter = new ArrayList<>();
	List<Object[]> filterProduct = new ArrayList<>();

	@RequestMapping("/more/{id}")
	public String more(Model model, @PathVariable("id") int id) {
		listfilter.clear();
		filterProduct.clear();
		List<Object[]> listvt = product.listAllProduct(id);
		model.addAttribute("listvt", listvt);
		loai = id;
		for (Object[] objects : listvt) {
			listfilter.add(objects);
		}
		sumFilter(model, listfilter);
		fillthongtin(model);
		return "main/user/more";
	}
	@RequestMapping("/resetFilter")
	public String resetFilter(Model model) {
		model.addAttribute("listvt", listfilter);
		fillthongtin(model);
		return "main/user/more";
	}
	@RequestMapping("/more/type/{type}")
	public String moreType(Model model, @PathVariable("type") String type) {
		List<Object[]> listvt = product.listProductByType(type);
		model.addAttribute("listvt", listvt);
		loai = 2;
		fillthongtin(model);
		return "main/user/more";
	}
	public void sumFilter(Model model,List<Object[]> list) {
		model.addAttribute("sum", list.size());
	}
	@RequestMapping("/more")
	public String allproduct(Model model) {
		List<Object[]> allsp = product.listProduct();
		model.addAttribute("listvt", allsp);
		for (Object[] objects : allsp) {
			listfilter.add(objects);
		}
		fillthongtin(model);
		return "main/user/more";
	}

	@RequestMapping("/chitiet/{id}")
	public String chitiet(@PathVariable("id") int id, Model model) {
		Object[] detailsp = product.detailSpTuan(id);
		model.addAttribute("detailsp", detailsp);
		List<Object[]> allimage = image.allImage(id);
		model.addAttribute("allimage", allimage);
//		
		List<Object[]> productBrand = product.getBrandId(id);
		String brandName = "";
		for (Object[] objects : productBrand) {
			brandName = objects[1].toString();
		}
		List<Object[]> sameProducts = product.sameProducts(brandName);
		model.addAttribute("sameProducts", sameProducts);
		footer(model);
		return "main/user/detail";
	}

	@RequestMapping("/filterGender/{gen}")
	public String filtergen(Model model, @PathVariable("gen") String gen) {
		List<Object[]> filterGender = new ArrayList<>();
		if (filterProduct.size() == 0) {
			for (Object[] objects : listfilter) {
				String genlist = (String) objects[7];
				if (genlist.equals(gen)) {
					filterProduct.add(objects);
				}

			}
			
		} else {
			for (Object[] objects : filterProduct) {
				String brandlist = (String) objects[7];
				if (brandlist.equals(gen)) {
					filterGender.add(objects);
				}

			}
			if (filterGender.size() > 0) {
				filterProduct.clear();
				for (Object[] objects : filterGender) {
					filterProduct.add(objects);
				}
			} else {
				filterProduct.clear();
			}
		}
		model.addAttribute("listvt", filterProduct);
		sumFilter(model, filterProduct);
		fillthongtin(model);
		return "main/user/more";
	}

	@RequestMapping("/filterBrand/{brand1}")
	public String filterBrand(Model model, @PathVariable("brand1") String brand1) {
		List<Object[]> filterbrand = new ArrayList<>();
		if (filterProduct.size() == 0) {
			for (Object[] objects : listfilter) {
				String bran = (String) objects[8];
				if (bran.equals(brand1)) {
					filterProduct.add(objects);

				}

			}
			model.addAttribute("listvt", filterProduct);
		} else {
			for (Object[] objects : filterProduct) {
				String brandlist = (String) objects[8];
				if (brandlist.equals(brand1)) {
					filterbrand.add(objects);
				}

			}
			if (filterbrand.size() > 0) {
				filterProduct.clear();
				for (Object[] objects : filterbrand) {
					filterProduct.add(objects);
				}
			} else {
				filterProduct.clear();
			}
		}

		fillthongtin(model);
		sumFilter(model, filterProduct);
		model.addAttribute("listvt", filterProduct);
		return "main/user/more";
	}

	@RequestMapping("/filterColor/{color}")
	public String filterColor(Model model, @PathVariable("color") String colorproduct) {
		List<Object[]> filtercolors = new ArrayList<>();
		if (filterProduct.size() == 0) {
			for (Object[] objects : listfilter) {
				String colorList = (String) objects[9];
				if (colorproduct.equals(colorList)) {
					filterProduct.add(objects);
				}

			}
			model.addAttribute("listvt", filterProduct);
		} else {
			for (Object[] objects : filterProduct) {
				String colorlist = (String) objects[9];
				if (colorlist.equals(colorproduct)) {
					filtercolors.add(objects);
				}
			}
			if (filtercolors.size() != 0) {
				filterProduct.clear();
				for (Object[] objects : filtercolors) {
					filterProduct.add(objects);
				}
			} else {
				filterProduct.clear();
			}
		}

		fillthongtin(model);
		sumFilter(model, filterProduct);
		model.addAttribute("listvt", filterProduct);
		return "main/user/more";
	}

	@RequestMapping("/filterSize/{size}")
	public String filterSize(Model model, @PathVariable("size") String sizeproduct) {
		List<Object[]> listsize = size.listfilterSize(loai, sizeproduct);
		List<Object[]> filterSizes = new ArrayList<>();
		System.out.println(filterProduct.size());
		if (filterProduct.size() > 0) {
			for (Object[] objects : filterProduct) {
				for (Object[] objects2 : listsize) {
					if (objects[6].equals(objects2[2])) {
						filterSizes.add(objects);
					}
				}
			}
			if (filterSizes.size() > 0) {
				filterProduct.clear();
				for (Object[] objects : filterSizes) {
					filterProduct.add(objects);
				}
			} else {
				filterProduct.clear();
			}
		} else {
			for (Object[] objects : listfilter) {
				for (Object[] objects2 : listsize) {
					if (objects[6].equals(objects2[2])) {
						filterProduct.add(objects);
						break;
					}
				}
			}
		}
//		System.out.println(filterSizes.size());

		model.addAttribute("listvt", filterProduct);
		sumFilter(model, filterProduct);
		fillthongtin(model);
		return "main/user/more";
	}

	@RequestMapping("/find/{name}")
	public String findProduct(Model model, @PathVariable("name") String name) {
		List<Object[]> findproducts = product.findProduct(name);
		model.addAttribute("listvt", findproducts);
		fillthongtin(model);
		return "main/user/more";
	}

	public void fillthongtin(Model model) {
		List<Genders> listGen = gen.listGen();
		model.addAttribute("listGen", listGen);
		List<Object[]> listBrand = brand.listbrands(loai);
		model.addAttribute("listBrand", listBrand);
		List<Object[]> listCate = cate.listcate();
		model.addAttribute("listCate", listCate);
		List<Object[]> listColor = color.listAllColor(loai);
		model.addAttribute("listColor", listColor);
		List<Object[]> listsize = size.listsize(loai);
		model.addAttribute("listsize", listsize);
	}
	
	
	//CODE DAN
	@Autowired
	UserDAO usDao;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private CookieService cookieService;
	@Autowired
	private UserServiceImpl userService;
	@Autowired
	EmailService emailService;
	
	
	@GetMapping("/main/user/login")
	public String GetLogin(@ModelAttribute("check") String check, Model model) {
		 model.addAttribute("check", check);
		return "/main/user/login";
	}
	@GetMapping("/main/user/register")
	public String GetRegister() {
		return "/main/user/register";
	}
	@GetMapping("/main/user/email-comfirm")
	public String GetEmail() {
		return "/main/user/email-comfirm";
	}
	@GetMapping("/main/user/forgot-password")
	public String GetForgot() {
		return "main/user/forgot-password";
	}
	@GetMapping("/main/user/reset-password")
	public String GetResetPasswordForm(Model model) {
	    return "/main/user/reset-password";
	}
	@GetMapping("/main/user/forgot-comfirm")
	public String GetPasswordForm(Model model) {
	    return "/main/user/forgot-comfirm";
	}
	
	//Dang nhap
		@PostMapping("/login-processing")
		public String login(@RequestParam("userid") String userid, @RequestParam("password") String password,
		        @RequestParam(value = "remember", defaultValue = "false") boolean remember, Model model,
		        HttpServletRequest request, HttpServletResponse response,  HttpSession session1) {

		    User user = usDao.findByUserId(userid); 
		    if (user != null && user.getPassword().equals(password)) {
		        HttpSession session = request.getSession(true);
		        session.setAttribute("user", user);
		        session.setAttribute("userid", user.getUserId());
		        if (remember) {
		            cookieService.add(response, "userid", userid);
		            cookieService.add(response, "password", password);
		            session1.setAttribute("loggedInUser", user.getUserId());
		        } else {
		            cookieService.remove(response, "userid");
		            cookieService.remove(response, "password");
		        }

		        session.removeAttribute("cartItems");
		        session.removeAttribute("cartService");
		        return "redirect:/";
		    } else {
		        model.addAttribute("errorMessage", "User ID hoặc mật khẩu không hợp lệ.");
		        return "/main/user/login";
		    }
		}
		@Autowired
		DiscountUserImpl discountUserImpl;
		// Dang ky 
				@PostMapping("/register")
				public String registerUser(@ModelAttribute("user") @Validated User user, BindingResult result,
				        @RequestParam("confirmPassword") String confirmPassword, @RequestParam(value = "gender", required = false) boolean gender,
				        Model model, HttpSession session) {

				    if (result.hasErrors()) {
				        return "/main/user/register";
				    }
				    if (usDao.findByUserId(user.getUserId()) != null) {
				        model.addAttribute("errorMessage", "UserID đã được sử dụng");
				        return "/main/user/register";
				    }
				    if (user.getPassword().length() < 5 || user.getPassword().length() > 10) {
				        model.addAttribute("errorMessage", "Mật khẩu phải có từ 5 đến 10 ký tự");
				        return "/main/user/register";
				    }
				    if (!user.getPassword().equals(confirmPassword)) {
				        model.addAttribute("errorMessage", "Mật khẩu không khớp");
				        return "/main/user/register";
				    }
				    String emailD = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
				    if (!user.getEmail().matches(emailD)) {
				        model.addAttribute("errorMessage", "Email không đúng định dạng");
				        return "/main/user/register";
				    }
				    
				    if (usDao.findByEmail(user.getEmail()) != null) {
				        model.addAttribute("errorMessage", "Email đã được sử dụng");
				        return "/main/user/register";
				    }
				    if (user.getPhone() == null || !user.getPhone().matches("\\d{10,11}")) {
				        model.addAttribute("errorMessage", "Số điện thoại không đúng định dạng");
				        return "/main/user/register";
				    }

				    if (gender) {
				        model.addAttribute("errorMessage", "Vui lòng chọn giới tính");
				        return "/main/user/register";
				    }

				    String confirmationCode = RandomConfirmationCodeGenerator.generateCode();
//				    String discountUsersCode = RandomConfirmationCodeGenerator.generateCode();
//				    code = discountUsersCode;
				    session.setAttribute("confirmationCode", confirmationCode);
				    session.setMaxInactiveInterval(60);
				    user.setActive(true);
				    user.setRole("người dùng");
				    user.setGender(gender); // Thiết lập giới tính cho đối tượng User
				    session.setAttribute("user", user);

				    try {
				        MimeMessage message = mailSender.createMimeMessage();
				        MimeMessageHelper helper = new MimeMessageHelper(message, true);
				        helper.setTo(user.getEmail());
				        helper.setSubject("Mã xác nhận đăng ký tài khoản");
				        helper.setText("Mã xác nhận của bạn là: " + confirmationCode );
				        mailSender.send(message);
				    } catch (Exception e) {
				        model.addAttribute("errorMessage", "Không thể gửi email xác nhận");
				        return "/main/user/register";
				    }

				    return "redirect:/main/user/email-comfirm";
				}
		// Ramdom mã
		public class RandomConfirmationCodeGenerator {

			private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			private static final int CODE_LENGTH = 6;

			public static String generateCode() {
				StringBuilder sb = new StringBuilder(CODE_LENGTH);
				Random random = new Random();
				for (int i = 0; i < CODE_LENGTH; i++) {
					int index = random.nextInt(CHARACTERS.length());
					sb.append(CHARACTERS.charAt(index));
				}
				return sb.toString();
			}
		}
		// Check mã dang ky
		@PostMapping("/confirm-code")
		public String confirmCode(@RequestParam("confirmationCode") String confirmationCode,
				HttpSession session,Model model) {
			String storedConfirmationCode = (String) session.getAttribute("confirmationCode");
			
			if (confirmationCode.equals(storedConfirmationCode)) {

				User user = (User) session.getAttribute("user");
				user.setRole("user");
				userService.saveUserDan(user);
				 String discountUsersCode = RandomConfirmationCodeGenerator.generateCode();
				try {
			        MimeMessage message = mailSender.createMimeMessage();
			        MimeMessageHelper helper = new MimeMessageHelper(message, true);
			        helper.setTo(user.getEmail());
			        helper.setSubject("Mã xác nhận đăng ký tài khoản");
			        helper.setText(" Mã khuyến mãi của bạn: " + discountUsersCode, true);
			        mailSender.send(message);
			    } catch (Exception e) {
			       
			    }
				session.removeAttribute("confirmationCode");
				DiscountUser discount = new DiscountUser();
				discount.setId(discountUsersCode);
				discount.setUser(user);
				discount.setDiscountAmount(0.5);
				discountUserImpl.saveDiscountUser(discount);
	
				return "redirect:/main/user/login";
			} 
			else {

				model.addAttribute("errorMessage", "Mã xác nhận không đúng");
				return "redirect:/main/user/email-comfirm";
			}
		}
		// Quen mat khau
		@PostMapping("/forgot-password")
		public String handleForgotPasswordForm(@RequestParam("email") String email, Model model, HttpSession session) {
		    User user = usDao.findByEmail(email);

		    if (user != null) {

		        String confirmationCode = RandomConfirmationCodeGenerator.generateCode();

		        session.setAttribute("confirmationCode", confirmationCode);
		        session.setMaxInactiveInterval(60);
		        session.setAttribute("user", user);
		        
		        emailService.sendForgotPasswordEmail(user, confirmationCode);
		        return "redirect:/main/user/forgot-comfirm";
		    } else {
		        model.addAttribute("error", "Địa chỉ email không hợp lệ. Vui lòng thử lại.");
		        return "/main/user/forgot-password";
		    }
		}
		// xac nhan ma quen
		@PostMapping("/forgot-comfirm")
		public String confirmForgotPassword(@RequestParam("confirmationCode") String confirmationCode, Model model, HttpSession session) {
		    String savedConfirmationCode = (String) session.getAttribute("confirmationCode");
		    User user = (User) session.getAttribute("user");

		    if (user != null && savedConfirmationCode != null && savedConfirmationCode.equals(confirmationCode)) {
		        return "redirect:/main/user/reset-password";
		    } else {
		        model.addAttribute("error", "Mã xác nhận không hợp lệ. Vui lòng thử lại.");
		        return "/main/user/forgot-comfirm";
		    }
		}
		// Mat khau moi 
		@PostMapping("/reset-password")
		public String resetPassword(@RequestParam("newPassword") String newPassword,Model model, HttpSession session, @RequestParam("confirmPassword") String confirmPassword) {
		    User user = (User) session.getAttribute("user");
		     if(newPassword.length() <5 || newPassword.length()>10) {
		    	model.addAttribute("errorMessage", "Mật khẩu phải có từ 5 đến 10 ký tự");
		        return "/main/user/reset-password"; 
		     	}
		     if (user != null && newPassword.equals(confirmPassword)) {
		    	user.setPassword(newPassword);
		        userService.saveUserDan(user);
		        session.removeAttribute("user");
		        session.removeAttribute("confirmationCode");
		        return "redirect:/main/user/login";
		     	}
		        else {
		        model.addAttribute("errorMessage", "Mật khẩu không khớp");
		        return "/main/user/reset-password"; 
		        }
		    
		}
	
		@GetMapping("/paymentXacNhan")
		public String paymetnXacNhan(@RequestParam(value = "vnp_Amount") String amount,
				@RequestParam(value = "vnp_BankCode") String bankcode,
				@RequestParam(value = "vnp_OrderInfo") String order,
				@RequestParam(value = "vnp_ResponseCode") String responsecode,
				Model model) {
			TransactionDTO tran = new TransactionDTO();
			if(responsecode.equals("00")){			
				tran.setStatus("Bạn đã thanh toán đơn hàng thành công");
				tran.setMessage("Vui lòng bấm vào nút bên dưới để hoàn tất đơn hàng và trở về trang chủ.");
				tran.setData("");
				
			}else {
				tran.setStatus("Bạn đã hủy thanh toán đơn hàng ");
				tran.setMessage("Vui lòng bấm vào nút bên dưới để trở về trang chủ.");
				tran.setData("");
			}
			model.addAttribute("tran",tran);
			return "/main/user/paymentInfo";
		}
		// gg
		@RequestMapping("/login-google")
		public String loginGoogle(OAuth2AuthenticationToken oAuth, Model model, HttpSession session,
				HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) {
			Root root = toperson(oAuth.getPrincipal().getAttributes());
			List<User> users = userService.listall();
			HttpSession session1 = request.getSession(true);
			String email = toperson(oAuth.getPrincipal().getAttributes()).getEmail();
			String id = toperson(oAuth.getPrincipal().getAttributes()).getSub();
			boolean check = true;
			for (User user2 : users) {
				if (user2.getEmail().equals(email) && user2.getUserId().equals(id)) {
					session1.setAttribute("user", user2);
					session1.setAttribute("userid", user2.getUserId());
					session.setAttribute("user", user2);
					check = false;
					break;
				}
			}
			boolean flag = true;
			if (check) {
				for (User user : users) {
					if (user.getEmail().equals(email)) {
						flag = false;
						redirectAttributes.addFlashAttribute("check", "Email đã được sử dụng");
						return "redirect:/main/user/login";
					}
				}
				if (flag) {
					User user = new User();
					user.setActive(true);
					user.setAddress("");
					user.setEmail(root.getEmail());
					user.setFullName(root.getName());
					user.setGender(true);
					user.setPassword("");
					user.setPhone("");
					user.setRole("user");
					user.setUserId(root.getSub());
					userService.saveUser(user);
					session1.setAttribute("user", user);
					session1.setAttribute("userid", user.getUserId());
					session.setAttribute("user", user);
					
				}
				
			}
			return "redirect:/";
			
		}

		public Root toperson(Map<String, Object> map) {
			if (map == null) {
				return null;
			}
			Root root = new Root();
			root.setEmail((String) map.get("email"));
			root.setEmail_verified((boolean) map.get("email_verified"));
			root.setFamily_name((String) map.get("family_name"));
			root.setGiven_name((String) map.get("given_name"));
			root.setLocale((String) map.get("locale"));
			root.setName((String) map.get("name"));
			root.setPicture((String) map.get("picture"));
			root.setSub((String) map.get("sub"));
			return root;
		}

		@RequestMapping("/user-info")
		public String userInfo(Model model, HttpSession session) {
			if (session != null && session.getAttribute("user") != null) {
				User user = (User) session.getAttribute("user");
				if (user != null) {

					List<Object[]> listOrder = orderImpl.fillAllForUser(user.getUserId());
					model.addAttribute("listOrder", listOrder);
					List<Object[]> listitem = itemImpl.listItemForOder();
					model.addAttribute("listItem", listitem);
					List<Object[]> listPrice = itemImpl.listSumPrice();
					model.addAttribute("listPrice", listPrice);
				}
			} else {
				return "redirect:/login";
			}
			return "/main/user/user-info";
		}

		@RequestMapping("/user-info/update")
		public String userInfoUpdate(@RequestParam("fullname") String fullname, @RequestParam("email") String email,
				@RequestParam("address") String address, @RequestParam("phone") String phone,
				@RequestParam("gender-input") boolean gender,HttpSession session,@RequestParam("password")  String password) {
			User user = (User) session.getAttribute("user");
			user.setFullName(fullname);
			user.setEmail(email);
			user.setAddress(address);
			user.setPhone(phone);
			user.setGender(gender);
			user.setPassword(password);
			userImpl.saveUser(user);
			return "redirect:/user-info";
		}
		@RequestMapping("/delete/bill/{id}")
		public String deleteBill(@PathVariable("id") int id) {
			Order order = orderImpl.findByOrderId(id);
			order.setActive(false);
			orderImpl.saveOrder(order);
			return "redirect:/user-info";
		}
		@RequestMapping("/log-out")
		public String logOut(HttpSession session) {
			session.removeAttribute("user");
			return "redirect:/";
		}


}
