package com.store.areas.product.models.binding;

import com.store.constants.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;


public class CreateProductBindingModel {



	@Size(min = 1, max = 50, message = Constants.PRODUCT_NAME_LENGTH)


	private String name;
	private String img;
	private String imgs;

	private String description;

	@NotNull
	private String brand;

	private String[] categories;

	@NotNull
	private BigDecimal price;

	public CreateProductBindingModel() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(MultipartFile img) {
		if (img != null) {
			File uploadPDir = new File("/home/bkmz/book2/");
			if (!uploadPDir.exists()) {
				uploadPDir.mkdir();
			}
			String uuidFile = UUID.randomUUID().toString();
			String resultFilename = uuidFile + "." + img.getOriginalFilename();
			System.out.println(uploadPDir+ "/" + resultFilename);
			try {
				img.transferTo(new File("/home/bkmz/book2/" + "/" + resultFilename));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.img = "/img/"+resultFilename;

		}


	}
	public String getImgs() {
		return imgs;
	}
	@RequestMapping(value = "/products/create", method = RequestMethod.POST)
	public void setImgs(MultipartFile[] imgs) {
		String sImgs = "";

		for (MultipartFile img :
				imgs) {
			if (img != null) {
				File uploadPDir = new File("/home/bkmz/book2/");
				if (!uploadPDir.exists()) {
					uploadPDir.mkdir();
				}
				String uuidFile = UUID.randomUUID().toString();
				String resultFilename = uuidFile + "." + img.getOriginalFilename();
				System.out.println(uploadPDir+ "/" + resultFilename);
				try {
					img.transferTo(new File("/home/bkmz/book2/" + "/" + resultFilename));
				} catch (IOException e) {
					e.printStackTrace();
				}
				sImgs += "/img/"+resultFilename +"&&&";


			}
		}
		//System.out.println(sImgs);


		this.imgs = sImgs;



	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String[] getCategories() {
		return categories;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
