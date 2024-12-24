package com.poly.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Root {
	 private String sub;
	 private String name;
	 private String given_name;
	 private String family_name;
	 private String picture;
	 private String email;
	 private boolean email_verified;
	 private String locale;

}
