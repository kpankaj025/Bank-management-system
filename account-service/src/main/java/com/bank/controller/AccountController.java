package com.bank.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bank.dto.AccountDto;
import com.bank.dto.RegistrationDto;
import com.bank.entity.enums.AccountType;
import com.bank.service.AccountService;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationDto> registerAccount(@Valid @RequestBody RegistrationDto registrationDto) {

        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            throw new IllegalArgumentException("Password and Confirm Password do not match");
        }

        RegistrationDto registeredAccount = accountService.registerAccount(registrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredAccount);
    }

    @PostMapping("/upload-profile/{id}")
    public ResponseEntity<AccountDto> uploadProfile(@PathVariable String id,
            @RequestParam("banner") MultipartFile banner) throws Exception {

        AccountDto acc = accountService.uploadProfile(id, banner);
        return new ResponseEntity<>(acc, HttpStatus.ACCEPTED);

    }

    @GetMapping("/serve-profile/{id}")
    public ResponseEntity<Resource> serveProfile(@PathVariable String id) throws Exception {

        Resource resource = accountService.getProfilePic(id);
        return ResponseEntity.ok()
                .contentType(MediaTypeFactory.getMediaType(resource)
                        .orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(resource);
    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto accountDto) {
        AccountDto createdAccount = accountService.createAccount(accountDto);
        return ResponseEntity.ok(createdAccount);
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable String accountId,
            @RequestBody AccountDto accountDto) {
        AccountDto updatedAccount = accountService.updateAccount(accountId, accountDto);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }

    // rate limiter on service layer
    @RateLimiter(name = "accountServiceRateLimiter", fallbackMethod = "getAllFallBack")
    @GetMapping
    public ResponseEntity<java.util.List<AccountDto>> getAllAccounts() {
        List<AccountDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    public ResponseEntity<List<AccountDto>> getAllFallBack(Exception ex) {
        List<AccountDto> accounts = Collections.emptyList();
        return new ResponseEntity<>(accounts, HttpStatus.TOO_MANY_REQUESTS);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable String accountId) {
        AccountDto account = accountService.getAccountById(accountId);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/type/{typ}")
    public ResponseEntity<java.util.List<AccountDto>> getAccountsByAccountType(@PathVariable String typ) {
        AccountType accountType;

        if (typ.equalsIgnoreCase("SAVINGS")) {
            accountType = AccountType.SAVINGS;
        } else if (typ.equalsIgnoreCase("CURRENT")) {
            accountType = AccountType.CURRENT;
        } else {
            return ResponseEntity.badRequest().build();
        }

        List<AccountDto> accounts = accountService.getAccountsByAccountType(accountType);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/name/{firstName}/{lastName}")
    public ResponseEntity<List<AccountDto>> getAccountsByFirstNameAndLastName(
            @PathVariable String firstName,
            @PathVariable String lastName) {
        List<AccountDto> accounts = accountService.getAccountsByFirstNameAndLastName(firstName, lastName);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AccountDto> getAccountByEmail(@PathVariable String email) {
        AccountDto account = accountService.getAccountByEmail(email);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/username/{userName}")
    public ResponseEntity<AccountDto> getAccountByUserName(@PathVariable String userName) {
        AccountDto account = accountService.getAccountByUserName(userName);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<AccountDto> getAccountByPhoneNumber(@PathVariable String phoneNumber) {
        AccountDto account = accountService.getAccountByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/addhar/{addharNumber}")
    public ResponseEntity<AccountDto> getAccountByAddharNumber(@PathVariable String addharNumber) {
        AccountDto account = accountService.getAccountByAddharNumber(addharNumber);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/pan/{panNumber}")
    public ResponseEntity<AccountDto> getAccountByPanNumber(@PathVariable String panNumber) {
        AccountDto account = accountService.getAccountByPanNumber(panNumber);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/accountNumber/{accNo}")
    public ResponseEntity<AccountDto> getAccountByAccountNumber(@PathVariable String accNo) {

        AccountDto acc = accountService.getAccountByAccountNumber(accNo);
        return new ResponseEntity<>(acc, HttpStatus.OK);
    }

    // get accounts in pages filtered

    @GetMapping("/filtered")
    public ResponseEntity<Page<AccountDto>> getFilteredAccount(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(sortDir) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AccountDto> pages = accountService.getFilteredAccount(pageable);
        return new ResponseEntity<>(pages, HttpStatus.OK);

    }
}
