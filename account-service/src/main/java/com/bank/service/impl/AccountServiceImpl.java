package com.bank.service.impl;

import java.util.Date;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.core.io.Resource;
import org.springframework.messaging.Message;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bank.config.Constants;
import com.bank.dto.AccountDto;
import com.bank.dto.EmailDto;
import com.bank.dto.FileData;
import com.bank.dto.RegistrationDto;
import com.bank.entity.Account;
import com.bank.entity.enums.AccountType;
import com.bank.exception.ResourceNotFoundException;
import com.bank.repository.AccountRepo;
import com.bank.service.AccountService;
import com.bank.service.FileUpload;
import com.bank.util.Helper;

@Service
public class AccountServiceImpl implements AccountService {

    private FileUpload fileUpload;
    private final AccountNumberGenerator accountNumberGenerator;
    private StreamBridge streamBridge;
    private final AccountRepo accountRepo;
    private ModelMapper modelMapper;

    public AccountServiceImpl(AccountNumberGenerator accountNumberGenerator, StreamBridge streamBridge,
            AccountRepo accountRepo, ModelMapper modelMapper, FileUpload fileUpload) {
        this.accountNumberGenerator = accountNumberGenerator;
        this.streamBridge = streamBridge;
        this.accountRepo = accountRepo;
        this.modelMapper = modelMapper;
        this.fileUpload = fileUpload;
    }

    @Override
    public RegistrationDto registerAccount(RegistrationDto registrationDto) {

        Account account = new Account();
        account.setId(Helper.generateId());
        account.setFirstName(registrationDto.getFirstName());
        account.setLastName(registrationDto.getLastName());
        account.setEmail(registrationDto.getEmail());
        account.setPassword(registrationDto.getPassword());
        account.setAccountNumber(accountNumberGenerator.generateAccountNumber());
        Account savedAccount = accountRepo.save(account);

        EmailDto emailDto = new EmailDto(savedAccount.getEmail(), "Account Created",
                "Your account has been created successfully with Account Number: " + savedAccount.getAccountNumber());

        Message<EmailDto> message = org.springframework.integration.support.MessageBuilder.withPayload(emailDto)
                .build();
        streamBridge.send("emailProducer-out-0", message);

        return registrationDto;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account acc = modelMapper.map(accountDto, Account.class);
        acc.setId(Helper.generateId());
        acc.setAccountNumber(accountNumberGenerator.generateAccountNumber());
        Account savedAcc = accountRepo.save(acc);

        EmailDto emailDto = new EmailDto(savedAcc.getEmail(), "Account Created",
                "Your account has been created successfully with ID: " + savedAcc.getId());

        Message<EmailDto> message = org.springframework.integration.support.MessageBuilder.withPayload(emailDto)
                .build();

        streamBridge.send("emailProducer-out-0", message);
        return modelMapper.map(savedAcc, AccountDto.class);
    }

    @Override
    public AccountDto getAccountById(String accountId) {
        Account acc = accountRepo.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        return modelMapper.map(acc, AccountDto.class);
    }

    @Override
    public AccountDto updateAccount(String accountId, AccountDto accountDto) {

        Account existingAcc = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        existingAcc.setAddharNumber(accountDto.getAddharNumber());
        return modelMapper.map(existingAcc, AccountDto.class);
    }

    @Override
    public void deleteAccount(String id) {
        // TODO Auto-generated method stub
        accountRepo.deleteById(id);

    }

    @Override
    public List<AccountDto> getAllAccounts() {
        // TODO Auto-generated method stub
        List<Account> accounts = accountRepo.findAll();
        return accounts.stream().map(acc -> modelMapper.map(acc, AccountDto.class)).toList();
    }

    @Override
    public List<AccountDto> getAccountsByAccountType(AccountType accountType) {
        // TODO Auto-generated method stub
        List<Account> accounts = accountRepo.findByAccountType(accountType);
        return accounts.stream().map(acc -> modelMapper.map(acc, AccountDto.class)).toList();
    }

    @Override
    public AccountDto getAccountByEmail(String email) {
        // TODO Auto-generated method stub
        Account acc = accountRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        return modelMapper.map(acc, AccountDto.class);
    }

    @Override
    public AccountDto getAccountByUserName(String userName) {
        // TODO Auto-generated method stub
        Account acc = accountRepo.findByUserName(userName).orElseThrow(() -> new RuntimeException("Account not found"));
        return modelMapper.map(acc, AccountDto.class);
    }

    @Override
    public AccountDto getAccountByPhoneNumber(String phoneNumber) {
        // TODO Auto-generated method stub
        Account acc = accountRepo.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return modelMapper.map(acc, AccountDto.class);
    }

    @Override
    public AccountDto getAccountByAddharNumber(String addharNumber) {
        // TODO Auto-generated method stub
        Account acc = accountRepo.findByAddharNumber(addharNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return modelMapper.map(acc, AccountDto.class);
    }

    @Override
    public AccountDto getAccountByPanNumber(String panNumber) {
        Account acc = accountRepo.findByPanNumber(panNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return modelMapper.map(acc, AccountDto.class);
    }

    @Override
    public List<AccountDto> getAccountsByFirstNameAndLastName(String firstName, String lastName) {

        List<Account> accounts = accountRepo.findByFirstNameAndLastName(firstName, lastName);
        return accounts.stream().map(acc -> modelMapper.map(acc, AccountDto.class)).toList();
    }

    @Override
    public AccountDto getAccountByAccountNumber(String accountNumber) {
        Account acc = accountRepo.findByAccountNumber(accountNumber);
        return modelMapper.map(acc, AccountDto.class);
    }

    @Override
    public AccountDto uploadProfile(String id, MultipartFile file) throws Exception {

        String fileName = file.getOriginalFilename();
        String fileExtension = fileName.substring(fileName.indexOf("."));
        String newfileName = new Date().getTime() + fileExtension;

        FileData fileData = fileUpload.uplaodFile(file, Constants.PROFILE_PATH + newfileName);
        Account acc = accountRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("account not exist"));
        acc.setProfileImageUrl(fileData.getFileName());
        accountRepo.save(acc);
        return modelMapper.map(acc, AccountDto.class);
    }

    @Override
    public Resource getProfilePic(String id) throws Exception {

        Account acc = accountRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("account not found"));

        String path = acc.getProfileImageUrl();
        Resource resource = fileUpload.loadFile(Constants.PROFILE_PATH + path);
        return resource;

    }

}
