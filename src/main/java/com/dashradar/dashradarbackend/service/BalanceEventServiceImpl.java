package com.dashradar.dashradarbackend.service;

import com.dashradar.dashradarbackend.domain.dto.AddressBalanceChange;
import com.dashradar.dashradarbackend.repository.BalanceEventRepository;
import com.dashradar.dashradarbackend.repository.MetaRepository;
import com.dashradar.dashradarbackend.repository.TransactionRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BalanceEventServiceImpl implements BalanceEventService {
    
    @Autowired
    private BalanceEventRepository balanceEventRepository;
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private MetaRepository metaRepository;

    @Override
    @Transactional
    public void createBalances(long blockheight) {
        List<String> findBlockTxIds = transactionRepository.findBlockTxIds(blockheight);
        for (String txid : findBlockTxIds) {
            this.createBalances(txid);
        }
        metaRepository.setLastBlockHeightWithBalanceEvent(blockheight);
    }

    @Override
    public void setLastBlockContainingBalanceEvent(long height) {
        metaRepository.setLastBlockHeightWithBalanceEvent(height);
    }

    @Override
    public Long lastBlockContainingBalanceEvent() {
        List<Long> res = metaRepository.lastBlockContainingBalanceEvent();
        if (res.isEmpty()) {
            res = balanceEventRepository.lastBlockContainingBalanceEventOld();
            if (res.isEmpty()) {
                return null;
            }    
        }
        return res.get(0);
    }
    
    
    @Transactional
    private void createBalances(String txid) {
        List<AddressBalanceChange> inputs = balanceEventRepository.findAddressBalanceChangesOfTransactionInputs(txid);
        List<AddressBalanceChange> outputs = balanceEventRepository.findAddressBalanceChangesOfTransactionOutputs(txid);
        Map<String, Long> addressToDelta = new HashMap<>();
        for (AddressBalanceChange c : inputs) {
            addressToDelta.put(c.address, c.deltaSat);
        }
        for (AddressBalanceChange c : outputs) {
            addressToDelta.merge(c.address, c.deltaSat, (oldValue, newValue) -> oldValue+newValue);
        }
        addressToDelta.entrySet().stream().forEach(e -> 
            balanceEventRepository.changeAdderssBalance(txid, e.getKey(), e.getValue())
        );
    }
    
   
}
