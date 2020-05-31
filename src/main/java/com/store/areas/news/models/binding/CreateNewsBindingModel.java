package com.store.areas.news.models.binding;

import com.store.constants.Constants;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

public class CreateNewsBindingModel {



	@Size(min = 1, max = 50, message = Constants.PRODUCT_NAME_LENGTH)


	private String name;
	private String img;

	private String description;



	public CreateNewsBindingModel() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
