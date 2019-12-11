package com.clansoft.ingredient.web;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clansoft.ingredient.parser.Ingredients;
import com.clansoft.ingredient.parser.IngredientsApplicationException;
import com.clansoft.ingredient.parser.IngredientsParser;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
	
	@Autowired
	private IngredientsParser ingredientsParser;

	/**
	 * TODO: will include JWT header for authentication.
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("/process/image")
    public @ResponseBody Optional<Ingredients> handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
        try {
			return ingredientsParser.parse(file.getBytes());
		} catch (IngredientsApplicationException | IOException e) {
			throw new ResponseStatusException(
			           HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e);
		}
    }
}
