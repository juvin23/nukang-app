package com.nukang.app.transaction;

import com.nukang.app.transactionHistory.service.TransactionHistoryService;
import com.nukang.app.user.AppUser;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService implements TransactionConstants {
    Logger log = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionRepository transactionRepository;

    private final TransactionHistoryService historyService;

    @Transactional
    public Transaction create(Transaction param) throws Exception {
        validateTransasction(param);
        String uuid = "";
        do{
            System.out.println(uuid);
            uuid = UUID.randomUUID().toString();
            transactionRepository.findByTransactionId(uuid);
            param.setTransactionId(uuid.replace("-",""));
        }while(transactionRepository.findByTransactionId(uuid).isPresent());

        log.info("transaction ID : " + uuid);


        LocalDateTime currTime = LocalDateTime.now();
        param.setCreatedDate(currTime);
        param.setLastUpdated(currTime);
        param.setIsSeen(2);
        if(param.getRecordStatus() != null)
            throw new Exception("");
        param.setRecordStatus(status.REQUEST_DATE);
        save(param);

        return param;
    }


    private void save(Transaction transaction) throws Exception {
        transactionRepository.findByTransactionId(transaction.getTransactionId()).ifPresent(historyService::save);
        transactionRepository.save(transaction);
    }


    private void validateTransasction(Transaction param){

    }

    public Page<Transaction> list(Predicate predicate, Pageable pageable) {
        if(predicate == null){
            return null;
        }
        Page<Transaction> transactions = transactionRepository.findAll(predicate,pageable);
        return transactions;
    }

    public Transaction reject(String transactionId, String reason, AppUser appUser) throws Exception{
        Transaction dbTrans = transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(()->new Exception(""));
        dbTrans.setRecordStatus(status.REJECTED);
        dbTrans.setUpdateBy(appUser.getUserId());
        dbTrans.setDeniedReason(reason);
        dbTrans.setLastUpdated(LocalDateTime.now());
        try{
            save(dbTrans);
        }catch (Exception e){
            log.info(e.getMessage());
            throw e;
        }
        return dbTrans;
    }

    public Transaction requestPrice(String transactionId,
                                    LocalDate startdate,
                                    LocalDate enddate,
                                    long price,
                                    AppUser appUser) throws Exception {
        Transaction dbTrans = transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(()->new Exception("Transaksi tidak ada."));
        if(!appUser.getUserId().equals(dbTrans.getCustomerId()))throw new Exception("Tidak berhak merubah transaksi");
        dbTrans.setRecordStatus(status.REQUEST_PRICE);
        dbTrans.setStartDate(startdate);
        dbTrans.setEndDate(enddate);
        dbTrans.setAmount(BigDecimal.valueOf(price));
        dbTrans.setLastUpdated(LocalDateTime.now());
        dbTrans.setUpdateBy(appUser.getUserId());
        try{
            save(dbTrans);
        }catch (Exception e){
            log.info(e.getMessage());
            throw e;
        }
        return dbTrans;
    }

    public Transaction approve(String transactionId, AppUser appUser) throws Exception {
        Transaction dbTrans = transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(()->new Exception(""));
        System.out.println("[approve-transaksi] status : " + dbTrans.getRecordStatus());
        System.out.println("appuser : " + appUser.getUserId());
        System.out.println("txnId   : " + dbTrans.getTransactionId());
        if(!appUser.getUserId().equals(dbTrans.getMerchantId())) throw new Exception("tidak berhak merubah transaksi.");
        if(dbTrans.getRecordStatus().equals(status.REQUEST_DATE))dbTrans.setRecordStatus(status.APPROVED_DATE);
        if(dbTrans.getRecordStatus().equals(status.REQUEST_PRICE))dbTrans.setRecordStatus(status.APPROVED_PRICE);
        if(dbTrans.getRecordStatus().equals(status.APPROVED_PRICE))dbTrans.setRecordStatus(status.DONE);
        dbTrans.setLastUpdated(LocalDateTime.now());
        try{
            save(dbTrans);
        }catch (Exception e){
            log.info(e.getMessage());
            throw e;
        }
        return dbTrans;
    }
}
