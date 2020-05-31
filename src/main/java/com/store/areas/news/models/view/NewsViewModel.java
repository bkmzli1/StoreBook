package com.store.areas.news.models.view;

import com.store.areas.product.models.service.ProductServiceModel;
import com.store.areas.product.models.view.ProductViewModel;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Set;

public class NewsViewModel {
	private String name;
	private String id;

	private String img;
	private String description;
	private String descriptionSize;
	private LocalDateTime createdAt;

	public NewsViewModel() {
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String[] getDescription() {


		return description.replace("\n","\n\t").split("\n");
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private Set<ProductViewModel> products;



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<ProductViewModel> getProducts() {
		return products;
	}

	public void setProducts(Set<ProductViewModel> products) {
		this.products = products;
	}


	public String getDescriptionSize() {

		String[] split = description.split(" ");
		String descriptionSize = "";
		int a = 0;
		for (String str :
				split) {
			descriptionSize += str;
			if (a == 10) {
				descriptionSize += " ...";
				break;
			}
			a++;
		}
		return descriptionSize;
	}

	public void setDescriptionSize(String descriptionSize) {
		this.descriptionSize = descriptionSize;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
