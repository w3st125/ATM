package org.atm;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.security.Principal;
import org.atm.db.model.Account;
import org.atm.service.AccountService;
import org.atm.service.BankOperationService;
import org.atm.web.controller.AtmController;
import org.atm.web.mapper.ResponseMapperImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@Disabled
@WebMvcTest(AtmController.class)
public class AtmControllerTest {

    @Autowired private MockMvc mvc;

    @SpyBean private ResponseMapperImpl responseMapper;
    @MockBean private BankOperationService bankOperationService;
    @MockBean private AccountService accountService;

    /*   @MockBean private GetTokenService getTokenService;*/
    /*@MockBean private TokenAuthenticationFilter tokenAuthenticationFilter;*/

    Account accountOne = new Account(1L, 1L, BigDecimal.valueOf(10), "222", 638L);

    @Test
    public void should_return_balance_when_given_number() throws Exception {

        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("me");
        when(bankOperationService.getBalanceByNumber("222")).thenReturn(accountOne.getBalance());
        mvc.perform(
                        get("/operation/show-balance/222")
                                .principal(mockPrincipal)
                                .with(user("w3st125")))
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
