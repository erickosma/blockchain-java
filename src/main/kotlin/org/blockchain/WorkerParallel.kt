import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.blockchain.Blockchain

object WorkerParallel {

    fun run() {
        val blockChain = Blockchain()
        runBlocking {
            launch {
                Worker.generateNewBlocks(blockChain)
            }
            launch {
                Worker.generateNewBlocks(blockChain)
            }
            launch {
                Worker.generateNewBlocks(blockChain)
            }
            launch {
                Worker.generateNewBlocks(blockChain)
            }
        }
    }


}