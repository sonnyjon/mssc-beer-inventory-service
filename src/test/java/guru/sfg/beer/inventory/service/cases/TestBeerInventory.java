package guru.sfg.beer.inventory.service.cases;

import guru.sfg.beer.inventory.service.domain.BeerInventory;

import java.util.UUID;

/**
 * Created by Sonny on 9/21/2022.
 */
public class TestBeerInventory
{
    public static BeerInventory getBeerInventory()
    {
        return getBeerInventory(
                    UUID.randomUUID(),
                    TestConstants.BEER_1_UUID,
                    TestConstants.BEER_1_UPC,
                    50
                );
    }

    public static BeerInventory getBeerInventory(UUID id, UUID beerId, String upc, Integer quantity)
    {
        return BeerInventory
                    .builder()
                    .id( id )
                    .beerId( beerId )
                    .upc( upc )
                    .quantityOnHand( quantity )
                    .build();
    }
}
