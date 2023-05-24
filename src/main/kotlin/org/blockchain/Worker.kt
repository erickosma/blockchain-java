import org.blockchain.Block
import org.blockchain.Blockchain

object Worker {

    fun run() {
        val blockChain = Blockchain()
        generateNewBlocks(blockChain)
        generateNewBlocks(blockChain)
        generateNewBlocks(blockChain)
    }

    private fun generate(blockChain: Blockchain): Block {
        val previousBlock: Block = blockChain.getPreviousBlock()
        val previousProof = previousBlock.proof
        val proofWork = blockChain.proofOfWork(previousProof)
        val previousHash = blockChain.hash(previousBlock)
        return blockChain.createBlock(proofWork, previousHash)
    }

    private fun generateNewBlocks(blockChain: Blockchain){
        val block = generate(blockChain)
        println("--- New Block has been created --- ")
        println("Index:  ${block.index} ")
        println("Timestamp:  ${block.timestamp} ")
        println("Proof:  ${block.proof} ")
        println("Previous Hash:  ${block.previousHash} ")
        println("Valid:  ${blockChain.isValid()} ")
    }
}