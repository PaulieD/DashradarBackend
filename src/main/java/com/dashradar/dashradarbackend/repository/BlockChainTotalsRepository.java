package com.dashradar.dashradarbackend.repository;

import com.dashradar.dashradarbackend.domain.BlockChainTotals;
import com.dashradar.dashradarbackend.domain.Transaction;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BlockChainTotalsRepository extends Neo4jRepository<BlockChainTotals, Long> {
    
    @Query("MATCH\n"
    + "	 (b:Block)\n"
    + "WITH\n"
    + "	 b\n"
    + "ORDER BY \n"
    + "	 b.height\n"
    + "MERGE \n"
    + "	 (a:BlockChainTotals {height: b.height})\n"
    + "ON CREATE SET \n"
    + "	 a += {\n"
    + "	   time: b.time\n"
    + "	 }")
    void create_block_chain_totals();
    
    @Query("MATCH \n"
    + "	 (a:BlockChainTotals)\n"
    + "WHERE\n"
    + "	 NOT exists(a.input_count)\n"
    + "WITH\n"
    + "	 a\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH \n"
    + "	 (b:Block {height: a.height})<-[:INCLUDED_IN]-(:Transaction)<-[:INPUT]-(input:TransactionInput)\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 count(input) as input_count\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH\n"
    + "	 (previousTotals:BlockChainTotals {height: a.height-1})\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 input_count,\n"
    + "	 previousTotals\n"
    + "ORDER BY\n"
    + "	 a.height\n"
    + "SET\n"
    + "	 a += {\n"
    + "	  input_count: coalesce(previousTotals.input_count, 0) + input_count\n"
    + "	 }"
    )
    void compute_input_counts();
    
    @Deprecated
    @Query("MATCH \n"
    + "	 (a:BlockChainTotals)\n"
    + "WHERE\n"
    + "	 NOT exists(a.privatesend_mixing_100_0_count)\n"
    + "WITH\n"
    + "	 a\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH \n"
    + "	 (b:Block {height: a.height})<-[:INCLUDED_IN]-(tx:Transaction {pstype:"+Transaction.PRIVATE_SEND_MIXING_100_0+"})\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 count(tx) as tx_count\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH\n"
    + "	 (previousTotals:BlockChainTotals {height: a.height-1})\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 tx_count,\n"
    + "	 previousTotals\n"
    + "ORDER BY\n"
    + "	 a.height\n"
    + "SET\n"
    + "	 a += {\n"
    + "    privatesend_mixing_100_0_count: coalesce(previousTotals.privatesend_mixing_100_0_count, 0) + tx_count\n"
    + "	 }")
    void compute_mixing_100_0_counts();
    
    @Deprecated
    @Query("MATCH \n"
    + "	 (a:BlockChainTotals)\n"
    + "WHERE\n"
    + "	 NOT exists(a.privatesend_mixing_10_0_count)\n"
    + "WITH\n"
    + "	 a\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH \n"
    + "	 (b:Block {height: a.height})<-[:INCLUDED_IN]-(tx:Transaction {pstype:"+Transaction.PRIVATE_SEND_MIXING_10_0+"})\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 count(tx) as tx_count\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH\n"
    + "	 (previousTotals:BlockChainTotals {height: a.height-1})\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 tx_count,\n"
    + "	 previousTotals\n"
    + "ORDER BY\n"
    + "	 a.height\n"
    + "SET\n"
    + "	 a += {\n"
    + "    privatesend_mixing_10_0_count: coalesce(previousTotals.privatesend_mixing_10_0_count, 0) + tx_count\n"
    + "	 }")
    void compute_mixing_10_0_counts();
    
    @Deprecated
    @Query("MATCH \n"
    + "	 (a:BlockChainTotals)\n"
    + "WHERE\n"
    + "	 NOT exists(a.privatesend_mixing_1_0_count)\n"
    + "WITH\n"
    + "	 a\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH \n"
    + "	 (b:Block {height: a.height})<-[:INCLUDED_IN]-(tx:Transaction {pstype:"+Transaction.PRIVATE_SEND_MIXING_1_0+"})\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 count(tx) as tx_count\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH\n"
    + "	 (previousTotals:BlockChainTotals {height: a.height-1})\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 tx_count,\n"
    + "	 previousTotals\n"
    + "ORDER BY\n"
    + "	 a.height\n"
    + "SET\n"
    + "	 a += {\n"
    + "	   privatesend_mixing_1_0_count: coalesce(previousTotals.privatesend_mixing_1_0_count, 0) + tx_count\n"
    + "	 }")
    void compute_mixing_1_0_counts();
    
    @Deprecated
    @Query("MATCH \n"
    + "	 (a:BlockChainTotals)\n"
    + "WHERE\n"
    + "	 NOT exists(a.privatesend_mixing_0_1_count)\n"
    + "WITH\n"
    + "	 a\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH \n"
    + "	 (b:Block {height: a.height})<-[:INCLUDED_IN]-(tx:Transaction {pstype:"+Transaction.PRIVATE_SEND_MIXING_0_1+"})\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 count(tx) as tx_count\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH\n"
    + "	 (previousTotals:BlockChainTotals {height: a.height-1})\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 tx_count,\n"
    + "	 previousTotals\n"
    + "ORDER BY\n"
    + "	 a.height\n"
    + "SET\n"
    + "	 a += {\n"
    + "	   privatesend_mixing_0_1_count: coalesce(previousTotals.privatesend_mixing_0_1_count, 0) + tx_count\n"
    + "	 }")
    void compute_mixing_0_1_counts();
    
    @Deprecated
    @Query("MATCH \n"
    + "	 (a:BlockChainTotals)\n"
    + "WHERE\n"
    + "	 NOT exists(a.privatesend_mixing_0_01_count)\n"
    + "WITH\n"
    + "	 a\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH \n"
    + "	 (b:Block {height: a.height})<-[:INCLUDED_IN]-(tx:Transaction {pstype:"+Transaction.PRIVATE_SEND_MIXING_0_01+"})\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 count(tx) as tx_count\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH\n"
    + "	 (previousTotals:BlockChainTotals {height: a.height-1})\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 tx_count,\n"
    + "	 previousTotals\n"
    + "ORDER BY\n"
    + "	 a.height\n"
    + "SET\n"
    + "	 a += {\n"
    + "	   privatesend_mixing_0_01_count: coalesce(previousTotals.privatesend_mixing_0_01_count, 0) + tx_count\n"
    + "	 }")
    void compute_mixing_0_01_counts();
    
    
    @Query("MATCH \n"
    + "  (a:BlockChainTotals)\n"
    + "WHERE\n"
    + "  NOT exists(a.output_count)\n"
    + "WITH\n"
    + "  a\n"
    + "ORDER BY \n"
    + "  a.height\n"
    + "OPTIONAL MATCH \n"
    + "	 (b:Block {height: a.height})<-[:INCLUDED_IN]-(:Transaction)-[:OUTPUT]->(output:TransactionOutput)\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 count(output) as output_count\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH\n"
    + "	 (previousTotals:BlockChainTotals {height: a.height-1})\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 output_count,\n"
    + "	 previousTotals\n"
    + "ORDER BY\n"
    + "	 a.height\n"
    + "SET\n"
    + "	 a += {\n"
    + "    output_count: coalesce(previousTotals.output_count, 0) + output_count\n"
    + "  }")
    void compute_output_counts();
    
    @Deprecated
    @Query("MATCH \n"
    + "	 (a:BlockChainTotals)\n"
    + "WHERE\n"
    + "	 NOT exists(a.privatesend_tx_count)\n"
    + "WITH\n"
    + "	 a\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH \n"
    + "	 (b:Block {height: a.height})<-[:INCLUDED_IN]-(tx:Transaction {pstype:"+Transaction.PRIVATE_SEND+"})\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 count(tx) as tx_count\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH\n"
    + "	 (previousTotals:BlockChainTotals {height: a.height-1})\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 tx_count,\n"
    + "	 previousTotals\n"
    + "ORDER BY\n"
    + "	 a.height\n"
    + "SET\n"
    + "	 a += {\n"
    + "	   privatesend_tx_count: coalesce(previousTotals.privatesend_tx_count, 0) + tx_count\n"
    + "	 }")
    void compute_privatesend_tx_count();
    
    @Query("MATCH \n"
    + "	 (a:BlockChainTotals)\n"
    + "WHERE\n"
    + "	 NOT exists(a.total_block_rewards_sat)\n"
    + "WITH\n"
    + " 	a\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH \n"
    + "	 (b:Block {height: a.height})<-[:INCLUDED_IN]-(tx:Transaction)-[:OUTPUT]->(output:TransactionOutput), (tx)<-[:INPUT]-(input:TransactionInput)\n"
    + "WHERE\n"
    + "	 input.coinbase IS NOT NULL\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 sum(distinct output.valueSat) as total_block_rewards_sat\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH\n"
    + "	 (previousTotals:BlockChainTotals {height: a.height-1})\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 total_block_rewards_sat,\n"
    + "	 previousTotals\n"
    + "ORDER BY\n"
    + " 	a.height\n"
    + "SET\n"
    + "	 a += {\n"
    + "	  total_block_rewards_sat: coalesce(previousTotals.total_block_rewards_sat, 0) + total_block_rewards_sat\n"
    + "	 }")
    void compute_total_block_rewards();
    
    @Query("MATCH \n"
    + "	 (a:BlockChainTotals)\n"
    + "WHERE\n"
    + "	 NOT exists(a.total_block_size)\n"
    + "WITH\n"
    + "	 a\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH \n"
    + "	 (b:Block {height: a.height})\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 b.size as block_size\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH\n"
    + "	 (previousTotals:BlockChainTotals {height: a.height-1})\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 block_size,\n"
    + "	 previousTotals\n"
    + "ORDER BY\n"
    + "	 a.height\n"
    + "SET\n"
    + "	 a += {\n"
    + "    total_block_size: coalesce(previousTotals.total_block_size, 0) + block_size\n"
    + "	 }")
    void compute_total_block_size();
    
    
    @Query("MATCH \n"
    + "  (a:BlockChainTotals)\n"
    + "WHERE \n"
    + "  NOT exists(a.total_fees_sat)\n"
    + "WITH\n"
    + "	 a\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH \n"
    + "	 (b:Block {height: a.height})<-[:INCLUDED_IN]-(tx:Transaction)\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 sum(tx.feesSat) as fees\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH\n"
    + "	 (previousTotals:BlockChainTotals {height: a.height-1})\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 fees,\n"
    + "	 previousTotals\n"
    + "ORDER BY\n"
    + "	 a.height\n"
    + "SET\n"
    + "	 a += {\n"
    + "	   total_fees_sat: coalesce(previousTotals.total_fees_sat, 0) + fees\n"
    + "	 }")
    void compute_total_fees();
    
    @Query("MATCH \n"
    + "	 (a:BlockChainTotals)\n"
    + "WHERE\n"
    + "	 NOT exists(a.total_output_volume_sat)\n"
    + "WITH\n"
    + "	 a\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH \n"
    + "	 (b:Block {height: a.height})<-[:INCLUDED_IN]-(:Transaction)-[:OUTPUT]->(output:TransactionOutput)\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 sum(output.valueSat) as total_output_volume_sat\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH\n"
    + "	 (previousTotals:BlockChainTotals {height: a.height-1})\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 total_output_volume_sat,\n"
    + "	 previousTotals\n"
    + "ORDER BY\n"
    + "	 a.height\n"
    + "SET\n"
    + "	 a += {\n"
    + "	   total_output_volume_sat: coalesce(previousTotals.total_output_volume_sat, 0) + total_output_volume_sat\n"
    + "	 }")
    void compute_total_output_volume();
    
    
    @Query("MATCH \n"
    + "	 (a:BlockChainTotals)\n"
    + "WHERE\n"
    + "	 NOT exists(a.total_transaction_size)\n"
    + "WITH\n"
    + "	 a\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH \n"
    + "	 (b:Block {height: a.height})<-[:INCLUDED_IN]-(tx:Transaction)\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 sum(tx.size) as total_transaction_size\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH\n"
    + "	 (previousTotals:BlockChainTotals {height: a.height-1})\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 total_transaction_size,\n"
    + "	 previousTotals\n"
    + "ORDER BY\n"
    + "	 a.height\n"
    + "SET\n"
    + "	 a += {\n"
    + "	   total_transaction_size: coalesce(previousTotals.total_transaction_size, 0) + total_transaction_size\n"
    + "	 }")
    void compute_total_transaction_size();
    
    
    @Query("MATCH \n"
    + "	 (a:BlockChainTotals)\n"
    + "WHERE\n"
    + "	 NOT exists(a.tx_count)\n"
    + "WITH\n"
    + "	 a\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH \n"
    + "	 (b:Block {height: a.height})<-[:INCLUDED_IN]-(tx:Transaction)\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 count(tx) as tx_count\n"
    + "ORDER BY \n"
    + "	 a.height\n"
    + "OPTIONAL MATCH\n"
    + "	 (previousTotals:BlockChainTotals {height: a.height-1})\n"
    + "WITH\n"
    + "	 a,\n"
    + "	 tx_count,\n"
    + "	 previousTotals\n"
    + "ORDER BY\n"
    + "	 a.height\n"
    + "SET\n"
    + "	 a += {\n"
    + "	   tx_count: coalesce(previousTotals.tx_count, 0) + tx_count\n"
    + "	 }")
    void compute_total_tx_count();
    
}
