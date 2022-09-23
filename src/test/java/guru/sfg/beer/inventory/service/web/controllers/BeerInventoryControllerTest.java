package guru.sfg.beer.inventory.service.web.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.sfg.beer.inventory.service.cases.TestBeerInventory;
import guru.sfg.beer.inventory.service.cases.TestBeerInventoryDto;
import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import guru.sfg.beer.inventory.service.web.mappers.BeerInventoryMapper;
import guru.sfg.beer.inventory.service.web.model.BeerInventoryDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Sonny on 9/21/2022.
 */
@ExtendWith(MockitoExtension.class)
@WebMvcTest(BeerInventoryController.class)
class BeerInventoryControllerTest
{
    private static final String BEER_PATH = "/api/v1/beer/";

    @MockBean
    BeerInventoryRepository beerInventoryRepository;

    @MockBean
    BeerInventoryMapper beerInventoryMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    AutoCloseable mocks;

    @BeforeEach
    void setUp()
    {
        this.mocks = MockitoAnnotations.openMocks( this );
    }

    @AfterEach
    void tearDown() throws Exception
    {
        this.mocks.close();
    }

    @Nested
    @DisplayName("listBeersById() Method")
    class ListBeersByIdTest
    {
        @Test
        @DisplayName("should return List of BeerInventoryDto")
        void givenId_whenListBeersById_thenReturnBeerInventoryDtoList() throws Exception
        {
            // given
            final BeerInventory beerInventory = TestBeerInventory.getBeerInventory();
            final BeerInventoryDto beerInventoryDto = TestBeerInventoryDto.getBeerInventoryDto();
            final UUID beerId = beerInventory.getBeerId();

            final String PATH = BEER_PATH + beerId + "/inventory";
            final List<BeerInventory> beers = new ArrayList<>();
            beers.add( beerInventory );

            // when
            when(beerInventoryRepository.findAllByBeerId( beerId )).thenReturn( beers );
            when(beerInventoryMapper.toBeerInventoryDto(any( BeerInventory.class ))).thenReturn( beerInventoryDto );

            MvcResult result = mockMvc.perform(get( PATH ).accept(  MediaType.APPLICATION_JSON ))
                    .andExpect(content().contentType( MediaType.APPLICATION_JSON ))
                    .andExpect( status().isOk() )
                    .andReturn();

            List<BeerInventoryDto> actual = objectMapper.readValue(
                    result.getResponse().getContentAsString(),
                    new TypeReference<>() {}
            );

            // then
            assertNotNull( actual );
            assertEquals( 1, actual.size() );
            assertEquals( beerId, actual.get( 0 ).getBeerId() );
        }

        @Test
        @DisplayName("should return empty list")
        void givenBadId_whenListBeersById_thenReturnEmptyList() throws Exception
        {
            // given
            final String PATH = BEER_PATH + UUID.randomUUID() + "/inventory";
            List<BeerInventory> emptyList = new ArrayList<>();

            // when
            when(beerInventoryRepository.findAllByBeerId(any( UUID.class ))).thenReturn( emptyList );

            MvcResult result = mockMvc.perform(get( PATH ).accept(  MediaType.APPLICATION_JSON ))
                    .andExpect(content().contentType( MediaType.APPLICATION_JSON ))
                    .andExpect( status().isOk() )
                    .andReturn();

            List<BeerInventoryDto> actual = objectMapper.readValue(
                    result.getResponse().getContentAsString(),
                    new TypeReference<>() {}
            );

            // then
            assertEquals( 0, actual.size() );
            verify(beerInventoryRepository, times(1)).findAllByBeerId(any( UUID.class ));
            verify(beerInventoryMapper, times(0)).toBeerInventoryDto(any( BeerInventory.class ));
        }
    }



}