package interviewAPI.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import interviewAPI.utils.ExcelUtility;
import io.restassured.RestAssured;
import io.restassured.response.Response;


public class Cards {
	
	
	
	@Test
	public void cards() {
		
		List<Map<String,String>> csvCards;
		
		String path = System.getProperty("user.dir")+"/src/test/resources/testdata/cards.xlsx";
		
		String sheetName= "cards";
		
		
		csvCards = ExcelUtility.excelIntoListOfMaps(path, sheetName);
		
		System.out.println(csvCards);

		RestAssured.baseURI = "https://api.magicthegathering.io";

		Response allCards = RestAssured.given().when().get("/v1/cards").prettyPeek()
				;

		int size = allCards.body().jsonPath().getList("cards").size();

		System.out.println("size ->" + size);

		for (int i = 0; i < size; i++) {
			
			String idAPI = allCards.body().jsonPath().getString("cards[" + i + "].id");

			String nameAPI = allCards.body().jsonPath().getString("cards[" + i + "].name");
			String manaCostAPI = allCards.body().jsonPath().getString("cards[" + i + "].manaCost");
			String rarityAPI = allCards.body().jsonPath().getString("cards[" + i + "].rarity");
			String multiverseIdAPI = allCards.body().jsonPath().getString("cards[" + i + "].multiverseid");
			String setAPI = allCards.body().jsonPath().getString("cards[" + i + "].set");
			String powerAPI = allCards.body().jsonPath().getString("cards[" + i + "].power");
			String toughnessAPI = allCards.body().jsonPath().getString("cards[" + i + "].toughness");

			
			for(Map<String,String> x: csvCards) {
				
				if(x.get("Id").equals(idAPI)) {
					
					Assert.assertEquals("Name Value did not match!",x.get("Name"), nameAPI);
					Assert.assertEquals("Converted Mana Cost values did not match!",x.get("Converted Mana Cost"), manaCostAPI);
					Assert.assertEquals("Rarity values did not match!",x.get("Rarity"), rarityAPI);
					Assert.assertEquals("Set values did not match!",x.get("Set"), setAPI);
					Assert.assertEquals("Power values did not match!",x.get("Power"), powerAPI);
					Assert.assertEquals("Toughness values did not match!",x.get("Toughness"), toughnessAPI);
					Assert.assertEquals("MultiverseId values did not match!",x.get("MultiverseId"), multiverseIdAPI);
					
					
					
				}
				
			}
		
		
		
		
		
		}
	}
}
