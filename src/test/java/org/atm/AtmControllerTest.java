package org.atm;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import org.atm.db.model.Account;
import org.atm.service.BankOperationService;
import org.atm.web.controller.AtmController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AtmController.class)
public class AtmControllerTest {

    @Autowired private MockMvc mvc;

    @MockBean private BankOperationService bankOperationService;

    Account accountOne = new Account(1, BigDecimal.valueOf(10), "222", 638L);

    @Test
    public void should_return_balance_when_given_number() throws Exception {

        when(bankOperationService.getBalanceByNumber("222")).thenReturn(accountOne.getBalance());
        mvc.perform(get("/operation/show-balance/222"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        content()
                                .json(
                                        """
                                {"balance": 10.00}
                                """));
    }
}
