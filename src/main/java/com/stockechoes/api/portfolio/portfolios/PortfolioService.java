package com.stockechoes.api.portfolio.portfolios;

import com.stockechoes.api.GenericApiException;
import com.stockechoes.api.accounts.Account;
import com.stockechoes.api.accounts.AccountService;
import com.stockechoes.api.portfolio.portfolios.exceptions.PortfolioExceptions.PortfolioNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class PortfolioService {
    @Inject
    PortfolioRepository portfolioRepository;

    @Inject
    AccountService accountService;

    public Portfolio findPortfolioOnAccountByIdOrThrow(Long id) throws PortfolioNotFoundException {
        Account account = accountService.getAccountFromContextOrThrow();

        List<Portfolio> portfolioList = account.getPortfolios();

        return portfolioList.stream()
                .filter(ptf -> Objects.equals(ptf.getId(), id))
                .findFirst()
                .orElseThrow( () -> new PortfolioNotFoundException(id));
    }

    public List<Portfolio> getAccountPortfolios() {
        Account account = accountService.getAccountFromContextOrThrow();

        return account.getPortfolios();
    }

    public void savePortfolioWithoutDuplicates(Portfolio portfolio) {
        Account account = accountService.getAccountFromContextOrThrow();
        portfolio.setAccount(account);

        assertDuplicatePortfolio(portfolio);

        portfolioRepository.persist(portfolio);
    }


    private void assertDuplicatePortfolio(Portfolio portfolio) {
        if(portfolioRepository.findDuplicateOptional(portfolio.getName(), portfolio.getAccount().getId()).isPresent()) {
            throw new GenericApiException(Response.Status.CONFLICT, "Duplicate portfolio, please choose another name");
        }
    }
}
