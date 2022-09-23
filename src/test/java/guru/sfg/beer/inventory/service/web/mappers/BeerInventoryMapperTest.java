package guru.sfg.beer.inventory.service.web.mappers;

import guru.sfg.beer.inventory.service.cases.TestBeerInventory;
import guru.sfg.beer.inventory.service.cases.TestBeerInventoryDto;
import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.web.model.BeerInventoryDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Sonny on 9/21/2022.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BeerInventoryMapperImpl.class, DateMapper.class})
class BeerInventoryMapperTest
{
    @Autowired
    private BeerInventoryMapper beerInventoryMapper;

    @Nested
    @DisplayName("toBeerInventory() Method")
    class ToBeerInventory
    {
        @Test
        @DisplayName("should convert BeerInventoryDto and return as BeerInventory")
        void givenDto_whenToBeerInventory_thenReturnEntity()
        {
            // given
            final BeerInventoryDto expected = TestBeerInventoryDto.getBeerInventoryDto();

            // when
            BeerInventory actual = beerInventoryMapper.toBeerInventory( expected );

            // then
            assertNotNull( actual );
            assertEquals( expected.getBeerId(), actual.getBeerId() );
            assertEquals( expected.getUpc(), actual.getUpc() );
            assertEquals( expected.getQuantityOnHand(), actual.getQuantityOnHand() );
        }

        @Test
        @DisplayName("should return null")
        void givenNull_whenToBeerInventory_thenReturnNull()
        {
            final BeerInventoryDto expected = null;                                     // given
            BeerInventory actual = beerInventoryMapper.toBeerInventory( expected );     // when

            assertNull( actual );                                                       // then
        }
    }

    @Nested
    @DisplayName("toBeerInventoryDo() Method")
    class ToBeerInventoryDto
    {
        @Test
        @DisplayName("should convert BeerInventory and return as BeerInventoryDto")
        void givenEntity_whenToBeerInventoryDto_thenReturnDto()
        {
            // given
            final BeerInventory expected = TestBeerInventory.getBeerInventory();

            // when
            BeerInventoryDto actual = beerInventoryMapper.toBeerInventoryDto( expected );

            // then
            assertNotNull( actual );
            assertEquals( expected.getBeerId(), actual.getBeerId() );
            assertEquals( expected.getUpc(), actual.getUpc() );
            assertEquals( expected.getQuantityOnHand(), actual.getQuantityOnHand() );
        }

        @Test
        @DisplayName("should return null")
        void givenNull_whenToBeerInventoryDto_thenReturnNull()
        {
            final BeerInventory expected = null;                                     // given
            BeerInventoryDto actual = beerInventoryMapper.toBeerInventoryDto( expected );     // when

            assertNull( actual );                                                       // then
        }
    }
}