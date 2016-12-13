package com.example.web;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import com.example.domain.FoodPrice;
import com.example.service.FoodPriceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
public class TestControllerAjax {

	@Autowired
	FoodPriceService foodService;

	@RequestMapping(value = "/onclickDeleteAjax")
	String onclickDeleteAjax(@RequestParam String id) {
		foodService.delete(Integer.parseInt(id));
		return null;
	}

	@RequestMapping(value = "/searchByNameAjax")
	String searchByNameAjax(@RequestParam String searchKey, @RequestParam String paginationVal) {
		
		// 現在のページ
		int currentPage = Integer.parseInt(paginationVal);
		// 1ページあたりの表示件数
		int elementsOnPage = 2;
		Pageable pageable = new PageRequest((currentPage - 1), elementsOnPage);
		Page<FoodPrice> page = foodService.findByName(searchKey, pageable);

		System.out.println("現在のページ：" + (page.getNumber() + 1));
		System.out.println("全ページ数：" + page.getTotalPages());
		System.out.println("1ページあたりの表示件数：" + page.getSize());
		System.out.println("全件数：" + page.getTotalElements());

		List<FoodPrice> foodList = page.getContent();

		System.out.println(foodList.toString());

		String ret = retPage((currentPage-1), page.getTotalPages()) + ";" + retTb(foodList);

		return ret;
	}

	String retPage(int nowPage, int allPage) {
		String ret = "";

		for (int i = 0; i < allPage; i++) {
			if(nowPage==i){
				ret += "<li class=\"active\" id="+ (i + 1) +" onclick=\"pageOnclick(" + (i + 1) + ")\"><a href=\"#\">" + (i + 1) + "</a></li>";
			} else{
				ret += "<li id="+ (i + 1) +" onclick=\"pageOnclick(" + (i + 1) + ")\"><a href=\"#\">" + (i + 1) + "</a></li>";
			}
		}

		return ret;
	}

	String retTb(List<FoodPrice> foodList) {
		String ret = "";
		for (int i = 0; i < foodList.size(); i++) {
			ret += "<tr>";
			FoodPrice fp = foodList.get(i);
			ret += "<td>";
			ret += fp.getId();
			ret += "</td>";
			ret += "<td>";
			ret += fp.getName();
			ret += "</td>";
			ret += "<td>";
			ret += fp.getPrice();
			ret += "</td>";
			ret += "<td>";
			ret += editBtn(fp.getId());
			ret += "</td>";
			ret += "<td>";
			ret += deleteBtn(fp.getId());
			ret += "</td>";
			ret += "</tr>";
		}
		return ret;
	}

	String editBtn(Integer param) {
		String ret = "<input class=\"btn btn-default btn-sm\" type=\"button\" value=\"編集\" onClick=\"onclickEdit("
				+ param + ")\" />";
		return ret;
	}

	String deleteBtn(Integer param) {
		String ret = "<input class=\"btn btn-danger btn-sm\" type=\"button\" value=\"削除\" onClick=\"onclickDelete("
				+ param + ")\" />";
		return ret;
	}

}
