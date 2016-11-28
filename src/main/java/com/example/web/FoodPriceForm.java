package com.example.web;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class FoodPriceForm {

	private int id;

	@Size(min = 1, max = 4, message = "商品名は{min}文字以上{max}文字以内で入力してください")
	private String name;

	@Max(value = 1000, message = "価格は{value}円以内で入力してください")
	private int price;

}
