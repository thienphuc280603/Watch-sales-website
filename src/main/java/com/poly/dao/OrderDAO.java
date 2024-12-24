package com.poly.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.Order;

public interface OrderDAO extends JpaRepository<Order, Integer> {

	Order findByOrderId(Integer id);

	@Query(value = "SELECT p.name, SUM(i.quantity) as 'Tổng số lượng bán', p.productsid, p.price ,sum(p.price * i.quantity) as N'Tổng thu', o.date\r\n"
			+ "FROM orders o\r\n" + "INNER JOIN items i ON o.ordersid = i.ordersid\r\n"
			+ "INNER JOIN products p ON p.productsid = i.productsid\r\n"
			+ "WHERE o.date >= :startDate AND o.date <= :endDate\r\n"
			+ "GROUP BY p.name, p.productsid, p.price,  o.date", nativeQuery = true)
	List<Object[]> getThongke(@Param("startDate") String startDate, @Param("endDate") String endDate);

	@Query(value = "SELECT p.name, SUM(i.quantity) as 'Tổng số lượng bán', p.productsid, p.price, SUM(p.price * i.quantity) as 'Tổng thu',\r\n"
			+ "			MAX(o.date) as 'Ngày mới nhất'\r\n"
			+ "FROM orders o\r\n"
			+ "INNER JOIN items i ON o.ordersid = i.ordersid\r\n"
			+ "INNER JOIN products p ON p.productsid = i.productsid\r\n"
			+ "GROUP BY p.productsid, p.name, p.price", nativeQuery = true)
	List<Object[]> getAllThongke();

	@Query(value = "SELECT u.userid, u.fullname, SUM(i.quantity) AS N'Tổng sản phẩm mua', SUM(i.price) AS N'Tổng tiền đã cúng'\r\n"
			+ "FROM users u\r\n" + "JOIN orders o ON u.userid = o.userid\r\n"
			+ "JOIN items i ON o.ordersid = i.ordersid\r\n" + "where o.date >= ?1 AND o.date <= ?2\r\n"
			+ "GROUP BY u.userid, u.fullname\r\n" + "ORDER BY N'Tổng tiền đã cúng' DESC, u.userid;", nativeQuery = true)
	List<Object[]> getThongkeTheoUser(@Param("startDate") String startDate, @Param("endDate") String endDate);
	
	@Query(value = "SELECT u.userid, u.fullname, SUM(i.quantity) AS N'Tổng sản phẩm mua', SUM(i.price) AS N'Tổng tiền đã cúng'\r\n"
			+ "FROM users u\r\n" + "JOIN orders o ON u.userid = o.userid\r\n"
			+ "JOIN items i ON o.ordersid = i.ordersid\r\n"
			+ "GROUP BY u.userid, u.fullname\r\n" + "ORDER BY N'Tổng tiền đã cúng' DESC, u.userid;", nativeQuery = true)
	List<Object[]> getAllThongkeTheoUser();
	
	
	 @Query("SELECT SUM(o.price) FROM Order o WHERE MONTH(o.date) = :month AND YEAR(o.date) = :year")
	    Double getTotalPriceForMonthAndYear(@Param("month") int month, @Param("year") int year);
	 @Query(value = "select * "
	 		+ "from orders "
	 		+ "where userid like :id order by ordersid desc",nativeQuery = true)
	 List<Object[]> findAllForUser(@Param("id") String id);
	 
	 
	 //BILL
	    @Query("SELECT o FROM Order o WHERE o.active = :active AND o.status = :status ORDER BY o.date DESC")
	    List<Order> findByActiveAndStatus(@Param("active") boolean active, @Param("status") boolean status);
}
