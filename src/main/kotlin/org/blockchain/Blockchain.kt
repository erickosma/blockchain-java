package org.blockchain

import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets

class Blockchain(
    private val chain: MutableList<Block> = mutableListOf(),
    private val nonce: Int = 1 //difficulty
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
        return chain[getChainLength() - 1]
    }

    /**
     *  Responsável por encontrar uma prova de trabalho válida.
     * Ela recebe uma prova anterior e gera uma nova prova incrementando um valor até encontrar um
     * hash que atenda aos critérios definidos pela função subStrCount.
     * Essa função verifica se o hash tem um determinado número de zeros no início,
     * com base na dificuldade definida pelo nonce.
     *
     */
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
        return chain.size
    }

    /**
     *  verifica a validade da cadeia. Ela percorre a cadeia verificando se o hash do
     *  bloco anterior corresponde ao hash registrado no bloco atual e se a prova de trabalho é válida
     */
    fun isValid(): Boolean {
        var previousBlock = chain.first()
        var blockIndex = 1
        val chainLength = chain.size

        while (blockIndex < chainLength) {
            val block = chain[blockIndex]
            if (block.previousHash != hash(previousBlock)) {
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