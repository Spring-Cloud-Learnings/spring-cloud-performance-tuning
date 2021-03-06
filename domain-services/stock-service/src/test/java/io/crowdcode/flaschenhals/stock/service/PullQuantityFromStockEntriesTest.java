package io.crowdcode.flaschenhals.stock.service;


import io.crowdcode.flaschenhals.stock.model.StockEntry;
import io.crowdcode.flaschenhals.stock.resource.StockEntryResponse;
import io.crowdcode.flaschenhals.stock.repository.StockEntryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static io.crowdcode.flaschenhals.stock.fixture.StockFixture.buildStockEntry;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PullQuantityFromStockEntriesTest {

    @InjectMocks
    private StockService stockService;

    @Mock
    private StockEntryRepository repository;

    @Test
    public void testPullEntriesFromExistingStock() throws Exception {
        StockEntry[] entries = new StockEntry[]{
                buildStockEntry(1l, 2l, 1.4),
                buildStockEntry(1l, 1l, 1.2),
                buildStockEntry(1l, 5l, 1.3),
                buildStockEntry(1l, 1l, 1.1)
        };

        when(repository.findByStockIdAndProductId(any(), any())).thenReturn(Arrays.asList(entries));

        List<StockEntryResponse> pull = stockService.pull(1l, 1l, 4l);

        assertThat(pull, hasSize(3));
        assertThat(pull, containsInAnyOrder(
                new StockEntryResponse(1l, 2l, BigDecimal.valueOf(1.4d)),
                new StockEntryResponse(1l, 1l, BigDecimal.valueOf(1.2d)),
                new StockEntryResponse(1l, 1l, BigDecimal.valueOf(1.3d))));
    }
}