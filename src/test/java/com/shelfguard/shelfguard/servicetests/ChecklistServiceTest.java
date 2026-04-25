package com.shelfguard.shelfguard.servicetests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.shelfguard.shelfguard.batch.entity.Batch;
import com.shelfguard.shelfguard.checklist.service.ChecklistService;
import com.shelfguard.shelfguard.product.entity.Product;
import com.shelfguard.shelfguard.risk.RiskLevel;
import com.shelfguard.shelfguard.risk.service.RiskService;

public class ChecklistServiceTest {

    @Mock
    private RiskService riskService;

    private ChecklistService checklistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        checklistService = new ChecklistService(riskService);
    }

    @Test
    void should_generate_checklist_sorted_and_mapped_correctly() {

        // 🔥 given
        Product product = Product.builder()
                .id(1L)
                .name("Süt")
                .build();

        Batch batch1 = Batch.builder()
                .id(1L)
                .product(product)
                .expiryDate(LocalDate.now().plusDays(5))
                .quantityReceived(10)
                .build();

        Batch batch2 = Batch.builder()
                .id(2L)
                .product(product)
                .expiryDate(LocalDate.now().plusDays(2))
                .quantityReceived(20)
                .build();

        when(riskService.getAllRiskyBatches())
                .thenReturn(List.of(batch1, batch2));

        when(riskService.calculateBatchRisk(batch1))
                .thenReturn(RiskLevel.HIGH);

        when(riskService.calculateBatchRisk(batch2))
                .thenReturn(RiskLevel.CRITICAL);

        // 🔥 when
        var result = checklistService.generateTodayChecklist();

        // 🔥 then
        assertThat(result).hasSize(2);

        var first = result.get(0);
        var second = result.get(1);

        // ✔ sorting kontrol (en yakın tarih üstte)
        assertThat(first.getDaysToExpiry()).isLessThan(second.getDaysToExpiry());

        // ✔ mapping kontrol
        assertThat(first.getProductName()).isEqualTo("Süt");
        assertThat(first.getExpiryDate()).isEqualTo(batch2.getExpiryDate());

        // ✔ risk kontrol
        assertThat(first.getRiskLevel()).isEqualTo(RiskLevel.CRITICAL);
        assertThat(second.getRiskLevel()).isEqualTo(RiskLevel.HIGH);

        // ✔ reason kontrol
        assertThat(first.getReason()).contains("Kritik");
        assertThat(second.getReason()).contains("Risk");
    }

    @Test
    void should_return_empty_list_when_no_batches() {

        when(riskService.getAllRiskyBatches())
                .thenReturn(List.of());

        var result = checklistService.generateTodayChecklist();

        assertThat(result).isEmpty();
    }

    @Test
    void should_calculate_days_to_expiry_correctly() {

        Product product = Product.builder()
                .id(1L)
                .name("Yoğurt")
                .build();

        Batch batch = Batch.builder()
                .id(1L)
                .product(product)
                .expiryDate(LocalDate.now().plusDays(3))
                .quantityReceived(10)
                .build();

        when(riskService.getAllRiskyBatches())
                .thenReturn(List.of(batch));

        when(riskService.calculateBatchRisk(batch))
                .thenReturn(RiskLevel.MEDIUM);

        var result = checklistService.generateTodayChecklist();

        assertThat(result.get(0).getDaysToExpiry()).isEqualTo(3);
    }
}
