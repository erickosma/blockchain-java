package org.blockchain

fun main() {
    Timer.stat()
    Worker.run()
    //WorkerParallel.run()
    Timer.end()
}