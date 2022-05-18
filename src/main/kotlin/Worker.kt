object Worker {

    fun run() {
        val blockChain = Blockchain()
        generateAllBlocks(blockChain)
        generateAllBlocks(blockChain)
        generateAllBlocks(blockChain)
    }

    fun generate(blockChain: Blockchain): Block {
        val previousBlock: Block = blockChain.getPreviousBlock()
        val previousProof = previousBlock.proof
        val proofWork = blockChain.proofOfWork(previousProof)
        val previousHash = blockChain.hash(previousBlock)
        return blockChain.createBlock(proofWork, previousHash)
    }

    fun generateAllBlocks(blockChain: Blockchain){
        val block = generate(blockChain)
        println("--- New Block has been created --- ")
        println("Index:  ${block.index} ")
        println("Timestamp:  ${block.timestamp} ")
        println("Proof:  ${block.proof} ")
        println("Previous Hash:  ${block.previousHash} ")
        println("Valid:  ${blockChain.isValid()} ")
    }
}