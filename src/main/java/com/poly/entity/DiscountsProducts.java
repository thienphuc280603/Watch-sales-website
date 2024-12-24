package com.poly.entity;
import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "discountsproducts")
public class DiscountsProducts {
	 @Id
	 @Column(name = "discountsproductsid", length = 20)
	 @NotBlank(message = "Không được bỏ trống!")
	 private String discountsProductsId;
	 
	    @Column(name = "discountamount", precision = 10, scale = 2)
	    @DecimalMin(value = "0", inclusive = true, message = "Giá trị giảm giá phải lớn hơn 0 !")
	    @DecimalMax(value = "1", inclusive = true, message = "Giá trị giảm giá phải nhỏ hơn 1 !")
		@NotNull(message = "Không được bỏ trống!")
	    private Double discountAmount;

	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    @Column(name = "datestart")
	    @NotNull(message = "Hãy chọn ngày bắt đầu!")
	    private Date dateStart;

	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    @Column(name = "dateend")
	    @NotNull(message = "Hãy chọn ngày kết thúc!")
	    private Date dateEnd;
}

