package org.blockchain

import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets

class Blockchain(
    private val chain: MutableList<Block> = mutableListOf(),
    private val nonce: Int = 4 //difficulty
) {

    init {
        createBlock(1, "0")
    }

    fun createBlock(proof: Long, previousHash: String): Block {
        val block = Block(index = getChainLength() + 1, proof = proof, previousHash = previousHash)
        chain.add(block)
        return block
    }

    fun getPreviousBlock(): Block {
        return chain[this.getChainLength() - 1]
    }

    fun proofOfWork(previousProof: Long): Long {
        var newProof = 1L
        var checkProof = false
        while (!checkProof) {
            val hashOperation = hashProve(newProof, previousProof)
            val str = subStrCount(hashOperation)
            if (str) {
                checkProof = true
            } else {
                newProof += 1
            }
        }
        return newProof
    }

    private fun subStrCount(hashOperation: String) =
        ("0".repeat(nonce)) == hashOperation.substring(0, nonce)

    fun hash(block: Block): String {
        return Hashing.sha256()
            .hashString(block.toJson(), StandardCharsets.UTF_8)
            .toString()
    }

    private fun hashProve(newProof: Long, previousProof: Long): String {
        val prove = calcProve(newProof, previousProof)
        return Hashing.sha256()
            .hashString(prove.toString(), StandardCharsets.UTF_8)
            .toString()
    }

    private fun calcProve(newProof: Long, previousProof: Long) =
        (newProof * 2) * 2 - (previousProof * 2) * 2

    private fun getChainLength(): Int {
        return this.chain.size
    }

    fun isValid(): Boolean {
        var previousBlock = chain.first()
        var blockIndex = 1
        val chainLength = chain.size

        while (blockIndex < chainLength) {
            val block = chain[blockIndex]
            if (block.previousHash != this.hash(previousBlock)) {
                return false
            }

            val previousProof = previousBlock.proof
            val proof = block.proof
            val hashOperation = hashProve(proof, previousProof)
            val str = subStrCount(hashOperation)
            if (!str) {
                return false
            }
            previousBlock = block
            blockIndex += 1
        }
        return true
    }
}