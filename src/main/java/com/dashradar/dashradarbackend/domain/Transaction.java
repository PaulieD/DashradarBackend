package com.dashradar.dashradarbackend.domain;

import java.util.ArrayList;
import java.util.List;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Transaction {

    public static final int PRIVATE_SEND = 2;

    public static final int PRIVATE_SEND_COLLATERAL_PAYMENT = 9;

    public static final int PRIVATE_SEND_CREATE_DENOMINATIONS = 1;

    public static final int PRIVATE_SEND_MAKE_COLLATERAL_PAYMENTS = 8;

    public static final int PRIVATE_SEND_MIXING_0_01 = 7;

    public static final int PRIVATE_SEND_MIXING_0_1 = 6;

    public static final int PRIVATE_SEND_MIXING_100_0 = 3;

    public static final int PRIVATE_SEND_MIXING_10_0 = 4;

    public static final int PRIVATE_SEND_MIXING_1_0 = 5;

    public static final int PRIVATE_SEND_NONE = 0;

    /**
     * Used only temporarily when importing the blockchain
     */
    public static final int PRIVATE_SEND_POTENTIALLY_MIXING = -1;

    /**
     * Used only temporarily when importing the blockchain
     */
    public static final int PRIVATE_SEND_POTENTIALLY_PRIVATE_SEND = -2;

    @Relationship(type = "CREATES", direction = Relationship.OUTGOING)
    private List<BalanceEvent> balanceEvents = new ArrayList<>();
    
    @Relationship(type = "INCLUDED_IN", direction = Relationship.OUTGOING)
    private Block block;

    private long feesSat;

    @GraphId
    private Long id;

    private long locktime;
    
    private int n;

    @Relationship(type = "PREVIOUS_ROUND", direction = Relationship.OUTGOING)
    private List<Transaction> previousRounds = new ArrayList<>();

    private int pstype = PRIVATE_SEND_NONE;

    private long size;

    private String txid;

    private int version;
   
    @Relationship(type = "INPUT", direction = Relationship.INCOMING)
    private List<TransactionInput> vin = new ArrayList<>();

    @Relationship(type = "OUTPUT", direction = Relationship.OUTGOING)
    private List<TransactionOutput> vout = new ArrayList<>();

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public long getFeesSat() {
        return feesSat;
    }

    public void setFeesSat(long feesSat) {
        this.feesSat = feesSat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getLocktime() {
        return locktime;
    }

    public void setLocktime(long locktime) {
        this.locktime = locktime;
    }
    
    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public List<Transaction> getPreviousRounds() {
        return previousRounds;
    }

    public void setPreviousRounds(List<Transaction> previousRounds) {
        this.previousRounds = previousRounds;
    }

    public int getPstype() {
        return pstype;
    }

    public void setPstype(int pstype) {
        this.pstype = pstype;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<TransactionInput> getVin() {
        return vin;
    }

    public void setVin(List<TransactionInput> vin) {
        this.vin = vin;
    }

    public List<TransactionOutput> getVout() {
        return vout;
    }

    public void setVout(List<TransactionOutput> vout) {
        this.vout = vout;
    }

}
