package com.clansoft.ingredient.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.translate.Detection;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;
import com.google.protobuf.ByteString;

@Service
public class IngredientsParser {

	Logger logger = LoggerFactory.getLogger(IngredientsParser.class);

	public Optional<Ingredients> parse(byte[] data) throws IngredientsApplicationException {
		GoogleCredentials credentials;
		String credentialsString = System.getenv("GOOGLE_CONFIG");
		ImageAnnotatorSettings ias = null;
		final Translate translate;

		try (InputStream inputStream = new ByteArrayInputStream(
				credentialsString.getBytes(Charset.forName("UTF-8")));) {
			credentials = ServiceAccountCredentials.fromStream(inputStream);
			ias = ImageAnnotatorSettings.newBuilder()
					.setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
			translate = TranslateOptions.newBuilder().setCredentials(credentials).build().getService();
		} catch (IOException e) {
			logger.error("Exception caught while building credentials", e);
			throw new IngredientsApplicationException(e);
		}
		try (ImageAnnotatorClient vision = ImageAnnotatorClient.create(ias)) {
			ByteString imgBytes = ByteString.copyFrom(data);

			// Builds the image annotation request
			List<AnnotateImageRequest> requests = new ArrayList<>();
			Image img = Image.newBuilder().setContent(imgBytes).build();
			Feature feat = Feature.newBuilder().setType(Type.TEXT_DETECTION).build();
			AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
			requests.add(request);

			try (ImageAnnotatorClient client = ImageAnnotatorClient.create(ias)) {
				BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
				List<AnnotateImageResponse> responses = response.getResponsesList();

				for (AnnotateImageResponse res : responses) {
					if (res.hasError()) {
						logger.warn("Error: {}", res.getError().getMessage());
						return Optional.empty();
					}

					// For full list of available annotations, see http://g.co/cloud/vision/docs
					Ingredients ingredients = new Ingredients();
					List<EntityAnnotation> annotations = res.getTextAnnotationsList();
					process(ingredients, annotations.get(0), translate);
					return Optional.of(ingredients);
				}
			}
		} catch (IOException e) {
			logger.error("Exception caught while parsing ingredients", e);
			throw new IngredientsApplicationException(e);
		}
		return Optional.empty();
	}

	private void process(Ingredients ingredients, EntityAnnotation annotation, Translate translate) {
		Detection detection = translate.detect(annotation.getDescription());
		String language = detection.getLanguage();
		String[] nonAllergens = new String[] { "water", "banana", "wheat", "soy", "sugar", "rice", "beef", "chicken", "cocoa",
				"salt", "milk",  };
		String[] allergens = new String[] { 
				"peanut", "walnut", "cashew", "pecan", "hazelnut", "sesame", "milk", "dairy" };
		String lc = annotation.getDescription();
		String tl = lc;
		if (!language.equals("en")) {
			Translation translation = translate.translate(lc, TranslateOption.sourceLanguage(language),
					TranslateOption.targetLanguage("en"));
			tl = translation.getTranslatedText().toLowerCase();
			logger.info("'{}' Translated to '{}'", lc, tl);
		} else {
			tl = lc.toLowerCase();
		}
		ingredients.setTranslatedText(lc);
		for (String allergen : allergens) {
			if (tl.contains(allergen)) {
				ingredients.addAllergen(new Allergen(allergen));
			}
		}
		for (String nonAllergen : nonAllergens) {
			if (tl.contains(nonAllergen)) {
				ingredients.addIngredient(new Ingredient(nonAllergen));
			}
		}
	}
}
