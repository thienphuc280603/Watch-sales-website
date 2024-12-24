package com.poly.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.entity.Genders;
import com.poly.entity.Products;
import com.poly.entity.User;

public interface ProductsDAO extends JpaRepository<Products, Integer> {

					
					 Page<Products> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

					
					
					
					
					
					
					@Query(value = "SELECT "
							+ "    products.name AS 'Tên sản phẩm', "
							+ "    products.price AS 'Giá sản phẩm', "
							+ "    discountsproducts.discountamount AS 'Giảm giá', "
							+ "    (products.price - (discountsproducts.discountamount * products.price)) AS 'Phải trả', "
							+ "    products.image, products.categorizesid, products.productsid  "
							+ "FROM "
							+ "    products "
							+ "LEFT JOIN "
							+ "    discountsproducts ON products.discountsproductsid = discountsproducts.discountsproductsid "
							+ "WHERE "
							+ "    products.categorizesid = 1 and products.active = 1;", nativeQuery = true)
						List<Object[]> listVongtay();
						@Query(value = "SELECT "
								+ "    products.name AS 'Tên sản phẩm', "
								+ "    products.price AS 'Giá sản phẩm', "
								+ "    discountsproducts.discountamount AS 'Giảm giá', "
								+ "    (products.price - (discountsproducts.discountamount * products.price)) AS 'Phải trả', "
								+ "    products.image, products.categorizesid, products.productsid  "
								+ "FROM "
								+ "    products "
								+ "LEFT JOIN "
								+ "    discountsproducts ON products.discountsproductsid = discountsproducts.discountsproductsid "
								+ "WHERE "
								+ "    products.categorizesid = 3 and products.active = 1;", nativeQuery = true)
							List<Object[]> listNhan();
						@Query(value = "SELECT top 8 "
								+ "			    products.name AS 'Tên sản phẩm', "
								+ "			    products.price AS 'Giá sản phẩm', "
								+ "			   discountsproducts.discountamount AS 'Giảm giá', "
								+ "			    (products.price - (discountsproducts.discountamount * products.price)) AS 'Phải trả', "
								+ "			    products.image, products.categorizesid, products.productsid  "
								+ "			FROM "
								+ "			    products "
								+ "			LEFT JOIN "
								+ "			    discountsproducts ON products.discountsproductsid = discountsproducts.discountsproductsid "
								+ "			WHERE "
								+ "			    products.categorizesid = 2 and products.active = 1 ", nativeQuery = true)
							List<Object[]> listDh();

						
						@Query(value = "select p.productsid, p.name as product_name , b.name, p.price, p.active"
						        + "from products p, brands b"
						        + "where p.brandid = b.brandid", nativeQuery = true)
						List<Object[]> listProductAdmin();
						
						
						
						
						@Query(value = "select p.productsid, p.name as product_name, p.price, p.describe, b.name, g.gender"
								+ "from products p, brands b, genders g"
								+ "where p.brandid = b.brandid and p.genderid = g.genderid and p.productsid = :id",
								nativeQuery = true)
						Object getProductByIdAdmin(@Param("id") Integer id);
						
						@Query(value = "select * from products where productsid = ?1", nativeQuery = true)
						Products getProductByID(Integer id);
						
						 Page<Products> findAll(Pageable pageable);
						 
						 @Query(value = "select * from products where name = ?1", nativeQuery = true)
							List<Products> getProductByProductName(String productName);

						
							
									
							//Code Tuan
						 @Query(value = "SELECT"
									+ "    products.name AS 'Tên sản phẩm',"
									+ "    products.price AS 'Giá sản phẩm',"
									+ "    discountsproducts.discountamount AS 'Giảm giá',"
									+ "    (products.price - (discountsproducts.discountamount * products.price)) AS 'Phải trả',"
									+ "    products.image AS 'Tên ảnh',"	
									+ "    products.categorizesid,"
									+ "    products.describe,"
									+ "    products.productsid,"
									+ "    brands.name AS 'Tên thương hiệu',"
									+ "    products.quantity"
									+ " FROM products "
									+ " LEFT JOIN discountsproducts ON products.discountsproductsid = discountsproducts.discountsproductsid "
									+ " LEFT JOIN brands ON products.brandid = brands.brandid "
									+ " WHERE products.productsid = :id", nativeQuery = true)
							Object[] detailSpTuan(@Param("id") int id);

							
							
							@Query(value =" SELECT  "
									+ "				 			    products.name AS 'Tên sản phẩm', "
									+ "				 			    products.price AS 'Giá sản phẩm', "
									+ "				 			   discountsproducts.discountamount AS 'Giảm giá', "
									+ "				 			    (products.price - (discountsproducts.discountamount * products.price)) AS 'Phải trả', "
									+ "				 			   products.image, products.categorizesid, products.productsid, brands.name  "
									+ "				 			FROM "
									+ "				 			    products "
									+ "				 			LEFT JOIN "
									+ "				 			    discountsproducts ON products.discountsproductsid = discountsproducts.discountsproductsid "
									+ "				 			LEFT JOIN "
									+ "				 				brands on brands.brandid = products.brandid"
									+ "							where products.active = 1	"
									+ "				 GROUP BY "
									+ "				     products.name, products.price, discountsproducts.discountamount,products.categorizesid, products.productsid, brands.name,products.image  "
									+ "				 ", nativeQuery = true)
								List<Object[]> listsp();
							
							
							
							
								@Query(value = "SELECT "
										+ "				     products.name AS 'Tên sản phẩm', "
										+ "				     products.price AS 'Giá sản phẩm', "
										+ "				     discountsproducts.discountamount AS 'Giảm giá', "
										+ "				     (products.price - (discountsproducts.discountamount * products.price)) AS 'Phải trả', "
										+ "				     products.image, products.categorizesid , products.productsid, genders.gender, brands.name,colors.colorname "
										+ "				 FROM "
										+ "				     sizes, images, colors, categorizes, genders, brands,products "
										+ "				 LEFT JOIN "
										+ "				     discountsproducts ON products.discountsproductsid = discountsproducts.discountsproductsid "
										+ "				 WHERE "
										+ "				    images.productsid = products.productsid "
										+ "                  AND products.active = 1"
										+ "				     AND categorizes.categorizesid = products.categorizesid "
										+ "				     AND colors.colorid = images.colorid "
										+ "				     AND genders.genderid = products.genderid "
										+ "				     AND brands.brandid = products.brandid "
										+ "				     AND sizes.productsid = products.productsid and  products.categorizesid = :id and products.active = 1"
										+ "				 GROUP BY "
										+ "				 	 products.name, products.price, discountsproducts.discountamount,products.categorizesid, products.productsid, genders.gender , brands.name,colors.colorname,products.image", nativeQuery = true)
									List<Object[]> listAllSp(@Param("id") int id);
							
							

									@Query(value = "SELECT "
											+ "    products.name AS 'Tên sản phẩm', "
											+ "    products.price AS 'Giá sản phẩm', "
											+ "    discountsproducts.discountamount AS 'Giảm giá', "
											+ "    (products.price - (discountsproducts.discountamount * products.price)) AS 'Phải trả', "
											+ "    products.image AS 'Tên ảnh',categorizes.name, products.describe "
											+ "FROM "
											+ "    categorizes,products "
											+ "LEFT JOIN "
											+ "    discountsproducts ON products.discountsproductsid = discountsproducts.discountsproductsid "
											+ "WHERE "
											+ "    products.categorizesid = categorizes.categorizesid and products.productsid = :id "
											+ "GROUP BY "
											+ "    products.name, products.price, discountsproducts.discountamount,categorizes.name, products.describe,products.image",nativeQuery = true)
									List<Object[]> detailSp(@Param("id") int id);
									
									@Query(value = "SELECT top 6 "
											+ "				 				     products.name AS 'Tên sản phẩm', "
											+ "				 				     products.price AS 'Giá sản phẩm', "
											+ "				 				     discountsproducts.discountamount AS 'Giảm giá', "
											+ "				 				     (products.price - (discountsproducts.discountamount * products.price)) AS 'Phải trả', "
											+ "				 				     products.image, products.categorizesid , products.productsid, genders.gender, brands.name,colors.colorname "
											+ "				 				 FROM "
											+ "				 				     sizes, images, colors, categorizes, genders, brands,products "
											+ "				 				 LEFT JOIN "
											+ "				 				     discountsproducts ON products.discountsproductsid = discountsproducts.discountsproductsid "
											+ "				 				 WHERE "
											+ "				 				    images.productsid = products.productsid "
											+ "				 				     AND categorizes.categorizesid = products.categorizesid "
											+ "				 				     AND colors.colorid = images.colorid "
											+ "				 				     AND genders.genderid = products.genderid "
											+ "				 				     AND brands.brandid = products.brandid "
											+ "				 				     AND sizes.productsid = products.productsid and  brands.name like :type and products.active = 1"
											+ "				 				 GROUP BY "
											+ "				 				 	 products.name, products.price, discountsproducts.discountamount,products.categorizesid, products.productsid, genders.gender , brands.name,colors.colorname,products.image",nativeQuery = true)
									List<Object[]> listProductByType(@Param("type") String type);
									
									@Query(value = "SELECT"
											+ "				 				     products.name AS 'Tên sản phẩm', "
											+ "				 				     products.price AS 'Giá sản phẩm', "
											+ "				 				     discountsproducts.discountamount AS 'Giảm giá', "
											+ "				 				     (products.price - (discountsproducts.discountamount * products.price)) AS 'Phải trả', "
											+ "				 				     products.image, products.categorizesid , products.productsid, genders.gender, brands.name,colors.colorname "
											+ "				 				 FROM "
											+ "				 				     sizes, images, colors, categorizes, genders, brands,products "
											+ "				 				 LEFT JOIN "
											+ "				 				     discountsproducts ON products.discountsproductsid = discountsproducts.discountsproductsid "
											+ "				 				 WHERE "
											+ "				 				    images.productsid = products.productsid "
											+ "				 				     AND categorizes.categorizesid = products.categorizesid "
											+ "				 				     AND colors.colorid = images.colorid "
											+ "				 				     AND genders.genderid = products.genderid "
											+ "				 				     AND brands.brandid = products.brandid "
											+ "				 				     AND sizes.productsid = products.productsid and  brands.name = :type and products.active = 1"
											+ "				 				 GROUP BY "
											+ "				 				 	 products.name, products.price, discountsproducts.discountamount,products.categorizesid, products.productsid, genders.gender , brands.name,colors.colorname,products.image",nativeQuery = true)
									List<Object[]> sameProducts(@Param("type") String type);
									
									@Query(value = "SELECT p.brandid, b.name "
							                + "FROM products AS p "
							                + "JOIN brands AS b "
							                + "ON p.brandid = b.brandid "
							                + "WHERE p.productsid = :id", nativeQuery = true)
									List<Object[]> getBrandId(@Param("id") int id);
							
									@Query(value = "SELECT  "
											+ "				 			    products.name AS 'Tên sản phẩm', "
											+ "				 			    products.price AS 'Giá sản phẩm', "
											+ "				 			   discountsproducts.discountamount AS 'Giảm giá', "
											+ "				 			    (products.price - (discountsproducts.discountamount * products.price)) AS 'Phải trả', "
											+ "				 			    products.image, products.categorizesid, products.productsid, brands.name  "
											+ "				 			FROM "
											+ "				 			    products "
											+ "				 			LEFT JOIN "
											+ "				 			    discountsproducts ON products.discountsproductsid = discountsproducts.discountsproductsid "
											+ "				 			LEFT JOIN "
											+ "				 				brands on brands.brandid = products.brandid"
											+ "							where products.name like %:name% and products.active = 1"
											+ "				 GROUP BY "
											+ "				     products.name, products.price, discountsproducts.discountamount,products.categorizesid, products.productsid, brands.name ,products.image "
											+ "				 ",nativeQuery = true)
									List<Object[]> findproduct(@Param("name") String name);

									
									@Query(value = "SELECT * FROM products WHERE [name] LIKE %:keyword% where products.active = 1", nativeQuery = true)
									List<Products> getProductByKeyWord(@Param("keyword") String keyword);
									
									@Query(value = "SELECT"
											+ "    products.productsid AS product_id,"
										    + "    products.name AS product_name,"
										    + "    products.price AS product_price,"
										    + "    products.categorizesid AS categorizes_id,"
										    + "    products.discountsproductsid AS discounts_products_id,"
										    + "    products.brandid AS brand_id,"						    
										    + "    products.describe AS product_describe,"
										    + "    products.genderid AS products_genderid,"
										    + "    products.active AS product_active,"	
										    + "    products.image AS product_image,"	
										    + "    products.quantity AS product_quantity"
										    + "FROM products"
										    + "WHERE products.productsid = :id", nativeQuery = true)
										Object[] getproduct(@Param("id") int id);

									@Query(value = "select products.image , products.name,products.price,(products.price*discountsproducts.discountamount) as 'gg',(products.price-(products.price*discountsproducts.discountamount)),products.categorizesid,brands.name as 'brand',products.productsid "
											+ "from items , products, discountsproducts,brands "
											+ "where items.productsid = products.productsid and products.discountsproductsid= discountsproducts.discountsproductsid and products.brandid = brands.brandid and products.active = 1 "
											+ "group by products.image , products.name,products.price,(products.price*discountsproducts.discountamount),products.categorizesid,brands.name,products.productsid ",nativeQuery = true)
									List<Object[]> listTrending();
									@Query(value = "SELECT "
											+ "										 				     products.name AS 'Tên sản phẩm', "
											+ "										 				     products.price AS 'Giá sản phẩm', "
											+ "										 				     discountsproducts.discountamount AS 'Giảm giá', "
											+ "										 				     (products.price - (discountsproducts.discountamount * products.price)) AS 'Phải trả', "
											+ "										 				     products.image, products.categorizesid , products.productsid, genders.gender, brands.name,colors.colorname "
											+ "										 				 FROM "
											+ "										 				     sizes, images, colors, categorizes, genders, brands,products "
											+ "										 				 LEFT JOIN "
											+ "										 				     discountsproducts ON products.discountsproductsid = discountsproducts.discountsproductsid "
											+ "										 				 WHERE "
											+ "										 				    images.productsid = products.productsid "
											+ "										                   AND products.active = 1 "
											+ "										 				     AND categorizes.categorizesid = products.categorizesid "
											+ "										 				     AND colors.colorid = images.colorid "
											+ "										 				     AND genders.genderid = products.genderid "
											+ "										 				     AND brands.brandid = products.brandid "
											+ "										 				     AND sizes.productsid = products.productsid and products.discountsproductsid <> '00' and products.categorizesid = 2 "
											+ "										 				 GROUP BY "
											+ "										 				 	 products.name, products.price, discountsproducts.discountamount,products.categorizesid, products.productsid, genders.gender , brands.name,colors.colorname,products.image ",nativeQuery = true)
									List<Object[]> listGG();
								

				

}
